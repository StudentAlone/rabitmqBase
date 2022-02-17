package com.laoyin.cloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class ConfigBean {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 如果Yaml文件中配置了，这里注意不要配置重复了
        // 当然这里配置的优先级更高
        routes.route("selectData",
                r -> r.path("/dept/get/**")
                        .and()
                        // 明天之前，都可访问
                        .before(ZonedDateTime.now().plusDays(1))
                        // filters 在downstream 即下游=服务提供端，获取信息
                        .filters(f -> f.addRequestHeader("X-Request-red", "blue"))
                        .uri("lb://CLOUD-PROVIDE")
        ).build();
        return routes.build();
    }



}
