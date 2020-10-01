package utn.poc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import utn.poc.dto.UserDtoRequest;
import utn.poc.exceptions.NotFoundException;
import utn.poc.models.User;
import utn.poc.services.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    public User save(UserDtoRequest userDtoRequest) {
        return userService.save(userDtoRequest);
    }

    public List<User> getAll() {
        return userService.getAll();
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.loadUserByUsername(username);
    }


    public void delete(Integer idUser) throws NotFoundException {
        userService.delete(idUser);
    }

}
