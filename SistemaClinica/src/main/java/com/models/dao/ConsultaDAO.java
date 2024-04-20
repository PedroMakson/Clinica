package com.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.App;
import com.models.entity.Consulta;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.Servico;

public class ConsultaDAO {

    private static Connection conexao;
    private static ServicoDAO servicoDAO = new ServicoDAO(conexao);

    public ConsultaDAO(Connection conexao) {
        ConsultaDAO.conexao = conexao;
        ServicoDAO.conexao = conexao;
    }

    public boolean inserirConsulta(Funcionario funcionario, Paciente paciente, Servico servico, Consulta consulta) {
        String sql = "INSERT INTO Consulta (funcionario_cpf, funcionario_nome, paciente_cpf, paciente_nome, " +
                "codigoServico, nomeServico, valorServico, dataConsulta, horario, statusConsulta, statusPagamento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getNome());
            stmt.setInt(5, servico.getCodigo());
            stmt.setString(6, servico.getNome());
            stmt.setDouble(7, servico.getValor());
            stmt.setDate(8, new java.sql.Date(consulta.getData().getTime()));
            stmt.setTime(9, java.sql.Time.valueOf(consulta.getHorario()));
            stmt.setString(10, consulta.getStatusConsulta());
            stmt.setBoolean(11, consulta.isPagamento());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existeConsultaParaDataHora(LocalDate data, LocalTime horario) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Consulta WHERE dataConsulta = ? AND horario = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(data));
            stmt.setTime(2, java.sql.Time.valueOf(horario));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Retorna true se existir pelo menos uma consulta para o horário especificado
                }
            }
        }
        return false; // Retorna false se não houver consulta para o horário especificado
    }

    public LocalDate getDataConsultaPeloId(int idConsulta) throws SQLException {
        String sql = "SELECT dataConsulta FROM Consulta WHERE consulta_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Converte o java.sql.Date para LocalDate
                Date dataConsulta = rs.getDate("dataConsulta");
                return dataConsulta.toLocalDate();
            }
        }
        return null; // Retorna null se não encontrar a consulta com o ID especificado
    }

    public boolean verificarConsultaPeloId(int idConsulta) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Consulta WHERE consulta_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    return count > 0; // Retorna true se existir uma consulta com o ID especificado
                }
            }
        }
        return false; // Retorna false se não houver consulta com o ID especificado
    }

    public void retornarConsultasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) throws SQLException {
        String sql = "SELECT * FROM Consulta WHERE dataConsulta BETWEEN ? AND ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(dataInicio));
            stmt.setDate(2, java.sql.Date.valueOf(dataFim));

            try (ResultSet rs = stmt.executeQuery()) {
                App.limparTela();
                while (rs.next()) {
                    int consultaId = rs.getInt("consulta_id");
                    String funcionarioNome = rs.getString("funcionario_nome");
                    String pacienteCpf = rs.getString("paciente_cpf");
                    String pacienteNome = rs.getString("paciente_nome");
                    String nomeServico = rs.getString("nomeServico");

                    // Exibição da tabela para cada consulta
                    System.out.println(
                            "\n+-------------+---------------+-----------------+---------------+-----------------+");
                    System.out
                            .println(
                                    "| CONSULTA ID | FUNCIONÁRIO   | PACIENTE CPF    | PACIENTE NOME | NOME DO SERVIÇO |");
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");
                    System.out.printf("| %-11d | %-14s| %-13s   | %-14s| %-14s  |\n", consultaId, funcionarioNome,
                            pacienteCpf, pacienteNome, nomeServico);
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");

                    // Exibição das informações sobre o serviço
                    System.out
                            .println(
                                    "| VALOR DO    | DATA          | HORÁRIO         | STATUS        | STATUS DO       |");
                    System.out
                            .println(
                                    "| SERVIÇO (R$)| CONSULTA      |                 | CONSULTA      | PAGAMENTO       |");
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+");
                    double valorServico = rs.getDouble("valorServico");
                    LocalDate dataConsulta = rs.getDate("dataConsulta").toLocalDate();
                    LocalTime horario = rs.getTime("horario").toLocalTime();
                    String statusConsulta = rs.getString("statusConsulta");
                    boolean statusPagamento = rs.getBoolean("statusPagamento");

                    System.out.printf("| R$%-10.2f| %-13s | %-15s | %-14s| %-15s |\n", valorServico, dataConsulta,
                            horario,
                            statusConsulta, (statusPagamento ? "Pago" : "Não pago"));
                    System.out
                            .println(
                                    "+-------------+---------------+-----------------+---------------+-----------------+\n");
                }
            }
        }
    }

    public boolean atualizarCodigoNomeValorServico(int idConsulta, int novoCodigo) throws SQLException {
        String novoNomeServico = servicoDAO.retornarNomeServico(novoCodigo);
        double novoValorServico = servicoDAO.retornarValorServico(novoCodigo);

        if (novoNomeServico == null) {
            System.out.println("O código do serviço especificado não existe.");
            return false;
        }

        String sql = "UPDATE Consulta SET codigoServico = ?, nomeServico = ?, valorServico = ? WHERE consulta_id = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novoCodigo);
            stmt.setString(2, novoNomeServico);
            stmt.setDouble(3, novoValorServico);
            stmt.setInt(4, idConsulta);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public boolean atualizarAtributosConsulta(int idConsulta, int opcao, String novoValor) throws SQLException {
        String sql = "";

        switch (opcao) {
            case 1:
                return atualizarCodigoNomeValorServico(idConsulta, Integer.parseInt(novoValor));
            case 2:
                sql = "UPDATE Consulta SET dataConsulta = ? WHERE consulta_id = ?";
                break;
            case 3:
                sql = "UPDATE Consulta SET horario = ? WHERE consulta_id = ?";
                break;
            case 4:
                sql = "UPDATE Consulta SET statusConsulta = ? WHERE consulta_id = ?";
                break;
            case 5:
                sql = "UPDATE Consulta SET statusPagamento = ? WHERE consulta_id = ?";
                break;
            default:
                return false;
        }

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Converte a String para LocalDate ou LocalTime de acordo com a opção
            if (opcao == 2 && !novoValor.isEmpty()) {
                // Converte a String para LocalDate
                LocalDate novaData = LocalDate.parse(novoValor, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                // Define a data formatada para a instrução SQL
                stmt.setDate(1, java.sql.Date.valueOf(novaData));
            } else if (opcao == 3 && !novoValor.isEmpty()) {
                // Converte a String para LocalTime
                LocalTime novoHorario = LocalTime.parse(novoValor, DateTimeFormatter.ofPattern("HH:mm"));
                // Converte para java.sql.Time
                stmt.setTime(1, java.sql.Time.valueOf(novoHorario));
            } else if (opcao == 5) {
                // Converte a String para boolean
                boolean statusPagamento = novoValor.equalsIgnoreCase("pago");
                stmt.setBoolean(1, statusPagamento);
                atualizarStatusConsultaParaConcluido(idConsulta);
            } else {
                stmt.setString(1, novoValor);
            }
            stmt.setInt(2, idConsulta);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (DateTimeParseException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void atualizarStatusConsultaParaConcluido(int idConsulta) throws SQLException {
        String sql = "UPDATE Consulta SET statusConsulta = 'Concluído' WHERE consulta_id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idConsulta);
            stmt.executeUpdate();
        }
    }
}