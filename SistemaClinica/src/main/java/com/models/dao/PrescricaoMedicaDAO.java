package com.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import com.App;
import com.models.entity.PrescricaoMedica;

public class PrescricaoMedicaDAO {

    private static Connection conexao;

    public PrescricaoMedicaDAO(Connection conexao) {
        PrescricaoMedicaDAO.conexao = conexao;
    }

    public boolean inserirPrescricaoMedica(PrescricaoMedica prescricao) {
        String sql = "INSERT INTO PrescricaoMedica (paciente_cpf, funcionario_cpf, dataPrescricao, diagnostico, descricao) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            // Define os valores dos parâmetros na consulta SQL
            stmt.setString(1, prescricao.getPaciente().getCpf());
            stmt.setString(2, prescricao.getFuncionario().getCpf());
            stmt.setDate(3, new java.sql.Date(prescricao.getData().getTime()));
            stmt.setString(4, prescricao.getDiagnostico());
            stmt.setString(5, prescricao.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void exibirPrescricoesPorCPF(String cpf) throws SQLException {
        String sql = "SELECT pm.numeroPrescricao, f.nome AS nome_funcionario, p.nome AS nome_paciente, " +
                "pm.dataPrescricao, pm.diagnostico, pm.descricao " +
                "FROM PrescricaoMedica pm " +
                "INNER JOIN Paciente p ON pm.paciente_cpf = p.cpf " +
                "INNER JOIN Funcionario f ON pm.funcionario_cpf = f.cpf " +
                "WHERE pm.paciente_cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                App.limparTela();
                System.out.println(
                        "+------------------+------------------+------------------+---------------------+---------------------+--------------------------------------------------------+");
                System.out.println(
                        "| Num. Prescrição  | Nome do Médico   | Nome do Paciente | Data da Prescrição  |     Diagnóstico     |                       Descrição                        |");
                System.out.println(
                        "+------------------+------------------+------------------+---------------------+---------------------+--------------------------------------------------------+");

                while (rs.next()) {
                    int numeroPrescricao = rs.getInt("numeroPrescricao");
                    String nomeFuncionario = rs.getString("nome_funcionario");
                    String nomePaciente = rs.getString("nome_paciente");
                    java.sql.Date dataPrescricao = rs.getDate("dataPrescricao");
                    String diagnostico = rs.getString("diagnostico");
                    String descricao = rs.getString("descricao");

                    System.out.printf("| %-16d | %-16s | %-16s | %-20s| %-20s| %-55s|\n", numeroPrescricao,
                            nomeFuncionario, nomePaciente,
                            dataPrescricao.toLocalDate(), diagnostico, descricao);
                }
                System.out.println(
                        "+------------------+------------------+------------------+---------------------+---------------------+--------------------------------------------------------+\n");
            }
        }
    }

    public boolean existePrescricaoMedicaPorId(int idPrescricao) throws SQLException {
        String sql = "SELECT 1 FROM PrescricaoMedica WHERE numeroPrescricao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPrescricao);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se houver algum resultado, indicando que o ID existe
            }
        }
    }

    public boolean deletarPrescricaoMedicaPorId(int idPrescricao) throws SQLException {
        String sql = "DELETE FROM PrescricaoMedica WHERE numeroPrescricao = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idPrescricao);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public boolean inserirProntuarioMedico(String funcionarioCpf, String funcionarioNome, String pacienteCpf,
            String pacienteNome, String tipoRegistro, String diagnosticoServico,
            String descricao, Date dataRegistro) {
        String sql = "INSERT INTO ProntuarioMedico (funcionario_cpf, funcionario_nome, paciente_cpf, paciente_nome, " +
                "tipoRegistro, diagnostico_servico, descricao, dataRegistro) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, funcionarioCpf);
            stmt.setString(2, funcionarioNome);
            stmt.setString(3, pacienteCpf);
            stmt.setString(4, pacienteNome);
            stmt.setString(5, tipoRegistro);
            stmt.setString(6, diagnosticoServico);
            stmt.setString(7, descricao);

            // Convertendo java.util.Date para java.sql.Date
            java.sql.Date sqlDataRegistro = new java.sql.Date(dataRegistro.getTime());
            stmt.setDate(8, sqlDataRegistro);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void obterProntuarioMedicoCPF(String cpfPaciente) throws SQLException {
        String sql = "SELECT id_prontuario, funcionario_nome, paciente_nome, " +
                "tipoRegistro, diagnostico_servico, descricao, dataRegistro " +
                "FROM ProntuarioMedico " +
                "WHERE paciente_cpf = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfPaciente);
            App.limparTela();

            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println(
                        "+--------------+------------------+---------------+------------------+----------------------+-------------------------------+-------------------------+");
                System.out.println(
                        "| Id Prontuário| Funcionário Nome | Paciente Nome | Tipo de Registro | Diagnóstico/Serviço  |          Descrição            |     Data de Registro    |");
                System.out.println(
                        "+--------------+------------------+---------------+------------------+----------------------+-------------------------------+-------------------------+");

                while (rs.next()) {
                    int idProntuario = rs.getInt("id_prontuario");
                    String funcionarioNome = rs.getString("funcionario_nome");
                    String pacienteNome = rs.getString("paciente_nome");
                    String tipoRegistro = rs.getString("tipoRegistro");
                    String diagnosticoServico = rs.getString("diagnostico_servico");
                    String descricao = rs.getString("descricao");
                    java.sql.Date dataRegistro = rs.getDate("dataRegistro");

                    // Limitar a descrição a 30 caracteres e formatar outras colunas para manter o
                    // mesmo tamanho
                    descricao = String.format("%-18s",
                            descricao.length() > 18 ? descricao.substring(0, 18) + "..." : descricao);
                    funcionarioNome = ajustarTamanhoString(funcionarioNome, 16);
                    pacienteNome = ajustarTamanhoString(pacienteNome, 13);
                    tipoRegistro = ajustarTamanhoString(tipoRegistro, 16);
                    diagnosticoServico = ajustarTamanhoString(diagnosticoServico, 21);

                    // Formatando a data de registro
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String dataFormatada = dateFormat.format(dataRegistro);

                    System.out.printf("| %-13d| %-16s | %-13s | %-16s | %-21s| %-30s| %-20s    |\n", idProntuario,
                            funcionarioNome, pacienteNome, tipoRegistro, diagnosticoServico, descricao,
                            dataFormatada);
                }
                System.out.println(
                        "+--------------+------------------+---------------+------------------+----------------------+-------------------------------+-------------------------+\n");

            }
        }
    }

    public boolean deletarPrescricaoMedicaPorCPF(String cpfPaciente) throws SQLException {
        String sql = "DELETE FROM PrescricaoMedica WHERE paciente_cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpfPaciente);
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        }
    }

    public void deletarUltimoProntuario() {
        String sql = "DELETE FROM ProntuarioMedico WHERE id_prontuario = (SELECT MAX(id_prontuario) FROM ProntuarioMedico)";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para ajustar o tamanho da string para manter o mesmo tamanho da coluna
    // na tabela
    private String ajustarTamanhoString(String texto, int tamanho) {
        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho - 3) + "...";
        } else {
            return String.format("%-" + tamanho + "s", texto);
        }
    }

}