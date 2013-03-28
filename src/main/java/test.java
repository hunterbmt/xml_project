
import com.vteam.xml_project.dto.ProductDTO;
import com.vteam.xml_project.dto.ProductListDTO;
import com.vteam.xml_project.util.XMLUtil;
import javax.xml.bind.JAXBException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lenovo
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException {
        ProductListDTO productListDTO = new ProductListDTO();
        productListDTO.setNumberOfProduct(2);
        ProductDTO p = new ProductDTO();
        p.setId(1);
        p.setName("bla bla");
        p.setDescription("<p>dep trai</p>");
        p.setImage("aaa/sss.jpg");
        p.setImageName("ffdfdfd");
        p.setCategoryId(1);
        p.setCategoryName("aaaa");
        p.setMaxPrice(10);
        p.setMinPrice(20);
        p.setBidId(1);
        p.setBidCost(2);
        p.setBidTimeRemain(312);
        productListDTO.getProductList().add(p);
        p = new ProductDTO();
        p.setId(2);
        p.setName("bla bla");
        p.setDescription("<p>dep trai</p>");
        p.setImage("aaa/sss.jpg");
        p.setImageName("ffdfdfd");
        p.setCategoryId(1);
        p.setCategoryName("aaaa");
        p.setMaxPrice(10);
        p.setMinPrice(20);
        p.setBidId(1);
        p.setBidCost(2);
        p.setBidTimeRemain(312);
        productListDTO.getProductList().add(p);
        XMLUtil.Marshall(productListDTO, "src/products.xml");
    }
}
