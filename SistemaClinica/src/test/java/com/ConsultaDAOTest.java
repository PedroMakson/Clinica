package com;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.models.dao.FuncionarioDAO;
import com.models.dao.PacienteDAO;
import com.models.dao.ServicoDAO;
import com.models.dao.ConsultaDAO;
import com.models.entity.Conexao;
import com.models.entity.Consulta;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.Servico;

public class ConsultaDAOTest {

    private static Connection conexao;
    private static Funcionario funcionario;
    private static FuncionarioDAO funcionarioDAO;
    private static Paciente paciente;
    private static PacienteDAO pacienteDAO;
    private static Servico servico;
    private static ServicoDAO servicoDAO;
    private static Consulta consulta;
    private static ConsultaDAO consultaDAO;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws SQLException, ParseException {
        conexao = Conexao.getInstancia();
        funcionarioDAO = new FuncionarioDAO(conexao);
        pacienteDAO = new PacienteDAO(conexao);
        servicoDAO = new ServicoDAO(conexao);
        consultaDAO = new ConsultaDAO(conexao);
    }

    @After
    public void tearDown() throws SQLException {
        if (consulta != null) {
            consultaDAO.deletarConsultaPorCPFPaciente(consulta);
            funcionarioDAO.deletarFuncionario(funcionario);
            pacienteDAO.deletarPaciente(paciente);
            servicoDAO.deletarServico(servico);
        }
        conexao.close();
    }

    // *********** C A D A S T R O - D E - C O N S U L T A ***********

    // Contextualizando, no sistema a consulta pode ter três status: 'Agendado',
    // 'Cancelado' ou 'Concluído' encerrado

    // CASO DE SUCESSO PARA INSERIR UMA CONSULTA (STATUS TAMANHO 9)
    @Test
    public void testCadastrarConsultaSucessoMax() throws ParseException {

        Date dataNascimentoFuncionario = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "12445488901",
                "Leo",
                dataNascimentoFuncionario,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "leonardoinacio@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        Date dataNascimentoPaciente = dateFormat.parse("2002-08-23");
        paciente = new Paciente(
                "32145648946",
                "Livia",
                dataNascimentoPaciente,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "livia@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        servico = new Servico(
                150,
                "testetestetesttetestetestetestetes",
                140,
                true);

        funcionarioDAO.inserirFuncionario(funcionario);
        pacienteDAO.inserirPaciente(paciente);
        servicoDAO.inserirServico(servico);

        Date dataConsulta = dateFormat.parse("2024-04-21");
        LocalTime horarioConsulta = LocalTime.of(10, 30);

        consulta = new Consulta(
                180,
                funcionario,
                paciente,
                servico,
                dataConsulta,
                horarioConsulta,
                "Cancelado", // status tamanho 9
                true);

        assertTrue(consultaDAO.inserirConsulta(funcionario, paciente, servico, consulta));
    }

    // CASO DE SUCESSO PARA INSERIR UMA CONSULTA (STATUS TAMANHO 8)
    @Test
    public void testCadastrarConsultaSucessoMaxMenosUm() throws ParseException {

        Date dataNascimentoFuncionario = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "12445488901",
                "Leo",
                dataNascimentoFuncionario,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "leonardoinacio@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        Date dataNascimentoPaciente = dateFormat.parse("2002-08-23");
        paciente = new Paciente(
                "32145648946",
                "Livia",
                dataNascimentoPaciente,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "livia@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        servico = new Servico(
                150,
                "testetestetesttetestetestetestetes",
                140,
                true);

        funcionarioDAO.inserirFuncionario(funcionario);
        pacienteDAO.inserirPaciente(paciente);
        servicoDAO.inserirServico(servico);

        Date dataConsulta = dateFormat.parse("2024-04-21");
        LocalTime horarioConsulta = LocalTime.of(10, 30);

        consulta = new Consulta(
                180,
                funcionario,
                paciente,
                servico,
                dataConsulta,
                horarioConsulta,
                "Agendado", // status tamanho 8
                true);

        assertTrue(consultaDAO.inserirConsulta(funcionario, paciente, servico, consulta));
    }

    // CASO DE FRACASSO PARA INSERIR UMA CONSULTA (STATUS TAMANHO 10)
    @Test
    public void testCadastrarConsultaFracassoMaxMaisUm() throws ParseException {

        Date dataNascimentoFuncionario = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "12445488901",
                "Leo",
                dataNascimentoFuncionario,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "leonardoinacio@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        Date dataNascimentoPaciente = dateFormat.parse("2002-08-23");
        paciente = new Paciente(
                "32145648946",
                "Livia",
                dataNascimentoPaciente,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "livia@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        servico = new Servico(
                150,
                "testetestetesttetestetestetestetes",
                140,
                true);

        funcionarioDAO.inserirFuncionario(funcionario);
        pacienteDAO.inserirPaciente(paciente);
        servicoDAO.inserirServico(servico);

        Date dataConsulta = dateFormat.parse("2024-04-21");
        LocalTime horarioConsulta = LocalTime.of(10, 30);

        consulta = new Consulta(
                180,
                funcionario,
                paciente,
                servico,
                dataConsulta,
                horarioConsulta,
                "Concluídoo", // status tamanho 10
                true);

        assertFalse(consultaDAO.inserirConsulta(funcionario, paciente, servico, consulta));
    }

    // *********** E D I Ç Ã O - D E - C O N S U L T A ***********

    // CASO DE SUCESSO PARA EDITAR UMA CONSULTA (STATUS TAMANHO 9)
    @Test
    public void testEditarConsultaSucessoMax() throws ParseException, SQLException {
        testCadastrarConsultaSucessoMax();
        int idConsulta = consultaDAO.obterUltimoIdInserido();
        assertTrue(consultaDAO.atualizarAtributosConsulta(idConsulta, 4, "Concluído"));
    }

    // CASO DE SUCESSO PARA EDITAR UMA CONSULTA (STATUS TAMANHO 8)
    @Test
    public void testEditarConsultaSucessoMaxMenosUm() throws ParseException, SQLException {
        testCadastrarConsultaSucessoMax();
        int idConsulta = consultaDAO.obterUltimoIdInserido();
        assertTrue(consultaDAO.atualizarAtributosConsulta(idConsulta, 4, "Agendado"));
    }

    // CASO DE FRACASSO PARA EDITAR UMA CONSULTA (STATUS TAMANHO 10)
    @Test
    public void testEditarConsultaSucessoMaxMaisUm() throws ParseException, SQLException {
        testCadastrarConsultaSucessoMax();
        int idConsulta = consultaDAO.obterUltimoIdInserido();
        assertFalse(consultaDAO.atualizarAtributosConsulta(idConsulta, 4, "Canceladoo"));
    }
}