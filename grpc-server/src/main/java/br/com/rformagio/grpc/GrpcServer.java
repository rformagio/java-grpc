package br.com.rformagio.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServer {
    public static void main(String[] args) {
        SpringApplication.run(GrpcServer.class, args);
    }
}
