package com.apiprojetoss.projetoss.rest.controllers;

import java.util.List;

import com.apiprojetoss.projetoss.model.Cliente;
import com.apiprojetoss.projetoss.model.enums.EnumCliente;
import com.apiprojetoss.projetoss.services.ClienteService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("listar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente findById(@PathVariable Integer id) {
        return clienteService.findById(id).get();
    }

    @GetMapping("/listar/nome/{nome}")
    public Cliente findByNome(@PathVariable String nome) {
        return clienteService.findByNome(nome).get();
    }

    @PostMapping("/gravar")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente) {
        cliente.setSituacao(EnumCliente.ATIVO);
        return clienteService.create(cliente);
    }

    @DeleteMapping("/deletar/{id}")
    public void delete(@PathVariable Integer id) {
        clienteService.findById(id)
                .map(c -> {
                    clienteService.delete(id);
                    return null;
                });
    }

    @PutMapping("gravar/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Cliente update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        Cliente clienteExistente = clienteService.findById(id).get();
        BeanUtils.copyProperties(cliente, clienteExistente);
        cliente.setId(id);
        return clienteService.update(cliente);
    }

    @PatchMapping("/situacao/{id}")
    public Cliente atualizaSituacao(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id).get();
        if (cliente.getSituacao().equals(EnumCliente.ATIVO)) {
            cliente.setSituacao(EnumCliente.DESATIVO);
        } else {
            cliente.setSituacao(EnumCliente.ATIVO);
        }
        return clienteService.atualizaSituacao(cliente);
    }

}
