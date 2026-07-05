package controller;

import java.util.List;
import model.domain.Mercadoria;
import model.dao.MercadoriaDAO;

public class ControladorCRUDMercadorias { 
    public static void atualizar(Mercadoria m) {
        MercadoriaDAO.atualizarMercadoria(m);
    }
    
    public static void deletar(Mercadoria m) {
        MercadoriaDAO.deletarMercadoria(m);
    }
    
    public static boolean salvar(Mercadoria m) {
        if (m.getCodigo() != 0 && MercadoriaDAO.getMercadoria(m.getCodigo()) != null) {
            return false;
        }
        
        MercadoriaDAO.inserirMercadoria(m);
        return true;
    }
    
    public static List<Mercadoria> search(Mercadoria m) {
        if (m != null) {
            return MercadoriaDAO.buscarMercadoriaFiltrada(m);
        } else {
            return MercadoriaDAO.listarMercadorias();
        }
    }
    
    public static Mercadoria buscarPorCodigo(int codigo) {
        return MercadoriaDAO.getMercadoria(codigo);
    }
}