package com.models.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RelatorioFinanceiroDAO {

    private static Connection conexao;

    public RelatorioFinanceiroDAO(Connection conexao) {
        RelatorioFinanceiroDAO.conexao = conexao;
    }

    public boolean gerarRelatorioFinanceiro(Date dataInicio, Date dataFim) {
        Statement stmt = null;
        ResultSet rs = null;

        // Configurações para o Excel
        String[] colunas = { "ID Consulta", "Funcionário CPF", "Funcionário Nome",
                "Paciente CPF", "Paciente Nome", "Código Serviço",
                "Nome Serviço", "Valor Serviço", "Data Consulta", "Horário", "Status Consulta",
                "Status Pagamento" };

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataInicioStr = sdf.format(dataInicio);
            String dataFimStr = sdf.format(dataFim);

            // Criar uma instrução SQL para selecionar as consultas dentro do intervalo de
            // datas
            String sql = "SELECT * FROM Consulta WHERE dataConsulta BETWEEN '" + dataInicioStr + "' AND '" + dataFimStr
                    + "'";

            // Criar uma instrução SQL
            stmt = conexao.createStatement();
            rs = stmt.executeQuery(sql);

            // Criar um novo arquivo Excel
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Relatório Consulta");

            // Criar a linha de cabeçalho
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
            }

            // Preencher os dados do relatório
            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                for (int i = 1; i <= colunas.length; i++) {
                    Cell cell = row.createCell(i - 1);
                    cell.setCellValue(rs.getString(i));
                }
            }

            // Escrever o conteúdo no arquivo Excel
            String caminhoArquivo = "C:/Users/Pedro Markson/Downloads/RelatoriosClinica/RelatorioConsulta.xlsx";
            FileOutputStream outputStream = new FileOutputStream(caminhoArquivo);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            return true;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return false; // Em caso de erro
    }
}
