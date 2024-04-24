package com;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.models.dao.FuncionarioDAO;
import com.models.entity.Conexao;
import com.models.entity.Funcionario;

public class FuncionarioDAOTest {

    private static Connection conexao;
    private static FuncionarioDAO funcionarioDAO;
    private static Funcionario funcionario;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws SQLException {
        conexao = Conexao.getInstancia();
        funcionarioDAO = new FuncionarioDAO(conexao);
        funcionario = null;
    }

    @After
    public void tearDown() throws SQLException {
        if (funcionario != null) {
            funcionarioDAO.deletarFuncionario(funcionario);
        }
        conexao.close();
    }

    // *********** L O G A R - N O - S I S T E M A ***********

    // CASO DE SUCESSO PARA LOGAR NO SISTEMA (CPF TAMANHO 11)
    @Test
    public void testLogarNoSistemaSucessoNominal() throws SQLException, ParseException {
        testCadastrarFuncionarioSucessoNominal();
        assertTrue(funcionarioDAO.validarCredenciaisFuncionario("12345678901", "senha123"));
    }

    // CASO DE FRACASSO PARA LOGAR NO SISTEMA (CPF TAMANHO 10)
    @Test
    public void testLogarNoSistemaFracassoMaxMenosUm() throws SQLException, ParseException {
        testCadastrarFuncionarioFracassoMaxMenosUm();
        assertFalse(funcionarioDAO.validarCredenciaisFuncionario("1234567891", "senha123"));
    }

    // CASO DE FRACASSO PARA LOGAR NO SISTEMA (CPF TAMANHO 12)
    @Test
    public void testLogarNoSistemaFracassoMaxMaisUm() throws SQLException, ParseException {
        testCadastrarFuncionarioFracassoMaxMaisUm();
        assertFalse(funcionarioDAO.validarCredenciaisFuncionario("123456789012", "senha123"));
    }

    // *********** C A D A S T R O - D E - F U N C I O N A R I O ***********

    // CASO DE SUCESSO PARA INSERIR UM FUNCIONÁRIO (CPF TAMANHO 11)
    @Test
    public void testCadastrarFuncionarioSucessoNominal() throws ParseException {

        Date dataNascimento = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "12345678901",
                "João da Silva",
                dataNascimento,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "joao789@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertTrue(funcionarioDAO.inserirFuncionario(funcionario));
    }

    // CASO DE FRACASSO PARA INSERIR UM FUNCIONÁRIO (CPF TAMANHO 10)
    @Test
    public void testCadastrarFuncionarioFracassoMaxMenosUm() throws ParseException {

        Date dataNascimento = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "1234567891",
                "João da Silva",
                dataNascimento,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "jao@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertFalse(funcionarioDAO.inserirFuncionario(funcionario));
    }

    // CASO DE FRACASSO PARA INSERIR UM FUNCIONÁRIO (CPF TAMANHO 12)
    @Test
    public void testCadastrarFuncionarioFracassoMaxMaisUm() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dataNascimento = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "123456789012",
                "João da Silva",
                dataNascimento,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "joao@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertFalse(funcionarioDAO.inserirFuncionario(funcionario));
    }

    // **** E D I T A R - C A D A S T R O - D E - F U N C I O N A R I O ****

    // CASO DE SUCESSO PARA EDITAR UM FUNCIONÁRIO (EDITANDO TELEFONE TAMANHO 11)
    @Test
    public void testEditarCadastroFuncionarioSucessoNominal() throws ParseException, SQLException {
        testCadastrarFuncionarioSucessoNominal();
        assertTrue(funcionarioDAO.atualizarAtributoFuncionario("12345678901", 6, "12345678911"));
    }

    // CASO DE SUCESSO PARA EDITAR UM FUNCIONÁRIO (EDITANDO TELEFONE TAMANHO 10)
    @Test
    public void testEditarCadastroFuncionarioFracassoMaxMenosUm() throws ParseException, SQLException {
        testCadastrarFuncionarioSucessoNominal();
        assertTrue(funcionarioDAO.atualizarAtributoFuncionario("12345678901", 6, "1234567891"));
    }

    // CASO DE SUCESSO PARA EDITAR UM FUNCIONÁRIO (EDITANDO TELEFONE TAMANHO 12)
    @Test
    public void testEditarCadastroFuncionarioFracassoMaxMaisUm() throws ParseException, SQLException {
        testCadastrarFuncionarioSucessoNominal();
        assertFalse(funcionarioDAO.atualizarAtributoFuncionario("12345678901", 6, "123456789112"));
    }

}