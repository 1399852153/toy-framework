package com.xiongyx;

import java.sql.Date;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/19.
 */
public class User {

    private String id;

    private String userName;

    private Integer age;

    private Double money;

    private String __frch_item_0;

    private List<String> idListQuery;

    public String get__frch_item_0() {
        return __frch_item_0;
    }

    public void set__frch_item_0(String __frch_item_0) {
        this.__frch_item_0 = __frch_item_0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }


    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public List<String> getIdListQuery() {
        return idListQuery;
    }

    public void setIdListQuery(List<String> idListQuery) {
        this.idListQuery = idListQuery;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", idListQuery=" + idListQuery +
                '}';
    }
}
