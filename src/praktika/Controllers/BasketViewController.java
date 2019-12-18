package praktika.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import praktika.Manager;
import praktika.Model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BasketViewController implements Initializable {

    @FXML
    private TableView lentele;
    @FXML
    private Text priceText;

    private Manager manager = null;
    private Stage stage = null;
    private String sessionCode = null;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSessionCode(String code) {
        this.sessionCode = code;
        loadProductsToTable();
    }

    public void LoadMeniu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/MainView.fxml"));
        Parent root = loader.load();

        MainViewController mainViewController = loader.getController();
        mainViewController.setManager(manager);
        mainViewController.setStage(stage);
        mainViewController.setSessionCode(sessionCode);

        Scene scene = new Scene(root);
        stage.setTitle("Meniu");
        stage.setScene(scene);
        stage.show();
    }

    public void deleteSelected() {
        Product preke = (Product) lentele.getSelectionModel().getSelectedItem();
        try {
            if (preke != null) {
                manager.deleteProduct(preke);
                loadProductsToTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increaseQuantityOfSelectedProduct() {
        Product preke = (Product) lentele.getSelectionModel().getSelectedItem();
        if (preke != null) {
            int increasedQuantity = preke.getKiekis() + 1;
            if (increasedQuantity < 1000) {
                preke.setKiekis(increasedQuantity);
                try {
                    manager.updateProcuctInfo(preke);
                    loadProductsToTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Neteisingas kiekis");
                alert.setHeaderText(null);
                alert.setContentText("Pasirinktas kiekis per didelis.");
                alert.showAndWait();
            }
        }
    }

    public void decreaseQuantityOfSelectedProduct() {
        Product preke = (Product) lentele.getSelectionModel().getSelectedItem();
        if (preke != null) {
            int decreasedQuantity = preke.getKiekis() - 1;
            if (decreasedQuantity > 0) {
                preke.setKiekis(decreasedQuantity);
                try {
                    manager.updateProcuctInfo(preke);
                    loadProductsToTable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Neteisingas kiekis");
                alert.setHeaderText(null);
                alert.setContentText("Pasirinktas kiekis negali būti mažesnis už vieną.");
                alert.showAndWait();
            }
        }
    }

    public void printCheck() {
        FileWriter fw = null;

        try {
            fw = new FileWriter("cekis.txt");
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            fw.append(dt.format(date));
            fw.append("\nPavadinimas\t\tKiekis\t\tKaina\n\n");
            for (Product p : manager.getAllProductsFromSession(sessionCode))
                fw.append(p.toString() + "\n");
            fw.append(getPrices());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void showPrices() {
        try {
            priceText.setText(getPrices());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrices() throws Exception {
        return "\nKaina (be PVM): " + manager.getSumOfProductsWithoutVAT(sessionCode) + "\nKaina (su PVM): " + manager.getSumOfProductsWithVAT(sessionCode);
    }

    public void loadProductsToTable() {
        try {
            lentele.getItems().clear();
            ObservableList<Product> products = FXCollections.observableArrayList(manager.getAllProductsFromSession(sessionCode));
            lentele.setItems(products);
            showPrices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<String, Product> column1 = new TableColumn<>("Produktas");
        column1.setCellValueFactory(new PropertyValueFactory<>("pavadinimas"));

        TableColumn<String, Product> column2 = new TableColumn<>("Kaina (be PVM)");
        column2.setCellValueFactory(new PropertyValueFactory<>("kainabepvm"));

        TableColumn<String, Product> column3 = new TableColumn<>("Kaina (su PVM)");
        column3.setCellValueFactory(new PropertyValueFactory<>("kainasupvm"));

        TableColumn<String, Product> column4 = new TableColumn<>("Kiekis");
        column4.setCellValueFactory(new PropertyValueFactory<>("kiekis"));

        lentele.getColumns().add(column1);
        lentele.getColumns().add(column2);
        lentele.getColumns().add(column3);
        lentele.getColumns().add(column4);
    }

}