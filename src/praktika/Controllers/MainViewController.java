package praktika.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/CatalogView.fxml"));
        Node catalogs = loader.load();

        CatalogViewController catalogViewController = loader.getController();
        catalogViewController.setManager(manager);
        catalogViewController.setStage(stage);
        catalogViewController.setSessionCode(sessionCode);

        emptyContainer.getChildren().clear();
        emptyContainer.getChildren().add(catalogs);
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
        File file = new File("assets/X.png");
        Image image = new Image(file.toURI().toString());
        xIcon.setImage(image);
        file = new File("assets/basket.png");
        image = new Image(file.toURI().toString());
        basketIcon.setImage(image);
    }

}
