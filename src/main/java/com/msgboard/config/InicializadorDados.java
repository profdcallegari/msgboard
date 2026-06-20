package com.msgboard.config;

import com.msgboard.modelo.Usuario;
import com.msgboard.servico.ServicoAutenticacao;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializadorDados implements CommandLineRunner {
    private final ServicoAutenticacao servicoAutenticacao;

    public InicializadorDados(ServicoAutenticacao servicoAutenticacao) {
        this.servicoAutenticacao = servicoAutenticacao;
    }

    @Override
    public void run(String... args) {
        servicoAutenticacao.adicionarUsuario(new Usuario("demo1", "123"));
        servicoAutenticacao.adicionarUsuario(new Usuario("demo2", "123"));
        servicoAutenticacao.adicionarUsuario(new Usuario("demo3", "123"));
        servicoAutenticacao.adicionarUsuario(new Usuario("demo4", "123"));
        servicoAutenticacao.adicionarUsuario(new Usuario("demo5", "123"));
    }
}
