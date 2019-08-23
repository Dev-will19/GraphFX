package Controladores;

import Entidades.VerticeGrafo;
import Utils.Pantalla;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MesaTrabajoController implements Initializable {

    private static int contador;

    public BorderPane root;
    public BorderPane panelDibujable;
    public JFXButton btnRegresar;
    public JFXButton btnArista;
    public JFXButton btnVertice;
    public Label lb;
    public JFXNodesList menuNodos;

    private JFXSnackbar notificacionInferior;

    private boolean modoVertice;
    private int verticeInicio;
    private List<VerticeGrafo> verticeGrafoList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contador = 0;
        verticeInicio = -1;
        modoVertice = true;
        verticeGrafoList = new ArrayList<>();
        notificacionInferior = new JFXSnackbar(root);
        establecerComportamientos();
    }

    private void establecerComportamientos() {
        btnRegresar.setOnAction(event -> volverPrincipal());
        panelDibujable.setOnMouseClicked(this::dibujarVertices);
        notificacionInferior.setPrefWidth(300);
        btnArista.setOnAction(event -> {
            modoVertice = false;
            menuNodos.animateList(false);
            notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Modo arista activado!")));
        });
        btnVertice.setOnAction(event -> {
            modoVertice = true;
            menuNodos.animateList(false);
            notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Modo vértice activado!")));
        });

    }

    private void deseleccionarVertice(int idVertice) {
        for (VerticeGrafo g : verticeGrafoList) {
            if (g.getIdentificador() == idVertice) {
                g.pintarColorDefecto();
            }
        }
    }

    private void dibujarVertices(MouseEvent event) {
        if (event.getClickCount() == 2 & modoVertice) {
            /* Crea un grupo con el vertice y texto */
            VerticeGrafo verticeGrafo = new VerticeGrafo(contador++, event.getX(), event.getY());
            establecerComportamientoVertices(verticeGrafo);             // Establece el mismo comportamiento a todos
            panelDibujable.getChildren().add(verticeGrafo);             // Añade el vertice al panel visible
            verticeGrafoList.add(verticeGrafo);
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

    private void establecerComportamientoVertices(VerticeGrafo verticeGrafo) {

        /* Cuando el circulo es presionado*/
        verticeGrafo.setOnMouseClicked(event -> {
            if (!modoVertice) {                                         // Es decir, está en modo arista.
                if (verticeInicio == -1) {                              //No hay vertice inicial seleccionado
                    verticeInicio = verticeGrafo.getIdentificador();
                    verticeGrafo.pintarColorSeleccionado();
                } else {
                    deseleccionarVertice(verticeInicio);
                    verticeInicio = -1;
                    verticeGrafo.pintarColorDefecto();
                }
            }
        });

        /* Permitir el movimiento del circulo y el texto */
        verticeGrafo.setOnMouseDragged(event -> {
            if (modoVertice) {                                           // Permite mover solo modo vertice
                verticeGrafo.setPosX(event.getX());
                verticeGrafo.setPosY(event.getY());
                verticeGrafo.setPosicionCirculo();
                verticeGrafo.setPosicionTexto();
            }
        });

        /* menu visible al hacer click derecho sobre un vertice */
        ContextMenu menu = new ContextMenu();
        MenuItem item = new MenuItem("item prueba");
        menu.getItems().add(item);
        verticeGrafo.getCirculo().setOnContextMenuRequested(event -> {
            if (modoVertice)                                             // Permite mostrar el menu, solo en modo vertice
                menu.show(verticeGrafo.getCirculo(), event.getScreenX(), event.getScreenY());
        });
    }
}
