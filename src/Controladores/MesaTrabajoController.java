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
import javafx.scene.input.MouseButton;
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
    public static boolean esDirigido;

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

    private boolean SeMostroNotificacionArrastre;
    private boolean modoVertice;
    private GrafoLA grafoLA;
    private VerticeGrafo verticeInicial;
    private List<VerticeGrafo> listaVertices;
    private List<AristaGrafo> listaAristas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valoresPorDefecto();
        establecerComportamientos();
    }

    private void valoresPorDefecto() {
        contadorVertices = 0;
        verticeInicial = null;
        modoVertice = true;
        SeMostroNotificacionArrastre = false;
        listaAristas = new ArrayList<>();
        listaVertices = new ArrayList<>();
    }

    private void establecerComportamientos() {
        notificacionInferior = new JFXSnackbar(root);
        btnReiniciar.setOnAction(event -> {
            panelAristas.getChildren().removeAll(listaAristas);
            panelVertices.getChildren().removeAll(listaVertices);
            valoresPorDefecto();
            accionesAlCambiarModos();
            menuNodos.animateList(false);
            mostrarNotificacionPush("Espacio de trabajo reiniciado!");
        });
        btnRegresar.setOnAction(event -> Pantalla.cargarPantalla("/Vistas/principal.fxml"));
        panelPila.setOnMouseClicked(this::dibujarVertices);
        notificacionInferior.setPrefWidth(300);
        btnArista.setOnAction(event -> {
            accionesAlCambiarModos();
            modoVertice = false;
            menuNodos.animateList(false);
            mostrarNotificacionPush("Modo arista activado!");
        });
        btnVertice.setOnAction(event -> {
            accionesAlCambiarModos();
            modoVertice = true;
            menuNodos.animateList(false);
            mostrarNotificacionPush("Modo vértice activado!");
        });
    }

    private void accionesAlCambiarModos() {
        SeMostroNotificacionArrastre = false;
        deseleccionarNodo();
    }

    private void deseleccionarNodo() {
        if (verticeInicial != null) {
            verticeInicial.pintarColorDefecto();
            verticeInicial = null;
        }
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
            } else {
                if (!SeMostroNotificacionArrastre) {
                    mostrarNotificacionPush("¿Estás intentando mover el vertice?, primero pasate al modo edicion de vertices!");
                    SeMostroNotificacionArrastre = true;
                }
            }
        });

        /* menu visible al hacer click derecho sobre un vertice */
        ContextMenu menu = new ContextMenu();
        MenuItem editar_vertice = new MenuItem("Editar vértice");
        MenuItem borrar_vertice = new MenuItem("Borrar vértice");

        /* accion del botón editar vertice */
        editar_vertice.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog(Integer.toString(verticeGrafo.getIdentificador()));
            dialog.setTitle("Editar vertice");
            dialog.setHeaderText(null);
            dialog.setContentText("Nueva tiqueta:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(verticeGrafo::setTexto);
        });

        /* accion del botón eliminar vertice */
        borrar_vertice.setOnAction(event -> {

            /* Extrae la referencia de todas las aristas involucradas con el vertice, y las inserta en una lista auxiliar
             *  con el fin de no interferir con el borrado dentro del bucle
             */
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
            contadorVertices--;
            listaVertices.remove(verticeGrafo);
            removerAdyacenciasImplicadas(verticeGrafo.getIdentificador());
            panelVertices.getChildren().remove(verticeGrafo);

            /* Actualiza los textos de cada vertice, siempre que no se hayan modificado a letras */
            for (int i = 0; i < listaVertices.size(); i++) {
                listaVertices.get(i).setIdentificador(i);
                try {
                    Integer.parseInt(listaVertices.get(i).getTexto());
                    listaVertices.get(i).setTexto(Integer.toString(i));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        menu.getItems().addAll(editar_vertice, borrar_vertice);

        /* Permite mostrar el menu, solo en modo vertice */
        verticeGrafo.getCirculo().setOnContextMenuRequested(event -> {
            if (modoVertice)
                menu.show(verticeGrafo.getCirculo(), event.getScreenX(), event.getScreenY());
        });
    }

    private void mostrarNotificacionPush(String texto) {
        notificacionInferior.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout(texto)));
    }

    private void removerAdyacenciasImplicadas(int identificador) {
        for (VerticeGrafo verticeGrafo : listaVertices) {                               // Por cada vertice restante
            List<Integer> verticesAdyacentes = verticeGrafo.getVerticesAdyacentes();    // obtenga sus adyacencias

            /* comprueba que no existan referencias al vertice borrado */
            for (int i = 0; i < verticesAdyacentes.size(); i++)
                if (verticesAdyacentes.get(i) == identificador) verticesAdyacentes.remove(i);
        }
        /* Reasigna las adyacencias de cada vertice */
        for (VerticeGrafo verticeGrafo : listaVertices) {
            List<Integer> verticesAdyacentes = verticeGrafo.getVerticesAdyacentes();
            /* comprueba que no existan referencias al vertice borrado */
            for (int i = 0; i < verticesAdyacentes.size(); i++)
                if (verticesAdyacentes.get(i) >= identificador)
                    verticesAdyacentes.set(i, verticesAdyacentes.get(i) - 1);
        }
    }

    void establecerConfiguracion(boolean dirigido) {
        esDirigido = dirigido;
        String texto = dirigido ? "es dirigido" : "no dirigido";
        lb.setText(texto);
    }

    private void dibujarVertices(MouseEvent event) {
        if (event.getClickCount() == 2 & modoVertice & event.getButton() == MouseButton.PRIMARY) {
            /* Crea un grupo con el vertice y texto */
            VerticeGrafo verticeGrafo = new VerticeGrafo(contadorVertices++, event.getX(), event.getY());
            listaVertices.add(verticeGrafo);
            establecerComportamientoVertices(verticeGrafo);            // Establece el mismo comportamiento a todos
            panelVertices.getChildren().add(verticeGrafo);             // Añade el vertice al panel visible
        }
    }

    private void dibujarArista(VerticeGrafo verticeFinal) {
        if (this.verticeInicial == verticeFinal) {              // Comprueba que se selecciono el mismo vertice

            /* De ser el caso, crea un bucle visual y se añade a si mismo como adyacencia. */
            verticeInicial.crearBucle();
            verticeInicial.getVerticesAdyacentes().add(verticeFinal.getIdentificador());
        } else {
            if (!existeArista(verticeFinal)) {
                AristaGrafo aristaGrafo = new AristaGrafo(verticeInicial, verticeFinal);
                listaAristas.add(aristaGrafo);
                verticeInicial.getVerticesAdyacentes().add(verticeFinal.getIdentificador());
                if (!esDirigido) {          // Si el grafo no es dirigido, añade al vertice final la adyacencia con el inicial
                    verticeFinal.getVerticesAdyacentes().add(verticeInicial.getIdentificador());
                }
                panelAristas.getChildren().add(aristaGrafo);
            } else {
                mostrarNotificacionPush("Ya existe una arista entre estos vertices!");
            }
        }
        deseleccionarNodo();
    }

    private boolean existeArista(VerticeGrafo verticeFinal) {
        for (AristaGrafo aristaGrafo : listaAristas) {
            if (aristaGrafo.getVerticeInicial().getIdentificador() == verticeInicial.getIdentificador() &&
                    aristaGrafo.getVerticeFinal().getIdentificador() == verticeFinal.getIdentificador()) {
                return true;
            }

            // Comprobación a la inversa para detectar cuando sea no dirigido
            if (esDirigido && aristaGrafo.getVerticeInicial().getIdentificador() == verticeFinal.getIdentificador() &&
                    aristaGrafo.getVerticeFinal().getIdentificador() == verticeInicial.getIdentificador()) {
                return true;
            }
        }
        return false;
    }

    private void instanciarGrafo() {
        grafoLA = null;
        grafoLA = new GrafoLA(listaVertices.size(), esDirigido);
        grafoLA.insertaVertice(listaVertices.size());
        for (VerticeGrafo verticeGrafo : listaVertices) {
            List<Integer> adyacentes = verticeGrafo.getVerticesAdyacentes();
            for (Integer arista : adyacentes) {
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
