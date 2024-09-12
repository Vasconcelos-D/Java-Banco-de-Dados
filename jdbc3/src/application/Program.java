package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {
	public static void main(String[] args) {
	
		  Connection conn = null;  // Inicializa a variável de conexão como nula
	        PreparedStatement st = null;  // Inicializa a variável para a declaração preparada

	        try {
	            // Objeto para formatar e converter a data no formato dd/MM/yyyy
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	            // Estabelece a conexão com o banco de dados
	            conn = DB.getConnection();
/*
	            // Prepara a declaração SQL com placeholders (?) para os valores
	            st = conn.prepareStatement(
	                "INSERT INTO seller "
	                + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
	                + "VALUES"
	                + "(?, ?, ?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	                // Define os 5 placeholders para os valores a serem inseridos
	            );

	            // Define o valor para o primeiro placeholder (nome do vendedor)
	            st.setString(1, "Carl Purple");

	            // Define o valor para o segundo placeholder (email do vendedor)
	            st.setString(2, "carl@gmail.com");

	            // Converte a string "22/05/1985" em um objeto java.sql.Date e define no terceiro placeholder (data de nascimento)
	            st.setDate(3, new java.sql.Date(sdf.parse("22/05/1985").getTime()));

	            // Define o valor para o quarto placeholder (salário base)
	            st.setDouble(4, 3000.0);

	            // Define o valor para o quinto placeholder (ID do departamento)
	            st.setInt(5, 4);
*/
	            
	            // Prepara uma nova declaração SQL para inserir valores na tabela department
	            st = conn.prepareStatement("insert into department (Name) values ('D3'), ('D4')", Statement.RETURN_GENERATED_KEYS);
	            
	            // Executa a instrução SQL e obtém o número de linhas afetadas
	            int rowsAffected = st.executeUpdate();

	            if (rowsAffected > 0) {
	            	ResultSet rs = st.getGeneratedKeys();
	            	while (rs.next()) {
	            		int id = rs.getInt(1);
	            		System.out.println("Done Id = " + id);
	            	}
	            }
	            else {
	            	System.out.println("No rown affected! ");
	            }
	        }
	        catch (SQLException e) {
	            // Trata possíveis erros de SQL
	            e.printStackTrace();
	        }

	        finally {
	            // Fecha a declaração e a conexão
	            DB.closeStatement(st);
	            DB.closeConnection();  // Corrigido para fechar a conexão em vez de abrir novamente
	        }
	}
}
