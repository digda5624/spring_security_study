package com.security.demo.trace;

import com.security.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그에 대한 식별 값
 *
 * 현재는 User, request, level 에 따라서 로그가 식별된다.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TraceId {

    private User user;
    private String request;
    private int level;

    public boolean isFirstLevel(){
        return level == 0;
    }

    @Override
    public String toString() {
        return user.getId() + " 요청내용 : " + request;
    }
}
