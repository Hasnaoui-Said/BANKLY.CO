package next.bankly.co.operations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("next.bankly.co.operations.rest.required.facade")
@EnableEurekaServer
public class OperationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperationsApplication.class, args);
    }

}
