package artur.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	// construtor est�tico - acontece uma �nica vez no primeiro uso da classe
	static {
		// carregar o Driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() {
		try {
			// conectar
			String url = "jdbc:postgresql://localhost/alunos";
			String usuario = "postgres";
			String senha = "timmaddren1234";
			Connection con = DriverManager.getConnection(url, usuario, senha);
			return con;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 	
	}
}