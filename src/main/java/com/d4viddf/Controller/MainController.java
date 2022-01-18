package com.d4viddf.Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.d4viddf.Error.Errores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController implements Initializable {
    Errores errores = new Errores();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        

    }


    /**
     * Gestiona el evento del enlace. Al pulsar el enlace de ayuda se abre el
     * navegador por defecto del usuario para ver la socumentación del programa
     * 
     * @param ae
     */
    @FXML
    private void url(ActionEvent ae) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://yourclass.d4viddf.com/index.html"));
            } catch (IOException e1) {
                errores.muestraErrorIO(e1);
            } catch (URISyntaxException e1) {
                errores.muestraErrorURY(e1);
            }
        }
    }

    /**
     * Gestiona el evento del botón modificar la base de datos. Al pulsar el botón
     * se abre la ventana para realizar las modificaciones pertinentes a la base de
     * datos
     * 
     * @param ae
     */
    @FXML
    private void abrirModificador(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/alterDataBase.fxml"));
        ModificadorController c = new ModificadorController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modificación de la base de datos");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }

    }

    /**
     * Método que gestiona el evento del botón Alumno que muestra la ventana para
     * poder interaccionar con la tabla Alumnos.
     * 
     * @param ae
     */
    @FXML
    private void abrirAlumno(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscarAlumno.fxml"));
        AlumnosController c = new AlumnosController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Alumnos");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }
    }

    /**
     * Método que gestiona el evento del botón Profesor que abre una ventana para
     * poder interactuar con la tabla Profesor.
     * 
     * @param ae
     */
    @FXML
    private void abrirProfesor(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscarProfesor.fxml"));
        ProfesorController c = new ProfesorController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Profesores");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }
    }

    /**
     * Método que gestiona el evento del botón Asignatura que abre una ventana para
     * poder interactuar con la tabla Asignaturas.
     * 
     * @param ae
     */
    @FXML
    private void abrirAsignatura(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscarAsignatura.fxml"));
        AsignaturaController c = new AsignaturaController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Asignaturas");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }
    }

    /**
     * Método que gestiona el evento del botón Departamento que abre una ventana
     * para poder interatuar con la tabla Departamentos.
     * 
     * @param ae
     */
    @FXML
    private void abrirDepartamento(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscarDepartamento.fxml"));
        DepartamentoController c = new DepartamentoController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Departamentos");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }
    }

    /**
     * Método que gestiona el evento del botón Imparten que abre una ventana para
     * interactuar con la tabla Imparten
     * 
     * @param ae
     */
    @FXML
    private void abrirImparten(ActionEvent ae) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/buscarImparten.fxml"));
        ImpartenController c = new ImpartenController();
        loader.setController(c);
        Parent root;
        try {
            root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Imparten");
            stage.getIcons().add(new Image("/drawable/blackboard.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }
    }
}
