package ua.social.network.controller;

import java.security.Principal;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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
import ua.social.network.exception.SnException;
import ua.social.network.exception.SnExceptionDetails;
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
    public void createCommunity(Principal principal, @Valid @RequestBody CreateCommunityRequest createCommunityRequest) {

        var community = new Community();
        community.setName(createCommunityRequest.getName());
        community.setDescription(createCommunityRequest.getDescription());
        community.setUserId(principal.getName());

        communityRepository.save(community);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @PutMapping(value = "/{id}")
    public void modifyCommunity(Principal principal, @PathVariable("id") String id,
                                @Valid @RequestBody ModifyCommunityRequest communityRequest) {

        var community = communityRepository.findById(id);
        if (!community.isPresent()) {
            throw new SnException(new SnExceptionDetails() {
                @Override
                public String code() {
                    return null;
                }

                @Override
                public HttpStatus status() {
                    return HttpStatus.NOT_FOUND;
                }
            });
        }

        community = community.filter(c -> c.getUserId().equals(principal.getName()));
        if (!community.isPresent()) {
            throw new SnException(new SnExceptionDetails() {
                @Override
                public String code() {
                    return null;
                }

                @Override
                public HttpStatus status() {
                    return HttpStatus.NOT_FOUND;
                }
            });
        }

        community
                .map(c -> {
                    c.setName(communityRequest.getName());
                    c.setDescription(communityRequest.getDescription());
                    return c;
                })
                .map(communityRepository::save);
    }
}
