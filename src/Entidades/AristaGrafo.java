package Entidades;

import Controladores.MesaTrabajoController;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;

public class AristaGrafo extends Group {

    private VerticeGrafo verticeInicial;
    private VerticeGrafo verticeFinal;
    private Line linea;

    public AristaGrafo(VerticeGrafo verticeInicial, VerticeGrafo verticeFinal) {
        this.verticeInicial = verticeInicial;
        this.verticeFinal = verticeFinal;
        crearComponentes();
    }

    private void crearComponentes() {
        //Componentes del grupo
        linea = new Line();
        linea.setStrokeWidth(5);
        linea.setStroke(Color.WHITE);
        if (MesaTrabajoController.esDirigido) crearFlecha();
        posicionarElementos();
        getChildren().addAll(linea);
    }

    public VerticeGrafo getVerticeInicial() {
        return verticeInicial;
    }

    public VerticeGrafo getVerticeFinal() {
        return verticeFinal;
    }

    public void posicionarElementos() {
        setPosicionLinea();
        setPosicionFlecha();
    }

    private void setPosicionFlecha() {
    }

    private void setPosicionLinea() {
        linea.setStartX(verticeInicial.getPosX());
        linea.setStartY(verticeInicial.getPosY());
        linea.setEndX(verticeFinal.getPosX());
        linea.setEndY(verticeFinal.getPosY());
    }

    private void crearFlecha() {
        Arrow arrow = new Arrow(linea.getStartX(),linea.getStartY());
        getChildren().add(arrow);
        posicionarElementos();
    }
}
