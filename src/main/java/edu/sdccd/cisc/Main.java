package edu.sdccd.cisc;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main extends Application {

    private final ObservableList<Dog> data = FXCollections.observableArrayList(
            new Dog("Rover", "Labrador", LocalDate.of(2018, 3, 14)),
            new Dog("Bella", "Corgi", LocalDate.of(2020, 7, 4)),
            new Dog("Max", "Border Collie", LocalDate.of(2019, 12, 2))
    );

    @Override
    public void start(Stage stage) {
        stage.setTitle("Dogs!");

        TableView<Dog> table = new TableView<>(data);
        table.setEditable(true);

        TableColumn<Dog, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(e -> e.getRowValue().setName(e.getNewValue()));
        nameCol.setPrefWidth(160);

        TableColumn<Dog, String> breedCol = new TableColumn<>("Breed");
        breedCol.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getBreed()));
        breedCol.setCellFactory(TextFieldTableCell.forTableColumn());
        breedCol.setOnEditCommit(e -> e.getRowValue().setBreed(e.getNewValue()));
        breedCol.setPrefWidth(180);

        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;
        StringConverter<LocalDate> dateConv = new LocalDateStringConverter(fmt, fmt);
        TableColumn<Dog, LocalDate> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(cd -> new SimpleObjectProperty<>(cd.getValue().getDob()));
        dobCol.setCellFactory(col -> new DatePickerTableCell(dateConv));
        dobCol.setPrefWidth(160);

        table.getColumns().addAll(nameCol, breedCol, dobCol);

        Button addBtn = new Button("Add");
        Button editBtn = new Button("Edit…");
        Button delBtn = new Button("Delete");
        HBox toolbar = new HBox(8, addBtn, editBtn, delBtn);
        toolbar.setPadding(new Insets(10));

        addBtn.setOnAction(e -> {
            Dog created = DogDialog.show(stage, null);
            if (created != null) data.add(created);
        });
        editBtn.setOnAction(e -> {
            Dog selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) { info("Select a dog to edit."); return; }
            Dog edited = DogDialog.show(stage, selected);
            if (edited != null) {
                selected.setName(edited.getName());
                selected.setBreed(edited.getBreed());
                selected.setDob(edited.getDob());
                table.refresh();
            }
        });
        delBtn.setOnAction(e -> {
            Dog selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) { info("Select a dog to delete."); return; }
            data.remove(selected);
        });

        BorderPane root = new BorderPane(table);
        root.setTop(toolbar);
        Scene scene = new Scene(root, 640, 380);
        stage.setScene(scene);
        stage.show();
    }

    private void info(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
