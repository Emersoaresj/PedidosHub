package com.fiap.postech.pedidohub.cliente.service;

import com.fiap.postech.pedidohub.cliente.api.dto.ClienteDto;
import com.fiap.postech.pedidohub.cliente.api.dto.ClienteRequest;
import com.fiap.postech.pedidohub.cliente.api.mapper.ClienteMapper;
import com.fiap.postech.pedidohub.cliente.domain.exceptions.*;
import com.fiap.postech.pedidohub.cliente.domain.model.Cliente;
import com.fiap.postech.pedidohub.cliente.gateway.port.ClienteRepositoryPort;
import com.fiap.postech.pedidohub.cliente.gateway.port.ClienteServicePort;
import com.fiap.postech.pedidohub.config.ErroInternoException;
import com.fiap.postech.pedidohub.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteServicePort {

    @Autowired
    private ClienteRepositoryPort repositoryPort;


    @Override
    public ClienteDto cadastraCliente(ClienteRequest request) {

        try {
            Cliente cliente = ClienteMapper.INSTANCE.requestToDomain(request);

            validarCliente(cliente);

            return repositoryPort.cadastraCliente(cliente);

        } catch (InvalidCpfException | ClienteExistsException | InvalidDataNascimentoException |
                 InvalidCepException | InvalidEstadoException e) {
            throw e;

        } catch (Exception e) {
            log.error("Erro inesperado ao cadastrar cliente", e);
            throw new ErroInternoException("Erro interno ao tentar cadastrar cliente: " + e.getMessage());
        }
    }

    private void validarCliente(Cliente cliente) {
        if (!cliente.cpfFormatoValido()) {
            log.warn("CPF com formato inválido: {}", cliente.getCpfCliente());
            throw new InvalidCpfException(ConstantUtils.CPF_INVALIDO);
        }

        if (repositoryPort.findByCpfCliente(cliente.getCpfCliente()).isPresent()) {
            log.warn("Cliente já cadastrado com o CPF: {}", cliente.getCpfCliente());
            throw new ClienteExistsException(ConstantUtils.CLIENTE_JA_EXISTE);
        }

        if (!cliente.nascimentoNaoEhFuturo()) {
            log.warn("Data de nascimento não pode ser no futuro: {}", cliente.getDataNascimento());
            throw new InvalidDataNascimentoException(ConstantUtils.DATA_NASCIMENTO_INVALIDA);
        }

        if (!cliente.cepFormatoValido()) {
            log.warn("CEP com formato inválido: {}", cliente.getCep());
            throw new InvalidCepException(ConstantUtils.CEP_INVALIDO);
        }

        if (!cliente.estadoFormatoValido()) {
            log.warn("Estado com formato inválido: {}", cliente.getEstado());
            throw new InvalidEstadoException(ConstantUtils.ESTADO_INVALIDO);
        }
    }


}
