package com.laoyin;

import java.io.Serializable;

public class MessageEntity implements Serializable {
    private String name;
    private Integer age;

    public MessageEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
