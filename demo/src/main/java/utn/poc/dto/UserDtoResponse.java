package utn.poc.dto;

import utn.poc.models.User;

public class UserDtoResponse {
    private String username;

    private String name;

    private String lastName;

    public UserDtoResponse(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
    }
}
