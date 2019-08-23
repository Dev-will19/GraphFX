package Entidades;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class AristaGrafo extends Group {

    private int identificador;
    private VerticeGrafo verticeInicial;
    private VerticeGrafo verticeFinal;
    private Line linea;
    private Text texto;

    public AristaGrafo(int identificador, VerticeGrafo verticeInicial, VerticeGrafo verticeFinal) {
        this.identificador = identificador;
        this.verticeInicial = verticeInicial;
        this.verticeFinal = verticeFinal;
        crearComponentes();
    }

    private void crearComponentes() {
        //Componentes del grupo
        linea = new Line();
        linea.setStrokeWidth(5);
        linea.setStroke(Color.rgb(255,255,255));
        texto = new Text();
        texto.setFill(Color.WHITE);
        posicionarElementos();
        getChildren().addAll(linea, texto);
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public VerticeGrafo getVerticeInicial() {
        return verticeInicial;
    }

    public void setVerticeInicial(VerticeGrafo verticeInicial) {
        this.verticeInicial = verticeInicial;
    }

    public VerticeGrafo getVerticeFinal() {
        return verticeFinal;
    }

    public void setVerticeFinal(VerticeGrafo verticeFinal) {
        this.verticeFinal = verticeFinal;
    }

    public Line getLinea() {
        return linea;
    }

    public void setLinea(Line linea) {
        this.linea = linea;
    }

    public Text getTexto() {
        return texto;
    }

    public void setTexto(Text texto) {
        this.texto = texto;
    }

    public void posicionarElementos() {
        setPosicionLinea();
        setPosicionTexto();
    }

    private void setPosicionLinea() {
        linea.setStartX(verticeInicial.getPosX());
        linea.setStartY(verticeInicial.getPosY());
        linea.setEndX(verticeFinal.getPosX());
        linea.setEndY(verticeFinal.getPosY());
    }

    private void setPosicionTexto() {
        texto.setX((verticeInicial.getPosX() + verticeFinal.getPosX())/2);
        texto.setY((verticeInicial.getPosY() + verticeFinal.getPosY())/2);
    }
}
