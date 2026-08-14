/*
 * |-------------------------------------------------
 * | Copyright © 2017 Colin But. All rights reserved.
 * |-------------------------------------------------
 */
package com.mycompany.entapp.snowman.infrastructure.rest.mappers;

import com.mycompany.entapp.snowman.infrastructure.rest.resources.UserResource;
import com.mycompany.entapp.snowman.domain.model.User;

public final class UserResourceMapper {

    private UserResourceMapper() {
    }

    public static UserResource mapUserToUserResource(User user) {
        if (user == null) {
            return null;
        }
        UserResource userResource = new UserResource();
        userResource.setUserId(user.getUserId());
        userResource.setUsername(user.getUsername());
        userResource.setFirstName(user.getFirstname());
        userResource.setSecondName(user.getLastname());
        userResource.setEmail(user.getEmail());
        userResource.setPassword(user.getPassword());
        return userResource;
    }

    public static User mapUserResourceToUser(UserResource userResource) {
        if (userResource == null) {
            return null;
        }
        User user = new User();
        user.setUserId(userResource.getUserId());
        user.setUsername(userResource.getUsername());
        user.setFirstname(userResource.getFirstName());
        user.setLastname(userResource.getSecondName());
        user.setEmail(userResource.getEmail());
        user.setPassword(userResource.getPassword());
        return user;
    }
}
