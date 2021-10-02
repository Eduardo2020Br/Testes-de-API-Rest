package dataFactory;

import pojo.UsuarioPojo;

public class UsusarioDataFactory {
    public static UsuarioPojo criarUsuarioAdmnistrador() {
        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("admin");
        usuario.setUsuarioSenha("admin");

        return usuario;
    }
}
