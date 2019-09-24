package com.jovana.nsibackend.controller;

import com.jovana.nsibackend.exception.ResourceNotFoundException;
import com.jovana.nsibackend.model.User;
import com.jovana.nsibackend.resource.*;
import com.jovana.nsibackend.repository.PollRepository;
import com.jovana.nsibackend.repository.UserRepository;
import com.jovana.nsibackend.repository.VoteRepository;
import com.jovana.nsibackend.security.UserPrincipal;
import com.jovana.nsibackend.service.PollService;
import com.jovana.nsibackend.security.CurrentUser;
import com.jovana.nsibackend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jovana on 05.11.2018
 */
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PollService pollService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserInfo getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserInfo(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    }

    @GetMapping("/user/checkIfAvailableUsername")
    public UsernameOrEmailAvailability checkIfAvailableUsername(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UsernameOrEmailAvailability(isAvailable);
    }

    @GetMapping("/user/checkIfAvailableEmail")
    public UsernameOrEmailAvailability checkIfAvailableEmail(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UsernameOrEmailAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfileResponse getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        return new UserProfileResponse(user.getId(), user.getUsername(), user.getName(), user.getDateCreated(), pollCount, voteCount);
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponse<PollResponse> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                         @CurrentUser UserPrincipal currentUser,
                                                         @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                                         @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponse<PollResponse> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = Constants.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
