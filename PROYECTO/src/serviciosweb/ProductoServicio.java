package serviciosweb;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import controlador.ProductoControl;
import modelo.Producto;
import proyecto1.ItemProducto;
 
@Path("/producto")
public class ProductoServicio {
 
    //--------------------LISTAR TODOS LOS PRODUCTO----------------------------------------------
        @GET
        @Path("/listar")
        @Produces("application/json")
        public ItemProducto listarProductosJson() throws Exception {
            ProductoControl productoControl = new ProductoControl();
            ArrayList<Producto> listaDeProductos = productoControl.listar();
            return new ItemProducto(listaDeProductos);             
        }
        
      //------------------------LISTAR UNICO PRODUCTO-----------
        @GET
        @Path("/listar/{parametro}")
        @Produces("application/json")
        public ItemProducto listarUnicoProductoXML(@PathParam("parametro") String codigoStr ) throws Exception {
            try{
                ProductoControl productoControl = new ProductoControl();
                int codigo = Integer.parseInt(codigoStr);
                ArrayList<Producto> listaDeProductos = productoControl.listarUnicoProducto(codigo);
                return new ItemProducto(listaDeProductos);
            }catch(NumberFormatException e){
                return null;
            }        
        }
}


