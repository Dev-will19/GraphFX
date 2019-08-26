package Entidades;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;

class Bucle extends Arc {

    Bucle(double posX, double posY) {
        setCenterX(posX - 20);
        setCenterY(posY - 20);
        setRadiusX(20);
        setRadiusY(20);
        setStartAngle(90);
        setLength(270);
        setFill(null);
        setStroke(Color.WHITE);
        setStrokeWidth(3);
    }
}
