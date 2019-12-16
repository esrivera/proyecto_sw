package proyecto1;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import modelo.Producto;
 
@XmlRootElement(name="ListaProductos")
public class ItemProducto {
     
    private List<Producto> items;
      
    public ItemProducto(){
    }
  
    public ItemProducto(List<Producto> items){
        this.items = items;
    }
  
    @XmlElement(name="producto")
    public List<Producto> getItems(){
        return items;
    }
}
