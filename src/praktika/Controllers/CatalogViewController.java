package praktika.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import praktika.Manager;
import praktika.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CatalogViewController implements Initializable {

    @FXML
    private ImageView kiausinisImage, duonaImage, batonasImage, desraImage;
    @FXML
    private Label _1kiausiniai, _2duona, _3batonas, _4desra, kainaKiausinis, kainaDuona, kainaBatonas, kainaDesra;
    @FXML
    private TextField kiekisKiausiniai, kiekisDuona, kiekisBatonas, kiekisDesra;

    private Manager manager = null;
    private Stage stage = null;
    private String sessionCode = null;

    public void setManager(Manager manager){
        this.manager = manager;
        try {
            setDishInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSessionCode(String code){
        this.sessionCode = code;
    }

    public void showDetails(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/Aprasymas.fxml"));
        Parent root = loader.load();

        int dishID = Integer.parseInt(String.valueOf(event.getSource().toString().charAt(10)));
        AprasymasController aprasymasController = loader.getController();
        aprasymasController.setManager(manager);
        aprasymasController.setDishID(dishID);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Patiekalo aprasymas");
        stage.setScene(scene);
        stage.initOwner(kiausinisImage.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void addToBasket(ActionEvent event){
        int dishID = Integer.parseInt(String.valueOf(event.getTarget().toString().charAt(11)));
        int quantity = getQuantity(dishID);
        try {
            if(quantity > 0) {
                Product preke = manager.getProductByID(dishID);
                preke.setKiekis(quantity);
                manager.insertIntoBasket(preke, sessionCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getQuantity(int dishID){
        switch(dishID){
            case 1:
                try {
                    return Integer.parseInt(kiekisKiausiniai.getText());
                } catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 2:
                try {
                    return Integer.parseInt(kiekisDuona.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 3:
                try {
                    return Integer.parseInt(kiekisBatonas.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 4:
                try {
                    return Integer.parseInt(kiekisDesra.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            default:
                return -1;
        }
    }

    public void setDishInfo() throws Exception {
        Product kiausiniai = manager.getProductByID(1);
        Product duona = manager.getProductByID(2);
        Product batonas = manager.getProductByID(3);
        Product desra = manager.getProductByID(4);
        kiausinisImage.setImage(kiausiniai.getPaveikslelis());
        duonaImage.setImage(duona.getPaveikslelis());
        batonasImage.setImage(batonas.getPaveikslelis());
        desraImage.setImage(desra.getPaveikslelis());
        _1kiausiniai.setText(kiausiniai.getPavadinimas());
        _2duona.setText(duona.getPavadinimas());
        _3batonas.setText(batonas.getPavadinimas());
        _4desra.setText(desra.getPavadinimas());
        kainaKiausinis.setText(String.valueOf(kiausiniai.getKainabepvm()));
        kainaDuona.setText(String.valueOf(duona.getKainabepvm()));
        kainaBatonas.setText(String.valueOf(batonas.getKainabepvm()));
        kainaDesra.setText(String.valueOf(desra.getKainabepvm()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kiekisBatonas.setText("0");
        kiekisDesra.setText("0");
        kiekisDuona.setText("0");
        kiekisKiausiniai.setText("0");
    }

}
