package co.edu.poli.Proyecto.controlador;

import co.edu.poli.Proyecto.modelo.Alimento;
import co.edu.poli.Proyecto.servicios.ServicioAlimentos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión de alimentos espaciales
 * @author Manuel Morales, Darien Díaz
 * @since 2025
 */
public class AlimentoController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCantidad;
    @FXML private DatePicker dateCaducidad;
    @FXML private Label lblMensaje;

    @FXML private TableView<Alimento> tablaAlimentos;
    @FXML private TableColumn<Alimento, String> colId;
    @FXML private TableColumn<Alimento, String> colNombre;
    @FXML private TableColumn<Alimento, Integer> colCantidad;
    @FXML private TableColumn<Alimento, String> colCaducidad;

    private ObservableList<Alimento> listaAlimentos;
    private ServicioAlimentos servicioAlimentos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicioAlimentos = new ServicioAlimentos();
        listaAlimentos = FXCollections.observableArrayList(servicioAlimentos.getAlimentos());
        
        configurarTabla();
        actualizarMensajeTabla();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        tablaAlimentos.setItems(listaAlimentos);
    }

    @FXML
    private void guardarAlimento() {
        try {
            if (!validarCampos()) return;

            String id = txtId.getText().trim();
            String nombre = txtNombre.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            LocalDate fecha = dateCaducidad.getValue();

            Alimento alimento = new Alimento(id, nombre, cantidad, fecha.toString());
            
            if (servicioAlimentos.agregarAlimento(alimento)) {
                listaAlimentos.setAll(servicioAlimentos.getAlimentos());
                limpiarCampos();
                mostrarMensaje("✅ Alimento guardado correctamente", "success");
                actualizarMensajeTabla();
            } else {
                mostrarMensaje("❌ Ya existe un alimento con ese ID", "error");
            }

        } catch (NumberFormatException ex) {
            mostrarMensaje("❌ La cantidad debe ser un número entero válido", "error");
        }
    }

    @FXML
    private void modificarAlimento() {
        Alimento seleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("⚠️ Seleccione un alimento para modificar", "error");
            return;
        }

        try {
            if (!validarCampos()) return;

            seleccionado.setNombre(txtNombre.getText().trim());
            seleccionado.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
            seleccionado.setFechaCaducidad(dateCaducidad.getValue().toString());

            servicioAlimentos.actualizarAlimento(seleccionado);
            listaAlimentos.setAll(servicioAlimentos.getAlimentos());
            limpiarCampos();
            mostrarMensaje("✅ Alimento modificado correctamente", "success");
        } catch (Exception ex) {
            mostrarMensaje("❌ Error al modificar el alimento", "error");
        }
    }

    @FXML
    private void eliminarAlimento() {
        Alimento seleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            servicioAlimentos.eliminarAlimento(seleccionado.getId());
            listaAlimentos.setAll(servicioAlimentos.getAlimentos());
            limpiarCampos();
            mostrarMensaje("✅ Alimento eliminado correctamente", "success");
            actualizarMensajeTabla();
        } else {
            mostrarMensaje("⚠️ Seleccione un alimento para eliminar", "error");
        }
    }

    @FXML
    private void serializar() {
        if (servicioAlimentos.serializar()) {
            mostrarMensaje("💾 Datos guardados en archivo", "success");
        } else {
            mostrarMensaje("❌ Error al guardar los datos", "error");
        }
    }

    @FXML
    private void deserializar() {
        if (servicioAlimentos.deserializar()) {
            listaAlimentos.setAll(servicioAlimentos.getAlimentos());
            mostrarMensaje("📂 Datos cargados correctamente", "success");
            actualizarMensajeTabla();
        } else {
            mostrarMensaje("❌ Error al cargar los datos", "error");
        }
    }

    @FXML
    private void mostrarSeleccion(MouseEvent event) {
        Alimento seleccionado = tablaAlimentos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtId.setText(seleccionado.getId());
            txtNombre.setText(seleccionado.getNombre());
            txtCantidad.setText(String.valueOf(seleccionado.getCantidad()));
            dateCaducidad.setValue(LocalDate.parse(seleccionado.getFechaCaducidad()));
        }
    }

    private boolean validarCampos() {
        if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() || 
            txtCantidad.getText().trim().isEmpty() || dateCaducidad.getValue() == null) {
            mostrarMensaje("❌ Todos los campos son obligatorios", "error");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtCantidad.clear();
        dateCaducidad.setValue(null);
        tablaAlimentos.getSelectionModel().clearSelection();
    }

    private void actualizarMensajeTabla() {
        if (listaAlimentos.isEmpty()) {
            tablaAlimentos.setPlaceholder(new Label("📋 Tabla sin contenido - Agregue alimentos para visualizarlos"));
        }
    }

    private void mostrarMensaje(String mensaje, String tipo) {
        lblMensaje.setText(mensaje);
        if ("success".equals(tipo)) {
            lblMensaje.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        } else {
            lblMensaje.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        }
    }
}