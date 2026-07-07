package controller;

import java.util.List;
import model.domain.ItemVenda;
import model.domain.Mercadoria;
import model.domain.Venda;
import model.dao.MercadoriaDAO;
import model.dao.VendasDAO;

public class ControladorVendas {
    
    public static Mercadoria buscarMercadoriaPorCodigo(int codigo) {
        Mercadoria m = MercadoriaDAO.getMercadoria(codigo);
        if (m != null && m.isAtivo() && m.getQuantidade() > 0) {
            return m;
        }
        return null;
    }
    
    public static List<Mercadoria> buscarMercadoriasPorNome(String nome) {
        Mercadoria filtro = new Mercadoria();
        filtro.setNome(nome);
        return MercadoriaDAO.buscarMercadoriaFiltrada(filtro);
    }
    
    public static boolean adicionarAoCarrinho(Venda carrinho, Mercadoria mercadoria, int quantidade) {
        if (mercadoria == null || quantidade <= 0) {
            return false;
        }

        if (mercadoria.getQuantidade() < quantidade) {
            return false;
        }

        for (ItemVenda item : carrinho.getItens()) {
            Mercadoria mCarrinho = (Mercadoria) item.getMercadoria();
            if (mCarrinho.getCodigo() == mercadoria.getCodigo()) {
                int novaQuantidade = item.getQuantidade() + quantidade;
                if (mercadoria.getQuantidade() < novaQuantidade) {
                    return false;
                }

                carrinho.setValorTotal(carrinho.getValorTotal() - item.getSubtotal());

                item.setQuantidade(novaQuantidade);
                item.setSubtotal(novaQuantidade * mercadoria.getPrecoUnitario());

                carrinho.setValorTotal(carrinho.getValorTotal() + item.getSubtotal());
                return true;
            }
        }

        ItemVenda novoItem = new ItemVenda();
        novoItem.setMercadoria(mercadoria);
        novoItem.setQuantidade(quantidade);
        novoItem.setSubtotal(quantidade * mercadoria.getPrecoUnitario());
        
        carrinho.adicionarItem(novoItem);
        return true;
    }
    
    public static boolean removerDoCarrinho(Venda carrinho, int indexItem) {
        if (indexItem >= 0 && indexItem < carrinho.getItens().size()) {
            ItemVenda itemRemovido = carrinho.getItens().remove(indexItem);
            carrinho.setValorTotal(carrinho.getValorTotal() - itemRemovido.getSubtotal());
            return true;
        }
        return false;
    }
    
    public static boolean finalizarVenda(Venda carrinho, int idFuncionario, String tipoPagamento, double valorPago) {
        if (carrinho.getItens().isEmpty()) {
            return false;
        }
        
        if (valorPago < carrinho.getValorTotal()) {
            return false;
        }
        
        VendasDAO.inserirVenda(carrinho, idFuncionario, tipoPagamento, valorPago);
        return true;
    }
    
    public static double calcularTroco(double valorTotal, double valorPago) {
        if (valorPago >= valorTotal) {
            return valorPago - valorTotal;
        }
        return 0.0;
    }
    
    public static String gerarComprovante(Venda venda, String tipoPagamento, double valorPago, double troco) {
        StringBuilder comprovante = new StringBuilder();
        comprovante.append("====================================\n");
        comprovante.append("       COMPROVANTE DE VENDA         \n");
        comprovante.append("====================================\n");
        comprovante.append(String.format("Data: %s\n", java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        comprovante.append("------------------------------------\n");
        comprovante.append("Qtd | Descrição | V. Unit | Subtotal\n");
        comprovante.append("------------------------------------\n");
        
        for (ItemVenda item : venda.getItens()) {
            Mercadoria m = (Mercadoria) item.getMercadoria();
            String nome = m.getNome();
            double preco = m.getPrecoUnitario();
            
            comprovante.append(String.format("%-3d | %-10s | %-7.2f | %-8.2f\n", 
                item.getQuantidade(), 
                nome.length() > 10 ? nome.substring(0, 10) : nome,
                preco,
                item.getSubtotal()));
        }
        
        comprovante.append("------------------------------------\n");
        comprovante.append(String.format("Total a pagar:      R$ %.2f\n", venda.getValorTotal()));
        comprovante.append(String.format("Forma de pagamento: %s\n", tipoPagamento));
        comprovante.append(String.format("Valor pago:         R$ %.2f\n", valorPago));
        if (tipoPagamento.equalsIgnoreCase("Dinheiro")) {
            comprovante.append(String.format("Troco:              R$ %.2f\n", troco));
        }
        comprovante.append("====================================\n");
        comprovante.append("     Obrigado pela preferência!     \n");
        comprovante.append("====================================\n");
        
        return comprovante.toString();
    }
}