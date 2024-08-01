package org.example.carassignment;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CarListController {
    @FXML
    private ListView<String> carListView;

    @FXML
    public void initialize() {
        CompletableFuture<List<Object>> carsFuture = CarService.getCars();
        carsFuture.thenAccept(cars -> {
            for (Object car : cars) {
                carListView.getItems().add(car.toString());
            }
        });
    }
}
