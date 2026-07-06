package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.database.DatabaseConnection;
import model.domain.Funcionario;
import model.domain.TipoUsuario;

public class FuncionarioDAO {
    // operações de escrita
    public static void inserirFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (Nome, Nome_Usuario, Sexo, Data_Nascimento, Email, Tel, CPF, Tipo_Usuario, Senha) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getNomeUsuario());
            stmt.setString(3, funcionario.getSexo());
            stmt.setDate(4, java.sql.Date.valueOf(funcionario.getDataNascimento()));
            stmt.setString(5, funcionario.getEmail());
            stmt.setString(6, funcionario.getTelefone());
            stmt.setString(7, funcionario.getCpf());
            stmt.setString(8, funcionario.getTipoUsuario().name());
            stmt.setString(9, funcionario.getSenhaHash());
            
            stmt.execute();
            System.out.println("Funcionario salvo!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar funcionario: " + e.getMessage());
        }
    }
    
    public static void atualizarFuncionario(Funcionario funcionario) {
        StringBuilder sql = new StringBuilder("UPDATE Funcionario SET ");
        List<Object> parametros = new ArrayList<>();

        if (funcionario.getNome() != null && !funcionario.getNome().isBlank()) {
            sql.append("Nome = ?, "); parametros.add(funcionario.getNome());
        }
        if (funcionario.getNomeUsuario() != null && !funcionario.getNomeUsuario().isBlank()) {
            sql.append("Nome_Usuario = ?, "); parametros.add(funcionario.getNomeUsuario());
        }
        if (funcionario.getSexo() != null && !funcionario.getSexo().isBlank()) {
            sql.append("Sexo = ?, "); parametros.add(funcionario.getSexo());
        }
        if (funcionario.getDataNascimento() != null) {
            sql.append("Data_Nascimento = ?, "); parametros.add(java.sql.Date.valueOf(funcionario.getDataNascimento()));
        }
        if (funcionario.getEmail() != null && !funcionario.getEmail().isBlank()) {
            sql.append("Email = ?, "); parametros.add(funcionario.getEmail());
        }
        if (funcionario.getTelefone() != null && !funcionario.getTelefone().isBlank()) {
            sql.append("Tel = ?, "); parametros.add(funcionario.getTelefone());
        }
        if (funcionario.getCpf() != null && !funcionario.getCpf().isBlank()) {
            sql.append("CPF = ?, "); parametros.add(funcionario.getCpf());
        }
        if (funcionario.getTipoUsuario() != null) {
            sql.append("Tipo_Usuario = ?, "); parametros.add(funcionario.getTipoUsuario().name());
        }
        if (funcionario.getSenhaHash() != null && !funcionario.getSenhaHash().isBlank()) {
            sql.append("Senha = ?, "); parametros.add(funcionario.getSenhaHash());
        }

        sql.delete(sql.length() - 2, sql.length());
        sql.append(" WHERE ID_Funcionario = ?");
        parametros.add(funcionario.getIdFuncionario());

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }
            stmt.execute();
            System.out.println("Funcionario atualizado!");
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionario: " + e.getMessage());
        }
    }
    
    public static void deletarFuncionario(Funcionario funcionario) {
        String sql = "UPDATE Funcionario SET Ativo = 0 WHERE Id_Funcionario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, funcionario.getIdFuncionario());
            stmt.execute();
            System.out.println("Funcionario excluido!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar funcionario: " + e.getMessage());
        }
    }
    
    // operações de leitura
    public static List<Funcionario> listarFuncionarios() {
        String sql = "SELECT * FROM Funcionario WHERE Ativo = 1";
        List<Funcionario> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("ID_Funcionario"));
                f.setNome(rs.getString("Nome"));
                f.setNomeUsuario(rs.getString("Nome_Usuario"));
                f.setSexo(rs.getString("Sexo"));
                f.setDataNascimento(rs.getDate("Data_Nascimento").toLocalDate());
                f.setEmail(rs.getString("Email"));
                f.setTelefone(rs.getString("Tel"));
                f.setCpf(rs.getString("CPF"));
                f.setTipoUsuario(TipoUsuario.valueOf(rs.getString("Tipo_Usuario")));
                f.setAtivo(rs.getBoolean("Ativo"));

                lista.add(f);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionarios: " + e.getMessage());
        }
        return lista;
    }

    public static List<Funcionario> buscarFuncionarioFiltrado(Funcionario funcionario) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Funcionario WHERE Ativo = 1 ");
        List<Object> parametros = new ArrayList<>();

        if (funcionario.getNome() != null && !funcionario.getNome().isBlank()) {
            sql.append("AND Nome LIKE ? "); parametros.add("%" + funcionario.getNome() + "%");
        }
        if (funcionario.getNomeUsuario() != null && !funcionario.getNomeUsuario().isBlank()) {
            sql.append("AND Nome_Usuario LIKE ? "); parametros.add("%" + funcionario.getNomeUsuario() + "%");
        }
        if (funcionario.getSexo() != null && !funcionario.getSexo().isBlank()) {
            sql.append("AND Sexo LIKE ? "); parametros.add("%" + funcionario.getSexo() + "%");
        }
        if (funcionario.getEmail() != null && !funcionario.getEmail().isBlank()) {
            sql.append("AND Email LIKE ? "); parametros.add("%" + funcionario.getEmail() + "%");
        }
        if (funcionario.getTipoUsuario() != null) {
            sql.append("AND Tipo_Usuario LIKE ? "); parametros.add("%" + funcionario.getTipoUsuario().name() + "%"); // bug corrigido
        }

        List<Funcionario> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setIdFuncionario(rs.getInt("ID_Funcionario"));
                    f.setNome(rs.getString("Nome"));
                    f.setNomeUsuario(rs.getString("Nome_Usuario"));
                    f.setSexo(rs.getString("Sexo"));
                    f.setDataNascimento(rs.getDate("Data_Nascimento").toLocalDate());
                    f.setEmail(rs.getString("Email"));
                    f.setTelefone(rs.getString("Tel"));
                    f.setCpf(rs.getString("CPF"));
                    f.setTipoUsuario(TipoUsuario.valueOf(rs.getString("Tipo_Usuario")));
                    f.setAtivo(rs.getBoolean("Ativo"));
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro na busca dinâmica: " + e.getMessage());
        }

        return lista;
    }
    
    public static Funcionario getFuncionario(String nomeUsuario) {
        String sql = "SELECT * FROM Funcionario WHERE Nome_Usuario = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nomeUsuario);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setIdFuncionario(rs.getInt("ID_Funcionario"));
                    f.setNome(rs.getString("Nome"));
                    f.setNomeUsuario(rs.getString("Nome_Usuario"));
                    f.setSexo(rs.getString("Sexo"));
                    f.setDataNascimento(rs.getDate("Data_Nascimento").toLocalDate());
                    f.setEmail(rs.getString("Email"));
                    f.setTelefone(rs.getString("Tel"));
                    f.setCpf(rs.getString("CPF"));
                    f.setTipoUsuario(TipoUsuario.valueOf(rs.getString("Tipo_Usuario")));
                    f.setSenhaHash(rs.getString("Senha"));
                    f.setAtivo(rs.getBoolean("Ativo"));
                    return f;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao retornar funcionario: " + e.getMessage());
        }
        return null;
    }
}
