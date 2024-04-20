package com.controllers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import com.App;
import com.models.dao.ServicoDAO;
import com.models.entity.Conexao;
import com.models.entity.Servico;

public class ServicoController {

    private static Connection conexao = Conexao.getInstancia();
    private static ServicoDAO servicoDAO = new ServicoDAO(conexao);
    static Scanner scanner = new Scanner(System.in);

    public static boolean verificarExistenciaServicoCodigo(int codigoServico) throws SQLException {
        if (servicoDAO.verificarCodigoServico(codigoServico)) {
            return true;
        }
        return false;
    }

    public static boolean verificarStatusServico(int codigoServico) throws SQLException {
        return servicoDAO.verificarStatusServico(codigoServico);
    }

    public static void cadastrarServico() throws SQLException {
        // Variáveis para armazenar os dados do serviço
        String nome = null;
        double valor = 0;
        int codigo = 0;
        boolean status = true;

        System.out.println("+-------------------------------------------+");
        System.out.println("|      C  A  D  A  S  T  R  O    D  E       |");
        System.out.println("|             S  E  R  V  I  Ç  O           |");
        System.out.println("+-------------------------------------------+");

        // Validação para o nome do serviço
        do {
            System.out.print("-> Nome do serviço: ");
            nome = scanner.nextLine();

            // Verifica se o nome não é nulo ou vazio
            if (Objects.isNull(nome) || nome.trim().isEmpty()) {
                App.limparTela();
                System.out.println("\n > O nome do serviço não pode ser nulo ou vazio. Tente novamente. <\n");
                continue;
            }

            // Verifica se o nome não excede o tamanho máximo permitido
            if (nome.length() > 40) {
                App.limparTela();
                System.out.println("\n > O nome do serviço deve conter no máximo 40 caracteres. Tente novamente. <\n");
                continue;
            }

            // Verifica se já existe um serviço com o mesmo nome
            if (servicoDAO.verificarNomeServico(nome)) {
                App.limparTela();
                System.out.println("\n > Já existe um serviço cadastrado com esse nome. Tente novamente. <\n");
                continue;
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Validação para o valor do serviço
        do {
            System.out.print("-> Valor do serviço: ");
            String valorStr = scanner.nextLine();

            // Tenta converter a entrada do usuário para um valor double
            try {
                valor = Double.parseDouble(valorStr);
            } catch (NumberFormatException e) {
                App.limparTela();
                System.out.println("\n > Valor do serviço inválido. Por favor, insira um valor numérico válido. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Verifica se o valor é um valor positivo
            if (valor <= 0) {
                App.limparTela();
                System.out.println("\n > Valor do serviço deve ser um valor positivo. Tente novamente. <\n");
                continue; // Volta ao início do loop para uma nova entrada
            }

            // Se passar por todas as verificações, encerra o loop
            break;
        } while (true);

        // Cria um objeto Servico com os dados validados
        Servico servico = new Servico(codigo, nome, valor, status);

        // Insere o paciente no banco de dados
        if (servicoDAO.inserirServico(servico)) {
            App.limparTela();
            System.out.println("\n > Serviço cadastrado COM SUCESSO! <\n");
        } else {
            App.limparTela();
            System.out.println("\n > Serviço cadastrado SEM SUCESSO! <\n");
        }

    }

    public static void exibirServicos() throws SQLException {
        servicoDAO.exibirServicosCadastrados();
    }

    public static void atualizarDadosDoServico() throws SQLException {
        int opcao;
        int codigoServico;
        int contador = 1;

        do {
            System.out.println(" Tentativa " + (contador) + "/3.");
            System.out.println("+----------------------------------------------+");
            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
            System.out.println("+----------------------------------------------+");
            System.out.printf("| Código do serviço: ");
            codigoServico = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            if (!servicoDAO.verificarCodigoServico(codigoServico)) {
                App.limparTela();
                System.out.println("\n > Serviço inexistente no banco de dados. <\n");
                contador++;
            }

            if (contador == 3) {
                break;
            }

        } while (!servicoDAO.verificarCodigoServico(codigoServico));

        if (contador != 3) {
            do {
                App.limparTela();
                System.out.println("+----------------------------------------------+");
                System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                System.out.println("+----------------------------------------------+");
                System.out.println("|  1 - Descrição                               |");
                System.out.println("|  2 - Valor                                   |");
                System.out.println("|  3 - Status                                  |");
                System.out.println("|  0 - Sair                                    |");
                System.out.println("+----------------------------------------------+");

                System.out.printf("| > Escolha o dado: ");

                // Verifica se a entrada é um número
                if (scanner.hasNextInt()) {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha
                    if (opcao < 0 || opcao > 3) {
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
                    case 1: // Descrição
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Nova Descrição: ");
                            novoValor = scanner.nextLine();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() || novoValor.length() > 255) {
                                App.limparTela();
                                System.out.println("\n > Descrição inválida, tente novamente! <\n");
                                continue;
                            }
                            // Verificar se a descrição já está em uso
                            if (servicoDAO.verificarNomeServico(novoValor)) {
                                App.limparTela();
                                System.out.println("\n > Esta descrição já está em uso. Por favor, insira outra. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 2: // Valor
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Valor: ");
                            novoValor = scanner.nextLine();
                            try {
                                double valor = Double.parseDouble(novoValor);
                                if (valor <= 0) {
                                    App.limparTela();
                                    System.out.println("\n > Valor deve ser um número positivo. Tente novamente. <\n");
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                App.limparTela();
                                System.out.println(
                                        "\n > Valor inválido. Por favor, insira um valor numérico válido. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    case 3: // Status
                        do {
                            System.out.println("+----------------------------------------------+");
                            System.out.println("|   A T U A L I Z A R  I N F O R M A Ç Õ E S   |");
                            System.out.println("+----------------------------------------------+");
                            System.out.print("-> Novo Status (Ativo/Inativo): ");
                            novoValor = scanner.nextLine().toLowerCase();
                            if (Objects.isNull(novoValor) || novoValor.trim().isEmpty() ||
                                    (!novoValor.equals("ativo") && !novoValor.equals("inativo"))) {
                                App.limparTela();
                                System.out.println("\n > Status inválido. Insira 'ativo' ou 'inativo'. <\n");
                                continue;
                            }
                            break;
                        } while (true);
                        break;
                    default:
                        System.out.println("\n > Opção inválida, tente novamente! <\n");
                        continue; // Reinicie o loop para solicitar uma nova entrada
                }

                // Chamar o método para atualizar o atributo do serviço
                if (servicoDAO.atualizarAtributoServico(codigoServico, opcao, novoValor)) {
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

}