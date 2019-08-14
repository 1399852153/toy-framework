package com.xiongyx;

/**
 * @author xiongyx
 * on 2019/8/14.
 */
public class Library {

    private String id;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Library{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
