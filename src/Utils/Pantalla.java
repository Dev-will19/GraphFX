package Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Pantalla {

    private static Stage stage = new Stage();

    public static FXMLLoader cargarPantalla(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(Pantalla.class.getResource(fxml));
        try {
            Parent panelCargado = fxmlLoader.load();
            stage.setScene(new Scene(panelCargado));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException ignored) {
        }
        return fxmlLoader;
    }
}
