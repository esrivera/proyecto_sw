package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Usuario;
import proyecto1.ConexionBD;

public class UsuarioControl {
	
	public String crear (Usuario u) {
		
		ConexionBD con = new ConexionBD();
		String result = "";
		try {
			Statement st = con.getConnection().createStatement();
			String sql = "INSERT INTO usuario (usu_nombre,usu_apellido,usu_email,usu_clave,usu_telefono,usu_sexo) "
					+ "VALUES('"+u.getNombre()+"', '"+u.getApellido()+"', '"+u.getEmail()+"', '"+ u.getClave()+"', '"+
					u.getTelefono()+"', '"+u.getSexo()+"')";
			st.executeUpdate(sql);
			st.close();
			con.desconectar();
			result = "EXITOSO";
		}catch(Exception e) {
			result = "ERROR: " + e;
		}
		return result;
	}
	
	public ArrayList<Usuario> getUsuario(String email) throws Exception {
        ArrayList<Usuario> user = new ArrayList<Usuario>();
        ConexionBD bd = new ConexionBD();
        Connection conexion = bd.getConnection();
        String sql = "SELECT * FROM usuario WHERE usu_email =  ? ";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Usuario usu = new Usuario();
            usu.setCodigo( rs.getInt("usu_codigo") );
            usu.setNombre( rs.getString("usu_nombre"));
            usu.setApellido( rs.getString("usu_apellido") );
            usu.setEmail(rs.getString("usu_email"));
            usu.setClave(rs.getString("usu_clave"));
            usu.setTelefono(rs.getString("usu_telefono"));
            usu.setSexo(rs.getString("usu_sexo"));
            user.add(usu);
        }       
        pst.close();
        conexion.close();
        return user;
    }
	
	public boolean searchUsuario(String email) throws Exception {
		boolean bandera;
        ConexionBD bd = new ConexionBD();
        Connection conexion = bd.getConnection();
        String sql = "SELECT * FROM usuario WHERE usu_email =  ? ";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
        	bandera = true;
        }
        else {
        	bandera=false;
        }
        pst.close();
        conexion.close();
        return bandera;
    }
	
	public boolean validarUsuario(String email, String password) throws Exception {
		boolean bandera;
        ConexionBD bd = new ConexionBD();
        Connection conexion = bd.getConnection();
        String sql = "SELECT * FROM usuario WHERE usu_email =  '"+ email +"' AND usu_clave = '" +password+"'";
        PreparedStatement pst = conexion.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
        	bandera = true;
        }
        else {
        	bandera=false;
        }
        pst.close();
        conexion.close();
        return bandera;
    }

}
