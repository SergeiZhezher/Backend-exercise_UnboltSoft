package com.example.unboltsoft.config;

import com.example.unboltsoft.handler.NodeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class NodeRouter {

    @Bean
    public RouterFunction<ServerResponse> nodeRoutes(NodeHandler nodeHandler) {
        return route(GET("/node"), nodeHandler::getNodesList)
                .andRoute(POST("/node").and(accept(APPLICATION_JSON)), nodeHandler::createNode);
    }
}