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

public class ModificadorController extends DBViewController implements Initializable {
    @FXML
    TextArea prompt;
    @FXML
    ProgressBar progress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Acción que realiza el botón Crear para ejecutar la creación de la base de
     * datos
     * 
     * @param ae
     */
    @FXML
    public void crear(ActionEvent ae) {
        prompt.appendText("------------------------------------------------------------------------------\n");
        prompt.appendText("Iniciando la creación de las tablas\n");
        try {
            progress.setProgress(0.1);
            progress.setProgress(0.5);
            Crear.createTables(mySQLDAOFactory.getConnection());
            prompt.appendText("Tablas creadas correctamente\n");
            progress.setProgress(1);
        } catch (Exception e) {
            prompt.appendText("No se han podido crear las tablas:\n");
            progress.setProgress(1);
            prompt.appendText(String
                    .valueOf("Causa: " + e.getCause() + "\nClase: " + e.getClass() + "\nMensaje: " + e.getMessage()));
        }
    }

    /**
     * Acción que realiza el btón Borrar que vacía las tablas de la base de datos
     * 
     * @param ae
     */
    @FXML
    public void borrar(ActionEvent ae) {
        new ImpartenService().deleteAll();
        new DepartamentosService().deleteAll();
        new AlumnosService().deleteAll();
        new ProfesoresService().deleteAll();
        new AsignaturasService().deleteAll();

    }
}
