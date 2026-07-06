package controller;

import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import model.domain.Funcionario;
import model.dao.FuncionarioDAO;
import utilitarios.Utilitarios;

public class ControladorCRUDFuncionarios { 
    public static void atualizar(Funcionario f) {
        FuncionarioDAO.atualizarFuncionario(f);
    }
    
    public static void desativar(Funcionario f) {
        FuncionarioDAO.deletarFuncionario(f);
    }
    
    public static boolean salvar(Funcionario f) {
        if(FuncionarioDAO.getFuncionario(f.getNomeUsuario()) != null) {
            return false;
        }
        f.setSenhaHash(BCrypt.hashpw(f.getCpf(), BCrypt.gensalt()));
        FuncionarioDAO.inserirFuncionario(f);
        return true;
    }
    
    public static List<Funcionario> search(Funcionario f) {
        if(f != null)
            return FuncionarioDAO.buscarFuncionarioFiltrado(f);
        else
            return FuncionarioDAO.listarFuncionarios();
    }
    
    public static int alterarSenha(String senhaAtual, String novaSenha, String novaSenha2, Funcionario f) {
        if(BCrypt.checkpw(senhaAtual, f.getSenhaHash())) {
            if(novaSenha.equals(novaSenha2)) {
                f.setSenhaHash(BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
                FuncionarioDAO.atualizarFuncionario(f);
                return 0; //Tudo certo
            }
            else
                return 2; //senhas diferentes
        }
        else
            return 1; //senha atual inválida
    }
}
