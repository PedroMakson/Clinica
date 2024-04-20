package com.controllers;

import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import com.App;
import com.models.dao.FuncionarioDAO;
import com.models.entity.Conexao;
import com.models.entity.Funcionario;

public class FuncionarioController {

    private static Connection conexao = Conexao.getInstancia();
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO(conexao);
    static Scanner scanner = new Scanner(System.in);

    public static boolean logar(String cpf, String senha) throws SQLException {
        if (funcionarioDAO.validarCredenciaisFuncionario(cpf, senha)) {
            return true;
        }
        return false;
    }

    public static boolean verificarStatus(String cpf) throws SQLException {
        if (funcionarioDAO.validarStatusFuncionario(cpf)) {
            return true;
        }
        return false;
    }

    public static boolean verificarExistenciaFuncionarioCPF(String cpf) throws SQLException {
        if (funcionarioDAO.validarCPFDoFuncionario(cpf)) {
            return true;
        }
        return false;
    }

    public static void cadastrarFuncionario() throws SQLException {

        // Variáveis para armazenar os dados do funcionário
        String cpf = null;
        String nome = null;
        Date dataNascimento = null;
        String sexo = null;
        String cargo = null;
        double salario = 0;
        Date dataContratacao = new Date(); // Obtém a data atual
        boolean status = true;
        String telefone = null;
        String email = null;
        String senha = "123mudar"; // Senha padrão
        String cep = null;
        String rua = null;
        int numeroDaCasa = 0;
        String bairro = null;
        String cidade = null;
        String uf = null;
        List<String> ufsValidas = Arrays.asList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
                "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

        System.out.println("+-------------------------------------------+");
        System.out.println("|      C  A  D  A  S  T  R  O    D  E       |");
        System.out.println("|    F  U  N  C  I  O  N  Á  R  I  O  S     |");
        System.out.println("+-------------------------------------------+");

        // Loop para garantir que o CPF seja válido e único
        do {
            System.out.print("-> CPF: ");
            cpf = scanner.nextLine();

            // Verifica se o CPF contém apenas dígitos
            if (!cpf.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CPF deve conter apenas números. <\n");
                continue;
            }

            // Verifica se o CPF tem 11 dígitos
            if (cpf.length() != 11) {
                App.limparTela();
                System.out.println("\n > CPF deve conter 11 números. <\n");
                continue;
            }

            // Verifica se um CPF já existe no banco de dados
            if (verificarExistenciaFuncionarioCPF(cpf)) {
                App.limparTela();
                System.out.println("\n > Já existe um usuário com esse CPF, tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o nome
        do {
            System.out.print("-> Nome completo: ");
            nome = scanner.nextLine();

            // Verifica se o nome não é nulo ou vazio
            if (Objects.isNull(nome) || nome.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O nome não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o nome não contém números
            if (nome.matches(".*\\d+.*")) {
                App.limparTela();
                System.out.println("\n > O nome não deve conter números. Tente novamente. <\n");
                continue;
            }

            // Verifica se o nome não excede o tamanho máximo permitido
            if (nome.length() > 60) {
                App.limparTela();
                System.out.println("\n > O nome deve conter no máximo 60 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para a data de nascimento
        while (dataNascimento == null) {
            System.out.print("-> Data de Nascimento (AAAA-MM-DD): ");
            String dataNascimentoStr = scanner.nextLine();

            try {
                // Verifica se a data não é nula ou vazia
                if (Objects.isNull(dataNascimentoStr) || dataNascimentoStr.trim().isEmpty()) {
                    App.limparTela();
                    System.out.println("\n > A data de nascimento não pode ser nula. Tente novamente. <\n");
                    continue;
                }

                // Verifica se a data inserida segue o formato esperado (AAAA-MM-DD)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                dataNascimento = dateFormat.parse(dataNascimentoStr);

                // Se a data inserida for posterior à data atual, avisa ao usuário, mas permite
                // continuar
                if (dataNascimento.after(new Date())) {
                    App.limparTela();
                    System.out.println(
                            "\n > A data de nascimento não pode ser futura. Por favor, insira uma data válida. <\n");
                    dataNascimento = null;
                    continue;
                }
            } catch (ParseException e) {
                App.limparTela();
                System.out.println(
                        "\n > Formato de data inválido. Por favor, insira uma data no formato AAAA-MM-DD. <\n");
            }
        }

        // Validação para o sexo
        do {
            System.out.print("-> Sexo (Masculino/Feminino/Outro): ");
            sexo = scanner.nextLine().toLowerCase(); // Convertendo para minúsculas para facilitar a comparação

            // Verifica se o sexo não é nulo ou vazio
            if (Objects.isNull(sexo) || sexo.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O sexo não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o sexo é válido
            if (!sexo.equals("masculino") && !sexo.equals("feminino") && !sexo.equals("outro")) {
                App.limparTela();
                System.out.println("\n > Sexo inválido. Por favor, insira 'Masculino', 'Feminino' ou 'Outro'. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o cargo
        do {
            System.out.print("-> Cargo: ");
            cargo = scanner.nextLine();

            // Verifica se o cargo não é nulo ou vazio
            if (Objects.isNull(cargo) || cargo.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O cargo não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o cargo contém apenas letras, espaços e caracteres especiais
            // comuns em nomes de cargos
            if (!cargo.matches("[\\p{L}\\s\\-']+")) {
                App.limparTela();
                System.out.println(
                        "\n > O cargo deve conter apenas letras, espaços e caracteres especiais comuns. Por favor, insira um cargo válido. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se o cargo não excede o tamanho máximo permitido
            if (cargo.length() > 40) {
                App.limparTela();
                System.out.println("\n > O cargo deve conter no máximo 40 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o salário
        do {
            System.out.print("-> Salário: ");
            String salarioStr = scanner.nextLine();

            // Tenta converter a entrada do usuário para um valor double
            try {
                salario = Double.parseDouble(salarioStr);
            } catch (NumberFormatException e) {
                App.limparTela();
                System.out.println("\n > Salário inválido. Por favor, insira um valor numérico válido. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se o salário é um valor positivo
            if (salario <= 0) {
                App.limparTela();
                System.out.println("\n > Salário deve ser um valor positivo. Tente novamente. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o telefone
        do {
            System.out.print("-> Telefone: ");
            telefone = scanner.nextLine();

            // Verifica se o telefone não é nulo ou vazio
            if (Objects.isNull(telefone) || telefone.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O telefone não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o telefone contém apenas dígitos
            if (!telefone.matches("[\\d,]+")) {
                App.limparTela();
                System.out.println("\n > Telefone não deve conter letras. <\n");
                continue;
            }

            // Verifica se o telefone tem 11 dígitos
            if (telefone.length() != 11) {
                App.limparTela();
                System.out.println("\n > Telefone deve conter exatamente 11 dígitos. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (!telefone.matches("\\d{11}"));

        // Validação para o email
        boolean emailExistente;
        do {
            System.out.print("-> E-mail: ");
            email = scanner.nextLine();

            // Verifica se o email não é nulo ou vazio
            if (Objects.isNull(email) || email.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O e-mail não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            if (email.contains("@") && email.indexOf("@") > 0 && email.indexOf("@") < email.length() - 1
                    && email.contains(".com")) {
                // Verifica se o email é válido.
                // Verifica se o email já existe no banco de dados
                try {
                    emailExistente = funcionarioDAO.verificarEmailFuncionario(email);
                } catch (SQLException e) {
                    emailExistente = true; // Em caso de exceção, considera o email como existente para evitar loops
                                           // infinitos
                    e.printStackTrace();
                }

                if (emailExistente) {
                    App.limparTela();
                    System.out.println("\n > Este endereço de email já está em uso. Por favor, escolha outro. <\n");
                } else {
                    break;
                }
            } else {
                // Caso contrário, informa que o email é inválido e solicita outra tentativa.
                App.limparTela();
                System.out.println(
                        "\n > Endereço de email inválido. O email deve conter '@' e '.com'. Tente novamente. <\n");
            }
        } while (true);

        // Validação para o CEP
        do {
            System.out.print("-> CEP: ");
            cep = scanner.nextLine();

            // Verifica se o CEP não é nulo ou vazio
            if (Objects.isNull(cep) || cep.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O CEP não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o CEP contém apenas dígitos
            if (!cep.matches("\\d+")) {
                App.limparTela();
                System.out.println("\n > CEP deve conter apenas dígitos. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se o CEP tem 8 dígitos
            if (cep.length() != 8) {
                App.limparTela();
                System.out.println("\n > CEP deve conter exatamente 8 dígitos. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para a rua
        do {
            System.out.print("-> Rua: ");
            rua = scanner.nextLine();

            // Verifica se a rua não é nula ou vazia
            if (Objects.isNull(rua) || rua.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > A rua não pode ser nula ou vazia. Tente novamente. <\n");
                continue;
            }

            // Verifica se a rua não excede o tamanho máximo permitido
            if (rua.length() > 50) {
                App.limparTela();
                System.out.println("\n > A rua deve conter no máximo 50 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o número da casa
        boolean numeroValido = false;
        do {
            System.out.print("-> Número da Casa: ");
            String input = scanner.nextLine();

            if (input.matches("\\d+")) {
                numeroDaCasa = Integer.parseInt(input);

                if (numeroDaCasa < 0) {
                    App.limparTela();
                    System.out.println(
                            "\n > Número da casa não pode ser negativo. Digite um valor numérico válido. <\n");
                } else {
                    numeroValido = true;
                }
            } else {
                App.limparTela();
                System.out.println("\n > Número da casa inválido. Digite um valor numérico. <\n");
            }
        } while (!numeroValido);

        // Validação para o bairro
        do {
            System.out.print("-> Bairro: ");
            bairro = scanner.nextLine();

            // Verifica se o bairro não é nulo ou vazio
            if (Objects.isNull(bairro) || bairro.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O bairro não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o bairro não excede o tamanho máximo permitido
            if (bairro.length() > 40) {
                App.limparTela();
                System.out.println("\n > O bairro deve conter no máximo 40 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para a cidade
        do {
            System.out.print("-> Cidade: ");
            cidade = scanner.nextLine();

            // Verifica se a cidade não é nula ou vazia
            if (Objects.isNull(cidade) || cidade.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > A cidade não pode ser nula ou vazia. Tente novamente. <\n");
                continue;
            }

            // Verifica se a cidade não excede o tamanho máximo permitido
            if (cidade.length() > 40) {
                App.limparTela();
                System.out.println("\n > A cidade deve conter no máximo 40 caracteres. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para a UF
        do {
            System.out.print("-> UF: ");
            uf = scanner.nextLine().toUpperCase(); // Converte para maiúsculas para garantir a validação

            // Verifica se a UF não é nula ou vazia
            if (uf == null || uf.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > A UF não pode ser nula ou vazia. Tente novamente. <\n");
                continue;
            }

            // Verifica se a UF tem exatamente 2 caracteres
            if (uf.length() != 2) {
                App.limparTela();
                System.out.println("\n > A UF deve conter exatamente 2 caracteres. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se a UF não contém números
            if (uf.matches(".*\\d+.*")) {
                App.limparTela();
                System.out.println("\n > A UF não deve conter números. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se a UF é válida
            if (!ufsValidas.contains(uf)) {
                App.limparTela();
                System.out.println(
                        "\n > A UF inserida não é válida. As UF's válidas são: AC, AL, AP, AM, BA, CE, DF, ES, GO, MA, MT, MS, MG, PA, PB, PR, PE, PI, RJ, RN, RS, RO, RR, SC, SP, SE, TO. Tente novamente. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Cria um objeto Funcionario com os dados validados
        Funcionario funcionario = new Funcionario(cpf, nome, dataNascimento, sexo, cargo, salario, dataContratacao,
                status, telefone, email, senha, cep, rua, numeroDaCasa, bairro, cidade, uf);

        // Insere o funcionário no banco de dados
        if (funcionarioDAO.inserirFuncionario(funcionario)) {
            App.limparTela();
            System.out.println("\n > Funcionário cadastrado COM SUCESSO! <\n");
        } else {
            System.out.println("\n > Funcionário cadastrado SEM SUCESSO! <\n");
        }
    }

    public static void visualizarDadosDoFuncionario() throws SQLException {
        App.limparTela();
        String cpf;
        int contador = 1;

        do {
            System.out.println(" Tentativa " + (contador) + "/3.");
            System.out.println("+-------------------------------------------+");
            System.out.println("|     V I S U A L I Z A R   D A D O S       |");
            System.out.println("+-------------------------------------------+");
            System.out.printf("| CPF do funcionário: ");
            cpf = scanner.nextLine();

            if (verificarExistenciaFuncionarioCPF(cpf) == false) {
                App.limparTela();
                System.out.println("\n > Usuário inexistente no banco de dados. <\n");
                contador++;
            }

            if (contador == 3) {
                break;
            }

        } while (verificarExistenciaFuncionarioCPF(cpf) == false);

        if (contador != 3) {
            Funcionario funcionario = funcionarioDAO.retornarFuncionarioPorCPF(cpf);

            App.limparTela();
            System.out.println("+-------------------------------------------+");
            System.out.println("|   D A D O S  D O  F U N C I O N A R I O   |");
            System.out.println("+-------------------------------------------+");
            System.out.println("| Nome: " + funcionario.getNome());
            System.out.println(
                    "| CPF: " + funcionario.getCpf().substring(0, 3) + "." + funcionario.getCpf().substring(3, 6)
                            + "." + funcionario.getCpf().substring(6, 9) + "-" + funcionario.getCpf().substring(9, 11));
            System.out.println("| Data de nascimento: " + funcionario.getDataNascimento().toString().substring(8, 10)
                    + "/" + funcionario.getDataNascimento().toString().substring(5, 7) + "/"
                    + funcionario.getDataNascimento().toString().substring(0, 4));
            System.out.println("| Sexo: " + funcionario.getSexo());
            System.out.println("+-------------------------------------------+");
            System.out.println("|               C O N T A T O               |");
            System.out.println("+-------------------------------------------+");
            System.out.printf("| Telefone: (" + funcionario.getTelefone().substring(0, 2) + ")"
                    + funcionario.getTelefone().substring(2, 7) + "-" + funcionario.getTelefone().substring(7)
                    + "\n");
            System.out.println("| Email: " + funcionario.getEmail());
            System.out.println("+-------------------------------------------+");
            System.out.println("|   D A D O S  P R O F I S S I O N A I S    |");
            System.out.println("+-------------------------------------------+");
            System.out.println("| Cargo: " + funcionario.getCargo());
            System.out.println("| Salário: " + funcionario.getSalario());
            System.out.println("| Data de contratação: " + funcionario.getDataContratacao().toString().substring(8, 10)
                    + "/" + funcionario.getDataContratacao().toString().substring(5, 7) + "/"
                    + funcionario.getDataContratacao().toString().substring(0, 4));
            System.out.println("| Status: " + (funcionario.getStatus() ? "Ativo" : "Inativo"));
            System.out.println("+-------------------------------------------+");
            System.out.println("|               E N D E R E Ç O             |");
            System.out.println("+-------------------------------------------+");
            System.out.println("| CEP: " + funcionario.getCep());
            System.out.println("| Rua: " + funcionario.getRua());
            System.out.println("| Número da Casa: " + funcionario.getNumeroDaCasa());
            System.out.println("| Bairro: " + funcionario.getBairro());
            System.out.println("| Cidade: " + funcionario.getCidade());
            System.out.println("| UF: " + funcionario.getUf());
            System.out.println("+-------------------------------------------+\n");
        }
    }

    public static void atualizarDadosDoFuncionario() throws SQLException {
        int opcao;
        String cpf;
        int contador = 1;

        do {
            System.out.println(" Tentativa " + (contador) + "/3.");
            System.out.println("+----------------------------------------------+");
            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
            System.out.println("+----------------------------------------------+");
            System.out.printf("| CPF do funcionário: ");
            cpf = scanner.nextLine();

            if (verificarExistenciaFuncionarioCPF(cpf) == false) {
                App.limparTela();
                System.out.println("\n > Usuário inexistente no banco de dados. <\n");
                contador++;
            }

            if (contador == 3) {
                break;
            }

        } while (verificarExistenciaFuncionarioCPF(cpf) == false);

        if (contador != 3) {
            do {
                App.limparTela();
                System.out.println("+----------------------------------------------+");
                System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                System.out.println("+----------------------------------------------+");
                System.out.println("|  1 - Nome                                    |");
                System.out.println("|  2 - Data de Nascimento                      |");
                System.out.println("|  3 - Sexo                                    |");
                System.out.println("|  4 - Cargo                                   |");
                System.out.println("|  5 - Salário                                 |");
                System.out.println("|  6 - Telefone                                |");
                System.out.println("|  7 - E-mail                                  |");
                System.out.println("|  8 - Status                                  |");
                System.out.println("|  9 - CEP                                     |");
                System.out.println("| 10 - Rua                                     |");
                System.out.println("| 11 - Número da Casa                          |");
                System.out.println("| 12 - Bairro                                  |");
                System.out.println("| 13 - Cidade                                  |");
                System.out.println("| 14 - UF                                      |");
                System.out.println("|  0 - Sair                                    |");
                System.out.println("+----------------------------------------------+");

                System.out.printf("| > Escolha o dado: ");

                // Verifica se a entrada é um número
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    if (opcao < 0 || opcao > 14) {
                        App.limparTela();
                        System.out.println("\n > Opção inválida, tente novamente! <\n");
                        continue;
                    }
                } else {
                    // Se a entrada não for um número, limpa o buffer do scanner e exibe uma
                    // mensagem de erro
                    scanner.nextLine(); // Limpar o buffer do scanner
                    App.limparTela();
                    System.out.println("\n > Por favor, digite apenas números! <\n");
                    continue; // Reinicie o loop para solicitar uma nova entrada
                }

                // Caso o usuário escolha sair, encerre o método
                if (opcao == 0) {
                    App.limparTela();
                    return;
                }

                App.limparTela();
                String novoValor;
                switch (opcao) {
                    case 1: // Nome
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Nome: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 60
                                    || novoValor.matches(".*\\d+.*")) {
                                App.limparTela();
                                System.out.println("\n > Nome inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 2: // Data de Nascimento
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Nova Data de Nascimento (AAAA-MM-DD): ");
                            novoValor = scanner.nextLine();
                            try {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                dateFormat.setLenient(false);
                                Date dataNascimento = dateFormat.parse(novoValor);
                                if (dataNascimento.after(new Date())) {
                                    App.limparTela();
                                    System.out.println(
                                            "\n > A data de nascimento não pode ser futura. Por favor, insira uma data válida. <\n");
                                    continue;
                                }
                            } catch (ParseException e) {
                                App.limparTela();
                                System.out.println(
                                        "\n > Formato de data inválido. Por favor, insira uma data no formato AAAA-MM-DD. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 3: // Sexo
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Sexo (Masculino/Feminino/Outro): ");
                            novoValor = scanner.nextLine().toLowerCase();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() ||
                                    (!novoValor.equals("masculino") && !novoValor.equals("feminino")
                                            && !novoValor.equals("outro"))) {
                                App.limparTela();
                                System.out.println(
                                        "\n > Sexo inválido. Por favor, insira 'Masculino', 'Feminino' ou 'Outro'. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 4: // Cargo
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Cargo: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 40 ||
                                    !novoValor.matches("[\\p{L}\\s\\-']+")) {
                                App.limparTela();
                                System.out.println("\n > Cargo inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 5: // Salário
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Salário: ");
                            novoValor = scanner.nextLine();
                            try {
                                double salario = Double.parseDouble(novoValor);
                                if (salario <= 0) {
                                    App.limparTela();
                                    System.out.println("\n > Salário deve ser um valor positivo. Tente novamente. <\n");
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                App.limparTela();
                                System.out.println(
                                        "\n > Salário inválido. Por favor, insira um valor numérico válido. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 6: // Telefone
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Telefone: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty()
                                    || !novoValor.matches("\\d{11}")) {
                                App.limparTela();
                                System.out.println("\n > Telefone inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 7: // E-mail
                        boolean emailExistente;
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo E-mail: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || !novoValor.contains("@")
                                    || novoValor.indexOf("@") <= 0 || novoValor.indexOf("@") >= novoValor.length() - 1
                                    || !novoValor.contains(".com")) {
                                App.limparTela();
                                System.out.println(
                                        "\n > Endereço de e-mail inválido. Por favor, insira um endereço de e-mail válido. <\n");
                                continue;
                            }
                            // Verificar se o e-mail já está cadastrado
                            try {
                                emailExistente = funcionarioDAO.verificarEmailFuncionario(novoValor);
                            } catch (SQLException e) {
                                System.out.println(
                                        "\n > Erro ao verificar o e-mail no banco de dados. Por favor, tente novamente. <\n");
                                continue;
                            }
                            if (emailExistente) {
                                App.limparTela();
                                System.out.println("\n > Este e-mail já está cadastrado. Por favor, insira outro. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 8: // Status
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Status (Ativo/Inativo): ");
                            novoValor = scanner.nextLine().toLowerCase();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() ||
                                    (!novoValor.equals("ativo") && !novoValor.equals("inativo"))) {
                                App.limparTela();
                                System.out.println("\n > Status inválido. Por favor, insira 'Ativo' ou 'Inativo'. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 9: // CEP
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo CEP (xxxxxxxx): ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() != 8
                                    || !novoValor.matches("\\d{8}")) {
                                App.limparTela();
                                System.out.println("\n > CEP inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 10: // Rua
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Nova Rua: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 60) {
                                App.limparTela();
                                System.out.println("\n > Rua inválida, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 11: // Número da Casa
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Número da Casa: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 10) {
                                App.limparTela();
                                System.out.println("\n > Número da casa inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 12: // Bairro
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Bairro: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 60) {
                                App.limparTela();
                                System.out.println("\n > Bairro inválido, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 13: // Cidade
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Nova Cidade: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 60) {
                                App.limparTela();
                                System.out.println("\n > Cidade inválida, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 14: // UF

                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Nova UF: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() != 2
                                    || !novoValor.matches("[A-Z]{2}")) {
                                App.limparTela();
                                System.out.println("\n > UF inválida, tente novamente! <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    default:
                        System.out.println("\n > Opção inválida, tente novamente! <\n");
                        continue; // Reinicie o loop para solicitar uma nova entrada
                }

                // Chamar o método para atualizar o atributo do funcionário
                if (funcionarioDAO.atualizarAtributoFuncionario(cpf, opcao, novoValor)) {
                    App.limparTela();
                    System.out.println("\n > Dados atualizados com sucesso! <\n");
                    return;
                } else {
                    App.limparTela();
                    System.out.println("\n > Falha ao atualizar os dados! <\n");
                    return;
                }

            } while (true);
        }
    }

    public static void alterarSenhaDeslogado() throws SQLException {

        String novaSenha1 = "";
        String novaSenha2 = "";
        String cpf = null;
        int tentativas = 0;

        do {
            System.out.println("+-------------------------------------------+");
            System.out.println("|    A  L  T  E  R  A  R   S  E  N  H  A    |");
            System.out.println("+-------------------------------------------+");
            System.out.printf("| Funcionário (CPF): ");
            cpf = scanner.nextLine();
            App.limparTela();

            if (!verificarExistenciaFuncionarioCPF(cpf)) {
                tentativas++;
                App.limparTela();
                System.out.println("\n > Funcionário inexistente no banco de dados! <\n");
                System.out.println(" Tentativa " + tentativas + "/3.");
            }

            if (tentativas >= 3) {
                App.limparTela();
                System.out.println("\n > Número máximo de tentativas excedido. <\n");
                break;
            }

        } while (!verificarExistenciaFuncionarioCPF(cpf));

        if (tentativas < 3) {
            do {
                System.out.println("+-------------------------------------------+");
                System.out.println("|    A  L  T  E  R  A  R   S  E  N  H  A    |");
                System.out.println("+-------------------------------------------+");
                System.out.println("| Funcionário (CPF): " + cpf);
                System.out.println("+-------------------------------------------+");
                System.out.printf("| Nova senha: ");
                novaSenha1 = scanner.next();

                // Verifica se a nova senha não é nula e tem até 30 caracteres
                if (novaSenha1 != null && novaSenha1.length() <= 30) {
                    System.out.printf("| Confirmar nova senha: ");
                    novaSenha2 = scanner.next();

                    // Verifica se as senhas coincidem
                    if (!(novaSenha1.equals(novaSenha2))) {
                        App.limparTela();
                        System.out.println("\n > As senhas não condizem, tente novamente. <\n");
                    }
                } else {
                    App.limparTela();
                    System.out.println("\n > A senha deve ter até 30 caracteres. <\n");
                }
            } while (!(novaSenha1.equals(novaSenha2)));

            funcionarioDAO.mudarSenhaFuncionario(cpf, novaSenha2);
            App.limparTela();
            System.out.println("\n > Senha alterada com sucesso. <\n");
        }
    }

    static Date stringParaData(String dataString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dataNascimento = sdf.parse(dataString);

            // Verificar se a data de nascimento é anterior à data atual
            Calendar calNascimento = Calendar.getInstance();
            calNascimento.setTime(dataNascimento);
            Calendar calAtual = Calendar.getInstance();

            if (calNascimento.before(calAtual)) {
                return dataNascimento; // Data válida
            } else {
                App.limparTela();
                System.out.println("\n > A data de nascimento deve ser anterior à data atual. Tente novamente. <\n");
                return null; // Data inválida
            }
        } catch (ParseException e) {
            App.limparTela();
            System.out.println(
                    "\n > Erro na conversão de data. Certifique-se de usar o formato AAAA-MM-DD. Tente novamente. <\n");
            return null; // Data inválida
        }
    }
}