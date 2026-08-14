/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.endpoint;

import com.mycompany.entapp.snowman.domain.model.User;
import com.mycompany.entapp.snowman.domain.service.UserService;
import com.mycompany.entapp.snowman.infrastructure.rest.mappers.UserResourceMapper;
import com.mycompany.entapp.snowman.infrastructure.rest.resources.UserResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UserRestEndpointUTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestEndpoint classInTest = new UserRestEndpoint();

    @Test
    public void testGetUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("Username");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setEmail("Email");

        UserResource userResource = new UserResource();
        userResource.setUserId(1);
        userResource.setUsername("Username");
        userResource.setFirstName("Firstname");
        userResource.setSecondName("Lastname");
        userResource.setEmail("Email");

        Mockito.when(userService.findUser("1")).thenReturn(user);

        try (MockedStatic<UserResourceMapper> mocked = Mockito.mockStatic(UserResourceMapper.class)) {
            mocked.when(() -> UserResourceMapper.mapUserToUserResource(user)).thenReturn(userResource);

            ResponseEntity<UserResource> responseEntity = classInTest.getUser("1");

            assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
            assertEquals(userResource, responseEntity.getBody());
        }
    }

    @Test
    public void testCreateNewUser() {
        UserResource userResource = new UserResource();
        userResource.setUserId(1);
        userResource.setUsername("Username");
        userResource.setFirstName("Firstname");
        userResource.setSecondName("Lastname");
        userResource.setEmail("Email");

        User user = new User();
        user.setUserId(1);
        user.setUsername("Username");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setEmail("Email");

        try (MockedStatic<UserResourceMapper> mocked = Mockito.mockStatic(UserResourceMapper.class)) {
            mocked.when(() -> UserResourceMapper.mapUserResourceToUser(userResource)).thenReturn(user);
            Mockito.doNothing().when(userService).createUser(user);

            classInTest.createNewUser(userResource);

            Mockito.verify(userService, Mockito.times(1)).createUser(user);
        }
    }

    @Test
    public void testUpdateExistingUser() {
        UserResource userResource = new UserResource();
        userResource.setUserId(1);
        userResource.setUsername("Username");
        userResource.setFirstName("Firstname");
        userResource.setSecondName("Lastname");
        userResource.setEmail("Email");

        User user = new User();
        user.setUserId(1);
        user.setUsername("Username");
        user.setFirstname("Firstname");
        user.setLastname("Lastname");
        user.setEmail("Email");

        try (MockedStatic<UserResourceMapper> mocked = Mockito.mockStatic(UserResourceMapper.class)) {
            mocked.when(() -> UserResourceMapper.mapUserResourceToUser(userResource)).thenReturn(user);
            Mockito.doNothing().when(userService).updateUser(user);

            classInTest.updateExistingUser(userResource);

            Mockito.verify(userService, Mockito.times(1)).updateUser(user);
        }
    }

    @Test
    public void testDeleteUser() {
        Mockito.doNothing().when(userService).deleteUser(1);

        classInTest.deleteUser(1);

        Mockito.verify(userService, Mockito.times(1)).deleteUser(1);
    }
}
