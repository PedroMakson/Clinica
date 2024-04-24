package com.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.models.entity.Paciente;

public class PacienteDAO {

    private static Connection conexao;

    public PacienteDAO(Connection conexao) {
        PacienteDAO.conexao = conexao;
    }

    public boolean validarCPFDoPaciente(String cpfPaciente) throws SQLException {
        String sql = "SELECT cpf FROM Paciente WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfPaciente);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String cpf = resultSet.getString("cpf");

                    if (cpf.equals(cpfPaciente)) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    public boolean verificarEmailPaciente(String emailPaciente) throws SQLException {
        String sql = "SELECT email FROM Paciente WHERE email = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, emailPaciente);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String email = resultSet.getString("email");

                    if (email.equals(emailPaciente)) {
                        return true;
                    }
                }
            }
        }
        return false; // Retorna false se não encontrar uma correspondência de CPF
    }

    // Método para inserir um novo paciente no banco de dados
    public boolean inserirPaciente(Paciente paciente) {
        String sql = "INSERT INTO Paciente (cpf, nome, dataNascimento, sexo, peso, altura, tipoSanguineo, telefone, email, senha, cep, rua, numeroDaCasa, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, paciente.getCpf());
            stmt.setString(2, paciente.getNome());
            stmt.setDate(3, new java.sql.Date(paciente.getDataNascimento().getTime()));
            stmt.setString(4, paciente.getSexo());
            stmt.setDouble(5, paciente.getPeso());
            stmt.setDouble(6, paciente.getAltura());
            stmt.setString(7, paciente.getTipoSanguineo());
            stmt.setString(8, paciente.getTelefone());
            stmt.setString(9, paciente.getEmail());
            stmt.setString(10, paciente.getSenha());
            stmt.setString(11, paciente.getCep());
            stmt.setString(12, paciente.getRua());
            stmt.setInt(13, paciente.getNumeroDaCasa());
            stmt.setString(14, paciente.getBairro());
            stmt.setString(15, paciente.getCidade());
            stmt.setString(16, paciente.getUf());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de exceção
        }
    }

    // Método para retornar todos os dados de um paciente com base no CPF
    public Paciente retornarPacientePorCPF(String cpfPaciente) {
        String sql = "SELECT * FROM Paciente WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfPaciente);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                // Recupera os dados do ResultSet e cria um objeto Paciente
                Paciente paciente = new Paciente(
                        resultSet.getString("cpf"),
                        resultSet.getString("nome"),
                        resultSet.getDate("dataNascimento"),
                        resultSet.getString("sexo"),
                        resultSet.getDouble("peso"),
                        resultSet.getDouble("altura"),
                        resultSet.getString("tipoSanguineo"),
                        resultSet.getString("telefone"),
                        resultSet.getString("email"),
                        resultSet.getString("senha"),
                        resultSet.getString("cep"),
                        resultSet.getString("rua"),
                        resultSet.getInt("numeroDaCasa"),
                        resultSet.getString("bairro"),
                        resultSet.getString("cidade"),
                        resultSet.getString("uf"));
                return paciente;
            } else {
                // Retorna null se nenhum paciente for encontrado com o CPF especificado
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Retorna null em caso de exceção
        }
    }

    // Método que atualiza um atributo específico do paciente com base no CPF
    public boolean atualizarAtributoPaciente(String cpf, int opcao, String novoValor) throws SQLException {
        String sql = "";

        switch (opcao) {
            case 1:
                sql = "UPDATE Paciente SET nome = ? WHERE cpf = ?";
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
                sql = "UPDATE Paciente SET dataNascimento = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setDate(1, new java.sql.Date(dataNascimento.getTime()));
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 3:
                sql = "UPDATE Paciente SET sexo = ? WHERE cpf = ?";
                break;
            case 4:
                sql = "UPDATE Paciente SET peso = ? WHERE cpf = ?";
                // Converter a string em um float
                float peso;
                try {
                    peso = Float.parseFloat(novoValor);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setFloat(1, peso);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 5:
                sql = "UPDATE Paciente SET altura = ? WHERE cpf = ?";
                // Converter a string em um float
                float altura;
                try {
                    altura = Float.parseFloat(novoValor);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return false; // Retorna false em caso de erro na conversão
                }
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setFloat(1, altura);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 6:
                sql = "UPDATE Paciente SET tipoSanguineo = ? WHERE cpf = ?";
                break;
            case 7:
                sql = "UPDATE Paciente SET telefone = ? WHERE cpf = ?";
                break;
            case 8:
                sql = "UPDATE Paciente SET email = ? WHERE cpf = ?";
                break;
            case 9:
                sql = "UPDATE Paciente SET cep = ? WHERE cpf = ?";
                break;
            case 10:
                sql = "UPDATE Paciente SET rua = ? WHERE cpf = ?";
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
                sql = "UPDATE Paciente SET numeroDaCasa = ? WHERE cpf = ?";
                try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                    stmt.setInt(1, numeroDaCasa);
                    stmt.setString(2, cpf);
                    int linhasAfetadas = stmt.executeUpdate();
                    return linhasAfetadas > 0;
                }
            case 12:
                sql = "UPDATE Paciente SET bairro = ? WHERE cpf = ?";
                break;
            case 13:
                sql = "UPDATE Paciente SET cidade = ? WHERE cpf = ?";
                break;
            case 14:
                sql = "UPDATE Paciente SET uf = ? WHERE cpf = ?";
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

    // Método usado para realizar os testes
    public boolean deletarPaciente(Paciente paciente) {
        String sql = "DELETE FROM Paciente WHERE cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, paciente.getCpf());

            int rowsAffected = stmt.executeUpdate();

            // Verifica se alguma linha foi afetada (ou seja, se a exclusão foi
            // bem-sucedida)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Retorna false em caso de exceção
        }
    }

}