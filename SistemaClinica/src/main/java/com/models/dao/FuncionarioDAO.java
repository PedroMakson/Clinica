package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.models.entity.Funcionario;

public class FuncionarioDAO {

    private static Connection conexao;

    public FuncionarioDAO(Connection conexao) {
        FuncionarioDAO.conexao = conexao;
    }

    public boolean validarCredenciaisFuncionario(String cpfFuncionario, String senhaFuncionario) throws SQLException {
        String sql = "SELECT cpf, senha FROM Funcionario WHERE cpf = ? AND senha = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);
            stmt.setString(2, senhaFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    String senha = resultSet.getString("senha");

                    if (cpf.equals(cpfFuncionario) && senha.equals(senhaFuncionario)) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência de CPF e senha
    }

    public boolean validarStatusFuncionario(String cpfFuncionario) throws SQLException {
        String sql = "SELECT cpf, status FROM Funcionario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");
                    Boolean statusConta = resultSet.getBoolean("status");

                    if (cpf.equals(cpfFuncionario) && statusConta.equals(false)) {
                        return false;
                    } else if (cpf.equals(cpfFuncionario) && statusConta.equals(true)) {
                        return true;
                    }
                }
            }
        }

        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    public boolean validarCPFDoFuncionario(String cpfFuncionario) throws SQLException {
        String sql = "SELECT cpf FROM Funcionario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");

                    if (cpf.equals(cpfFuncionario)) {
                        return true;
                    }
                }
            }
        }

        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    public boolean verificarEmailFuncionario(String emailFuncionario) throws SQLException {
        String sql = "SELECT email FROM Funcionario WHERE email = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, emailFuncionario);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("email");

                    if (email.equals(emailFuncionario)) {
                        return true;
                    }
                }
            }
        }

        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    // Método para inserir um novo funcionário no banco de dados
    public boolean inserirFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO Funcionario (cpf, nome, dataNascimento, sexo, cargo, salario, dataContratacao, status, telefone, email, senha, cep, rua, numeroDaCasa, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setDate(3, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            stmt.setString(4, funcionario.getSexo());
            stmt.setString(5, funcionario.getCargo());
            stmt.setDouble(6, funcionario.getSalario());
            stmt.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now()));
            stmt.setBoolean(8, funcionario.getStatus());
            stmt.setString(9, funcionario.getTelefone());
            stmt.setString(10, funcionario.getEmail());
            stmt.setString(11, funcionario.getSenha());
            stmt.setString(12, funcionario.getCep());
            stmt.setString(13, funcionario.getRua());
            stmt.setInt(14, funcionario.getNumeroDaCasa());
            stmt.setString(15, funcionario.getBairro());
            stmt.setString(16, funcionario.getCidade());
            stmt.setString(17, funcionario.getUf());

            int rowsAffected = stmt.executeUpdate();

            // Verifica se alguma linha foi afetada (ou seja, se a inserção foi
            // bem-sucedida)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de exceção
        }
    }

    // Método para retornar todos os dados de um funcionário com base no CPF
    public Funcionario retornarFuncionarioPorCPF(String cpf) {
        String sql = "SELECT * FROM Funcionario WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Recupera os dados do ResultSet e cria um objeto Funcionario
                Funcionario funcionario = new Funcionario(
                        resultSet.getString("cpf"),
                        resultSet.getString("nome"),
                        resultSet.getDate("dataNascimento"),
                        resultSet.getString("sexo"),
                        resultSet.getString("cargo"),
                        resultSet.getDouble("salario"),
                        resultSet.getDate("dataContratacao"),
                        resultSet.getBoolean("status"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("senha"),
                        resultSet.getString("cep"),
                        resultSet.getString("rua"),
                        resultSet.getInt("numeroDaCasa"),
                        resultSet.getString("bairro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("uf"));
                return funcionario;
            } else {
                // Retorna null se nenhum funcionário for encontrado com o CPF especificado
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de exceção
        }
    }

    // Método que atualiza um atributo específico do funcionário com base no CPF
    public boolean atualizarAtributoFuncionario(String cpf, int opcao, String novoValor) throws SQLException {
        String sql = "";

        switch (opcao) {
            case 1:
                sql = "UPDATE Funcionario SET nome = ? WHERE cpf = ?";
                break;
            case 2:
                // Converter a string em uma data
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dataNascimento;
                try {
                    dataNascimento = dateFormat.parse(novoValor);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                sql = "UPDATE Funcionario SET dataNascimento = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setDate(1, new java.sql.Date(dataNascimento.getTime()));
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 3:
                sql = "UPDATE Funcionario SET sexo = ? WHERE cpf = ?";
                break;
            case 4:
                sql = "UPDATE Funcionario SET cargo = ? WHERE cpf = ?";
                break;
            case 5:
                // Converter a string em um double
                double salario;
                try {
                    salario = Double.parseDouble(novoValor);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                sql = "UPDATE Funcionario SET salario = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setDouble(1, salario);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 6:
                sql = "UPDATE Funcionario SET telefone = ? WHERE cpf = ?";
                break;
            case 7:
                sql = "UPDATE Funcionario SET email = ? WHERE cpf = ?";
                break;
            case 8:
                // Converter a string em um boolean
                boolean status = novoValor.equalsIgnoreCase("ativo");
                sql = "UPDATE Funcionario SET status = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setBoolean(1, status);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 9:
                sql = "UPDATE Funcionario SET cep = ? WHERE cpf = ?";
                break;
            case 10:
                sql = "UPDATE Funcionario SET rua = ? WHERE cpf = ?";
                break;
            case 11:
                // Converter a string em um inteiro
                int numeroDaCasa;
                try {
                    numeroDaCasa = Integer.parseInt(novoValor);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                sql = "UPDATE Funcionario SET numeroDaCasa = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setInt(1, numeroDaCasa);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 12:
                sql = "UPDATE Funcionario SET bairro = ? WHERE cpf = ?";
                break;
            case 13:
                sql = "UPDATE Funcionario SET cidade = ? WHERE cpf = ?";
                break;
            case 14:
                sql = "UPDATE Funcionario SET uf = ? WHERE cpf = ?";
                break;
            default:
                return false;
        }

        // Para os casos que não necessitam de conversão de tipo
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoValor);
            stmt.setString(2, cpf);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public boolean mudarSenhaFuncionario(String cpf, String novaSenha) {
        String sql = "UPDATE Funcionario SET senha = ? WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novaSenha);
            stmt.setString(2, cpf);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna falso em caso de exceção
        }
    }

}