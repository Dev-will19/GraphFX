package Controladores;

import Utils.Pantalla;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController extends Application implements Initializable {

    public JFXToggleButton tglDirigido;
    public JFXButton btnEmpezar;
    public JFXButton btnSalir;
    public JFXButton btnContacto;
    public JFXButton btnDocumentacion;
    public JFXButton btnGithub;

    @Override
    public void start(Stage primaryStage) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tglDirigido.setText("Grafo no dirigido");
        tglDirigido.setOnAction(event -> cambioEstadoDirigido());
        btnEmpezar.setOnAction(event -> abrirEspacioDeTrabajo());
        btnSalir.setOnAction(event -> terminarPrograma());

        btnContacto.setOnAction(event -> abrirVinculo("https://mail.google.com/mail/?view=cm&fs=1&to=dev.willct@gmail.com&su=GraphFX%20consulta"));
        btnDocumentacion.setOnAction(event -> abrirVinculo("https://drive.google.com/drive/folders/13R4WOlUrm1bke75ObX1HUXtUZhdXkJWw?usp=sharing"));
        btnGithub.setOnAction(event -> abrirVinculo("https://github.com/Dev-will19/GraphFX"));
    }

    private void abrirVinculo(String url) {
        HostServicesDelegate hostServices = HostServicesFactory.getInstance(this);
        hostServices.showDocument(url);
    }

    private void cambioEstadoDirigido() {
        if (tglDirigido.isSelected()) tglDirigido.setText("Grafo dirigido");
        else tglDirigido.setText("Grafo no dirigido");
    }

    private void terminarPrograma() {
        Stage stage = (Stage) btnSalir.getScene().getWindow();
        stage.close();
    }

    private void abrirEspacioDeTrabajo() {
        FXMLLoader loader = Pantalla.cargarPantalla("/Vistas/mesaTrabajo.fxml");
        MesaTrabajoController mesaTrabajoController = loader.getController();
        mesaTrabajoController.establecerConfiguracion(tglDirigido.isSelected());
    }
}
