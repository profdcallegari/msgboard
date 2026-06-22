package com.msgboard.servico;

import com.msgboard.modelo.Usuario;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ServicoAutenticacao {
    private static final String SESSION_KEY = "usuarioLogado";
    private final Map<String, Usuario> usuarios;

    public ServicoAutenticacao() {
        this.usuarios = new HashMap<>();
    }

    public Usuario registrar(String email, String senha) {
        Predicate<String> emailJaExiste = e -> usuarios.values().stream()
                .anyMatch(u -> u.getEmail().equals(e));

        if (emailJaExiste.test(email)) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario novoUsuario = new Usuario(email, senha);
        usuarios.put(novoUsuario.getId(), novoUsuario);
        return novoUsuario;
    }

    public Usuario login(String email, String senha) {
        Optional<Usuario> usuarioEncontrado = usuarios.values().stream()
                .filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha))
                .findFirst();

        usuarioEncontrado.ifPresent(u -> VaadinSession.getCurrent().setAttribute(SESSION_KEY, u));
        return usuarioEncontrado.orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
    }

    public void logout() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session != null) {
            session.setAttribute(SESSION_KEY, null);
        }
    }

    public Usuario getUsuarioLogado() {
        VaadinSession session = VaadinSession.getCurrent();
        if (session == null) return null;
        return (Usuario) session.getAttribute(SESSION_KEY);
    }

    public boolean estaLogado() {
        return getUsuarioLogado() != null;
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.get(id);
    }

    public Map<String, Usuario> getTodosUsuarios() {
        return new HashMap<>(usuarios);
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }
}
