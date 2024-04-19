package com.example.apigatewayservice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    // Generic 내부에 설정에 필요한 자료형을 넣어줘야하는데
    // CustomFilter의 설정 클래스는 CustomFilter 내부의 Config로 사용할 것
    // Config를 이너클래스로 생성한 이유? -> 응집도를 높여주기 위해
    @Data
    static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());
//            if(config.isPreLogger()) {
//                log.info("Logging Filter PRE : {}", config.getBaseMessage());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if(config.isPostLogger()) {
//                    log.info("");
//                }
//            }));
//        });
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain)-> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage : {}", config.getBaseMessage());
            if(config.isPreLogger()) {
                log.info("Logging PRE Filter : request id -> {}", request.getURI());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("Logging POST Filter : response code -> {}", response.getStatusCode());
                }
            }));
        }, OrderedGatewayFilter.LOWEST_PRECEDENCE);

        return filter;
    }
}
