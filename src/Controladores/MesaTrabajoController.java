package Controladores;

import Entidades.AristaGrafo;
import Entidades.VerticeGrafo;
import Entidades.grafos.Grafo;
import Entidades.grafos.GrafoLA;
import Entidades.grafos.Recorridos;
import Entidades.tadCola.ColaVacia;
import Utils.Pantalla;
import com.jfoenix.controls.*;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MesaTrabajoController implements Initializable {

    private static int contadorVertices;
    private static int contadorAristas;

    public BorderPane root;
    public BorderPane panelVertices;
    public BorderPane panelAristas;
    public JFXButton btnRegresar;
    public JFXButton btnArista;
    public JFXButton btnVertice;
    public JFXNodesList menuNodos;
    public StackPane panelPila;
    public JFXTextArea txtArea;

    private JFXSnackbar notificacionInferior;

    public Label lb;
    private boolean esDirigido;
    private boolean modoVertice;
    private int IdVerticeInicial;
    private GrafoLA grafoLA;
    private List<VerticeGrafo> listaVertices;
    private List<AristaGrafo> aristaGrafoList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        esDirigido = false;
        contadorVertices = 0;
        IdVerticeInicial = -1;
        modoVertice = true;
        aristaGrafoList = new ArrayList<>();
        listaVertices = new ArrayList<>();
        notificacionInferior = new JFXSnackbar(root);
        establecerComportamientos();
    }

    private void establecerComportamientos() {
        btnRegresar.setOnAction(event -> volverPrincipal());
        panelPila.setOnMouseClicked(this::dibujarVertices);
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
                if (IdVerticeInicial == -1) {                              //No hay vertice inicial seleccionado
                    IdVerticeInicial = verticeGrafo.getIdentificador();
                    verticeGrafo.pintarColorSeleccionado();
                } else {
                    verticeGrafo.pintarColorDefecto();
                    dibujarArista(verticeGrafo.getIdentificador());
                    IdVerticeInicial = -1;
                }
            }
        });

        /* Permitir el movimiento del circulo y el texto */
        verticeGrafo.setOnMouseDragged(event -> {
            if (modoVertice) {
                verticeGrafo.setPosX(event.getX());
                verticeGrafo.setPosY(event.getY());
                verticeGrafo.posicionarElementos();
                if (verticeGrafo.getVerticesAdyacentes().size() != 0) {                    // Si posee aristas
                    for (AristaGrafo aristaGrafo : aristaGrafoList) {
                        if (aristaGrafo.getVerticeInicial().getIdentificador() == verticeGrafo.getIdentificador() |
                                aristaGrafo.getVerticeFinal().getIdentificador() == verticeGrafo.getIdentificador()) {
                            aristaGrafo.posicionarElementos();
                        }
                    }
                }
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

    private void dibujarVertices(MouseEvent event) {
        if (event.getClickCount() == 2 & modoVertice) {
            /* Crea un grupo con el vertice y texto */
            VerticeGrafo verticeGrafo = new VerticeGrafo(contadorVertices++, event.getX(), event.getY());
            listaVertices.add(verticeGrafo);
            establecerComportamientoVertices(verticeGrafo);             // Establece el mismo comportamiento a todos
            panelVertices.getChildren().add(verticeGrafo);             // Añade el vertice al panel visible
        }
    }

    private void dibujarArista(int IdVerticeFinal) {
        VerticeGrafo verticeInicio = buscarVertice(IdVerticeInicial);
        VerticeGrafo verticeFinal = buscarVertice(IdVerticeFinal);

        if(IdVerticeInicial == IdVerticeFinal){
            if (!(grafoLA.existeArista(IdVerticeInicial,IdVerticeFinal))){
            verticeInicio.crearBucle();
            verticeInicio.getVerticesAdyacentes().add(IdVerticeFinal);
            }
        } else {
            AristaGrafo aristaGrafo = new AristaGrafo(contadorAristas++, verticeInicio, verticeFinal);
            aristaGrafoList.add(aristaGrafo);
            verticeInicio.getVerticesAdyacentes().add(IdVerticeFinal);
            if (!esDirigido) {
                verticeFinal.getVerticesAdyacentes().add(IdVerticeInicial);
            }
            panelAristas.getChildren().add(aristaGrafo);
        }
    }

    private VerticeGrafo buscarVertice(int idVertice) {
        for (VerticeGrafo verticeGrafo : listaVertices) {
            if (verticeGrafo.getIdentificador() == idVertice) {
                return verticeGrafo;
            }
        }
        return null;
    }

    public void probar() {
        grafoLA = null;
        grafoLA = new GrafoLA(listaVertices.size(), false);
        grafoLA.insertaVertice(listaVertices.size());

        for (VerticeGrafo verticeGrafo : listaVertices) {
            List<Integer> aristaGrafoList = verticeGrafo.getVerticesAdyacentes();
            for (Integer arista : aristaGrafoList) {
                grafoLA.insertaArista(verticeGrafo.getIdentificador(), arista);
            }
        }
        grafoLA.imprimirGrafo();
        imprimirInformacionGrafo(grafoLA);

    }

    private static void imprimirInformacionGrafo(Grafo grafo) {
        // Mostrar los grados de entrada y salida, as� como la incidencia para cada
        System.out.println("\nGrados de entrada y salida e incidencia en cada v�rtice:");
        for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
            System.out.println("Vertice " + i + ": GIn: " + grafo.gradoIn(i) + ", GOut: " + grafo.gradoOut(i)
                    + " Incidencia: " + grafo.incidencia(i));
        }
        // Recorrido profundidad
        System.out.println("Recorrido en profundidad:");
        Recorridos.profundidad(grafo);
        // Recorrido en anchura
        System.out.println("Recorrido en anchura:");
        try {
            Recorridos.amplitud(grafo);
        } catch (ColaVacia ignored) {
        }

    }
}
