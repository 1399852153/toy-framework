package com.xiongyx;

import java.sql.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author xiongyx
 * on 2019/6/19.
 */
public class User {

    private String id;

    private String userName;

    private Integer age;

    private Double money;

    private List<String> idListQuery;

    private List<Book> bookList;

    private Account account;

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

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", userName='" + userName + '\'' +
            ", age=" + age +
            ", money=" + money +
            ", idListQuery=" + idListQuery + '\n' +
            ", bookList=" + bookList + '\n' +
            ", account=" + account + '\n' +
            '}';
    }
}
