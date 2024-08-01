package org.example.carassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, Integer> idColumn;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> yearColumn;
    @FXML
    private Label carDetailsLabel;

    private final ObservableList<Car> carData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carTableView.setItems(carData);
    }

    @FXML
    private void onFetchCarData() {
        // Fetch data from API and add to carData
        carData.addAll(
                new Car(1, "Toyota", "Camry", 2020),
                new Car(2, "Honda", "Accord", 2019),
                new Car(3, "Ford", "Mustang", 2021)
        );
    }

    @FXML
    private void onCarSelected(MouseEvent event) {
        Car selectedCar = carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            carDetailsLabel.setText("ID: " + selectedCar.getId() + "\n" +
                    "Make: " + selectedCar.getMake() + "\n" +
                    "Model: " + selectedCar.getModel() + "\n" +
                    "Year: " + selectedCar.getYear());
        }
    }
}
