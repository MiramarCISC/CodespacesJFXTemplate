package edu.sdccd.cisc;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;

import java.time.LocalDate;

/** TableCell with inline DatePicker for LocalDate editing. */
public class DatePickerTableCell extends TableCell<Dog, LocalDate> {
    private final DatePicker picker = new DatePicker();
    private final StringConverter<LocalDate> converter;

    DatePickerTableCell(StringConverter<LocalDate> converter) {
        this.converter = converter;
        picker.setEditable(false);
        picker.valueProperty().addListener((obs, oldV, newV) -> {
            if (isEditing()) commitEdit(newV);
        });
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            picker.setValue(getItem());
            setText(null);
            setGraphic(picker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(format(getItem()));
        setGraphic(null);
    }

    @Override
    protected void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                picker.setValue(item);
                setText(null);
                setGraphic(picker);
            } else {
                setText(format(item));
                setGraphic(null);
            }
        }
    }

    @Override
    public void commitEdit(LocalDate newValue) {
        super.commitEdit(newValue);
        Dog rowItem = getTableView().getItems().get(getIndex());
        rowItem.setDob(newValue);
    }

    private String format(LocalDate d) {
        return d == null ? "" : converter.toString(d);
    }
}