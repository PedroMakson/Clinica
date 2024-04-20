package com.models.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    // Variável estática para armazenar a única instância da conexão
    private static Connection conexao;

    // Método privado para criar a conexão (se necessário) e retorná-la
    private static Connection criarConexao() {
        try {
            // Certificar-se que a conexão não foi criada anteriormente
            if (conexao == null) {
                // DRIVERMANAGER --> COMUNICAÇÃO COM TODOS OS DRIVERS
                // FAZ A PONTE DA PROGRAMAÇÃO COM O BANCO DE DADOS
                conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinica", "postgres",
                        "12345");

                // VERIFICA SE A CONEXÃO DEU CERTO
                if (conexao != null) {
                    System.out.println("\n  +---------------------------+");
                    System.out.println("  | Banco de dados conectado. |");
                    System.out.println("  +---------------------------+\n");

                } else {
                    System.out.println("Erro de conexão.");
                }
            }
            return conexao;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método público para obter a instância da conexão
    public static Connection getInstancia() {
        return criarConexao();
    }
}
