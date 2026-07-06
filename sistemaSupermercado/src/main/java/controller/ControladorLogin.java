package controller;

import org.mindrot.jbcrypt.BCrypt;
import model.dao.FuncionarioDAO;
import model.domain.Funcionario;

public class ControladorLogin {
    public static Funcionario autenticar(String usuario, String senha) {
        Funcionario f = FuncionarioDAO.getFuncionario(usuario);
        if(f == null) {
            return null;
        }
        else if(!f.isAtivo())
            return null;
        if(BCrypt.checkpw(senha, f.getSenhaHash())) {
            return f;
        } else {
            return null;
        }
    }
}
