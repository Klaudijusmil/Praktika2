package praktika.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import praktika.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private TextField slapyvardis, slaptazodis;

    private Manager manager = null;
    private Stage stage = null;
    private String sessionCode = null;

    public void setManager(Manager manager){
        this.manager = manager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSessionCode(String code){
        this.sessionCode = code;
    }

    public void confirm(){
        String username = slapyvardis.getText();
        String pass = slaptazodis.getText();
        System.out.println(username + "  " + pass);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
