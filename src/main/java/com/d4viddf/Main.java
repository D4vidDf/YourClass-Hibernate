package com.d4viddf;

import java.io.File;
import java.io.IOException;

import com.d4viddf.Controller.MainController;
import com.d4viddf.Error.Errores;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Clase que extiende de Aplicación que es la encargada de mostrar la interfaz y
 * la ventana inicial
 */
public class Main extends Application {
    Errores errores = new Errores();

    /**
     * Método encargado de lanzar la aplicación
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Método que instancia la vista
     * 
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/main.fxml"));
        MainController c = new MainController();
        loader.setController(c);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("YourClass");
        primaryStage.getIcons().add(new Image("/drawable/blackboard.png"));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

}
