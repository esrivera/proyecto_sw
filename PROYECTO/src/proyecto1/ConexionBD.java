package proyecto1;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
	
	static String bd = "tuto1_bd";
	static String login = "postgres";
	static String password = "root";
	static String url = "jdbc:postgresql://127.0.0.1:5432/"+bd;
	Connection conexion = null;
	
	public ConexionBD() {
		try {
			Class.forName("org.postgresql.Driver");
			conexion = DriverManager.getConnection(url,login,password);
			
			if(conexion != null) {
				System.out.println("Conexion a base de datos \""+bd+"\" realizada ok");
			}
			
		}catch(Exception e) {
			System.out.println("Error al conectar a la base "+e);
		}
	}
	
	public Connection getConnection() {
		return conexion;
	}
	
	public void desconectar() {
		conexion = null;
	}

}
