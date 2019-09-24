package com.jovana.nsibackend.util;

import com.jovana.nsibackend.model.Poll;
import com.jovana.nsibackend.model.User;
import com.jovana.nsibackend.resource.OptionResponse;
import com.jovana.nsibackend.resource.PollResponse;
import com.jovana.nsibackend.resource.UserInfo;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by jovana on 05.11.2018
 */
public class ModelMapper {

    private ModelMapper() {}

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long, Long> optionVotes, User creator, Long userVote) {
        PollResponse pollResponse = new PollResponse();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setDateCreated(poll.getDateCreated());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setExpired(poll.getExpirationDateTime().isBefore(now));

        List<OptionResponse> optionResponses = poll.getOptions().stream().map(option -> {
            OptionResponse optionResponse = new OptionResponse();
            optionResponse.setId(option.getId());
            optionResponse.setText(option.getText());

            if (optionVotes.containsKey(option.getId())) {
                optionResponse.setVoteCount(optionVotes.get(option.getId()));
            } else {
                optionResponse.setVoteCount(0);
            }
            return optionResponse;
        }).collect(Collectors.toList());

        pollResponse.setOptions(optionResponses);
        UserInfo creatorSummary = new UserInfo(creator.getId(), creator.getUsername(), creator.getName());
        pollResponse.setCreatedBy(creatorSummary);

        if (userVote != null) {
            pollResponse.setSelectedOption(userVote);
        }

        long totalVotes = pollResponse.getOptions().stream().mapToLong(OptionResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }

}
