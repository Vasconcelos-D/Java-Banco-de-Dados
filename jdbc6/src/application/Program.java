package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {
	
	public static void main(String[] args) {
		
		// Declaração da variável de conexão como nula
		Connection conn = null;
		// Declaração da variável Statement como nula
		Statement st = null;
		
		try {
			// Estabelecendo a conexão com o banco de dados usando o método estático getConnection da classe DB
			conn = DB.getConnection();
			// Desabilitando o modo de auto commit da conexão para controlar as transações manualmente
			conn.setAutoCommit(false);
			// Criando um Statement para executar comandos SQL
			st = conn.createStatement();
			
			// Executando uma atualização SQL que ajusta o salário base dos vendedores no departamento 1
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*
			// Código comentado que simula a geração de um erro para testar o rollback
			int x = 1;
			if (x < 2) {
				throw new SQLException("Fake error");
			}
			*/
			
			// Executando uma segunda atualização SQL que ajusta o salário base dos vendedores no departamento 2
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			// Comitando a transação para efetivar as alterações no banco de dados
			conn.commit();
			
			// Exibindo o número de linhas afetadas pelas duas operações de atualização
			System.out.println("Rows1 " + rows1);
			System.out.println("Rows2 " + rows2);

		}
		// Tratando exceção em caso de erro SQL durante a execução dos comandos
		catch (SQLException e) {
			try {
				// Em caso de erro, faz o rollback da transação para desfazer as alterações
				conn.rollback();
				// Lançando uma exceção personalizada com a mensagem do erro original
				throw new DbException("Transition rolled back! Caused by: " + e.getMessage());
			} catch (SQLException el) {
				// Se ocorrer um erro ao tentar o rollback, lança uma exceção adicional
				throw new DbException("Error trying to rollback! Caused by: " + el.getMessage());
			}
		}
		finally {
			// Fechando o Statement para liberar os recursos alocados
			DB.closeStatement(st);
			// Fechando a conexão com o banco de dados
			DB.closeConnection();
		}
	}
}
