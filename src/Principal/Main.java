package Principal;

import Utils.Pantalla;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Pantalla.cargarPantalla("/Vistas/principal.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
