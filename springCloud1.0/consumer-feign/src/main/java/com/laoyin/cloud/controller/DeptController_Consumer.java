package com.laoyin.cloud.controller;

import org.laoyin.entity.Dept;
import org.laoyin.service.DeptClientService;
import org.laoyin.service.DeptClientServiceFallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeptController_Consumer {
    @Autowired
    DeptClientService deptClientService;
    @Autowired
    DeptClientServiceFallbackFactory deptClientServiceFallbackFactory;
    @RequestMapping(value = "/consumer/dept/add", method = RequestMethod.POST)
    public boolean add(@RequestBody Dept dept) {
        return deptClientService.add(dept);
    }

    @RequestMapping(value = "/consumer/dept/get/{id}", method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id) {
        return deptClientService.get(id);
    }
    @RequestMapping(value = "/consumer/dept/list", method = RequestMethod.GET)
    public List<Dept> list() {
        return deptClientService.list();
    }
}
