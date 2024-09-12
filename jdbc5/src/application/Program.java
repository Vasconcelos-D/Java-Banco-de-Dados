package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import db.DbIntegrityException;

public class Program {
	
	public static void main(String[] args) {
		
		// Declaração da variável de conexão como nula
		Connection conn = null;
		// Declaração da variável PreparedStatement como nula
		PreparedStatement st = null;
		
		try {
			// Estabelecendo a conexão com o banco de dados usando o método estático getConnection da classe DB
			conn = DB.getConnection();
			
			// Preparando a consulta SQL para deletar um registro na tabela department
			st = conn.prepareStatement(
					"DELETE FROM department " // Define a tabela da qual o registro será removido
					+ "WHERE " // Condição para a exclusão
					+ "Id = ?"); // Especifica que a exclusão será feita onde o ID for igual ao valor fornecido no parâmetro
			
			// Atribuindo o valor 2 ao primeiro parâmetro (correspondente ao ID do departamento que será deletado)
			st.setInt(1, 2);
			
			// Executando o comando DELETE e armazenando o número de linhas afetadas
			int rowsAffected = st.executeUpdate();
			
			// Exibindo o número de linhas afetadas pela exclusão
			System.out.println("Done! Rows affected: " + rowsAffected);
		}
		catch (SQLException e) {
			// Em caso de erro, lança uma exceção personalizada DbIntegrityException com a mensagem do erro
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			// Fechando o PreparedStatement para liberar os recursos alocados
			DB.closeStatement(st);
			// Fechando a conexão com o banco de dados
			DB.closeConnection();
		}
	}
}
