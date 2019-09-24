package com.jovana.nsibackend.service;

import com.jovana.nsibackend.exception.BadRequestException;
import com.jovana.nsibackend.exception.ResourceNotFoundException;
import com.jovana.nsibackend.model.*;
import com.jovana.nsibackend.repository.PollRepository;
import com.jovana.nsibackend.repository.UserRepository;
import com.jovana.nsibackend.repository.VoteRepository;
import com.jovana.nsibackend.resource.PagedResponse;
import com.jovana.nsibackend.resource.PollRequest;
import com.jovana.nsibackend.resource.PollResponse;
import com.jovana.nsibackend.resource.VoteRequest;
import com.jovana.nsibackend.security.UserPrincipal;
import com.jovana.nsibackend.util.Constants;
import com.jovana.nsibackend.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by jovana on 05.11.2018
 */
@Service
public class PollService {

    public static final String DATE_CREATED = "dateCreated";

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(PollService.class);

    public PagedResponse<PollResponse> getAllPolls(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, DATE_CREATED);
        Page<Poll> polls = pollRepository.findAll(pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> optionVotesMap = getOptionVotes(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getPollCreatorMap(polls.getContent());

        List<PollResponse> pollResponses = polls.map(poll -> ModelMapper.mapPollToPollResponse(
                poll,
                optionVotesMap,
                creatorMap.get(poll.getCreatedBy()),
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null)
        )).getContent();

        return new PagedResponse<>(pollResponses, polls.getNumber(), polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponse<PollResponse> getPollsCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, DATE_CREATED);
        Page<Poll> polls = pollRepository.findByCreatedBy(user.getId(), pageable);

        if (polls.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), polls.getNumber(),
                    polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
        }

        List<Long> pollIds = polls.map(Poll::getId).getContent();
        Map<Long, Long> optionVotesMap = getOptionVotes(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);

        List<PollResponse> pollResponses = polls.map(poll -> ModelMapper.mapPollToPollResponse(
                poll,
                optionVotesMap,
                user,
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null)
        )).getContent();

        return new PagedResponse<>(pollResponses, polls.getNumber(), polls.getSize(), polls.getTotalElements(), polls.getTotalPages(), polls.isLast());
    }

    public PagedResponse<PollResponse> getPollsVotedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, DATE_CREATED);
        Page<Long> userVotedPollIds = voteRepository.findVotedPollIdsByUserId(user.getId(), pageable);

        if (userVotedPollIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userVotedPollIds.getNumber(),
                    userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(),
                    userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
        }

        List<Long> pollIds = userVotedPollIds.getContent();

        Sort sort = new Sort(Sort.Direction.DESC, DATE_CREATED);
        List<Poll> polls = pollRepository.findByIdIn(pollIds, sort);

        Map<Long, Long> optionVotesMap = getOptionVotes(pollIds);
        Map<Long, Long> pollUserVoteMap = getPollUserVoteMap(currentUser, pollIds);
        Map<Long, User> creatorMap = getPollCreatorMap(polls);

        List<PollResponse> pollResponses = polls.stream().map(poll -> ModelMapper.mapPollToPollResponse(
                poll,
                optionVotesMap,
                creatorMap.get(poll.getCreatedBy()),
                pollUserVoteMap == null ? null : pollUserVoteMap.getOrDefault(poll.getId(), null)
        )).collect(Collectors.toList());

        return new PagedResponse<>(pollResponses, userVotedPollIds.getNumber(), userVotedPollIds.getSize(), userVotedPollIds.getTotalElements(), userVotedPollIds.getTotalPages(), userVotedPollIds.isLast());
    }


    public Poll createPoll(PollRequest pollRequest) {
        Poll poll = new Poll();
        poll.setQuestion(pollRequest.getQuestion());

        pollRequest.getOptions().forEach(req -> poll.addOption(new Option(req.getText())));

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(pollRequest.getPollDuration().getDays()))
                .plus(Duration.ofHours(pollRequest.getPollDuration().getHours()));
        poll.setExpirationDateTime(expirationDateTime);

        return pollRepository.save(poll);
    }

    public PollResponse getPollById(Long pollId, UserPrincipal currentUser) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        List<OptionVotes> votes = voteRepository.countByPollIdGroupByOptionId(pollId);

        Map<Long, Long> optionVotes = votes.stream()
                .collect(Collectors.toMap(OptionVotes::getOptionId, OptionVotes::getVoteCount));

        User creator = userRepository.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

        Vote userVote = null;
        if (currentUser != null) {
            userVote = voteRepository.findByUserIdAndPollId(currentUser.getId(), pollId);
        }

        return ModelMapper.mapPollToPollResponse(poll, optionVotes,
                creator, userVote != null ? userVote.getOption().getId() : null);
    }

    public PollResponse submitVoteAndGetUpdatedPoll(Long pollId, VoteRequest voteRequest, UserPrincipal currentUser) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new ResourceNotFoundException("Poll", "id", pollId));

        if (poll.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException("This poll has already expired!");
        }

        User user = userRepository.getOne(currentUser.getId());

        Option selectedOption = poll.getOptions().stream()
                .filter(option -> option.getId().equals(voteRequest.getOptionId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Option", "id", voteRequest.getOptionId()));

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setOption(selectedOption);

        try {
            vote = voteRepository.save(vote);
        } catch (DataIntegrityViolationException ex) {
            log.info("User {} has already voted in poll {}", currentUser.getId(), pollId);
            throw new BadRequestException("You already voted in this poll!");
        }

        List<OptionVotes> votes = voteRepository.countByPollIdGroupByOptionId(pollId);

        Map<Long, Long> optionVotes = votes.stream()
                .collect(Collectors.toMap(OptionVotes::getOptionId, OptionVotes::getVoteCount));

        User creator = userRepository.findById(poll.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", poll.getCreatedBy()));

        return ModelMapper.mapPollToPollResponse(poll, optionVotes, creator, vote.getOption().getId());
    }


    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size > Constants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size cannot be greater than " + Constants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getOptionVotes(List<Long> pollIds) {
        List<OptionVotes> votes = voteRepository.countByPollIdInGroupByOptionId(pollIds);
        return votes.stream().collect(Collectors.toMap(OptionVotes::getOptionId, OptionVotes::getVoteCount));
    }

    private Map<Long, Long> getPollUserVoteMap(UserPrincipal currentUser, List<Long> pollIds) {
        Map<Long, Long> pollUserVoteMap = null;
        if (currentUser != null) {
            List<Vote> userVotes = voteRepository.findByUserIdAndPollIdIn(currentUser.getId(), pollIds);

            pollUserVoteMap = userVotes.stream()
                    .collect(Collectors.toMap(vote -> vote.getPoll().getId(), vote -> vote.getOption().getId()));
        }
        return pollUserVoteMap;
    }

    private Map<Long, User> getPollCreatorMap(List<Poll> polls) {
        List<Long> creatorIds = polls.stream().map(Poll::getCreatedBy).distinct().collect(Collectors.toList());
        List<User> creators = userRepository.findByIdIn(creatorIds);
        return creators.stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

}
