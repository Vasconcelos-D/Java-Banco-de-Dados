package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;

public class Program {
	@SuppressWarnings("null")
	public static void main(String[] args) {
	
		// Declaração da variável de conexão como nula
		Connection conn = null;
		// Declaração da variável de PreparedStatement como nula
		PreparedStatement st = null;
		
		try {
			// Estabelecendo a conexão com o banco de dados através do método estático getConnection da classe DB
			conn = DB.getConnection();
			
			// Preparando a consulta SQL com parâmetros
			st = conn.prepareStatement(
					"UPDATE seller " //nunca esquecer espaço entre as (") pois da erro de sintaxe
					+ "SET BaseSalary = BaseSalary + ? " // Atualiza o salário base somando o valor fornecido como parâmetro
					+ "WHERE " // Cláusula WHERE para limitar quais registros serão atualizados
					+ "(DepartmentId = ?)"); // Condição de filtro: atualiza apenas os registros com DepartmentId igual ao fornecido
			
			// Atribuindo o valor 200.0 ao primeiro parâmetro da consulta (que incrementa o BaseSalary)
			st.setDouble(1, 200.0);
			// Atribuindo o valor 2 ao segundo parâmetro da consulta (o DepartmentId)
			st.setInt(2, 2);
			
			// Executando a consulta SQL de atualização e recebendo o número de linhas afetadas
			int rowsAffected = st.executeUpdate();
			
			// Imprimindo o resultado na saída padrão (quantas linhas foram afetadas pela consulta)
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch (SQLException e) {
			// Tratamento de exceções SQL, imprimindo a pilha de erros caso ocorra alguma exceção
			e.printStackTrace();
		}
		finally {
			// Fechando o PreparedStatement para liberar recursos
			DB.closeStatement(st);
			// Fechando a conexão com o banco de dados
			DB.closeConnection();
		}
		
	}
}
