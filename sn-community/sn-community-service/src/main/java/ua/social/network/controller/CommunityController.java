package ua.social.network.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.dto.CreateCommunityRequest;
import ua.social.network.dto.ModifyCommunityRequest;
import ua.social.network.entity.Community;
import ua.social.network.exception.AccessDeniedException;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.repository.CommunityRepository;

/**
 * @author Mykola Yashchenko
 */
@RestController(value = "communities")
@AllArgsConstructor
public class CommunityController {

    private final CommunityRepository communityRepository;

    @PreAuthorize("#oauth2.hasScope('server')")
    @PostMapping
    public void createCommunity(Principal principal,
                                @Valid @RequestBody CreateCommunityRequest createCommunityRequest) {
        // todo use mapper
        Community community = new Community();
        community.setName(createCommunityRequest.getName());
        community.setUserId(principal.getName());

        communityRepository.save(community);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @PutMapping(value = "/{id}")
    public void modifyCommunity(Principal principal, @PathVariable("id") String id,
                                @Valid @RequestBody ModifyCommunityRequest communityRequest) {
        // todo use mapper
        Community community = communityRepository.findOne(id);
        if (community == null) {
            throw new EntityNotFoundException("Community with id=%s not found", id);
        }

        if (!community.getUserId().equals(principal.getName())) {
            throw new AccessDeniedException();
        }

        community.setName(communityRequest.getName());

        communityRepository.save(community);
    }
}
