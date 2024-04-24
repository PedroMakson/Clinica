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

import com.models.dao.PacienteDAO;
import com.models.entity.Conexao;
import com.models.entity.Paciente;

public class PacienteDAOTest {

    private static Connection conexao;
    private static PacienteDAO pacienteDAO;
    private static Paciente paciente;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws SQLException {
        conexao = Conexao.getInstancia();
        pacienteDAO = new PacienteDAO(conexao);
        paciente = null;
    }

    @After
    public void tearDown() throws SQLException {
        if (paciente != null) {
            pacienteDAO.deletarPaciente(paciente);
        }
        conexao.close();
    }

    // *********** C A D A S T R O - D E - P A C I E N T E S ***********

    // CASO DE SUCESSO PARA INSERIR UM PACIENTE (CPF TAMANHO 11)
    @Test
    public void testCadastrarPacienteSucessoNominal() throws ParseException {

        Date dataNascimento = dateFormat.parse("2002-08-23");

        paciente = new Paciente(
                "32145678946",
                "Pedro Makson",
                dataNascimento,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "pedr485o@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertTrue(pacienteDAO.inserirPaciente(paciente));
    }

    // CASO DE FRACASSO PARA INSERIR UM PACIENTE (CPF TAMANHO 10)
    @Test
    public void testCadastrarPacienteFracassoMin() throws ParseException {

        Date dataNascimento = dateFormat.parse("2002-08-23");

        paciente = new Paciente(
                "1234567898",
                "Pedro Makson",
                dataNascimento,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "pedro2@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertFalse(pacienteDAO.inserirPaciente(paciente));
    }

    // CASO DE FRACASSO PARA INSERIR UM PACIENTE (CPF TAMANHO 12)
    @Test
    public void testCadastrarPacienteFracassoMax() throws ParseException {

        Date dataNascimento = dateFormat.parse("2002-08-23");

        paciente = new Paciente(
                "123456789912",
                "Pedro Makson",
                dataNascimento,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "pedro@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        assertFalse(pacienteDAO.inserirPaciente(paciente));
    }

    // **** E D I T A R - C A D A S T R O - D E - P A C I E N T E ****

    // CASO DE SUCESSO PARA EDITAR UM PACIENTE (EDITANDO TELEFONE TAMANHO 11)
    @Test
    public void testEditarCadastroPacienteSucesso() throws ParseException, SQLException {
        testCadastrarPacienteSucessoNominal();
        assertTrue(pacienteDAO.atualizarAtributoPaciente("32145678946", 7, "12345678912"));
    }

    // CASO DE FRACASSO PARA EDITAR UM PACIENTE (EDITANDO TELEFONE TAMANHO 10)
    @Test
    public void testEditarCadastroPacienteFracassoMin() throws ParseException, SQLException {
        testCadastrarPacienteSucessoNominal();
        assertTrue(pacienteDAO.atualizarAtributoPaciente("32145678946", 7, "1234567891"));
    }

    // CASO DE FRACASSO PARA EDITAR UM PACIENTE (EDITANDO TELEFONE TAMANHO 12)
    @Test
    public void testEditarCadastroPacienteFracassoMax() throws ParseException, SQLException {
        testCadastrarPacienteSucessoNominal();
        assertTrue(pacienteDAO.atualizarAtributoPaciente("32145678946", 7, "123456789112"));
    }
}