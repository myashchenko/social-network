package ua.social.network.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.dto.AssignRoleRequest;
import ua.social.network.service.RoleService;
import ua.social.network.validation.ValidationOrder;

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