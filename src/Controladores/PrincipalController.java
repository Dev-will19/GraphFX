package Controladores;

import Utils.Pantalla;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    public JFXToggleButton tglPonderado;
    public JFXToggleButton tglDirigido;
    public JFXButton btnEmpezar;
    public JFXButton btnSalir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tglDirigido.setText("Grafo no dirigido");
        tglPonderado.setText("Grafo no ponderado");
        tglDirigido.setOnAction(event -> cambioEstadoDirigido());
        tglPonderado.setOnAction(event -> cambioEstadoPonderado());
        btnEmpezar.setOnAction(event -> abrirEspacioDeTrabajo());
        btnSalir.setOnAction(event -> terminarPrograma());
    }

    private void cambioEstadoDirigido() {
        if(tglDirigido.isSelected()) tglDirigido.setText("Grafo dirigido");
        else tglDirigido.setText("Grafo no dirigido");
    }

    private void cambioEstadoPonderado() {
        if(tglPonderado.isSelected()) tglPonderado.setText("Grafo ponderado");
        else tglPonderado.setText("Grafo no ponderado");
    }

    private void terminarPrograma() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void abrirEspacioDeTrabajo() {
        FXMLLoader loader = Pantalla.cargarPantalla("/Vistas/mesaTrabajo.fxml");
        MesaTrabajoController mesaTrabajoController = loader.getController();
        mesaTrabajoController.establecerConfiguracion(tglDirigido.isSelected(), tglPonderado.isSelected());
    }

}
