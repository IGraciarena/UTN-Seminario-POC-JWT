package utn.poc.controllers.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import utn.poc.controllers.UserController;
import utn.poc.dto.UserDtoRequest;
import utn.poc.dto.UserDtoResponse;
import utn.poc.exceptions.AlreadyExistsException;
import utn.poc.exceptions.NotFoundException;
import utn.poc.models.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static utn.poc.utils.Constants.USER_NOT_FOUND;

public class UserWebControllerTest {

    UserWebController userWebController;
    @Mock
    UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        userWebController = new UserWebController( userController);
    }


    @Test
    public void testGetAllUsersNoContent() {
        List<User> userList = new ArrayList<>();

        when(userController.getAll()).thenReturn(userList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<List<UserDtoResponse>> response = userWebController.getAllUsers();

//        assertEquals(responseRta, response);
        verify(userController, times(1)).getAll();
    }

    //********************************************************getAll*************************************************************************************

    @Test
    public void testGetAllUsersOk() {
        List<User> list = new ArrayList<>();
        User user = new User(1, "carlos", "lolo", "user", "owd", null);
        list.add(user);
        List<UserDtoResponse> userList = new ArrayList<>();
        User u2 = new User(1, "carlos", "lolo", "user", "owd", null);
        UserDtoResponse dto = new UserDtoResponse(u2);
        userList.add(dto);
        when(userController.getAll()).thenReturn(list);
        ResponseEntity responseRta = ResponseEntity.ok(userList);
        ResponseEntity<List<UserDtoResponse>> response = userWebController.getAllUsers();
        //assertEquals(responseRta, response);
        verify(userController, times(1)).getAll();
    }
//******************************************************delete*************************************************************************************

    @Test
    public void testDeleteOk() throws NotFoundException {
        doNothing().when(userController).delete(1);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.OK).build();
        ResponseEntity response = userWebController.deleteUser( 1);
        assertEquals(responseRta, response);
        verify(userController, times(1)).delete(1);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws NotFoundException {
        doThrow(new NotFoundException(USER_NOT_FOUND)).when(userController).delete(1);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        ResponseEntity response = userWebController.deleteUser( 1);
        assertEquals(responseRta, response);
        verify(userController, times(1)).delete(1);
    }
}
