package org.example.carassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    @FXML
    private TextField searchField;

    private final ObservableList<Car> carData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        makeColumn.setCellValueFactory(new PropertyValueFactory<>("make"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        carTableView.setItems(carData);

        // Fetch car data when the system starts
        fetchCarData(""); // Optionally pass a default value or leave it empty
    }


    @FXML
    private void onFetchCarData() {
        fetchCarData("");
    }

    @FXML
    private void onSearchCarData() {
        String model = searchField.getText().trim();
        fetchCarData(model);
    }

    private void fetchCarData(String model) {
        try {
            String apiKey = "rR27C8PXsHynl7caNW0YXnkkA1Xz3rp3pukAfU5G";
            String apiUrl = "https://api.api-ninjas.com/v1/cars?limit=20&model=camry";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestProperty("X-Api-Key", apiKey);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) { // HTTP OK
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
            carDetailsLabel.setText("ID: " + selectedCar.getId() + "\n" +
                    "Make: " + selectedCar.getMake() + "\n" +
                    "Model: " + selectedCar.getModel() + "\n" +
                    "Year: " + selectedCar.getYear());
        }
    }
}
