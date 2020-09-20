package com.eason.openfeign.beans;

/**
 * @Auther: Eason
 * @Date: 2020/9/17 - 09 - 17 - 15:49
 * @Description: com.eason.openfeign
 * @version: 1.0
 */
public class User {
    private String id;
    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
