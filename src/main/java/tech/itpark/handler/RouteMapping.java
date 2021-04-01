package tech.itpark.handler;

import org.springframework.stereotype.Component;
import tech.itpark.BeanMethod;
import tech.itpark.adapter.Adapter;
import tech.itpark.annotation.RequestBody;
import tech.itpark.annotation.RequestHeader;
import tech.itpark.annotation.RequestParam;
import tech.itpark.http.exception.client.NotFoundException;
import tech.itpark.http.model.Request;
import tech.itpark.http.model.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class RouteMapping {

    private final Map<String, BeanMethod> routesMapping = new HashMap<>();
    private final Adapter adapter = new Adapter();

    public void registerRoute(String route, BeanMethod beanMethod) {
        routesMapping.put(route, beanMethod);
    }

    public void callMethod(Request request, Response response) throws NotFoundException {
        final var path = (request.getHttpMethod() + request.getPath()).replace("/", "");
        final var beanMethod = routesMapping.get(path);
        if (beanMethod == null)
            throw new NotFoundException("Not found path:" + request.getPath());

        final var bean = beanMethod.getBean();
        final var method = beanMethod.getMethod();
        final var parameters = method.getParameters();
        final var args = getParametersMethod(request, parameters);
        try {
            Object invoke = method.invoke(bean, args);
            final var transfer = adapter.transfer(invoke);
            response.setBody(transfer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private Object[] getParametersMethod(final Request request, final Parameter[] parameters) {
        return Arrays.stream(parameters)
                .map(p -> {
                    if (p.isAnnotationPresent(RequestParam.class)) {
                        Map<String, Object> urlParams = request.getUrlParams();
                        RequestParam annotation = p.getAnnotation(RequestParam.class);
                        String value = annotation.value();
                        return urlParams.keySet()
                                .stream()
                                .filter(param -> param.equals(value))
                                .findFirst()
                                .map(urlParams::get)
                                .orElse(null);
                    } else if (p.isAnnotationPresent(RequestBody.class)) {
                        Class<?> type = p.getType();
                        return adapter.transfer(request.getBody(), type);
                    } else if (p.isAnnotationPresent(RequestHeader.class)) {
                        RequestHeader annotation = p.getAnnotation(RequestHeader.class);
                        String value = annotation.value();
                        return request.getHeaders().get(value);
                    }

                    return p;
                }).toArray();
    }
}