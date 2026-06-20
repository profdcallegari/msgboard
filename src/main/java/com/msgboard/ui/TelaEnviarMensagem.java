package com.msgboard.ui;

import com.msgboard.modelo.Usuario;
import com.msgboard.servico.ServicoAutenticacao;
import com.msgboard.servico.ServicoMensagem;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Map;

@Route("enviar-mensagem")
public class TelaEnviarMensagem extends VerticalLayout {
    private final ServicoAutenticacao servicoAutenticacao;
    @SuppressWarnings("unused")
    private final ServicoMensagem servicoMensagem;

    public TelaEnviarMensagem(ServicoAutenticacao servicoAutenticacao, ServicoMensagem servicoMensagem) {
        this.servicoAutenticacao = servicoAutenticacao;
        this.servicoMensagem = servicoMensagem;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setPadding(true);

        H3 titulo = new H3("Enviar Mensagem");

        ComboBox<Usuario> comboDestinatario = new ComboBox<>("Destinatário");
        comboDestinatario.setWidth("300px");
        comboDestinatario.setRequired(true);
        comboDestinatario.setItemLabelGenerator(Usuario::getEmail);

        TextField campoMensagem = new TextField("Mensagem");
        campoMensagem.setWidth("300px");
        campoMensagem.setRequired(true);
        campoMensagem.setMaxLength(50);
        campoMensagem.addValueChangeListener(evento -> {
            int caracteresRestantes = 50 - evento.getValue().length();
            campoMensagem.setHelperText(caracteresRestantes + " caracteres restantes");
        });

        Button botaoEnviar = new Button("Enviar", evento -> {
            Usuario destinatario = comboDestinatario.getValue();
            String conteudo = campoMensagem.getValue();

            if (destinatario == null) {
                Notification.show("Selecione um destinatário", 3000, Notification.Position.MIDDLE);
                return;
            }

            try {
                Usuario usuarioLogado = servicoAutenticacao.getUsuarioLogado();
                servicoMensagem.enviarMensagem(usuarioLogado.getId(), destinatario.getId(), conteudo);
                Notification.show("Mensagem enviada com sucesso!", 3000, Notification.Position.MIDDLE);
                campoMensagem.clear();
            } catch (RuntimeException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        botaoEnviar.setWidth("300px");

        Button botaoVoltar = new Button("Voltar", evento -> getUI().ifPresent(ui -> ui.navigate("caixa-entrada")));
        botaoVoltar.setWidth("300px");

        Button botaoSair = new Button("Sair", evento -> {
            servicoAutenticacao.logout();
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        botaoSair.setWidth("300px");

        add(titulo, comboDestinatario, campoMensagem, botaoEnviar, botaoVoltar, botaoSair);

        carregarUsuarios(comboDestinatario);
    }

    private void carregarUsuarios(ComboBox<Usuario> comboDestinatario) {
        Usuario usuarioLogado = servicoAutenticacao.getUsuarioLogado();
        if (usuarioLogado == null) {
            getUI().ifPresent(ui -> ui.navigate(""));
            return;
        }

        Map<String, Usuario> usuarios = servicoAutenticacao.getTodosUsuarios();
        comboDestinatario.setItems(
                usuarios.values().stream()
                        .filter(u -> !u.getId().equals(usuarioLogado.getId()))
                        .toList()
        );
    }
}
