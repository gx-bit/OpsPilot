package com.opspilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import java.net.http.HttpClient;

@SpringBootApplication
public class OpsPilotApplication {
    public static void main(String[] args) { SpringApplication.run(OpsPilotApplication.class, args); }
    @Bean RestClient.Builder restClientBuilder() {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return RestClient.builder().requestFactory(new JdkClientHttpRequestFactory(httpClient));
    }
}
