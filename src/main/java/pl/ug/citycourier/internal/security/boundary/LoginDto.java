package pl.ug.citycourier.internal.security.boundary;

import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    String username;
    @NotNull
    String password;

    public LoginDto() {
    }

    public LoginDto(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
