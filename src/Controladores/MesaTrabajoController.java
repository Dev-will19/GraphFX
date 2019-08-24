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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MesaTrabajoController implements Initializable {

    private static int contadorVertices;
    private static int contadorAristas;

    public BorderPane root;
    public StackPane panelPila;
    public BorderPane panelVertices;
    public BorderPane panelAristas;

    public JFXButton btnRegresar;
    public JFXButton btnReiniciar;
    public JFXButton btnArista;
    public JFXButton btnVertice;

    public JFXNodesList menuNodos;
    public JFXTextArea txtArea;
    private JFXSnackbar notificacionInferior;
    public Label lb;

    private boolean esDirigido;
    private boolean modoVertice;
    private VerticeGrafo verticeInicial;
    private GrafoLA grafoLA;
    private List<VerticeGrafo> listaVertices;
    private List<AristaGrafo> listaAristas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        esDirigido = false;
        valoresPorDefecto();
        notificacionInferior = new JFXSnackbar(root);
        establecerComportamientos();
    }

    private void valoresPorDefecto() {
        contadorVertices = 0;
        contadorAristas = 0;
        verticeInicial = null;
        modoVertice = true;
        listaAristas = new ArrayList<>();
        listaVertices = new ArrayList<>();
    }

    private void establecerComportamientos() {
        btnReiniciar.setOnAction(event -> {
            panelAristas.getChildren().removeAll(listaAristas);
            panelVertices.getChildren().removeAll(listaVertices);
            valoresPorDefecto();
            menuNodos.animateList(false);
            notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(
                    new JFXSnackbarLayout("Espacio de trabajo reiniciado!")));
        });
        btnRegresar.setOnAction(event -> Pantalla.cargarPantalla("/Vistas/principal.fxml"));
        panelPila.setOnMouseClicked(this::dibujarVertices);
        notificacionInferior.setPrefWidth(300);
        btnArista.setOnAction(event -> {
            modoVertice = false;
            menuNodos.animateList(false);
            notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(
                    new JFXSnackbarLayout("Modo arista activado!")));
        });
        btnVertice.setOnAction(event -> {
            modoVertice = true;
            menuNodos.animateList(false);
            notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(
                    new JFXSnackbarLayout("Modo vértice activado!")));
        });
    }

    private void establecerComportamientoVertices(VerticeGrafo verticeGrafo) {

        /* Cuando el circulo es presionado */
        verticeGrafo.setOnMouseClicked(event -> {
            if (!modoVertice) {                                            // Es decir, está en modo arista.
                if (verticeInicial == null) {                              //No hay vertice inicial seleccionado
                    verticeInicial = verticeGrafo;
                    verticeGrafo.pintarColorSeleccionado();
                } else {
                    dibujarArista(verticeGrafo);
                    verticeInicial = null;
                }
            }
        });

        /* Permitir el movimiento del circulo y el texto */
        verticeGrafo.setOnMouseDragged(event -> {
            if (modoVertice) {
                verticeGrafo.setPosX(event.getX());
                verticeGrafo.setPosY(event.getY());
                verticeGrafo.posicionarElementos();
                if (verticeGrafo.getVerticesAdyacentes().size() != 0) {       // Si posee aristas
                    for (AristaGrafo aristaGrafo : listaAristas) {
                        if (aristaGrafo.getVerticeInicial().getIdentificador() == verticeGrafo.getIdentificador() ||
                                aristaGrafo.getVerticeFinal().getIdentificador() == verticeGrafo.getIdentificador()) {
                            aristaGrafo.posicionarElementos();
                        }
                    }
                }
            }
        });

        /* menu visible al hacer click derecho sobre un vertice */
        ContextMenu menu = new ContextMenu();
        MenuItem editar_vertice = new MenuItem("Editar vértice");
        MenuItem borrar_vertice = new MenuItem("Borrar vértice");
        editar_vertice.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog(Integer.toString(verticeGrafo.getIdentificador()));
            dialog.setTitle("Editar vertice");
            dialog.setHeaderText(null);
            dialog.setContentText("Nueva tiqueta:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(verticeGrafo::setTexto);
        });
        borrar_vertice.setOnAction(event -> {
            /* Extrae la referencia de todas las aristas involucradas con el vertice, y las inserta en una lista auxiliar*/
            List<AristaGrafo> aristaGrafoListBorrar = new ArrayList<>();
            if (verticeGrafo.getVerticesAdyacentes().size() != 0) {       // Si posee aristas
                for (AristaGrafo aristaGrafo : listaAristas) {
                    if (aristaGrafo.getVerticeInicial().getIdentificador() == verticeGrafo.getIdentificador() ||
                            aristaGrafo.getVerticeFinal().getIdentificador() == verticeGrafo.getIdentificador()) {
                        aristaGrafoListBorrar.add(aristaGrafo);
                    }
                }
            }

            for (AristaGrafo aristaGrafo : aristaGrafoListBorrar) {
                panelAristas.getChildren().remove(aristaGrafo);
                listaAristas.remove(aristaGrafo);
            }
            listaVertices.remove(verticeGrafo);
            panelVertices.getChildren().remove(verticeGrafo);
        });
        menu.getItems().addAll(editar_vertice, borrar_vertice);
        verticeGrafo.getCirculo().setOnContextMenuRequested(event -> {
            if (modoVertice)                                             // Permite mostrar el menu, solo en modo vertice
                menu.show(verticeGrafo.getCirculo(), event.getScreenX(), event.getScreenY());
        });
    }

    void establecerConfiguracion(boolean dirigido, boolean ponderado) {
        String texto = "";
        esDirigido = dirigido;
        if (dirigido) texto += "es dirigido";
        else texto += "no dirigido";
        if (ponderado) texto += " + es ponderado";
        else texto += " + no ponderado";
        lb.setText(texto);
    }

    private void dibujarVertices(MouseEvent event) {
        if (event.getClickCount() == 2 & modoVertice) {
            /* Crea un grupo con el vertice y texto */
            VerticeGrafo verticeGrafo = new VerticeGrafo(contadorVertices++, event.getX(), event.getY());
            listaVertices.add(verticeGrafo);
            establecerComportamientoVertices(verticeGrafo);            // Establece el mismo comportamiento a todos
            panelVertices.getChildren().add(verticeGrafo);             // Añade el vertice al panel visible
        }
    }

    private void dibujarArista(VerticeGrafo verticeFinal) {
        if (this.verticeInicial == verticeFinal) {
            verticeInicial.crearBucle();
            verticeInicial.getVerticesAdyacentes().add(verticeFinal.getIdentificador());
        } else {
            AristaGrafo aristaGrafo = new AristaGrafo(contadorAristas++, verticeInicial, verticeFinal);
            listaAristas.add(aristaGrafo);
            verticeInicial.getVerticesAdyacentes().add(verticeFinal.getIdentificador());
            if (!esDirigido) {
                verticeFinal.getVerticesAdyacentes().add(verticeInicial.getIdentificador());
            }
            verticeInicial.pintarColorDefecto();
            panelAristas.getChildren().add(aristaGrafo);
        }
    }


    private void instanciarGrafo() {
        grafoLA = null;
        grafoLA = new GrafoLA(listaVertices.size(), false);
        grafoLA.insertaVertice(listaVertices.size());

        for (VerticeGrafo verticeGrafo : listaVertices) {
            List<Integer> aristaGrafoList = verticeGrafo.getVerticesAdyacentes();
            for (Integer arista : aristaGrafoList) {
                grafoLA.insertaArista(verticeGrafo.getIdentificador(), arista);
            }
        }
    }

    private String textoGradosInOut(Grafo grafo) {
        StringBuilder resul = new StringBuilder("Grados de entrada & salida e incidencia en cada vértice:\n");
        // Mostrar los grados de entrada y salida, as� como la incidencia para cada
        for (int i = 0; i < grafo.obtenerNumVertices(); i++) {
            resul.append("Vertice ").append(i).append(": GIn: ").append(grafo.gradoIn(i)).append(", GOut: ").
                    append(grafo.gradoOut(i)).append(" Incidencia: ").append(grafo.incidencia(i)).append("\n");
        }
        return resul.toString();
    }

    public void gradosInOut() {
        instanciarGrafo();
        if (grafoLA != null)
            txtArea.setText(textoGradosInOut(grafoLA));
    }

    public void recorridoProfundidad() {
        instanciarGrafo();
        if (grafoLA != null)
            txtArea.setText(Recorridos.profundidad(grafoLA));
    }

    public void recorridoAmplitud() {
        instanciarGrafo();
        if (grafoLA != null)
            try {
                txtArea.setText(Recorridos.amplitud(grafoLA));
            } catch (ColaVacia colaVacia) {
                colaVacia.printStackTrace();
            }
    }

    public void imprimirGrafo() {
        instanciarGrafo();
        if (grafoLA != null)
            txtArea.setText(grafoLA.imprimirGrafo());
    }
}
