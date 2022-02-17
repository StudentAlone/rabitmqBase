package com.laoyin.cloud.Service;


import com.laoyin.cloud.dao.DeptDao;
import com.laoyin.cloud.entity.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.logging.Logger;

@Service
public class DeptServiceImpl implements DeptService {
    Logger logger = Logger.getLogger("DeptServiceImpl");
    @Autowired
    private DeptDao dao;

    @Override
    public boolean add(Dept dept) {
        return dao.addDept(dept);
    }


    @HystrixCommand(fallbackMethod = "getHystrix" ,commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="6000")
    })
    @Override
    public Dept get(Long id) {
        logger.info(String.valueOf(id));
        Dept byId=null;
    /*    try {
           Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        byId = dao.findById(id);
        if (null == byId) {
            throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
        }
        return byId;
    }

    @Override
    public List<Dept> list() {
        return dao.findAll();
    }

    public Dept getHystrix(Long id) {
        return new Dept("falfe","服务端接口超时或出错");
    }


    //=====服务熔断
    @HystrixCommand(fallbackMethod = "circuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public Dept getOne(Long id) {
        logger.info(String.valueOf(id));
        Dept byId=null;
    /*    try {
           Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        byId = dao.findById(id);
        if (null == byId) {
            throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
        }
        return byId;
    }
    public String circuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
