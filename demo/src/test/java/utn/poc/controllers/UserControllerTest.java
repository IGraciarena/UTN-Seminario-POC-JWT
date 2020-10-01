package utn.poc.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import utn.poc.dto.UserDtoRequest;
import utn.poc.exceptions.AlreadyExistsException;
import utn.poc.exceptions.NotFoundException;
import utn.poc.models.User;
import utn.poc.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static utn.poc.utils.Constants.USER_NOT_FOUND;

public class UserControllerTest {
    UserController userController;
    UserService userService;

    @Before
    public void setUp() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }
//***********************************************************************************************************************************
    @Test
    public void testSaveOk()throws AlreadyExistsException {
        User savedUsed = new User(1, "carlos", "lolo", "user", "owd", null);
        UserDtoRequest userDtoRequest = new UserDtoRequest("asd","asd","asd","asd",null);
        when(userService.save(userDtoRequest)).thenReturn(savedUsed);
        User returnedUser = userController.save(userDtoRequest);
        assertEquals(savedUsed.getId(), returnedUser.getId());
        assertEquals(savedUsed.getName(), returnedUser.getName());
        assertEquals(savedUsed.getLastName(), returnedUser.getLastName());
        assertEquals(savedUsed.getUsername(), returnedUser.getUsername());
        assertEquals(savedUsed.getPwd(), returnedUser.getPwd());
        assertEquals(savedUsed.getRole(), returnedUser.getRole());
        verify(userService, times(1)).save(userDtoRequest);
    }

    @Test(expected = AlreadyExistsException.class)
    public void testSaveAlreadyExists()throws AlreadyExistsException {
        UserDtoRequest userDtoRequest = new UserDtoRequest("asd","asd","asd","asd",null);
        when(userService.save(userDtoRequest)).thenThrow(new AlreadyExistsException(""));
        userController.save(userDtoRequest);
    }
//***********************************************************************************************************************************
    @Test
    public void testGetAllOk() {
        List<User> list = new ArrayList<>();
        User user = new User(1, "carlos", "lolo", "user", "owd", null);
        list.add(user);
        when(userService.getAll()).thenReturn(list);
        List<User> returnedList = userController.getAll();
        assertEquals(returnedList.size(), list.size());
        verify(userService, times(1)).getAll();
    }
//***********************************************************************************************************************************
    @Test
    public void TestLoadUserByUsername() throws UsernameNotFoundException{
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
        when(userService.loadUserByUsername("username")).thenReturn(userDetails);
        UserDetails userDetails1 = userController.loadUserByUsername("username");
        assertEquals(userDetails, userDetails1);

        verify(userService, times(1)).loadUserByUsername("username");
    }
    @Test(expected = UsernameNotFoundException.class)
    public void TestLoadUserByUsernameException() throws UsernameNotFoundException {
        when(userService.loadUserByUsername("username")).thenThrow(new UsernameNotFoundException(""));
        userController.loadUserByUsername("username");
    }
//**********************************************************************************************************************
    @Test
    public void testRemoveUserOk() throws NotFoundException {
        doNothing().when(userService).delete(1);
        userController.delete(1);
        verify(userService, times(1)).delete(1);
    }

    @Test(expected = NotFoundException.class)
    public void testRemoveUserUserNotFoundException() throws NotFoundException {
        doThrow(new NotFoundException(USER_NOT_FOUND)).when(userService).delete(null);
        userController.delete(null);
    }
}
