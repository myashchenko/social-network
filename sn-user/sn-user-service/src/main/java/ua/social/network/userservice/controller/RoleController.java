package ua.social.network.userservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.userservice.dto.AssignRoleRequest;
import ua.social.network.userservice.service.RoleService;
import ua.social.network.userservice.validation.ValidationOrder;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PutMapping
    @PreAuthorize("#oauth2.hasScope('ui')")
    public void assignRole(@Validated(ValidationOrder.class) @RequestBody final AssignRoleRequest request) {
        roleService.assign(request);
    }

}