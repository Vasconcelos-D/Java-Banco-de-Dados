package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;

public class Program {
	public static void main(String[] args) {
	
		// Declaração das variáveis para a conexão com o banco de dados, o statement e o result set
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			// Estabelece a conexão com o banco de dados usando o método getConnection() da classe DB
			conn = DB.getConnection();
			
			// Cria um objeto Statement para enviar comandos SQL para o banco de dados
			st = conn.createStatement();
			
			// Executa uma consulta SQL que seleciona todos os registros da tabela "department"
			rs = st.executeQuery("select * from department");
			
			// Itera sobre o conjunto de resultados (ResultSet) retornado pela consulta SQL
			while (rs.next()) {
				// Obtém os valores da coluna "Id" e "name" e imprime-os
				// rs.getInt("Id") recupera o valor da coluna "Id" como um inteiro
				// rs.getString("name") recupera o valor da coluna "name" como uma string
				System.out.println(rs.getInt("Id") + ", " + rs.getString("name"));
			}
		}
		catch (SQLException e) {
			// Em caso de exceção SQL, imprime a pilha de erros para depuração
			e.printStackTrace();
		}
		finally {
			// Fecha o ResultSet para liberar os recursos associados
			DB.closeResultSet(rs);
			
			// Fecha o Statement para liberar os recursos associados
			DB.closeStatement(st);
			
			// Fecha a conexão com o banco de dados
			DB.closeConnection();
		}
	}
}
