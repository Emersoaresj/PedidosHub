package com.fiap.postech.pedidoHub.cliente.service.impl;

import com.fiap.postech.pedidoHub.cliente.domain.Cliente;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteDto;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteRequest;
import com.fiap.postech.pedidoHub.cliente.gateway.ClienteRepositoryPort;
import com.fiap.postech.pedidoHub.cliente.mapper.ClienteMapper;
import com.fiap.postech.pedidoHub.cliente.service.port.ClienteServicePort;
import com.fiap.postech.pedidoHub.exceptions.cliente.ClienteExistsException;
import com.fiap.postech.pedidoHub.exceptions.cliente.InvalidCpfException;
import com.fiap.postech.pedidoHub.exceptions.cliente.InvalidDataNascimentoException;
import com.fiap.postech.pedidoHub.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClienteServiceImpl implements ClienteServicePort {

    @Autowired
    ClienteRepositoryPort repositoryPort;


    @Override
    public ClienteDto cadastraCliente(ClienteRequest request) {

        Cliente cliente = ClienteMapper.INSTANCE.requestToDomain(request);

        if (!cliente.cpfFormatoValido()) {
            log.warn("CPF com formato inválido: {}", cliente.getCpfCliente());
            throw new InvalidCpfException(ConstantUtils.CPF_INVALIDO);
        }
        else if (!cliente.nascimentoNaoEhFuturo()) {
            log.warn("Data de nascimento não pode ser no futuro: {}", cliente.getDataNascimento());
            throw new InvalidDataNascimentoException(ConstantUtils.DATA_NASCIMENTO_INVALIDA);
        }
        else if (repositoryPort.findByCpfCliente(cliente.getCpfCliente()).isPresent()) {
            log.warn("Cliente já cadastrado com o CPF: {}", cliente.getCpfCliente());
            throw new ClienteExistsException(ConstantUtils.CLIENTE_JA_EXISTE);
        }


        // SE ESTIVER TUDO CERTO, CHAMA O REPOSITORY PARA CADASTRAR O CLIENTE
        return repositoryPort.cadastraCliente(cliente);
    }

}
