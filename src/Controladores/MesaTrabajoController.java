package Controladores;

import Entidades.VerticeGrafo;
import Utils.Pantalla;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MesaTrabajoController implements Initializable {

    public Label lb;
    public JFXButton btnRegresar;
    public BorderPane panelDibujable;
    private static int contador;
    public JFXSlider slider;
    private List<VerticeGrafo> verticeGrafoList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contador = 0;
        verticeGrafoList = new ArrayList<>();
        btnRegresar.setOnAction(event -> volverPrincipal());
        panelDibujable.setOnMouseClicked(this::dibujarVertices);
        slider.getIndicatorPosition();
    }

    private void dibujarVertices(MouseEvent event) {
        if( event.getClickCount() == 2) {
            VerticeGrafo verticeGrafo = new VerticeGrafo(contador,event.getX(),event.getY(),Integer.toString(contador++));
            verticeGrafo.establecerEtiqueta(panelDibujable);
            verticeGrafoList.add(verticeGrafo);
            System.out.println("existen x= " + verticeGrafoList.size() + " grafos añadidos");
        }
    }

    private void volverPrincipal() {
        Pantalla.cargarPantalla("/Vistas/principal.fxml");
    }

    void establecerConfiguracion(boolean dirigido, boolean ponderado) {
        String texto = "";
        if (dirigido) texto += "es dirigido";
        else texto += "no dirigido";
        if (ponderado) texto += " + es ponderado";
        else texto += " + no ponderado";
        lb.setText(texto);
    }
}
