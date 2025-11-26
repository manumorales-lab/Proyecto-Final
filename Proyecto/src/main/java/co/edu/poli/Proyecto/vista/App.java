package co.edu.poli.Proyecto.vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Aplicación principal para gestión de alimentos espaciales
 * @author Manuel Morales, Darien Díaz
 * @since 2025
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/co/edu/poli/Proyecto/primary.fxml"));
        Parent root = fxmlLoader.load();
        
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("CosmoTravel - Gestión de Alimentos Espaciales");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}