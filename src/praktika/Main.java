package praktika;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import praktika.Controllers.MainViewController;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Manager manager = new Manager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/MainView.fxml"));
        Parent root = loader.load();

        MainViewController mainViewController = loader.getController();
        mainViewController.setManager(manager);
        mainViewController.setStage(primaryStage);
        mainViewController.setSessionCode(generateSessionCode());

        primaryStage.setTitle("Meniu");
        primaryStage.setScene(new Scene(root, 800, 750));
        primaryStage.show();
    }
    // paleidus programa sukuriamas session code. Tam daroma, kad butu galima sekti pirkimu istorija.
    private String generateSessionCode(){
        String code = "";
        Random r = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 5; i++)
            code += alphabet.charAt(r.nextInt(alphabet.length()));
        return code;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
