package serviciosweb;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import controlador.UsuarioControl;
import modelo.Usuario;
import proyecto1.ItemUsuario;
 
@Path("/usuario")
public class UsuarioServicio {
        
      //------------------------LISTAR USUARIO-----------
        @GET
        @Path("/listar/{parametro}")
        @Produces("application/json")
        public ItemUsuario listarUsuario(@PathParam("parametro") String email ) throws Exception {
            try{
                UsuarioControl userCtr = new UsuarioControl();
                ArrayList<Usuario> lstUsu = userCtr.getUsuario(email);
                return new ItemUsuario(lstUsu);
            }catch(NumberFormatException e){
                return null;
            }        
        }
        
}
