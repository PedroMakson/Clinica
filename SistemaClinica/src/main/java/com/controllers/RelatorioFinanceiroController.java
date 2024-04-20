package com.controllers;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import com.App;
import com.models.entity.Conexao;

public class RelatorioFinanceiroController {

    private static Connection conexao = Conexao.getInstancia();
    private static com.models.dao.RelatorioFinanceiroDAO relatorioFinanceiroDAO = new com.models.dao.RelatorioFinanceiroDAO(
            conexao);
    static Scanner scanner = new Scanner(System.in);

    public static void gerarRelatorioFinanceiro() {
        Date dataInicio = null;
        Date dataFim = null;

        // Validação para a data de início
        while (dataInicio == null) {
            System.out.println("+-------------------------------------------+");
            System.out.println("|          R  E  L  A  T  Ó  R  I  O        |");
            System.out.println("|        F  I  N  A  N  C  E  I  R  O       |");
            System.out.println("+-------------------------------------------+");
            System.out.print("-> Data de Início (AAAA-MM-DD): ");
            String dataInicioStr = scanner.nextLine();

            // Verifica se a entrada não é nula ou vazia
            if (Objects.isNull(dataInicioStr) || dataInicioStr.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > A data de início não pode ser nula. Tente novamente. <\n");
                continue;
            }

            // Converte a string para a data
            dataInicio = stringParaData(dataInicioStr);
        }

        App.limparTela();

        // Validação para a data de fim
        while (dataFim == null) {
            System.out.println("+-------------------------------------------+");
            System.out.println("|          R  E  L  A  T  Ó  R  I  O        |");
            System.out.println("|        F  I  N  A  N  C  E  I  R  O       |");
            System.out.println("+-------------------------------------------+");
            System.out.print("-> Data de Fim (AAAA-MM-DD): ");
            String dataFimStr = scanner.nextLine();

            // Verifica se a entrada não é nula ou vazia
            if (Objects.isNull(dataFimStr) || dataFimStr.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > A data de fim não pode ser nula. Tente novamente. <\n");
                continue;
            }

            // Converte a string para a data
            dataFim = stringParaData(dataFimStr);
        }

        // Chamando o método gerarRelatorioConsulta() com as datas informadas
        if (relatorioFinanceiroDAO.gerarRelatorioFinanceiro(dataInicio, dataFim)) {
            App.limparTela();
            System.out.println("\n > Relatório gerado com sucesso! <\n");
        } else {
            App.limparTela();
            System.out.println("\n > Relatório gerado sem êxito! <\n");
        }
    }

    public static Date stringParaData(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date data = sdf.parse(dataString);
            return data; // Data válida
        } catch (ParseException e) {
            App.limparTela();
            System.out.println(
                    "\n > Erro na conversão de data. Certifique-se de usar o formato AAAA-MM-DD. Tente novamente. <\n");
            return null; // Data inválida
        }
    }
}