package com.msgboard.ui;

import com.msgboard.modelo.Mensagem;
import com.msgboard.modelo.Usuario;
import com.msgboard.servico.ServicoAutenticacao;
import com.msgboard.servico.ServicoMensagem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("mensagens-enviadas")
public class TelaMensagensEnviadas extends VerticalLayout {
    private final ServicoAutenticacao servicoAutenticacao;
    private final ServicoMensagem servicoMensagem;

    public TelaMensagensEnviadas(ServicoAutenticacao servicoAutenticacao, ServicoMensagem servicoMensagem) {
        this.servicoAutenticacao = servicoAutenticacao;
        this.servicoMensagem = servicoMensagem;

        setSizeFull();
        setPadding(true);

        H3 titulo = new H3("Mensagens Enviadas");

        Button botaoEnviar = new Button("Enviar Mensagem", evento -> getUI().ifPresent(ui -> ui.navigate("enviar-mensagem")));
        Button botaoCaixaEntrada = new Button("Caixa de Entrada", evento -> getUI().ifPresent(ui -> ui.navigate("caixa-entrada")));
        Button botaoSair = new Button("Sair", evento -> {
            servicoAutenticacao.logout();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        HorizontalLayout barraNavegacao = new HorizontalLayout(botaoEnviar, botaoCaixaEntrada, botaoSair);
        barraNavegacao.setSpacing(true);

        VerticalLayout listaMensagens = new VerticalLayout();
        listaMensagens.setWidthFull();

        carregarMensagens(listaMensagens);

        add(titulo, barraNavegacao, listaMensagens);
    }

    private void carregarMensagens(VerticalLayout listaMensagens) {
        Usuario usuarioLogado = servicoAutenticacao.getUsuarioLogado();
        if (usuarioLogado == null) {
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

        List<Mensagem> mensagens = servicoMensagem.getMensagensEnviadas(usuarioLogado.getId());
        listaMensagens.removeAll();

        if (mensagens.isEmpty()) {
            listaMensagens.add(new Span("Nenhuma mensagem enviada."));
            return;
        }

        mensagens.forEach(mensagem -> {
            Usuario destinatario = servicoAutenticacao.buscarUsuarioPorId(mensagem.getDestinatarioId());
            String nomeDestinatario = destinatario != null ? destinatario.getEmail() : "Desconhecido";

            String statusLeitura = mensagem.foiLida() 
                    ? "Lida em " + mensagem.getDataHoraLeitura().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    : "Não lida";

            Span spanMensagem = new Span(
                    String.format("Para: %s | Mensagem: %s | Enviada: %s | Status: %s",
                            nomeDestinatario,
                            mensagem.getConteudo(),
                            mensagem.getDataHoraEnvio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                            statusLeitura)
            );

            listaMensagens.add(spanMensagem);
        });
    }
}
