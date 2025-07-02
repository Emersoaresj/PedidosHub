package com.fiap.postech.pedidoHub.cliente.gateway.database;

import com.fiap.postech.pedidoHub.cliente.domain.Cliente;
import com.fiap.postech.pedidoHub.cliente.gateway.ClienteRepositoryPort;
import com.fiap.postech.pedidoHub.cliente.gateway.database.entity.ClienteEntity;
import com.fiap.postech.pedidoHub.cliente.dto.ClienteDto;
import com.fiap.postech.pedidoHub.cliente.mapper.ClienteMapper;
import com.fiap.postech.pedidoHub.cliente.gateway.database.repository.ClienteRepository;
import com.fiap.postech.pedidoHub.utils.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ClienteRepositoryImpl implements ClienteRepositoryPort {

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public ClienteDto cadastraCliente(Cliente cliente) {
        try {
            ClienteEntity clienteEntity = ClienteMapper.INSTANCE.domainToEntity(cliente);
            clienteRepository.save(clienteEntity);
            return montaResponse(clienteEntity);
        } catch (Exception e) {
            log.error("Erro ao cadastrar cliente", e);
            throw new RuntimeException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @Override
    public Optional<Cliente> findByCpfCliente(String cpf) {
        try {
            ClienteEntity clienteEntity = clienteRepository.findByCpfCliente(cpf).orElse(null);
            Cliente cliente = ClienteMapper.INSTANCE.entityToDomain(clienteEntity);
            return Optional.of(cliente);
        } catch (Exception e) {
            log.error("Erro ao buscar CPF", e);
            throw new RuntimeException("Erro ao buscar CPF: " + e.getMessage());
        }
    }

    private ClienteDto montaResponse(ClienteEntity clienteEntity) {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setMensagem(ConstantUtils.CLIENTE_CADASTRADO);
        clienteDto.setNome(clienteEntity.getNomeCliente());
        clienteDto.setCpf(clienteEntity.getCpfCliente());
        clienteDto.setDataNascimento(clienteEntity.getDataNascimento());

        return clienteDto;
    }


}
