# DogApp — POJO Model, UI Uses JavaFX Bean Properties

- `Dog` is a **plain POJO** (no `javafx.beans` in the model).
- The **UI layer** uses **`SimpleStringProperty`** / **`SimpleObjectProperty`**
  in `setCellValueFactory(...)` (instead of `ReadOnly*Wrapper`).

## Run (Codespaces)
```bash
mvn -q -Dprism.order=sw javafx:run
```
