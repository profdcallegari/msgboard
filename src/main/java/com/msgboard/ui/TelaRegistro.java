package com.msgboard.ui;

import com.msgboard.servico.ServicoAutenticacao;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;

@Route("registro")
public class TelaRegistro extends VerticalLayout {
    @SuppressWarnings("unused")
    private final ServicoAutenticacao servicoAutenticacao;

    public TelaRegistro(ServicoAutenticacao servicoAutenticacao) {
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

        Button botaoRegistrar = new Button("Registrar", evento -> {
            try {
                servicoAutenticacao.registrar(campoEmail.getValue(), campoSenha.getValue());
                Notification.show("Conta criada com sucesso!", 3000, Notification.Position.MIDDLE);
                getUI().ifPresent(ui -> ui.navigate(""));
            } catch (RuntimeException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
        botaoRegistrar.setWidth("300px");

        Button botaoVoltar = new Button("Voltar", evento -> getUI().ifPresent(ui -> ui.navigate("")));
        botaoVoltar.setWidth("300px");

        add(campoEmail, campoSenha, botaoRegistrar, botaoVoltar);
    }
}
