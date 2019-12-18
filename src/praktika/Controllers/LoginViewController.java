package praktika.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import praktika.Manager;
import praktika.Model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private TextField slapyvardis, slaptazodis;
    @FXML
    private Pane emptyContainer;
    @FXML
    private Button login, catalog;

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

    public void setLogin(Button login){
        this.login = login;
    }

    public void setCatalog(Button catalog){
        this.catalog = catalog;
    }

    public void confirm(){
        String username = slapyvardis.getText();
        String pass = slaptazodis.getText();

        User user;
        try {
            user = manager.getUserByUsername(username);
            if(manager.isValidLogin(user, pass)) {
                manager.setAdmin(user.isAdmin());
                login.setDisable(true);
                catalog.setDisable(false);
                manager.setOnline(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadRegister() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/RegisterView.fxml"));
        Node register = loader.load();

        RegisterViewController registerViewController = loader.getController();
        registerViewController.setManager(manager);
        registerViewController.setStage(stage);
        registerViewController.setContainer(emptyContainer);
        registerViewController.setSessionCode(sessionCode);

        emptyContainer.getChildren().clear();
        emptyContainer.getChildren().add(register);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
