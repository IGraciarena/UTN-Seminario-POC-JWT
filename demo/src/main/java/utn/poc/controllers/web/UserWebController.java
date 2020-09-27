package utn.poc.controllers.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utn.poc.controllers.UserController;
import utn.poc.dto.UserDtoRequest;
import utn.poc.exceptions.AlreadyExistsException;
import utn.poc.models.User;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserWebController {

    private UserController userController;

    @Autowired
    UserWebController(UserController userController) {
        this.userController = userController;
    }

    @PostMapping("/user")
    @ApiOperation(value = "Add a new User.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content."),
            @ApiResponse(code = 400, message = "Already Exists.")
    })
    public ResponseEntity addUser(@RequestBody UserDtoRequest userDtoRequest, @RequestHeader("Authorization") String token) throws AlreadyExistsException {
        return ResponseEntity.created(RestUtils.getLocationUser(userController.save(userDtoRequest))).build();
    }

    @GetMapping("/user")
    @ApiOperation(value = "Bring all the users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content.")
    })
    public ResponseEntity getAllUsers(@RequestHeader("Authorization") String token) {
        List<User> users = userController.getAll();
        return (users.size() > 0) ?
                ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}