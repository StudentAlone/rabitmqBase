package org.laoyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class DeptHystrixDashBorad_App {
    public static void main(String[] args) {
        SpringApplication.run(DeptHystrixDashBorad_App.class, args);
    }
}
