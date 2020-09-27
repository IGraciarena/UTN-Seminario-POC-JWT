package utn.poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import utn.poc.models.enums.Role;

@AllArgsConstructor
@Data
public class UserDtoRequest {
    String username;
    String pwd;
    String name;
    String last_name;
    Role role;
}
