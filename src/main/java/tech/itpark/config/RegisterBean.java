package tech.itpark.config;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.itpark.bpp.HandlerMappingBeanPostProcessor;
import tech.itpark.handler.RouteMapping;

//@Configuration
public class RegisterBean {

    @Bean
    RouteMapping routeMapping() {
        return new RouteMapping();
    }

    @Bean
    BeanPostProcessor handlerMappingBeanPostProcessor() {
        return new HandlerMappingBeanPostProcessor(routeMapping());
    }
}
