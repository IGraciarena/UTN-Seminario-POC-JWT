package utn.poc.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import utn.poc.dto.UserDtoRequest;
import utn.poc.models.enums.Role;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
@Builder
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UserDtoRequest user) {
        this.id = null;
        this.name = user.getName();
        this.lastName = user.getLast_name();
        this.username = user.getUsername();
        this.pwd = user.getPwd();
        this.role = user.getRole();
    }
}
