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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CatalogAdminViewController implements Initializable {

    @FXML
    private ImageView peleImage, klaviaturaImage, ausinesImage, monitoriusImage, caseImage;
    @FXML
    private Label _1pele, _2klaviatura, _3ausines, _4monitorius, _5case, kainaPele1, kainaKlaviatura2, kainaAusines3, kainaMonitorius4, kainaCase5;
    @FXML
    private TextField kiekisPele1, kiekisklaviatura2, kiekisausines3, kiekismonitorius4, kiekiscase5;
    @FXML
    private ArrayList<Label> kainos = new ArrayList<>();
    @FXML
    private ArrayList<TextField> kainosInput = new ArrayList<>();

    private Manager manager = null;
    private Stage stage = null;
    private String sessionCode = null;

    public void setManager(Manager manager){
        this.manager = manager;
        try {
            setProductInfo();
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
        stage.initOwner(peleImage.getScene().getWindow());
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

    public void changeQuantity(ActionEvent event){
        int productId = Integer.parseInt(event.getSource().toString().substring(11, 12));

        Label label = kainos.get(productId - 1);
        int labelID = Integer.parseInt(label.getId().substring(label.getId().length() - 1));

        int naujaKaina = Integer.parseInt(kainosInput.get(labelID - 1).getText());

        try {
            manager.editProductInfo(productId, naujaKaina);
            label.setText(kainosInput.get(labelID - 1).getText());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int getQuantity(int dishID){
        switch(dishID){
            case 1:
                try {
                    return Integer.parseInt(kiekisPele1.getText());
                } catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 2:
                try {
                    return Integer.parseInt(kiekisklaviatura2.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 3:
                try {
                    return Integer.parseInt(kiekisausines3.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            case 4:
                try {
                    return Integer.parseInt(kiekismonitorius4.getText());
                }
                catch (Exception e){
                    System.out.println("Bloga įvestis.");
                }
            default:
                return -1;
        }
    }

    public void setProductInfo() throws Exception {
        Product peles = manager.getProductByID(1);
        Product klaviatura = manager.getProductByID(2);
        Product ausines = manager.getProductByID(3);
        Product monitorius = manager.getProductByID(4);
        Product caseRGB = manager.getProductByID(5);
        peleImage.setImage(peles.getPaveikslelis());
        klaviaturaImage.setImage(klaviatura.getPaveikslelis());
        ausinesImage.setImage(ausines.getPaveikslelis());
        monitoriusImage.setImage(monitorius.getPaveikslelis());
        caseImage.setImage(caseRGB.getPaveikslelis());
        _1pele.setText(peles.getPavadinimas());
        _2klaviatura.setText(klaviatura.getPavadinimas());
        _3ausines.setText(ausines.getPavadinimas());
        _4monitorius.setText(monitorius.getPavadinimas());
        _5case.setText(caseRGB.getPavadinimas());
        kainaPele1.setText(String.valueOf(peles.getKainabepvm()));
        kainaKlaviatura2.setText(String.valueOf(klaviatura.getKainabepvm()));
        kainaAusines3.setText(String.valueOf(ausines.getKainabepvm()));
        kainaMonitorius4.setText(String.valueOf(monitorius.getKainabepvm()));
        kainaCase5.setText(String.valueOf(caseRGB.getKainabepvm()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kainos.add(kainaPele1);
        kainos.add(kainaKlaviatura2);
        kainos.add(kainaAusines3);
        kainos.add(kainaMonitorius4);
        kainos.add(kainaCase5);

        kainosInput.add(kiekisPele1);
        kainosInput.add(kiekisklaviatura2);
        kainosInput.add(kiekisausines3);
        kainosInput.add(kiekismonitorius4);
        kainosInput.add(kiekiscase5);

        kiekisausines3.setText("0");
        kiekismonitorius4.setText("0");
        kiekisklaviatura2.setText("0");
        kiekisPele1.setText("0");
        kiekiscase5.setText("0");
    }

}
