package com.msgboard.servico;

import com.msgboard.modelo.Usuario;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ServicoAutenticacao {
    private final Map<String, Usuario> usuarios;
    private Usuario usuarioLogado;

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

        usuarioEncontrado.ifPresent(u -> usuarioLogado = u);
        return usuarioEncontrado.orElseThrow(() -> new RuntimeException("Credenciais inválidas"));
    }

    public void logout() {
        usuarioLogado = null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
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
