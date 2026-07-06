package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.database.DatabaseConnection;
import model.domain.ItemVenda;
import model.domain.Mercadoria;
import model.domain.Venda;

public class VendasDAO {
    
    public static void inserirVenda(Venda venda, int idFuncionario, String tipoPagamento, double valorPago) {
        String sqlVenda = "INSERT INTO Venda (Valor_Total, ID_Identificador_Funcionario, Tipo_Pagamento, Valor_Pago) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO ItemVenda (Quantidade, Subtotal, ID_Venda, Codigo_Mercadoria) VALUES (?, ?, ?, ?)";
        String sqlAtualizarEstoque = "UPDATE Mercadoria SET Quantidade_Estoque = Quantidade_Estoque - ? WHERE Codigo = ?";

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            int idVendaGerado = 0;
            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS)) {
                stmtVenda.setDouble(1, venda.getValorTotal());
                stmtVenda.setInt(2, idFuncionario);
                stmtVenda.setString(3, tipoPagamento);
                stmtVenda.setDouble(4, valorPago);
                stmtVenda.executeUpdate();
                
                try (ResultSet rs = stmtVenda.getGeneratedKeys()) {
                    if (rs.next()) {
                        idVendaGerado = rs.getInt(1);
                        venda.setIdVenda(idVendaGerado);
                    }
                }
            }
            
            if (idVendaGerado > 0) {
                try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
                     PreparedStatement stmtEstoque = conn.prepareStatement(sqlAtualizarEstoque)) {
                    
                    for (ItemVenda item : venda.getItens()) {
                        Mercadoria m = (Mercadoria) item.getMercadoria();
                        
                        stmtItem.setInt(1, item.getQuantidade());
                        stmtItem.setDouble(2, item.getSubtotal());
                        stmtItem.setInt(3, idVendaGerado);
                        stmtItem.setInt(4, m.getCodigo());
                        stmtItem.executeUpdate();
                        
                        stmtEstoque.setInt(1, item.getQuantidade());
                        stmtEstoque.setInt(2, m.getCodigo());
                        stmtEstoque.executeUpdate();
                    }
                }
            }
            
            conn.commit();
            System.out.println("Venda salva com sucesso!");
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao salvar venda: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
    
    public static List<Venda> listarVendas() {
        String sql = "SELECT * FROM Venda ORDER BY Data_Venda DESC";
        List<Venda> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda v = new Venda();
                v.setIdVenda(rs.getInt("ID_Venda"));
                if (rs.getTimestamp("Data_Venda") != null) {
                    v.setDataVenda(rs.getTimestamp("Data_Venda").toLocalDateTime());
                }
                v.setValorTotal(rs.getDouble("Valor_Total"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas: " + e.getMessage());
        }
        return lista;
    }
    
    public static Venda getVendaComItens(int idVenda) {
        String sqlVenda = "SELECT * FROM Venda WHERE ID_Venda = ?";
        String sqlItens = "SELECT * FROM ItemVenda WHERE ID_Venda = ?";
        
        Venda venda = null;
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            try (PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {
                stmtVenda.setInt(1, idVenda);
                try (ResultSet rsVenda = stmtVenda.executeQuery()) {
                    if (rsVenda.next()) {
                        venda = new Venda();
                        venda.setIdVenda(rsVenda.getInt("ID_Venda"));
                        if (rsVenda.getTimestamp("Data_Venda") != null) {
                            venda.setDataVenda(rsVenda.getTimestamp("Data_Venda").toLocalDateTime());
                        }
                        venda.setValorTotal(rsVenda.getDouble("Valor_Total"));
                    }
                }
            }
            
            if (venda != null) {
                try (PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {
                    stmtItens.setInt(1, idVenda);
                    try (ResultSet rsItens = stmtItens.executeQuery()) {
                        while (rsItens.next()) {
                            ItemVenda item = new ItemVenda();
                            item.setIdItemVenda(rsItens.getInt("ID_ItemVenda"));
                            item.setQuantidade(rsItens.getInt("Quantidade"));
                            item.setSubtotal(rsItens.getDouble("Subtotal"));
                            
                            int codigoMercadoria = rsItens.getInt("Codigo_Mercadoria");
                            Mercadoria mercadoria = MercadoriaDAO.getMercadoria(codigoMercadoria);
                            item.setMercadoria(mercadoria);
                            
                            venda.adicionarItem(item);
                        }
                    }
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar detalhes da venda: " + e.getMessage());
        }
        
        return venda;
    }
}