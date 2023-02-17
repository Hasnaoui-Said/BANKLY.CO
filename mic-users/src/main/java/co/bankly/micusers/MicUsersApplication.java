package co.bankly.micusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableZuulProxy
@EnableEurekaServer
public class MicUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicUsersApplication.class, args);
    }

}
