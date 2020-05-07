package pl.ug.citycourier.internal.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserWithRoleDto extends UserDto {

    @NotNull
    @NotEmpty
    private int roleId;

    public UserWithRoleDto() {
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}