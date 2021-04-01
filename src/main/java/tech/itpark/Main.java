package tech.itpark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tech.itpark.handler.RouteMapping;
import tech.itpark.http.container.Container;
import tech.itpark.http.container.listener.ContainerListener;
import tech.itpark.http.container.listener.DefaultContainerListenerImpl;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        final var context =
                new AnnotationConfigApplicationContext("tech.itpark");
        RouteMapping routeMapping = context.getBean(RouteMapping.class);
        ContainerListener containerListener = new DefaultContainerListenerImpl();
        containerListener.register("/", routeMapping::callMethod);
        Container container = new Container(Collections.singletonList(containerListener));
        container.listen(9999);
    }
}
