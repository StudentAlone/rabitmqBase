package com.laoyin.controller;

import com.laoyin.provider.MyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/rabbitmq")
public class RabbitMQController {
    @Autowired
    MyProvider myProvider;

    @RequestMapping("/sendMsg")
    public void SendMsg() {
        myProvider.send();
    }

    @RequestMapping("/test")
    public void test() {
        System.out.println("dfasdf");
    }
}
