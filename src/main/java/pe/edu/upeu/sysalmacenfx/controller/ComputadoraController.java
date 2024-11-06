package pe.edu.upeu.sysalmacenfx.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.modelo.Computadora;
import pe.edu.upeu.sysalmacenfx.servicio.ComputadoraService;

@Component
public class ComputadoraController {
    @FXML private TextField marcaField;
    @FXML private TextField modeloField;
    @FXML private TextField precioField;
    @FXML private TextField stockField;
    @FXML private TextArea descripcionArea;
    @FXML private TextField procesadorField;
    @FXML private TextField ramField;
    @FXML private TextField almacenamientoField;

    @FXML private TableView<Computadora> computadoraTable;
    @FXML private TableColumn<Computadora, String> marcaColumn;
    @FXML private TableColumn<Computadora, String> modeloColumn;
    @FXML private TableColumn<Computadora, Double> precioColumn;
    @FXML private TableColumn<Computadora, Integer> stockColumn;
    @FXML private TableColumn<Computadora, String> procesadorColumn;
    @FXML private TableColumn<Computadora, Integer> ramColumn;

    @FXML private Button guardarButton;
    @FXML private Button limpiarButton;
    @FXML private Button eliminarButton;

    @Autowired
    private ComputadoraService computadoraService;

    private Computadora computadoraSeleccionada;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarDatos();
        configurarListeners();
        configurarValidaciones();
    }

    private void configurarColumnas() {
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        procesadorColumn.setCellValueFactory(new PropertyValueFactory<>("procesador"));
        ramColumn.setCellValueFactory(new PropertyValueFactory<>("ram"));
    }

    private void configurarListeners() {
        computadoraTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                computadoraSeleccionada = newSelection;
                cargarDatosEnFormulario(newSelection);
                eliminarButton.setDisable(false);
            } else {
                computadoraSeleccionada = null;
                eliminarButton.setDisable(true);
            }
        });

        // Validación en tiempo real para campos numéricos
        precioField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                precioField.setText(oldValue);
            }
        });

        stockField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                stockField.setText(oldValue);
            }
        });

        ramField.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ramField.setText(oldValue);
            }
        });
    }

    private void configurarValidaciones() {
        guardarButton.disableProperty().bind(
                marcaField.textProperty().isEmpty()
                        .or(modeloField.textProperty().isEmpty())
                        .or(precioField.textProperty().isEmpty())
                        .or(stockField.textProperty().isEmpty())
                        .or(procesadorField.textProperty().isEmpty())
                        .or(ramField.textProperty().isEmpty())
                        .or(almacenamientoField.textProperty().isEmpty())
        );
    }

    @FXML
    private void handleGuardar() {
        if (!validarCampos()) {
            mostrarAlerta("Error de Validación", "Por favor, complete todos los campos correctamente.");
            return;
        }

        try {
            Computadora computadora = computadoraSeleccionada != null ?
                    computadoraSeleccionada : new Computadora();

            computadora.setMarca(marcaField.getText());
            computadora.setModelo(modeloField.getText());
            computadora.setPrecio(Double.parseDouble(precioField.getText()));
            computadora.setStock(Integer.parseInt(stockField.getText()));
            computadora.setDescripcion(descripcionArea.getText());
            computadora.setProcesador(procesadorField.getText());
            computadora.setRam(Integer.parseInt(ramField.getText()));
            computadora.setAlmacenamiento(almacenamientoField.getText());

            computadoraService.guardar(computadora);
            mostrarAlerta("Éxito", "Computadora guardada exitosamente!");
            limpiarFormulario();
            cargarDatos();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar la computadora: " + e.getMessage());
        }
    }

    @FXML
    private void handleEliminar() {
        if (computadoraSeleccionada != null) {
            try {
                computadoraService.eliminar(computadoraSeleccionada.getId());
                mostrarAlerta("Éxito", "Computadora eliminada exitosamente!");
                limpiarFormulario();
                cargarDatos();
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al eliminar la computadora: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleLimpiar() {
        limpiarFormulario();
    }

    private void cargarDatos() {
        computadoraTable.setItems(FXCollections.observableArrayList(
                computadoraService.listarTodo()));
    }

    private void cargarDatosEnFormulario(Computadora computadora) {
        marcaField.setText(computadora.getMarca());
        modeloField.setText(computadora.getModelo());
        precioField.setText(computadora.getPrecio().toString());
        stockField.setText(computadora.getStock().toString());
        descripcionArea.setText(computadora.getDescripcion());
        procesadorField.setText(computadora.getProcesador());
        ramField.setText(computadora.getRam().toString());
        almacenamientoField.setText(computadora.getAlmacenamiento());
    }

    private void limpiarFormulario() {
        marcaField.clear();
        modeloField.clear();
        precioField.clear();
        stockField.clear();
        descripcionArea.clear();
        procesadorField.clear();
        ramField.clear();
        almacenamientoField.clear();
        computadoraSeleccionada = null;
        computadoraTable.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        try {
            if (precioField.getText() != null && !precioField.getText().isEmpty()) {
                Double.parseDouble(precioField.getText());
            }
            if (stockField.getText() != null && !stockField.getText().isEmpty()) {
                Integer.parseInt(stockField.getText());
            }
            if (ramField.getText() != null && !ramField.getText().isEmpty()) {
                Integer.parseInt(ramField.getText());
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}