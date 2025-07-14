package com.fiap.postech.pedidohub.commom.config;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fiap.postech.pedidohub.cliente.domain.exceptions.*;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.EstoqueExistsException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.EstoqueNotFoundException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.InvalidQuantidadeEstoqueException;
import com.fiap.postech.pedidohub.estoque.domain.exceptions.InvalidSkuEstoqueException;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.InvalidPedidoException;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoNotFoundException;
import com.fiap.postech.pedidohub.pedido.domain.exceptions.PedidoProdutoNotFoundException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidPrecoException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.InvalidSkuException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.ProdutoExistsException;
import com.fiap.postech.pedidohub.produto.domain.exceptions.ProdutoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandlerException {


    // Constantes para as chaves de resposta
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERROR = "error";
    private static final String MENSAGEM = "mensagem";
    private static final String PATH = "path";


    @ExceptionHandler(ErroInternoException.class)
    public ResponseEntity<Map<String, Object>> handlerErroBancoDeDados(ErroInternoException erroInternoException, WebRequest request) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put(ERROR, "Erro ao salvar no Banco de Dados.");
        response.put(MENSAGEM, erroInternoException.getMessage());
        response.put(PATH, request.getDescription(false));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // PARA VALIDAÇÃO DE CAMPOS NA REQUISIÇÃO
    public ResponseEntity<Map<String, Object>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // PARA DATA INVALIDA NA REQUISIÇÃO
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());

        String mensagem = "Requisição malformada";

        if (ex.getCause() instanceof InvalidFormatException invalidFormat) {
            if (invalidFormat.getTargetType().equals(LocalDate.class)) {
                mensagem = "Formato de data inválido. Use o padrão yyyy-MM-dd";
            }
        }

        response.put("mensagem", mensagem);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ClienteExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerClienteExistsException(ClienteExistsException clienteExistsException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, clienteExistsException.getMessage());
        response.put(STATUS, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ClienteNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerClienteNotFoundException(ClienteNotFoundException clienteNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, clienteNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidCpfException(InvalidCpfException invalidCpfException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidCpfException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDataNascimentoException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidDataNascimentoException(InvalidDataNascimentoException invalidDataNascimentoException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidDataNascimentoException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCepException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidCepException(InvalidCepException invalidCepException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidCepException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidEstadoException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidEstadoException(InvalidEstadoException invalidEstadoException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidEstadoException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPrecoException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidPrecoException(InvalidPrecoException invalidPrecoException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidPrecoException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSkuException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidPrecoException(InvalidSkuException invalidSkuException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidSkuException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProdutoExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidPrecoException(ProdutoExistsException produtoExistsException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, produtoExistsException.getMessage());
        response.put(STATUS, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerProdutoNotFoundException(ProdutoNotFoundException produtoNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, produtoNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EstoqueExistsException.class)
    public ResponseEntity<Map<String, Object>> handlerEstoqueExistsException(EstoqueExistsException estoqueExistsException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, estoqueExistsException.getMessage());
        response.put(STATUS, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(EstoqueNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerEstoqueNotFoundException(EstoqueNotFoundException estoqueNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, estoqueNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidQuantidadeEstoqueException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidQuantidadeEstoqueException(InvalidQuantidadeEstoqueException invalidQuantidadeEstoqueException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidQuantidadeEstoqueException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSkuEstoqueException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidSkuEstoqueException(InvalidSkuEstoqueException invalidSkuEstoqueException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidSkuEstoqueException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoProdutoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerProdutoPedidoNotFoundException(PedidoProdutoNotFoundException pedidoProdutoNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, pedidoProdutoNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPedidoException.class)
    public ResponseEntity<Map<String, Object>> handlerInvalidPedidoException(InvalidPedidoException invalidPedidoException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, invalidPedidoException.getMessage());
        response.put(STATUS, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerPedidoNotFoundException(PedidoNotFoundException pedidoNotFoundException) {
        Map<String, Object> response = new HashMap<>();
        response.put(TIMESTAMP, LocalDateTime.now());
        response.put(MENSAGEM, pedidoNotFoundException.getMessage());
        response.put(STATUS, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



}
