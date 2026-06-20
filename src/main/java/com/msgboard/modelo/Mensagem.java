package com.msgboard.modelo;

import java.time.LocalDateTime;
import java.util.UUID;

public class Mensagem {
    private String id;
    private String remetenteId;
    private String destinatarioId;
    private String conteudo;
    private LocalDateTime dataHoraEnvio;
    private LocalDateTime dataHoraLeitura;

    public Mensagem() {
        this.id = UUID.randomUUID().toString();
    }

    public Mensagem(String remetenteId, String destinatarioId, String conteudo) {
        this();
        this.remetenteId = remetenteId;
        this.destinatarioId = destinatarioId;
        this.conteudo = conteudo;
        this.dataHoraEnvio = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(String remetenteId) {
        this.remetenteId = remetenteId;
    }

    public String getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(String destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }

    public LocalDateTime getDataHoraLeitura() {
        return dataHoraLeitura;
    }

    public void setDataHoraLeitura(LocalDateTime dataHoraLeitura) {
        this.dataHoraLeitura = dataHoraLeitura;
    }

    public boolean foiLida() {
        return dataHoraLeitura != null;
    }
}
