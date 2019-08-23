package Entidades;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.Cursor.HAND;

public class VerticeGrafo extends Group {

    private int identificador;
    private double posX;
    private double posY;
    private List<AristaGrafo> aristas;
    private Circle circulo;
    private Text texto;

    public VerticeGrafo(int identificador, double posX, double posY) {
        this.identificador = identificador;
        this.aristas = new ArrayList<>();
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

    public List<AristaGrafo> getAristas() {
        return aristas;
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
    }

    private void setPosicionCirculo() {
        circulo.setCenterX(getPosX());
        circulo.setCenterY(getPosY());
    }

    private void setPosicionTexto() {
        texto.setX(getPosX() - 25);
        texto.setY(getPosY() - 20);
    }
}