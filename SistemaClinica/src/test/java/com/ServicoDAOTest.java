package com;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.models.dao.ServicoDAO;
import com.models.entity.Conexao;
import com.models.entity.Servico;

public class ServicoDAOTest {

    private static Connection conexao;
    private static ServicoDAO servicoDAO;
    private static Servico servico;

    @Before
    public void setUp() throws SQLException {
        conexao = Conexao.getInstancia();
        servicoDAO = new ServicoDAO(conexao);
        servico = null;
    }

    @After
    public void tearDown() throws SQLException {
        if (servico != null) {
            servicoDAO.deletarServico(servico);
        }
        conexao.close();
    }

    // *********** C A D A S T R O - D E - S E R V I Ç O S ***********

    // CASO DE SUCESSO PARA INSERIR UM SERVIÇO (NOME TAMANHO 40)
    @Test
    public static void testCadastrarServicoSucesso() throws ParseException {

        servico = new Servico(
                100,
                "testetestetestetestetestetestetesteteste",
                140,
                true);

        assertTrue(servicoDAO.inserirServico(servico));
    }

    // CASO DE FRACASSO PARA INSERIR UM SERVIÇO (NOME TAMANHO 39)
    @Test
    public void testCadastrarServicoFracassoMin() throws ParseException {

        servico = new Servico(
                100,
                "testetestetestetestetestetestetestetest",
                140,
                true);

        assertFalse(servicoDAO.inserirServico(servico));
    }

    // CASO DE FRACASSO PARA INSERIR UM SERVIÇO (NOME TAMANHO 41)
    @Test
    public void testCadastrarServicoFracassoMax() throws ParseException {

        servico = new Servico(
                100,
                "testetestetestetestetestetestetestetestes",
                140,
                true);

        assertFalse(servicoDAO.inserirServico(servico));
    }

    // **** E D I T A R - C A D A S T R O - D E - S E R V I Ç O S ****

    // CASO DE SUCESSO PARA EDITAR UM SERVIÇO (EDITANDO TELEFONE TAMANHO 40)
    @Test
    public void testEditarServicoSucesso() throws ParseException, SQLException {
        testCadastrarServicoSucesso();
        String nomeTamanho40 = "testetestetestetestetestetestetestetes40";
        assertTrue(servicoDAO.atualizarAtributoServicoPeloNome(servico.getNome(), 1,
                nomeTamanho40));
        servico.setNome(nomeTamanho40);

    }

    // CASO DE SUCESSO PARA EDITAR UM SERVIÇO (EDITANDO TELEFONE TAMANHO 39)
    @Test
    public void testEditarServicoFracassoMin() throws ParseException, SQLException {
        testCadastrarServicoSucesso();
        String nomeTamanho39 = "testetestetestetestetestetestetestete39";
        assertTrue(servicoDAO.atualizarAtributoServicoPeloNome(servico.getNome(), 1,
                nomeTamanho39));
        servico.setNome(nomeTamanho39);
    }

    // CASO DE SUCESSO PARA EDITAR UM SERVIÇO (EDITANDO TELEFONE TAMANHO 41)
    @Test
    public void testEditarServicoFracassoMax() throws ParseException, SQLException {
        testCadastrarServicoSucesso();
        String nomeTamanho41 = "testetestetestetestetestetestetestetest41";
        assertFalse(servicoDAO.atualizarAtributoServicoPeloNome(servico.getNome(), 1,
                nomeTamanho41));
        servico.setNome(nomeTamanho41);
    }

}
