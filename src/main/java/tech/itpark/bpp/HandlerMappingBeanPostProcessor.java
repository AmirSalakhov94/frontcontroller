package tech.itpark.bpp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import tech.itpark.annotation.GetMapping;
import tech.itpark.annotation.PostMapping;
import tech.itpark.handler.RouteMapping;
import tech.itpark.http.enums.HttpMethod;
import tech.itpark.model.BeanMethod;

import java.util.Arrays;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class HandlerMappingBeanPostProcessor implements BeanPostProcessor {
    private final RouteMapping routeMapping;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        final var clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(Controller.class)) {
            return bean;
        }

        Arrays.stream(clazz.getMethods())
                .forEach(method -> {
                    if (method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping getMapping = method.getAnnotation(GetMapping.class);
                        BeanMethod beanMethod = BeanMethod.builder()
                                .bean(bean)
                                .method(method)
                                .build();
                        routeMapping.registerRoute(getMapping.value(), Map.of(HttpMethod.GET, beanMethod));
                    } else if (method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping postMapping = method.getAnnotation(PostMapping.class);
                        BeanMethod beanMethod = BeanMethod.builder()
                                .bean(bean)
                                .method(method)
                                .build();
                        routeMapping.registerRoute(postMapping.value(), Map.of(HttpMethod.POST, beanMethod));
                    }
                });

        return bean;
    }
}
