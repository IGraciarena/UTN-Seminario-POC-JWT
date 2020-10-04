package utn.poc.controllers.web;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utn.poc.controllers.UserController;
import utn.poc.dto.UserDtoRequest;
import utn.poc.dto.UserDtoResponse;
import utn.poc.exceptions.NotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserWebController {

    private UserController userController;

    @Autowired
    UserWebController(UserController userController) {
        this.userController = userController;
    }

    @GetMapping("/client")
    @ApiOperation(value = "Bring all the users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content.")
    })
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> users = userController.getAll()
                .stream()
                .map(UserDtoResponse::new)
                .collect(Collectors.toList());
        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(users) : ResponseEntity.ok(users);
    }

    @PostMapping("/employee")
    @ApiOperation(value = "Add a new User.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 204, message = "No Content."),
            @ApiResponse(code = 400, message = "Already Exists.")
    })
    public ResponseEntity addUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        return ResponseEntity.created(RestUtils.getLocationUser(userController.save(userDtoRequest))).build();
    }

    @ApiOperation(value = "Delete an existing user.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 404, message = "User not found.")
    })
    @DeleteMapping("/admin/{idUser}")
    public ResponseEntity deleteUser(@PathVariable Integer idUser) throws NotFoundException {
        userController.delete(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}