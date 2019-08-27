package Entidades;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static javafx.scene.Cursor.HAND;

public class AristaGrafo extends Group {

    private VerticeGrafo verticeInicial;
    private VerticeGrafo verticeFinal;
    private boolean esDirigido;
    private Line linea;

    public AristaGrafo(VerticeGrafo verticeInicial, VerticeGrafo verticeFinal, boolean esDirigido) {
        this.esDirigido = esDirigido;
        this.verticeInicial = verticeInicial;
        this.verticeFinal = verticeFinal;
        crearComponentes();
    }

    private void crearComponentes() {
        linea = new Line();
        linea.setCursor(HAND);
        linea.setStrokeWidth(5);
        linea.setStroke(Color.WHITE);
        if (esDirigido)  {
            verticeFinal.getCirculo().setStroke(Color.WHITE);
            verticeFinal.getCirculo().setStrokeWidth(5);
        }
        posicionarElementos();
        getChildren().addAll(linea);
    }

    public VerticeGrafo getVerticeInicial() {
        return verticeInicial;
    }

    public VerticeGrafo getVerticeFinal() {
        return verticeFinal;
    }

    public Line getLinea() {
        return linea;
    }

    public void posicionarElementos() {
        setPosicionLinea();
    }

    private void setPosicionLinea() {
        linea.setStartX(verticeInicial.getPosX());
        linea.setStartY(verticeInicial.getPosY());
        linea.setEndX(verticeFinal.getPosX());
        linea.setEndY(verticeFinal.getPosY());
    }

}
