package com.d4viddf.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.d4viddf.Factory.DAOFactory;
import com.d4viddf.Tablas.Borrar;
import com.d4viddf.Tablas.Crear;

import com.d4viddf.TablasDAO.AsignaturasDAO;
import com.d4viddf.TablasService.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;

public class ModificadorController implements Initializable {
    @FXML
    TextArea prompt;
    @FXML
    ProgressBar progress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    /**
     * Acción que realiza el btón Borrar que vacía las tablas de la base de datos
     * 
     * @param ae
     */
    @FXML
    public void borrar(ActionEvent ae) {
        progress.setProgress(0.1);
        try {
            prompt.appendText("------------------------------------------------------------------------------\n");
            new ImpartenService().deleteAll();
            prompt.appendText("Borrado los datos de la tabla Imparten");
            progress.setProgress(0.2);
            new ProfesoresService().deleteAll();
            prompt.appendText("\nBorrado los datos de la tabla Profesores");
            progress.setProgress(0.4);
            new DepartamentosService().deleteAll();
            prompt.appendText("\nBorrado los datos de la tabla Departamentos");
            progress.setProgress(0.6);
            new AlumnosService().deleteAll();
            prompt.appendText("\nBorrado los datos de la tabla Alumnos");
            progress.setProgress(0.8);
            new AsignaturasService().deleteAll();
            prompt.appendText("\nBorrado los datos de la tabla Asignaturas");
            progress.setProgress(1);
            prompt.appendText("\n------------------------------------------------------------------------------\n");

            prompt.appendText("\nTodas las tablas borradas correctamente");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
