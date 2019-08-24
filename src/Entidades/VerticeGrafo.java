package Entidades;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.Cursor.HAND;

public class VerticeGrafo extends Group {

    private int identificador;
    private double posX;
    private double posY;
    private List<Integer> verticesAdyacentes;
    private Circle circulo;
    private Arc bucle;
    private Text texto;

    public VerticeGrafo(int identificador, double posX, double posY) {
        this.identificador = identificador;
        this.verticesAdyacentes = new ArrayList<>();
        this.posX = posX;
        this.posY = posY;
        crearComponentes();
    }

    private void crearComponentes() {
        //Componentes del grupo
        circulo = new Circle(20);
        circulo.setFill(Color.rgb(59, 139, 169));
        circulo.setCursor(HAND);
        texto = new Text(Integer.toString(identificador));
        texto.setFill(Color.WHITE);
        bucle = null;
        posicionarElementos();
        getChildren().addAll(circulo, texto);
    }

    public int getIdentificador() {
        return identificador;
    }

    double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Circle getCirculo() {
        return circulo;
    }

    public List<Integer> getVerticesAdyacentes() {
        return verticesAdyacentes;
    }

    public void pintarColorDefecto() {
        circulo.setFill(Color.rgb(59, 139, 169));
    }

    public void pintarColorSeleccionado() {
        circulo.setFill(Color.rgb(54, 139, 30));
    }

    public void posicionarElementos() {
        setPosicionCirculo();
        setPosicionTexto();
        if(bucle != null)
            setPosicionBucle();
    }

    private void setPosicionCirculo() {
        circulo.setCenterX(getPosX());
        circulo.setCenterY(getPosY());
    }

    private void setPosicionTexto() {
        texto.setX(getPosX() - 25);
        texto.setY(getPosY() - 20);
    }

    private void setPosicionBucle() {
        bucle.setCenterX(getPosX()-20);
        bucle.setCenterY(getPosY()-20);
    }

    public void crearBucle() {
        Arc arc = new Arc(getPosX() - 20, getPosY() - 20, 20, 20, 0, 270);
        arc.setFill(null);
        arc.setStroke(Color.WHITE);
        arc.setStrokeWidth(3);
        bucle = arc;
        getChildren().add(bucle);
        posicionarElementos();
    }
}