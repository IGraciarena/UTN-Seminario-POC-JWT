package utn.poc.controllers.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.poc.models.User;

import java.net.URI;

public class RestUtils {

    public static URI getLocationUser(User user) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
    }
}
