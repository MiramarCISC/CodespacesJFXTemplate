package edu.sdccd.cisc;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

public final class DogDialog {

    private DogDialog() {}

    public static Dog show(Stage owner, Dog existing) {
        Dialog<Dog> dlg = new Dialog<>();
        dlg.initOwner(owner);
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(existing == null ? "Add Dog" : "Edit Dog");

        ButtonType saveBtn = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        TextField nameField = new TextField(existing == null ? "" : existing.getName());
        TextField breedField = new TextField(existing == null ? "" : existing.getBreed());
        DatePicker dobPicker = new DatePicker(existing == null ? LocalDate.now() : existing.getDob());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Breed:"), breedField);
        grid.addRow(2, new Label("Date of Birth:"), dobPicker);
        dlg.getDialogPane().setContent(grid);

        dlg.setResultConverter(btn -> {
            if (btn == saveBtn) {
                String n = nameField.getText() == null ? "" : nameField.getText().trim();
                String b = breedField.getText() == null ? "" : breedField.getText().trim();
                LocalDate d = dobPicker.getValue();
                if (n.isEmpty() || b.isEmpty() || d == null) {
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "All fields are required.", ButtonType.OK);
                    a.showAndWait();
                    return null;
                }
                return new Dog(n, b, d);
            }
            return null; // canceled
        });

        return dlg.showAndWait().orElse(null);
    }
}
