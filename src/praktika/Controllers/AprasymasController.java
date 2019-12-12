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
    private int dishID = 0;

    public void setManager(Manager manager){
        this.manager = manager;
    }

    public void setDishID(int id){
        this.dishID = id;
        loadDishDetails();
    }

    public void loadDishDetails(){
        try {
            Product preke = manager.getProductByID(this.dishID);
            aprasymas.setText(preke.getAprasymas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
