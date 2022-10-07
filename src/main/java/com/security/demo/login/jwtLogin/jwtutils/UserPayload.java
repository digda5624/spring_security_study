package com.security.demo.login.jwtLogin.jwtutils;

import com.security.demo.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPayload {

    private Long userId;
    private String userName;
    private Role userRole;

    public static UserPayload createUserPayload(Long userId, String userName, Role userRole){
        return new UserPayload(userId, userName, userRole);
    }
}
