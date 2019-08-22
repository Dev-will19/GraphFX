package Entidades;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import static javafx.scene.Cursor.HAND;

public class VerticeGrafo extends Group {

    private int identificador;
    private double posX;
    private double posY;
    private Circle circulo;
    private Text texto;

    public VerticeGrafo(int identificador, double posX, double posY) {
        this.identificador = identificador;
        this.posX = posX;
        this.posY = posY;

        crearComponentes();
        establecerComportamientoCirculo();
    }

    private void crearComponentes() {
        //Componentes del grupo
        circulo = new Circle(posX, posY, 20);
        circulo.setFill(Color.rgb(59, 139, 169));
        circulo.setCursor(HAND);
        texto = new Text(posX - 25, posY - 20, Integer.toString(identificador));
        texto.setFill(Color.WHITE);
        getChildren().addAll(circulo, texto);
    }

    private void establecerComportamientoCirculo() {
        this.setOnMouseDragged(event -> {
            posX = event.getX();
            posY = event.getY();
            circulo.setCenterX(posX);
            circulo.setCenterY(posY);
            texto.setX(posX - 25);
            texto.setY(posY - 20);
        });
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public Circle getCirculo() {
        return circulo;
    }

    public void setCirculo(Circle circulo) {
        this.circulo = circulo;
    }

    public Text getTexto() {
        return texto;
    }

    public void setTexto(Text texto) {
        this.texto = texto;
    }
}