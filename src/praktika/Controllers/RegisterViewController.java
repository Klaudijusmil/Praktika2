package praktika.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import praktika.Manager;
import praktika.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterViewController implements Initializable {

    @FXML
    private TextField slapyvardis, slaptazodis, slaptazodis2;
    @FXML
    private Pane emptyContainer;

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

    public void setContainer(Pane emptyContainer){
        this.emptyContainer = emptyContainer;
    }

    public void confirm(){
        String username = slapyvardis.getText();
        String pass = slaptazodis.getText();
        String pass2 = slaptazodis2.getText();

        if(pass.equals(pass2)) {
            User user = new User(0, username, pass, false);
            try {
                manager.insertUser(user);
                loadLogin();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("BAD PASSWORD");
        }

        System.out.println(username + "  " + pass);
    }

    public void loadLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LoginView.fxml"));
        Node login = loader.load();

        LoginViewController loginViewController = loader.getController();
        loginViewController.setManager(manager);
        loginViewController.setStage(stage);
        loginViewController.setContainer(emptyContainer);
        loginViewController.setSessionCode(sessionCode);

        emptyContainer.getChildren().clear();
        emptyContainer.getChildren().add(login);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
