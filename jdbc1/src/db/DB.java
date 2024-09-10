package db;  
// Define o pacote onde a classe DB está localizada.

import java.io.FileInputStream;  
// Importa a classe para abrir um arquivo como fluxo de entrada.

import java.io.IOException;  
// Importa a classe que lida com exceções de entrada/saída (I/O).

import java.sql.Connection;  
// Importa a classe que representa uma conexão com o banco de dados.

import java.sql.DriverManager;  
// Importa a classe responsável por gerenciar as conexões com o banco de dados.

import java.sql.SQLException;  
// Importa a classe que trata exceções relacionadas ao SQL.

import java.util.Properties;  
// Importa a classe que lida com arquivos de propriedades.

public class DB {  
    // Declara a classe pública DB, usada para gerenciar a conexão com o banco de dados.

    private static Connection conn = null;  
    // Declara uma variável estática do tipo Connection para armazenar a conexão com o banco de dados.

    public static Connection getConnection() {  
        // Método público e estático que retorna a conexão com o banco de dados.

        if (conn == null) {  
            // Verifica se a conexão já foi estabelecida (se for nula).

            try {  
                // Inicia um bloco de código que pode lançar exceções (tentativa de estabelecer a conexão).

                Properties props = loadProperties();  
                // Carrega as propriedades do arquivo db.properties usando o método loadProperties().

                String url = props.getProperty("dburl");  
                // Obtém a URL do banco de dados a partir das propriedades carregadas.

                conn = DriverManager.getConnection(url, props);  
                // Usa o DriverManager para estabelecer a conexão com o banco de dados usando a URL e as propriedades.

            } catch (SQLException e) {  
                // Captura uma exceção SQL, caso ocorra um erro ao tentar se conectar.

                throw new DbException(e.getMessage());  
                // Lança uma exceção personalizada chamada DbException com a mensagem do erro.

            }
        }
        return conn;  
        // Retorna a conexão estabelecida (ou já existente) com o banco de dados.
    }

    public static void closeConnection() {  
        // Método público e estático que fecha a conexão com o banco de dados.

        if (conn != null) {  
            // Verifica se a conexão não é nula (ou seja, se já foi aberta).

            try {  
                // Inicia um bloco de código que pode lançar exceções.

                conn.close();  
                // Fecha a conexão com o banco de dados.

            } catch (SQLException e) {  
                // Captura uma exceção SQL, caso ocorra um erro ao tentar fechar a conexão.

                throw new DbException(e.getMessage());  
                // Lança uma exceção personalizada com a mensagem de erro.
            }
        }
    }

    private static Properties loadProperties() {  
        // Método privado e estático que carrega as propriedades do arquivo db.properties.

        try (FileInputStream fs = new FileInputStream("db.properties")) {  
            // Abre o arquivo db.properties usando FileInputStream e tenta ler seu conteúdo.

            Properties props = new Properties();  
            // Cria um objeto Properties que armazenará as chaves e valores do arquivo.

            props.load(fs);  
            // Carrega o conteúdo do arquivo de propriedades no objeto props.

            return props;  
            // Retorna o objeto Properties com as informações carregadas.

        } catch (IOException e) {  
            // Captura uma exceção de I/O, caso ocorra um erro ao abrir ou ler o arquivo.

            throw new DbException(e.getMessage());  
            // Lança uma exceção personalizada com a mensagem do erro de I/O.
        }
    }
}
