package proyecto1;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Usuario;
 
@XmlRootElement(name="ListaUsuarios")
public class ItemUsuario {
     
    private List<Usuario> items;
      
    public ItemUsuario(){
    }
  
    public ItemUsuario(List<Usuario> items){
        this.items = items;
    }
  
    @XmlElement(name="usuario")
    public List<Usuario> getItems(){
        return items;
    }
}
