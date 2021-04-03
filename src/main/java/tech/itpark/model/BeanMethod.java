package tech.itpark.model;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@Data
@Builder
public class BeanMethod {

    private Object bean;
    private Method method;
}
