package com.laoyin.cloud.controller;


import com.laoyin.cloud.entity.Dept;
import com.laoyin.cloud.service.DeptClientService;
import com.laoyin.cloud.service.DeptClientServiceFallbackFactory;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@DefaultProperties(defaultFallback = "getDefaultHystrix")
public class DeptController_Consumer {
    //@Autowired
    @Resource
    DeptClientService deptClientService;
    //@Autowired
   // DeptClientServiceFallbackFactory deptClientServiceFallbackFactory;

    @RequestMapping(value = "/consumer/dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept) {
        return deptClientService.add(dept);
    }
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="6000")
    })
    /* @HystrixCommand*/
    @RequestMapping(value = "/consumer/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id) {
       /* try {
            Thread.sleep(7000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/
        return deptClientService.get(id);
    }
    @RequestMapping(value = "/consumer/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return deptClientService.list();
    }

    public Dept getHystrix(Long id) {
        return new Dept("falfe","客户端接口超时或出错");
    }

    public Dept getDefaultHystrix() {
        return new Dept("falfe","default服务端接口超时或出错");
    }

}
