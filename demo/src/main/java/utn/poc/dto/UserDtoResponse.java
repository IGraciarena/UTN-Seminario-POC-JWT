package utn.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import utn.poc.models.User;

@AllArgsConstructor
@Data
public class UserDtoResponse {

    private Integer id;

    private String username;

    private String name;

    private String lastName;


    public UserDtoResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.lastName = user.getLastName();
    }
}
