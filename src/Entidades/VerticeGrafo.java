package Entidades;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class VerticeGrafo extends Circle {

    private int identificador;
    private double posX;
    private double posY;
    private String etiquetaVertice;
    private Text texto;

    public VerticeGrafo(int identificador, double posX, double posY, String etiquetaVertice) {
        super(posX, posY, 20);
        super.setFill(Color.rgb(59, 139, 169));
        this.identificador = identificador;
        this.posX = posX;
        this.posY = posY;
        this.etiquetaVertice = etiquetaVertice;
        setTexto(etiquetaVertice);
        acciones();
    }

    private void acciones() {
        this.setOnMouseEntered(event -> super.setFill(Color.rgb(22, 178, 33)));
        this.setOnMouseExited(event -> super.setFill(Color.rgb(59, 139, 169)));

        super.setOnContextMenuRequested(event -> {
            ContextMenu menu = new ContextMenu();
            MenuItem item = new MenuItem("Consultar");
            menu.getItems().add(item);
            menu.show(this, Side.LEFT,posX,posY);
        });

        this.setOnMouseDragged(event -> {
            posX = event.getX();
            posY = event.getY();
            super.setCenterX(posX);
            super.setCenterY(posY);
            this.texto.setX(posX - 25);
            this.texto.setY(posY - 20);
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

    public String getEtiquetaVertice() {
        return etiquetaVertice;
    }

    public void setEtiquetaVertice(String etiquetaVertice) {
        this.etiquetaVertice = etiquetaVertice;
    }

    private Text getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = new Text(texto);
        this.texto.setFill(Color.rgb(255, 255, 255));
        this.texto.setX(posX - 25);
        this.texto.setY(posY - 20);
    }

    public void establecerEtiqueta(BorderPane panelDibujable) {
        panelDibujable.getChildren().add(this);
        panelDibujable.getChildren().add(getTexto());
    }
}