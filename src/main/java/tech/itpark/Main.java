package tech.itpark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tech.itpark.handler.RouteMapping;
import tech.itpark.http.container.Container;
import tech.itpark.http.container.listener.ContainerListener;
import tech.itpark.http.container.listener.DefaultContainerListener;
import tech.itpark.http.handler.HandlerHttpServer;

import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        final var context =
                new AnnotationConfigApplicationContext("tech.itpark");
        RouteMapping routeMapping = context.getBean(RouteMapping.class);
        HandlerHttpServer handlerHttpServer = new HandlerHttpServer();
        handlerHttpServer.register("/", routeMapping::callMethod);
        ContainerListener containerListener = new DefaultContainerListener(handlerHttpServer);
        Container container = new Container(Collections.singletonList(containerListener));
        container.listen(9999);
    }
}
