package ir.maktabsharif.achareh.dto.user;

import ir.maktabsharif.achareh.enums.RoleUserEnum;
import ir.maktabsharif.achareh.enums.StatusUserEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private StatusUserEnum status;
    private RoleUserEnum role;
    private String sub_duty_name;
    private String duty_name;

}
