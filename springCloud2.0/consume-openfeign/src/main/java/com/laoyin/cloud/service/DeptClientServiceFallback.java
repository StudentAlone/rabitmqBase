package com.laoyin.cloud.service;

import com.laoyin.cloud.entity.Dept;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 2020新版实现 自己的接口 DeptClientService
 */
@Component
public class DeptClientServiceFallback implements DeptClientService{
    @Override
    public Dept get(long id) {
        return new Dept().setDeptno(id).setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
                .setDb_source("");
    }
    @Override
    public List<Dept> list() {
        return null;
    }
    @Override
    public boolean add(Dept dept) {
        return false;
    }
}
