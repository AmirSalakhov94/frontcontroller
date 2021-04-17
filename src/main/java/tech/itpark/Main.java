package tech.itpark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tech.itpark.handler.RouteMapping;
import tech.itpark.http.connection.handler.ConnectionHandlerImpl;
import tech.itpark.http.container.Container;
import tech.itpark.http.handler.HandlerHttpServer;

public class Main {

    public static void main(String[] args) {
        final var context =
                new AnnotationConfigApplicationContext("tech.itpark");
        RouteMapping routeMapping = context.getBean(RouteMapping.class);
        HandlerHttpServer handlerHttpServer = new HandlerHttpServer();
        handlerHttpServer.register("/", routeMapping::callMethod);
        Container container = new Container(new ConnectionHandlerImpl(handlerHttpServer));
        container.listen(9999);
    }
}
