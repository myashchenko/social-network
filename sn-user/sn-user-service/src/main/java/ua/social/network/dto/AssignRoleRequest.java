package ua.social.network.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.social.network.validation.AdditionalGroup;

/**
 * @author Mykola Yashchenko
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {
    @NotEmpty(message = "SN-001")
    @Pattern(regexp = "USER|ADMIN|SUPER_ADMIN", groups = AdditionalGroup.class)
    private String role;

    @NotEmpty(message = "SN-001")
    private String userId;
}
