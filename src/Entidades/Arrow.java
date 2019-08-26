package Entidades;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Arrow extends Polygon {

    public Arrow(double x, double y) {
        getPoints().addAll(x, y, 2 / x, 2 * y, 2 * x, 2 * y);
        setStroke(Color.WHITE);
        setFill(Color.WHITE);
    }
}