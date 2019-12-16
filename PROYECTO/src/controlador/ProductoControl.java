package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Producto;
import proyecto1.ConexionBD;

public class ProductoControl {
	
	public String insertar (Producto p) {
		
		ConexionBD con = new ConexionBD();
		String result = "";
		try {
			Statement st = con.getConnection().createStatement();
			String sql = "INSERT INTO producto (pro_nombre,pro_precio,pro_categoria,pro_stock) "
					+ "VALUES('"+p.getNombre()+"', "+p.getPrecio()+", '"+p.getCategoria()+"' ,"+p.getStock()+")";
			st.executeUpdate(sql);
			st.close();
			con.desconectar();
			result = "EXITOSO";
		}catch(Exception e) {
			result = "ERROR: " + e;
		}
		return result;
	}
	
	public ArrayList<Producto> listar(){
		ConexionBD con = new ConexionBD();
		ArrayList<Producto> listaProductos = new ArrayList<Producto>();
		try {
			Statement st = con.getConnection().createStatement();
			String sql = "SELECT * FROM producto";
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) {
				int codigo =  Integer.parseInt(rs.getString("pro_codigo"));
				String nombre = rs.getString("pro_nombre");
				float precio = rs.getFloat("pro_precio");
				String categoria = rs.getString("pro_categoria");
				int stock = rs.getInt("pro_stock");
				Producto p = new Producto();
				p.setCodigo(codigo);
				p.setNombre(nombre);
				p.setPrecio(precio);
				p.setCategoria(categoria);
				p.setStock(stock);
				listaProductos.add(p);
			}
			st.close();
			rs.close();
			con.desconectar();
		}catch(Exception e) {
			System.out.println("Error: " + e);
		}
		return listaProductos;
	}
	
	public ArrayList<Producto> listarUnicoProducto(int codigo) throws Exception {
        ArrayList<Producto> listaDeProductos = new ArrayList<Producto>();
        ConexionBD bd = new ConexionBD();
        Connection conexion = bd.getConnection();
        String sql = "SELECT * FROM producto WHERE pro_codigo =  ? ";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setInt(1, codigo);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Producto producto = new Producto();
            producto.setCodigo( rs.getInt("pro_codigo") );
            producto.setNombre( rs.getString("pro_nombre"));
            producto.setPrecio( rs.getFloat("pro_precio") );
            producto.setCategoria(rs.getString("pro_categoria"));
            producto.setStock(rs.getInt("pro_stock"));
            listaDeProductos.add(producto);
        }       
        pst.close();
        conexion.close();
        return listaDeProductos;
    }
	
	public String modificarProducto(Producto pro) {
		String result = "";
        try {
        	ConexionBD bd = new ConexionBD();
            Connection conexion = bd.getConnection();
            PreparedStatement ps = conexion.prepareStatement("UPDATE producto SET pro_nombre='" +pro.getNombre()
            		+ "',pro_precio=" +pro.getPrecio() + ", pro_categoria='" + pro.getCategoria() 
            		+ "',pro_stock=" + pro.getStock() + " WHERE pro_codigo=" + pro.getCodigo());
            ps.executeUpdate();
            ps.close();
            conexion.close();
            result = "PRODUCTO MODIFICADO EXITOSAMENTE";
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            result = "ERROR: " + e;
        }
        return result;
    }

    public boolean deleteProduct(int id) {
    	boolean result = false;
        try {
            String SQL = "DELETE FROM producto where pro_codigo=" + id;
            ConexionBD bd = new ConexionBD();
            Connection conexion = bd.getConnection();
            Statement st = conexion.createStatement();
            int n = st.executeUpdate(SQL);
            if (n >= 0) {
            	result = true;
            }
            else {
            	result = false;
            }
            st.close();
            conexion.close();

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        return result;
    }
    
    public Producto getProducto(int codigo) throws Exception {
    	Producto producto = new Producto();
        ConexionBD bd = new ConexionBD();
        Connection conexion = bd.getConnection();
        String sql = "SELECT * FROM producto WHERE pro_codigo =  ? ";
        PreparedStatement pst = conexion.prepareStatement(sql);
        pst.setInt(1, codigo);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
        	while (rs.next()) {     
                producto.setCodigo( rs.getInt("pro_codigo") );
                producto.setNombre( rs.getString("pro_nombre"));
                producto.setPrecio( rs.getFloat("pro_precio") );
                producto.setCategoria(rs.getString("pro_categoria"));
                producto.setStock(rs.getInt("pro_stock"));
            }  
        }
        else {
        	producto = null;
        }
             
        pst.close();
        conexion.close();
        return producto;
    }

}
