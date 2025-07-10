package com.fiap.postech.pedidohub.pedido.gateway.client;

import com.fiap.postech.pedidohub.pedido.api.dto.PedidoClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service", url = "${cliente.service.url}")
public interface PedidoClienteClient {

    @GetMapping("/api/clientes/cpf/{cpf}")
    PedidoClienteDto buscarPorCpf(@PathVariable("cpf") String cpf);
}
