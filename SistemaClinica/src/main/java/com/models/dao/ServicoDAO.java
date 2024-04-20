package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.App;
import com.models.entity.Servico;

public class ServicoDAO {

    static Connection conexao;

    public ServicoDAO(Connection conexao) {
        ServicoDAO.conexao = conexao;
    }

    public boolean verificarNomeServico(String nomeServico) throws SQLException {
        String sql = "SELECT nome FROM Servicos WHERE nome = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");

                    if (nome.equals(nomeServico)) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência
    }

    public boolean verificarCodigoServico(int codigoServico) throws SQLException {
        String sql = "SELECT codigoServico FROM Servicos WHERE codigoServico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int codigo = resultSet.getInt("codigoServico");

                    if (codigo == codigoServico) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência
    }

    public boolean verificarStatusServico(int codigoServico) throws SQLException {
        String sql = "SELECT status FROM Servicos WHERE codigoServico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    boolean status = resultSet.getBoolean("status");
                    return status;
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência
    }

    public boolean inserirServico(Servico servico) {
        String sql = "INSERT INTO Servicos (nome, valor, status) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, servico.getNome()); // Obter nome do serviço do objeto Servico
            stmt.setDouble(2, servico.getValor()); // Obter valor do serviço do objeto Servico
            stmt.setBoolean(3, servico.getStatus()); // Obter status do serviço do objeto Servico

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exibirServicosCadastrados() throws SQLException {
        String sql = "SELECT codigoServico, nome, valor, status FROM Servicos ORDER BY codigoServico ASC";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            try (ResultSet resultSet = stmt.executeQuery()) {
                App.limparTela();
                System.out.println("+----------------------------------------------+");
                System.out.println("|    S E R V I Ç O S  C A D A S T R A D O S    |");
                System.out.println("+----------------------------------------------+");

                while (resultSet.next()) {
                    int codigoServico = resultSet.getInt("codigoServico");
                    String nome = resultSet.getString("nome");
                    double valor = resultSet.getDouble("valor");
                    boolean status = resultSet.getBoolean("status");

                    // Imprime cada serviço em uma nova linha
                    System.out.printf("| Código    | %d\n", codigoServico);
                    System.out.printf("| Nome      | %s\n", nome);
                    System.out.printf("| Valor     | R$%.2f\n", valor);
                    System.out.printf("| Status    | %s\n", (status ? "Ativo" : "Inativo"));
                    System.out.println("+-----------+----------------------------------+");
                }
                System.out.println();
            }
        }
    }

    public boolean atualizarAtributoServico(int codigoServico, int opcao, String novoValor) throws SQLException {
        String sql = "";

        switch (opcao) {
            case 1:
                sql = "UPDATE Servicos SET nome = ? WHERE codigoServico = ?";
                break;
            case 2:
                sql = "UPDATE Servicos SET valor = ? WHERE codigoServico = ?";
                break;
            case 3:
                sql = "UPDATE Servicos SET status = ? WHERE codigoServico = ?";
                break;
            default:
                return false; // Retorna false se a opção não for válida
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Define o valor apropriado no PreparedStatement com base no tipo de atributo
            if (opcao == 1) {
                stmt.setString(1, novoValor);
            } else if (opcao == 2) {
                // Converter a string em um double
                double valor;
                try {
                    valor = Double.parseDouble(novoValor);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                stmt.setDouble(1, valor);
            } else if (opcao == 3) {
                // Converter a string em um booleano
                boolean status;
                if (novoValor.equalsIgnoreCase("ativo")) {
                    status = true;
                } else if (novoValor.equalsIgnoreCase("inativo")) {
                    status = false;
                } else {
                    return false; // Retorna false se o valor não for "ativo" ou "inativo"
                }
                stmt.setBoolean(1, status);
            }

            stmt.setInt(2, codigoServico);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public Servico retornarServicoPorCodigo(int codigoServico) {
        String sql = "SELECT * FROM Servicos WHERE codigoServico = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Recupera os dados do ResultSet e cria um objeto Servico
                Servico servico = new Servico(
                        resultSet.getInt("codigoServico"),
                        resultSet.getString("nome"),
                        resultSet.getDouble("valor"),
                        resultSet.getBoolean("status"));
                return servico;
            } else {
                // Retorna null se nenhum serviço for encontrado com o código especificado
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de exceção
        }
    }

    public String retornarNomeServico(int codigoServico) throws SQLException {
        String sql = "SELECT nome FROM Servicos WHERE codigoServico = ?";
        String nomeServico = null;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    nomeServico = resultSet.getString("nome"); // Ajuste aqui para "nome"
                }
            }
        }
        return nomeServico;
    }

    public double retornarValorServico(int codigoServico) throws SQLException {
        String sql = "SELECT valor FROM Servicos WHERE codigoServico = ?";
        double valorServico = 0.0;

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, codigoServico);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    valorServico = resultSet.getDouble("valor");
                }
            }
        }
        return valorServico;
    }
}