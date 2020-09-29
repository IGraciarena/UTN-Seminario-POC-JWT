package utn.poc.controllers.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.poc.controllers.UserController;
import utn.poc.dto.UserDtoRequest;
import utn.poc.models.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserWebController {

    private UserController userController;

    @Autowired
    UserWebController(UserController userController) {
        this.userController = userController;
    }

    //todo hacer un get para client, un post para employee y un delete para admin

    @PostMapping("/user")
    @ApiOperation(value = "Add a new User.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content."),
            @ApiResponse(code = 400, message = "Already Exists.")
    })
    public ResponseEntity addUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        return ResponseEntity.created(RestUtils.getLocationUser(userController.save(userDtoRequest))).build();
    }

    @GetMapping("/user")
    @ApiOperation(value = "Bring all the users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content.")
    })
    public ResponseEntity getAllUsers() {
        List<User> users = userController.getAll();
        return (users.size() > 0) ?
                ResponseEntity.ok(users) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}