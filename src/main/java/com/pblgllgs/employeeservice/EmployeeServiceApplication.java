package com.pblgllgs.employeeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class EmployeeServiceApplication {

//	RestTemplate en camino a deprecated
//	@Bean
//  @LoadBalanced
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

    //  WebClient webflux
//  @Bean
//  public WebClient webClient() {
//      return WebClient.builder().build();
//  }
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

}
