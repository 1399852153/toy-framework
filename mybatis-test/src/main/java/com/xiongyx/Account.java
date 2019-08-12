package com.xiongyx;

import java.util.StringJoiner;

/**
 * @author xiongyx
 * on 2019/8/4.
 */
public class Account {

    private String id;
    private String userId;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]").add("id='" + id + "'")
            .add("userId='" + userId + "'")
            .add("password='" + password + "'")
            .toString();
    }
}
