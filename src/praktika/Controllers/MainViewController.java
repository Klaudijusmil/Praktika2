package praktika.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import praktika.Manager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private ImageView xIcon, basketIcon;
    @FXML
    private Pane emptyContainer, empty;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button loginButton;

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

    public void close(){
        Platform.exit();
    }


    public void loadBasket() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/BasketView.fxml"));
        Parent root = loader.load();

        BasketViewController basketViewController = loader.getController();
        basketViewController.setManager(manager);
        basketViewController.setStage(stage);
        basketViewController.setSessionCode(sessionCode);

        Scene scene = new Scene(root);
        stage.setTitle("Krepselis");
        stage.setScene(scene);
        stage.show();
    }

    public void loadCatalog() throws IOException {
        FXMLLoader loader = null;
        if(!manager.isAdmin())
            loader = new FXMLLoader(getClass().getResource("../Views/CatalogView.fxml"));
        else
            loader = new FXMLLoader(getClass().getResource("../Views/CatalogAdminView.fxml"));

        Node catalogs = loader.load();

        if(!manager.isAdmin()) {
            CatalogViewController catalogViewController = loader.getController();
            catalogViewController.setManager(manager);
            catalogViewController.setStage(stage);
            catalogViewController.setSessionCode(sessionCode);
        }
        else{
            CatalogAdminViewController catalogAdminViewController = loader.getController();
            catalogAdminViewController.setManager(manager);
            catalogAdminViewController.setStage(stage);
            catalogAdminViewController.setSessionCode(sessionCode);
        }

        empty.getChildren().clear();
        empty.getChildren().add(catalogs);
        scrollPane.setContent(empty);
    }

    public void loadLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/LoginView.fxml"));
        Node login = loader.load();

        LoginViewController loginViewController = loader.getController();
        loginViewController.setManager(manager);
        loginViewController.setStage(stage);
        loginViewController.setContainer(emptyContainer);
        loginViewController.setLogin(loginButton);
        loginViewController.setSessionCode(sessionCode);

        emptyContainer.getChildren().clear();
        emptyContainer.getChildren().add(login);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("assets/X.png");
        Image image = new Image(file.toURI().toString());
        xIcon.setImage(image);
        file = new File("assets/basket.png");
        image = new Image(file.toURI().toString());
        basketIcon.setImage(image);
    }

}
