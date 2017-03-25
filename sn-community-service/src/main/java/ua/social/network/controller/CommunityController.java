package ua.social.network.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.social.network.dto.CreateCommunityRequest;
import ua.social.network.dto.ModifyCommunityRequest;
import ua.social.network.entity.Community;
import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.repository.CommunityRepository;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Mykola Yashchenko
 */
@RestController(value = "communities")
@AllArgsConstructor(onConstructor = @__(@Autowired))
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
            throw new RuntimeException("Forbidden");
        }

        community.setName(communityRequest.getName());

        communityRepository.save(community);
    }
}
