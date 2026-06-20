package com.msgboard.ui;

import com.msgboard.modelo.Mensagem;
import com.msgboard.modelo.Usuario;
import com.msgboard.servico.ServicoAutenticacao;
import com.msgboard.servico.ServicoMensagem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("caixa-entrada")
public class TelaCaixaEntrada extends VerticalLayout {
    private final ServicoAutenticacao servicoAutenticacao;
    private final ServicoMensagem servicoMensagem;

    public TelaCaixaEntrada(ServicoAutenticacao servicoAutenticacao, ServicoMensagem servicoMensagem) {
        this.servicoAutenticacao = servicoAutenticacao;
        this.servicoMensagem = servicoMensagem;

        setSizeFull();
        setPadding(true);

        H3 titulo = new H3("Caixa de Entrada");

        Button botaoEnviar = new Button("Enviar Mensagem", evento -> getUI().ifPresent(ui -> ui.navigate("enviar-mensagem")));
        Button botaoEnviadas = new Button("Mensagens Enviadas", evento -> getUI().ifPresent(ui -> ui.navigate("mensagens-enviadas")));
        Button botaoSair = new Button("Sair", evento -> {
            servicoAutenticacao.logout();
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        HorizontalLayout barraNavegacao = new HorizontalLayout(botaoEnviar, botaoEnviadas, botaoSair);
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

        List<Mensagem> mensagens = servicoMensagem.getMensagensRecebidas(usuarioLogado.getId());
        listaMensagens.removeAll();

        if (mensagens.isEmpty()) {
            listaMensagens.add(new com.vaadin.flow.component.html.Span("Nenhuma mensagem recebida."));
            return;
        }

        mensagens.forEach(mensagem -> {
            Usuario remetente = servicoAutenticacao.buscarUsuarioPorId(mensagem.getRemetenteId());
            String nomeRemetente = remetente != null ? remetente.getEmail() : "Desconhecido";

            HorizontalLayout linhaMensagem = new HorizontalLayout();
            linhaMensagem.setWidthFull();
            linhaMensagem.setAlignItems(Alignment.CENTER);

            com.vaadin.flow.component.html.Span spanMensagem = new com.vaadin.flow.component.html.Span(
                    String.format("%s: %s (%s)", nomeRemetente, mensagem.getConteudo(), 
                            mensagem.getDataHoraEnvio().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
            );
            spanMensagem.setWidth("400px");

            Checkbox checkboxLida = new Checkbox("Lida", mensagem.foiLida());
            checkboxLida.addValueChangeListener(evento -> {
                if (evento.getValue()) {
                    servicoMensagem.marcarComoLida(mensagem.getId());
                } else {
                    servicoMensagem.desmarcarComoLida(mensagem.getId());
                }
            });

            linhaMensagem.add(spanMensagem, checkboxLida);
            listaMensagens.add(linhaMensagem);
        });
    }
}
