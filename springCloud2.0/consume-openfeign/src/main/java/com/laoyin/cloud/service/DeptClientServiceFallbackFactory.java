package com.laoyin.cloud.service;


import com.laoyin.cloud.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

// 不要忘记添加Component
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable throwable) {
        return new DeptClientService() {
            @Override
            public Dept get(long id) {
                return new Dept().setDeptno(id).setDname("该ID：" + id + "没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
                        .setDb_source("全局考虑，服务降级，服务停止了（停止部分服务器），会返回该消息");
            }
            @Override
            public List<Dept> list() {
                return null;
            }
            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
