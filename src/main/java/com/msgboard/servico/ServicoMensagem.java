package com.msgboard.servico;

import com.msgboard.modelo.Mensagem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ServicoMensagem {
    private final List<Mensagem> mensagens;

    public ServicoMensagem() {
        this.mensagens = new ArrayList<>();
    }

    public Mensagem enviarMensagem(String remetenteId, String destinatarioId, String conteudo) {
        Predicate<String> validarConteudo = c -> c != null && !c.trim().isEmpty() && c.length() <= 50;

        if (!validarConteudo.test(conteudo)) {
            throw new RuntimeException("Mensagem deve ter entre 1 e 50 caracteres");
        }

        Mensagem mensagem = new Mensagem(remetenteId, destinatarioId, conteudo);
        mensagens.add(mensagem);
        return mensagem;
    }

    public List<Mensagem> getMensagensRecebidas(String destinatarioId) {
        return mensagens.stream()
                .filter(m -> m.getDestinatarioId().equals(destinatarioId))
                .sorted((m1, m2) -> m2.getDataHoraEnvio().compareTo(m1.getDataHoraEnvio()))
                .collect(Collectors.toList());
    }

    public List<Mensagem> getMensagensEnviadas(String remetenteId) {
        return mensagens.stream()
                .filter(m -> m.getRemetenteId().equals(remetenteId))
                .sorted((m1, m2) -> m2.getDataHoraEnvio().compareTo(m1.getDataHoraEnvio()))
                .collect(Collectors.toList());
    }

    public void marcarComoLida(String mensagemId) {
        mensagens.stream()
                .filter(m -> m.getId().equals(mensagemId))
                .findFirst()
                .ifPresent(m -> m.setDataHoraLeitura(LocalDateTime.now()));
    }

    public void desmarcarComoLida(String mensagemId) {
        mensagens.stream()
                .filter(m -> m.getId().equals(mensagemId))
                .findFirst()
                .ifPresent(m -> m.setDataHoraLeitura(null));
    }

    public Mensagem buscarMensagemPorId(String id) {
        return mensagens.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
