package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.database.DatabaseConnection;
import model.domain.Mercadoria;

public class MercadoriaDAO {
    
    public static void inserirMercadoria(Mercadoria mercadoria) {
        String sql = "INSERT INTO Mercadoria (Nome, Tipo, Modelo, Descricao, Preco_Unitario, Quantidade_Estoque, Fornecedor) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, mercadoria.getNome());
            stmt.setString(2, "Geral"); 
            stmt.setString(3, mercadoria.getModelo());
            stmt.setString(4, mercadoria.getDescrição());
            stmt.setDouble(5, mercadoria.getPrecoUnitario());
            stmt.setInt(6, mercadoria.getQuantidade());
            stmt.setString(7, mercadoria.getFornecedor());
            
            stmt.execute();
            System.out.println("Mercadoria salva com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar mercadoria: " + e.getMessage());
        }
    }
    
    public static void atualizarMercadoria(Mercadoria mercadoria) {
        String sql = "UPDATE Mercadoria SET Nome = ?, Modelo = ?, Descricao = ?, Preco_Unitario = ?, Quantidade_Estoque = ?, Fornecedor = ? WHERE Codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mercadoria.getNome());
            stmt.setString(2, mercadoria.getModelo());
            stmt.setString(3, mercadoria.getDescrição());
            stmt.setDouble(4, mercadoria.getPrecoUnitario());
            stmt.setInt(5, mercadoria.getQuantidade());
            stmt.setString(6, mercadoria.getFornecedor());
            stmt.setInt(7, mercadoria.getCodigo());
            
            stmt.execute();
            System.out.println("Mercadoria atualizada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar mercadoria: " + e.getMessage());
        }
    }
    
    public static void deletarMercadoria(Mercadoria mercadoria) {
        String sql = "UPDATE Mercadoria SET Ativo = 0 WHERE Codigo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, mercadoria.getCodigo());
            stmt.execute();
            System.out.println("Mercadoria excluída com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar mercadoria: " + e.getMessage());
        }
    }
    
    public static List<Mercadoria> listarMercadorias() {
        String sql = "SELECT * FROM Mercadoria WHERE Ativo = 1";
        List<Mercadoria> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mercadoria m = new Mercadoria();
                m.setCodigo(rs.getInt("Codigo"));
                m.setNome(rs.getString("Nome"));
                m.setModelo(rs.getString("Modelo"));
                m.setDescrição(rs.getString("Descricao"));
                m.setPrecoUnitario(rs.getDouble("Preco_Unitario"));
                m.setQuantidade(rs.getInt("Quantidade_Estoque"));
                m.setFornecedor(rs.getString("Fornecedor"));
                m.setAtivo(rs.getBoolean("Ativo"));

                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar mercadorias: " + e.getMessage());
        }
        return lista;
    }

    public static List<Mercadoria> buscarMercadoriaFiltrada(Mercadoria mercadoria) {
        List<Object> parametros = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Mercadoria WHERE Ativo = 1 ");
        
        if (mercadoria.getNome() != null && !mercadoria.getNome().trim().isEmpty()) {
            sql.append("AND Nome LIKE ? ");
            parametros.add("%" + mercadoria.getNome() + "%");
        }
        
        if (mercadoria.getFornecedor() != null && !mercadoria.getFornecedor().trim().isEmpty()) {
            sql.append("AND Fornecedor LIKE ? ");
            parametros.add("%" + mercadoria.getFornecedor() + "%");
        }
        
        List<Mercadoria> lista = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i)); 
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Mercadoria m = new Mercadoria();
                    m.setCodigo(rs.getInt("Codigo"));
                    m.setNome(rs.getString("Nome"));
                    m.setModelo(rs.getString("Modelo"));
                    m.setDescrição(rs.getString("Descricao"));
                    m.setPrecoUnitario(rs.getDouble("Preco_Unitario"));
                    m.setQuantidade(rs.getInt("Quantidade_Estoque"));
                    m.setFornecedor(rs.getString("Fornecedor"));
                    m.setAtivo(rs.getBoolean("Ativo"));
                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro na busca filtrada: " + e.getMessage());
        }
        return lista;
    }
    
    public static Mercadoria getMercadoria(int codigo) {
        String sql = "SELECT * FROM Mercadoria WHERE Codigo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, codigo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Mercadoria m = new Mercadoria();
                    m.setCodigo(rs.getInt("Codigo"));
                    m.setNome(rs.getString("Nome"));
                    m.setModelo(rs.getString("Modelo"));
                    m.setDescrição(rs.getString("Descricao"));
                    m.setPrecoUnitario(rs.getDouble("Preco_Unitario"));
                    m.setQuantidade(rs.getInt("Quantidade_Estoque"));
                    m.setFornecedor(rs.getString("Fornecedor"));
                    m.setAtivo(rs.getBoolean("Ativo"));
                    return m;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar mercadoria: " + e.getMessage());
        }
        return null;
    }
}
