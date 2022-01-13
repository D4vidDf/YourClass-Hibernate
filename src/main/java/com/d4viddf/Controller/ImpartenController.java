package com.d4viddf.Controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Imparten;
import com.d4viddf.Tablas.ViewImparten;
import com.d4viddf.TablasDAO.AlumnosDAO;
import com.d4viddf.TablasDAO.ImpartenDAO;
import com.d4viddf.TablasDAO.ViewImpartenDAO;

import com.d4viddf.TablasService.ViewImpartenService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImpartenController extends DBViewController implements Initializable {
    ViewImpartenService viewImpartenService = new ViewImpartenService();
    Errores errores = new Errores();
    @FXML
    private TableView<ViewImparten> tabAlumnos;
    @FXML
    private TableColumn<ViewImparten, String> colCurso;
    @FXML
    private TableColumn<ViewImparten, Integer> colidAlumno;
    @FXML
    private TableColumn<ViewImparten, String> colNombreAlumno;
    @FXML
    private TableColumn<ViewImparten, String> colNombreProfesor;
    @FXML
    private TableColumn<ViewImparten, String> colApellidosAlumno;
    @FXML
    private TableColumn<ViewImparten, String> colApellidosProfesor;
    @FXML
    private TableColumn<ViewImparten, String> colDNIProfesor;
    @FXML
    private TableColumn<ViewImparten, String> colDNIAlumno;
    @FXML
    private TableColumn<ViewImparten, Integer> colidProfesor;
    @FXML
    private TableColumn<ViewImparten, String> colNombredepar;
    @FXML
    private TableColumn<ViewImparten, String> colNombreAsignatura;
    @FXML
    private TableColumn<ViewImparten, String> colCursoAsignatura;
    @FXML
    private TableColumn<ViewImparten, Integer> colidAsignatura;
    @FXML
    private ComboBox<String> cbxBuscarPor;
    @FXML
    private TextField txtBusqueda, txtCurso, txtExp, txtProfesor, txtAsig, path;
    @FXML
    private TextArea estado;

    private String selectedItem = "";

    /**
     * Método que inicializa la tabla con las columnas asigandas para cada atributo
     * de la clase Alumno
     */
    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colCurso.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("CursoImparten"));
        colidAlumno.setCellValueFactory(new PropertyValueFactory<ViewImparten, Integer>("Expedientealumno"));
        colNombreAlumno.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("Nombrealumno"));
        colApellidosAlumno.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("Apellidosalumno"));
        colNombreProfesor.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("NombreProfesor"));
        colApellidosProfesor.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("ApellidosProfesor"));
        colidProfesor.setCellValueFactory(new PropertyValueFactory<ViewImparten, Integer>("CodProf"));
        colDNIAlumno.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("DNIalumno"));
        colDNIProfesor.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("DNIprofesor"));
        colNombredepar.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("Nombredepartamento"));
        colidAsignatura.setCellValueFactory(new PropertyValueFactory<ViewImparten, Integer>("IDasignatura"));
        colNombreAsignatura.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("Nombreasignatura"));
        colCursoAsignatura.setCellValueFactory(new PropertyValueFactory<ViewImparten, String>("Cursoasignatura"));

        
        cbxBuscarPor.getItems().setAll("Curso", "Código Alumno", "DNI Alumno", "Código profesor", "DNI Profesor",
                "Código Asignatura", "Nombre Asignatura", "Todos");
        cbxBuscarPor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldI, String newI) {
                selectedItem = newI;
            }
        });
    }

    /**
     * Método que gestiona el evento del botón buscar.
     * 
     * @param ae
     */
    @FXML
    private void buscar(ActionEvent ae) {
        if (selectedItem.isEmpty() && txtBusqueda.getText().isEmpty()) {
            mostrar();
        } else if (selectedItem.equals("Todos") && txtBusqueda.getText().isEmpty()) {
            mostrar();
        } else if (txtBusqueda.getText().isEmpty()) {
            errores.mostrar("Por favor,\nIntroduce un valor para realizar la búsqueda");
        } else {
            if (selectedItem != null) {
                switch (selectedItem) {
                case "Curso":
                    findByRowLike(ViewImpartenDAO.ROW_Curso_imparten);
                    ;
                    break;
                case "Código alumno":
                    findByRowLikeINT(ViewImpartenDAO.ROW_COD_alumno);
                    ;
                    break;
                case "DNI alumno":
                    findByRowLike(ViewImpartenDAO.ROW_DNI_alumno);
                    ;
                    break;
                case "Código Profesor":
                    findByRowLikeINT(ViewImpartenDAO.ROW_COD_prof);
                    break;
                case "DNI Profesor":
                    findByRowLike(ViewImpartenDAO.ROW_DNI_profesor);
                    break;
                case "Código Asignatura":
                    findByRowLikeINT(ViewImpartenDAO.ROW_COD_asg);
                    break;
                case "Nombre Asignatura":
                    findByRowLike(ViewImpartenDAO.ROW_NOMBRE_Aasg);
                    ;
                    break;
                case "Todos":
                    mostrar();
                    break;
                }
            } else
                mostrar();
        }
    }

    /**
     * Método que muestra todos los alumnos existentes en la tabla.
     */
    private void mostrar() {
        List<ViewImparten> als = new ArrayList<>();
        try {
            als = viewImpartenService.findAll();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        tabAlumnos.getItems().setAll(als);
    }

    /**
     * Método que muestra el alumno por el nombre o apellido introducido
     * 
     * @param row
     */
    private void findByRowLike(String row) {

        List<ViewImparten> als = new ArrayList<>();
        try {
            als = mySQLDAOFactory.getViewImpartenDAO().getByRowLike(mySQLDAOFactory.getConnection(), row,
                    txtBusqueda.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabAlumnos.getItems().setAll(als);
    }
    private void findByRowLikeINT(String row) {

        List<ViewImparten> als = new ArrayList<>();
        try {
            als = mySQLDAOFactory.getViewImpartenDAO().getByRowLikeINT(mySQLDAOFactory.getConnection(), row,
                    txtBusqueda.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tabAlumnos.getItems().setAll(als);
    }

    /**
     * Método para crear un alumno
     * 
     * @param ae
     */
    @FXML
    private void crear(ActionEvent ae) {
        try {
            ImpartenDAO im = new ImpartenDAO();
            im.insertar(mySQLDAOFactory.getConnection(), txtCurso.getText().toString(),
                    Integer.parseInt(txtExp.getText().toString()), Integer.parseInt(txtAsig.getText().toString()),
                    Integer.parseInt(txtProfesor.getText().toString()));
        } catch (Exception e) {
            errores.muestraError(e);
        }
    }

    /**
     * Método para abrir el archivo del cuál insertar datos a la base de datos
     * 
     * @param ae
     */
    @FXML
    private void abrir(ActionEvent ae) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Seleccionar archivo", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        path.setText(selectedFile.toPath().toString());
    }

    /**
     * Método para exportar la tabla Alumnos en un fichero JSON
     * 
     * @param ae
     */
    @FXML
    private void exportar(ActionEvent ae) {
        if (path.getText().isEmpty()) {
            guardar();
            try {
                mySQLDAOFactory.getImpartenDAO().exportar(mySQLDAOFactory.getConnection(), path.getText().toString());
                estado.setText("Se ha exportado correctamente.");
            } catch (SQLException e) {
                errores.muestraErrorSQL(e);
            }
        } else {
            try {
                mySQLDAOFactory.getImpartenDAO().exportar(mySQLDAOFactory.getConnection(), path.getText().toString());
                estado.setText("Se ha exportado correctamente.");
            } catch (SQLException e) {
                errores.muestraErrorSQL(e);
            }

        }
    }

    /**
     * Método para abrir un selector de archivos para escoger el nombre y ruta donde
     * guardar el fichero json
     */
    private void guardar() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Guardar archivo", "*.json"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        path.setText(selectedFile.toPath().toString());
    }

    /**
     * Método para importar desde un fichero JSON los datos de un alumno para la
     * tabla Alumno
     * 
     * @param ae
     */
    @FXML
    private void importar(ActionEvent ae) {
        if (path.getText().isEmpty()) {
            abrir(ae);
            try {
                mySQLDAOFactory.getImpartenDAO().insertarLote(mySQLDAOFactory.getConnection(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (SQLException se) {
                errores.muestraErrorSQL(se);
            } catch (Exception e) {
                errores.muestraError(e);
            }
        } else {
            try {
                mySQLDAOFactory.getImpartenDAO().insertarLote(mySQLDAOFactory.getConnection(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (SQLException se) {
                errores.muestraErrorSQL(se);
            } catch (Exception e) {
                errores.muestraError(e);
            }

        }
    }

}
