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
import com.models.dao.PacienteDAO;
import com.models.dao.PrescricaoMedicaDAO;
import com.models.entity.Conexao;
import com.models.entity.Funcionario;
import com.models.entity.Paciente;
import com.models.entity.PrescricaoMedica;

public class PrescricaoMedicaDAOTest {

    private static Connection conexao;
    private static Funcionario funcionario;
    private static FuncionarioDAO funcionarioDAO;
    private static Paciente paciente;
    private static PacienteDAO pacienteDAO;
    private static PrescricaoMedicaDAO prescricaoMedicaDAO;
    private static PrescricaoMedica prescricaoMedica;
    Date dataPrescricao = null;
    Date dataRegistro;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() throws SQLException, ParseException {
        conexao = Conexao.getInstancia();
        funcionarioDAO = new FuncionarioDAO(conexao);
        pacienteDAO = new PacienteDAO(conexao);
        prescricaoMedicaDAO = new PrescricaoMedicaDAO(conexao);
        prescricaoMedica = null;

        Date dataNascimento = dateFormat.parse("2002-08-23");
        Date dataContratacao = dateFormat.parse("2024-04-20");
        dataRegistro = dateFormat.parse("2024-04-20");
        dataPrescricao = dateFormat.parse("2024-04-20");

        funcionario = new Funcionario(
                "12755678901",
                "João da Silva",
                dataNascimento,
                "Masculino",
                "Gerente",
                5000.00,
                dataContratacao,
                true,
                "12345678911",
                "joao758Silva@example.com",
                "senha123",
                "12345678",
                "Rua dos Funcionários",
                123,
                "Centro",
                "Cidade",
                "UF");

        Date dataNascimentoPaciente = dateFormat.parse("2002-08-23");
        paciente = new Paciente(
                "32145674946",
                "Pedro Makson",
                dataNascimentoPaciente,
                "Masculino",
                62,
                1.76,
                "A+",
                "12345678911",
                "pedro471@example.com",
                "senha123",
                "12345678",
                "Rua dos Pacientes",
                123,
                "Centro",
                "Cidade",
                "UF");

        funcionarioDAO.inserirFuncionario(funcionario);
        pacienteDAO.inserirPaciente(paciente);
    }

    @After
    public void tearDown() throws SQLException {
        if (prescricaoMedica != null) {
            prescricaoMedicaDAO.deletarPrescricaoMedicaPorCPF(prescricaoMedica.getPaciente().getCpf());
            funcionarioDAO.deletarFuncionario(funcionario);
            pacienteDAO.deletarPaciente(paciente);
        } else {
            prescricaoMedicaDAO.deletarUltimoProntuario();
        }
        conexao.close();
    }

    // *********** C A D A S T R O - D E - P R E S C R I Ç Ã O ***********

    // CASO DE SUCESSO PARA INSERIR UMA PRESCRIÇÃO (TAMANHO DIAGNOSTICO IGUAL A 50)
    @Test
    public void testCadastrarPrescricaoSucessoMax() throws ParseException {
        prescricaoMedica = new PrescricaoMedica(100, paciente, funcionario, dataPrescricao,
                "testetestetestetestetestetestetestetestetesteteste", "Teste");

        assertTrue(prescricaoMedicaDAO.inserirPrescricaoMedica(prescricaoMedica));
    }

    // CASO DE SUCESSO PARA INSERIR UMA PRESCRIÇÃO (TAMANHO DIAGNOSTICO IGUAL A 1)
    @Test
    public void testCadastrarPrescricaoSucessoMin() throws ParseException {
        prescricaoMedica = new PrescricaoMedica(100, paciente, funcionario, dataPrescricao,
                "t", "Teste");

        assertTrue(prescricaoMedicaDAO.inserirPrescricaoMedica(prescricaoMedica));
    }

    // CASO DE SUCESSO PARA INSERIR UMA PRESCRIÇÃO (TAMANHO DIAGNOSTICO IGUAL A 51)
    @Test
    public void testCadastrarPrescricaoFracassoMaxMaisUm() throws ParseException {
        prescricaoMedica = new PrescricaoMedica(100, paciente, funcionario, dataPrescricao,
                "testetestetestetestetestetestetestetestetestetestes", "Teste");

        assertFalse(prescricaoMedicaDAO.inserirPrescricaoMedica(prescricaoMedica));
    }

    // *********** C A D A S T R O - D E - P R O N T U Á R I O ***********

    // CASO DE SUCESSO PARA INSERIR UMA PRONTUÁRIO (TAMANHO DIAGNOSTICO IGUAL A 50)
    @Test
    public void testCadastrarProntuarioMedicoSucessoMax() {
        java.sql.Date sqlDataRegistro = new java.sql.Date(dataRegistro.getTime());
        assertTrue(prescricaoMedicaDAO.inserirProntuarioMedico(funcionario.getCpf(), funcionario.getNome(),
                paciente.getCpf(),
                paciente.getNome(), "Consulta Médica", "testetestetestetestetestetestetestetestetesteteste",
                "Ficar de repouso", sqlDataRegistro));

    }

    // CASO DE SUCESSO PARA INSERIR UMA PRONTUÁRIO (TAMANHO DIAGNOSTICO IGUAL A 49)
    @Test
    public void testCadastrarProntuarioMedicoSucessoMaxMenosUm() {
        java.sql.Date sqlDataRegistro = new java.sql.Date(dataRegistro.getTime());
        assertTrue(prescricaoMedicaDAO.inserirProntuarioMedico(funcionario.getCpf(), funcionario.getNome(),
                paciente.getCpf(),
                paciente.getNome(), "Consulta Médica", "testetestetestetestetestetestetestetestetestetest",
                "Ficar de repouso", sqlDataRegistro));

    }

    // CASO DE SUCESSO PARA INSERIR UMA PRONTUÁRIO (TAMANHO DIAGNOSTICO IGUAL A 51)
    @Test
    public void testCadastrarProntuarioMedicoSucessoMaxMaisUm() {
        java.sql.Date sqlDataRegistro = new java.sql.Date(dataRegistro.getTime());
        assertFalse(prescricaoMedicaDAO.inserirProntuarioMedico(funcionario.getCpf(), funcionario.getNome(),
                paciente.getCpf(),
                paciente.getNome(), "Consulta Médica", "testetestetestetestetestetestetestetestetestetest74",
                "Ficar de repouso", sqlDataRegistro));

    }

}