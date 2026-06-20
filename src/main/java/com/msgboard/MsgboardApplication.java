package com.msgboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MsgboardApplication {

    private final Environment environment;

    public MsgboardApplication(Environment environment) {
        this.environment = environment;
    }

    public static void main(String[] args) {
        SpringApplication.run(MsgboardApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port = environment.getProperty("server.port", "8080");
        String appName = environment.getProperty("spring.application.name", "msgboard");
        System.out.println();
        System.out.println("-----------------------------------------------------");
        System.out.println("Aplicacao " + appName + " iniciada com sucesso.");
        System.out.println("Disponivel na URL: http://localhost:" + port);
        System.out.println("Para parar a aplicacao, pressione Ctrl+C no terminal.");
        System.out.println("-----------------------------------------------------");
    }
}
