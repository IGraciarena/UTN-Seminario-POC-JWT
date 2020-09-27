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
import utn.poc.models.User;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserWebControllerTest {

    UserWebController userWebController;
    @Mock
    UserController userController;

    @Before
    public void setUp() {
        initMocks(this);
        userWebController = new UserWebController( userController);
    }



    //********************************************************getAll*************************************************************************************
    @Test
    public void testGetAllUsersOk() {
        List<User> userList = new ArrayList<>();
        User u1 = new User(1, "carlos", "lolo", "user", "owd", null);
        User u2 = new User(1, "carlos", "lolo", "user", "owd", null);
        userList.add(u1);
        userList.add(u2);
        when(userController.getAll()).thenReturn(userList);
        ResponseEntity responseRta = ResponseEntity.ok(userList);
        ResponseEntity<List<User>> response = userWebController.getAllUsers("234");
        assertEquals(responseRta, response);
        verify(userController, times(1)).getAll();
    }

    @Test
    public void testGetAllUsersNoContent() {
        List<User> userList = new ArrayList<>();

        when(userController.getAll()).thenReturn(userList);
        ResponseEntity responseRta = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        ResponseEntity<List<User>> response = userWebController.getAllUsers("234");

        assertEquals(responseRta, response);
        verify(userController, times(1)).getAll();
    }

}
