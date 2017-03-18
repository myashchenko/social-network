package ua.social.network.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Mykola Yashchenko
 */
@RestController(value = "communities")
public class CommunityController {

    @PreAuthorize("#oauth2.hasScope('server')")
    @PostMapping
    public void createCommunity(Principal principal, @Valid @RequestBody Object communityRequest) {

    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @PutMapping
    public void modifyCommunity(Principal principal, @Valid @RequestBody Object communityRequest) {

    }
}
