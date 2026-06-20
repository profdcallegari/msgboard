package com.msgboard.ui;

import com.msgboard.servico.ServicoAutenticacao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class TelaLogin extends VerticalLayout {
    private final ServicoAutenticacao servicoAutenticacao;

    public TelaLogin(ServicoAutenticacao servicoAutenticacao) {
        this.servicoAutenticacao = servicoAutenticacao;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        EmailField campoEmail = new EmailField("Email");
        campoEmail.setRequired(true);
        campoEmail.setWidth("300px");

        PasswordField campoSenha = new PasswordField("Senha");
        campoSenha.setRequired(true);
        campoSenha.setWidth("300px");

        Button botaoLogin = new Button("Entrar", evento -> {
            try {
                servicoAutenticacao.login(campoEmail.getValue(), campoSenha.getValue());
                getUI().ifPresent(ui -> ui.navigate(TelaCaixaEntrada.class));
            } catch (RuntimeException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        botaoLogin.setWidth("300px");

        RouterLink linkRegistro = new RouterLink("Criar conta", TelaRegistro.class);

        add(campoEmail, campoSenha, botaoLogin, linkRegistro);
    }
}
