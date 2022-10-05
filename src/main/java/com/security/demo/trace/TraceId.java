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

    public TraceId(User user, String request) {
        this.user = user;
        this.request = request;
        this.level = 0;
    }

    public boolean isFirstLevel(){
        return level == 0;
    }

    public TraceId levelUp(){
        level++;
        return this;
    }

    public TraceId levelDown(){
        level--;
        return this;
    }

    @Override
    public String toString() {
        return user.getId() + " 요청내용 : " + request;
    }

    public TraceId createNextId() {
        return new TraceId(user, request, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(user, request,level - 1);
    }

}
