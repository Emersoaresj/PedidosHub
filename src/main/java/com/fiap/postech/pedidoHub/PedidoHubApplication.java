package com.fiap.postech.pedidohub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PedidoHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PedidoHubApplication.class, args);
	}

}
