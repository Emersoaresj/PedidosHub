package com.fiap.postech.pedidohub.pagamento.api;

import com.fiap.postech.pedidohub.pagamento.api.dto.PagamentoRequest;
import com.fiap.postech.pedidohub.pagamento.api.dto.PagamentoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoMockController {

    @PostMapping("/processar")
    public ResponseEntity<PagamentoResponse> processarPagamento(@RequestBody PagamentoRequest request) {
        boolean aprovado = request.getValorTotalPedido() != null && request.getValorTotalPedido().remainder(BigDecimal.valueOf(13)).compareTo(BigDecimal.ZERO) != 0;
        PagamentoResponse response;
        if (aprovado) {
            response = new PagamentoResponse(true, "Pagamento aprovado (mock)");
            return ResponseEntity.ok(response);
        } else {
            response = new PagamentoResponse(false, "Pagamento recusado (mock)");
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(response);
        }
    }
}