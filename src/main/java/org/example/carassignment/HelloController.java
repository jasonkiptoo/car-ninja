package org.example.carassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HelloController {
    @FXML
    private TableView<Car> carTableView;
    @FXML
    private TableColumn<Car, Integer> yearColumn;
    @FXML
    private TableColumn<Car, String> makeColumn;
    @FXML
    private TableColumn<Car, String> modelColumn;
    @FXML
    private TableColumn<Car, Integer> indexColumn;
    @FXML
    private Label carDetailsLabel;
    @FXML
    private Label carCountLabel; // New Label for car count
    @FXML
    private TextField searchField;
    @FXML
    private VBox detailsBox;
    @FXML
    private VBox tableBox;

    private final ObservableList<Car> carData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        // Set the cell factory for the index column
        indexColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                }
            }
        });

        carTableView.setItems(carData);

        // Fetch car data when the system starts
        fetchCarData("camry");
    }

    @FXML
    private void onFetchCarData() {
        fetchCarData("camry");
    }

    @FXML
    private void onSearchCarData() {
        String model = searchField.getText().trim();
        fetchCarData(model);
    }

    private void fetchCarData(String model) {
        try {
            String apiKey = "rR27C8PXsHynl7caNW0YXnkkA1Xz3rp3pukAfU5G";
            String apiUrl = "https://api.api-ninjas.com/v1/cars?limit=20&model=" + model;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestProperty("X-Api-Key", apiKey);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                Gson gson = new Gson();
                List<Car> cars = gson.fromJson(response.toString(), new TypeToken<List<Car>>() {}.getType());
                carData.setAll(cars);
                carCountLabel.setText("Cars found: " + cars.size()); // Update the car count
            } else {
                System.out.println("GET request failed: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCarSelected(MouseEvent event) {
        Car selectedCar = carTableView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            detailsBox.setVisible(true);
            detailsBox.setManaged(true);
            tableBox.setVisible(false);
            tableBox.setManaged(false);
            carDetailsLabel.setText(
                    "Make: " + selectedCar.getMake() + "\n" +
                            "Model: " + selectedCar.getModel() + "\n" +
                            "Year: " + selectedCar.getYear() + "\n" +
                            "City MPG: " + selectedCar.getCityMpg() + "\n" +
                            "Class: " + selectedCar.getCarClass() + "\n" +
                            "Combination MPG: " + selectedCar.getCombinationMpg() + "\n" +
                            "Cylinders: " + selectedCar.getCylinders() + "\n" +
                            "Displacement: " + selectedCar.getDisplacement() + "\n" +
                            "Drive: " + selectedCar.getDrive() + "\n" +
                            "Fuel Type: " + selectedCar.getFuelType() + "\n" +
                            "Highway MPG: " + selectedCar.getHighwayMpg() + "\n" +
                            "Transmission: " + selectedCar.getTransmission()
            );
        }
    }

    @FXML
    private void onBackToTable() {
        detailsBox.setVisible(false);
        detailsBox.setManaged(false);
        tableBox.setVisible(true);
        tableBox.setManaged(true);
    }
}
