package test.demo.model;

import java.util.Date;
import java.util.List;

/**
 * @author xiongyx
 * on 2019/6/19.
 */
public class User {

    private String id;

    private String userName;

    private int age;

    private double money;

    private List<String> idListQuery;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
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
