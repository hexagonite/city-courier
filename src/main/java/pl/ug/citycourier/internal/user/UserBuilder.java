package pl.ug.citycourier.internal.user;

import pl.ug.citycourier.internal.security.entity.Role;

public final class UserBuilder {
    private long id;
    private String username;
    private String email;
    private String name;
    private String secondName;
    private String password;
    private String surname;
    private Role role;
    private boolean isAvailable;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    public UserBuilder withAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setName(name);
        user.setSecondName(secondName);
        user.setPassword(password);
        user.setSurname(surname);
        user.setRole(role);
        user.setAvailable(isAvailable);
        return user;
    }
}
