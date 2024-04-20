package com.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

import com.App;
import com.models.dao.FuncionarioDAO;
import com.models.dao.PacienteDAO;
import com.models.dao.PrescricaoMedicaDAO;
import com.models.entity.Conexao;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.PrescricaoMedica;

public class PrescricaoMedicaController {

    private static Connection conexao = Conexao.getInstancia();
    private static PrescricaoMedicaDAO prescricaoMedicaDAO = new PrescricaoMedicaDAO(conexao);
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
    private static PacienteDAO pacienteDAO = new PacienteDAO(conexao);
    static Scanner scanner = new Scanner(System.in);

    public static void cadastrarPrescricaoMedica() throws SQLException {
        String pacienteCPF;
        String funcionarioCPF;
        String diagnostico;
        String descricao;
        Date dataPrescricao = null;
        int numeroPrescricao = 0;
        Paciente paciente;
        Funcionario funcionario;
        LocalDate parsedDate = null;

        System.out.println("+-------------------------------------------+");
        System.out.println("|       C  A  D  A  S  T  R  O    D  E      |");
        System.out.println("|     P R E S C R I Ç Ã O   M É D I C A     |");
        System.out.println("+-------------------------------------------+");

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        while (true) {
            System.out.print("-> CPF do cliente: ");
            pacienteCPF = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!pacienteCPF.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (pacienteCPF.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!PacienteController.verificarExistenciaPacienteCPF(pacienteCPF)) {
                App.limparTela();
                System.out.println("\n > Não existe um paciente com esse CPF, tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        }

        paciente = pacienteDAO.retornarPacientePorCPF(pacienteCPF);

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        do {
            System.out.print("-> CPF do profissional: ");
            funcionarioCPF = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!funcionarioCPF.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (funcionarioCPF.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!FuncionarioController.verificarExistenciaFuncionarioCPF(funcionarioCPF)) {
                App.limparTela();
                System.out.println("\n > Não existe um profissional com esse CPF, tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        funcionario = funcionarioDAO.retornarFuncionarioPorCPF(funcionarioCPF);

        // Validação da data da prescrição
        while (dataPrescricao == null) {
            System.out.print("-> Data da prescrição (AAAA-MM-DD): ");
            String dataString = scanner.nextLine();

            try {
                // Converte a string para um objeto LocalDate
                parsedDate = LocalDate.parse(dataString);

                // Obtém a data de hoje
                LocalDate dataAtual = LocalDate.now();

                // Verifica se a data da prescrição é igual à data atual
                if (parsedDate.isEqual(dataAtual)) {
                    // Data de prescrição válida
                    dataPrescricao = java.sql.Date.valueOf(parsedDate);
                    break;
                } else {
                    App.limparTela();
                    System.out.println("\n > A data da prescrição deve ser igual à data de hoje. <\n");
                    continue;
                }
            } catch (DateTimeParseException e) {
                App.limparTela();
                System.out.println("\n > Formato de data inválido. Utilize o formato AAAA-MM-DD. <\n");
                continue;
            }
        }

        // Solicitar e validar o diagnóstico
        do {
            System.out.print("-> Diagnóstico: ");
            diagnostico = scanner.nextLine();

            // Verificar se o diagnóstico não é nulo ou vazio
            if (diagnostico == null || diagnostico.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n> O diagnóstico não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verificar o tamanho máximo do diagnóstico
            if (diagnostico.length() > 50) {
                App.limparTela();
                System.out.println("\n> O diagnóstico deve ter no máximo 50 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Solicitar e validar a descrição
        do {
            System.out.print("-> Descrição: ");
            descricao = scanner.nextLine();

            // Verificar se a descrição não é nula ou vazia
            if (descricao == null || descricao.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n> A descrição não pode ser nula ou vazia. Tente novamente. <\n");
                continue;
            }

            // Verificar o tamanho máximo da descrição
            if (descricao.length() > 100) {
                App.limparTela();
                System.out.println("\n> A descrição deve ter no máximo 100 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        PrescricaoMedica prescricao = new PrescricaoMedica(numeroPrescricao, paciente, funcionario,
                dataPrescricao, diagnostico, descricao);

        // Chamar o método para inserir a prescrição médica no banco de dados
        if (prescricaoMedicaDAO.inserirPrescricaoMedica(prescricao)) {
            App.limparTela();
            prescricaoMedicaDAO.inserirProntuarioMedico(funcionario.getCpf(), funcionario.getNome(), paciente.getCpf(),
                    paciente.getNome(), "Prescrição Médica", diagnostico,
                    descricao, (java.sql.Date) dataPrescricao);

            System.out.println("\n > Prescrição médica cadastrada COM SUCESSO! <\n");
        } else {
            System.out.println("\n > Falha ao cadastrar a prescrição médica! <\n");
        }
    }

    public static void visualizarPrescricoesMedicas() throws SQLException {
        String cpfPaciente;
        int contador = 1;

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        do {
            System.out.println(" Tentativa " + (contador) + "/3.");
            System.out.println("+----------------------------------------------+");
            System.out.println("|     V  I  S  U  A  L  I  Z  A  Ç  Ã  O       |");
            System.out.println("|        P  R  E  S  C  R  I  Ç  Ã  O          |");
            System.out.println("+----------------------------------------------+");
            System.out.print("-> CPF do paciente: ");
            cpfPaciente = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!cpfPaciente.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                contador++;
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (cpfPaciente.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                contador++;
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!PacienteController.verificarExistenciaPacienteCPF(cpfPaciente)) {
                App.limparTela();
                System.out.println("\n > Não existe um paciente com esse CPF, tente novamente. <\n");
                contador++;
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (contador <= 3);

        if (contador <= 3) {
            prescricaoMedicaDAO.exibirPrescricoesPorCPF(cpfPaciente);
        } else {
            System.out.println("Você excedeu o número máximo de tentativas. Operação cancelada.");
        }
    }

    public static void excluirPrescricaoMedica() throws SQLException {

        int contador = 1;
        int idPrescricao = 0;

        do {
            System.out.println(" Tentativa " + contador + "/3.");
            System.out.println("+----------------------------------------------+");
            System.out.println("|      E X C L U I R  P R E S C R I Ç Ã O      |");
            System.out.println("+----------------------------------------------+");
            System.out.print("| ID da prescrição: ");
            idPrescricao = scanner.nextInt();

            scanner.nextLine(); // Limpa o buffer do scanner

            try {
                if (!prescricaoMedicaDAO.existePrescricaoMedicaPorId(idPrescricao)) {
                    App.limparTela();
                    System.out.println("\n > Prescrição inexistente no banco de dados. <\n");
                    contador++;
                }
            } catch (SQLException e) {
                System.out.println("Erro ao acessar o banco de dados: " + e.getMessage());
                return;
            }

            if (contador == 3) {
                break;
            }

        } while (!prescricaoMedicaDAO.existePrescricaoMedicaPorId(idPrescricao));

        if (prescricaoMedicaDAO.deletarPrescricaoMedicaPorId(idPrescricao)) {
            App.limparTela();
            System.out.println("\n > Prescrição deletada COM SUCESSO! <\n");
        } else {
            System.out.println("\n > Prescrição deletada SEM SUCESSO! <\n");
        }
    }

    public static void visualizarProntuario() throws SQLException {
        String cpfPaciente;
        int contador = 1;

        // Loop para garantir que o CPF seja válido e exista no banco de dados
        do {
            System.out.println(" Tentativa " + (contador) + "/3.");
            System.out.println("+----------------------------------------------+");
            System.out.println("|     V  I  S  U  A  L  I  Z  A  Ç  Ã  O       |");
            System.out.println("|        P  R  O  N  T  U  Á  R  I  O          |");
            System.out.println("+----------------------------------------------+");
            System.out.print("-> CPF do paciente: ");
            cpfPaciente = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!cpfPaciente.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                contador++;
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (cpfPaciente.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                contador++;
                continue;
            }

            // Verifica se um CPF existe no banco de dados
            if (!PacienteController.verificarExistenciaPacienteCPF(cpfPaciente)) {
                App.limparTela();
                System.out.println("\n > Não existe um paciente com esse CPF, tente novamente. <\n");
                contador++;
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (contador <= 3);

        if (contador <= 3) {
            prescricaoMedicaDAO.obterProntuarioMedicoCPF(cpfPaciente);
        }

    }

}