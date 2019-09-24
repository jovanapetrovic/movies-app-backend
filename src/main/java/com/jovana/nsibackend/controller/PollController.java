package com.jovana.nsibackend.controller;

import com.jovana.nsibackend.model.*;
import com.jovana.nsibackend.resource.*;
import com.jovana.nsibackend.security.CurrentUser;
import com.jovana.nsibackend.security.UserPrincipal;
import com.jovana.nsibackend.service.PollService;
import com.jovana.nsibackend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

/**
 * Created by jovana on 05.11.2018
 */
@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping
    public PagedResponse<PollResponse> getPolls(@CurrentUser UserPrincipal currentUser,
                                                @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> createPoll(@Valid @RequestBody PollRequest pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pollId}").buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Poll created successfully!"));
    }

    @GetMapping("/{pollId}")
    public PollResponse getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId) {
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponse submitVote(@CurrentUser UserPrincipal currentUser, @PathVariable Long pollId, @Valid @RequestBody VoteRequest voteRequest) {
        return pollService.submitVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }

}
