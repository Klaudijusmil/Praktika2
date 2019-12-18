package praktika.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import praktika.Manager;
import praktika.Model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class AprasymasController implements Initializable {

    @FXML
    private Text aprasymas;

    private Manager manager = null;
    private int productID = 0;

    public void setManager(Manager manager){
        this.manager = manager;
    }

    public void setProductID(int id){
        this.productID = id;
        loadProductDetails();
    }

    public void loadProductDetails(){
        try {
            Product preke = manager.getProductByID(this.productID);
            aprasymas.setText(preke.getAprasymas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
