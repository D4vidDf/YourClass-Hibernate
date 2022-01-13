package com.d4viddf.Controller;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Profesores;
import com.d4viddf.TablasDAO.ProfesoresDAO;
import com.d4viddf.TablasService.ProfesoresService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProfesorController extends DBViewController implements Initializable {
    ProfesoresService profesoresService = new ProfesoresService();
    Errores errores = new Errores();
    @FXML
    private TableView<Profesores> tabAlumnos;
    @FXML
    private TableColumn<Profesores, Integer> colNum;
    @FXML
    private TableColumn<Profesores, String> colDNI;
    @FXML
    private TableColumn<Profesores, String> colNombre;
    @FXML
    private TableColumn<Profesores, String> colApellidos;
    @FXML
    private TableColumn<Profesores, LocalDate> colNac;
    @FXML
    private TableColumn<Profesores, Integer> colDepar;
    @FXML
    private ComboBox<String> cbxBuscarPor;
    @FXML
    private TextField txtBusqueda, txtNum, txtApellidos, txtNombre, txtDNI, txtDep, path;
    @FXML
    private DatePicker fecha;
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
        colNum.setCellValueFactory(new PropertyValueFactory<Profesores, Integer>("cod_prof"));
        colDNI.setCellValueFactory(new PropertyValueFactory<Profesores, String>("DNI"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Profesores, String>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<Profesores, String>("apellidos"));
        colNac.setCellValueFactory(new PropertyValueFactory<Profesores, LocalDate>("fecha_nacimiento"));
        colDepar.setCellValueFactory(new PropertyValueFactory<Profesores, Integer>("departamento"));

        cbxBuscarPor.getItems().setAll("Código de profesor", "DNI", "Nombre", "Apellidos", "Año de nacimiento",
                "Departamento", "Todos");
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
                System.out.println(selectedItem);
                switch (selectedItem) {
                    case "Código de profesor":
                        findByID();
                        break;
                    case "DNI":
                        findByDNI();
                        break;
                    case "Nombre":
                        findByRowLike(ProfesoresDAO.ROW_NOMBRE);
                        break;
                    case "Apellidos":
                        findByRowLike(ProfesoresDAO.ROW_APELLIDOS);
                        break;
                    case "Año de nacimiento":
                        findByAnho();
                        break;
                    case "Departamento":
                        findByRowLike(ProfesoresDAO.ROW_DEPARTAMENTO);
                        break;
                    case "Todos":
                        mostrar();
                        break;
                    default:
                        System.out.println("Hola");
                        break;
                }
            } else
                mostrar();
        }
    }

    private void findByID() {
        int id = Integer.parseInt(txtBusqueda.getText());
        Profesores profesores = new Profesores();
        try {
            profesores = profesoresService.findById(id);
            System.out.println(profesores.toString());
        } catch (Exception e) {
            errores.muestraError(e);
        }
        tabAlumnos.getItems().setAll(profesores);


    }

    /**
     * Método que muestra todos los alumnos existentes en la tabla.
     */
    private void mostrar() {
        List<Profesores> pro = new ArrayList<>();
        try {
            pro = mySQLDAOFactory.getProfesoresDAO().getAll(HibernateUtil.getSessionFactory().openSession());
        } catch (Exception e) {
            errores.muestraError(e);
        }
        tabAlumnos.getItems().setAll(pro);
    }

    /**
     * Método que muestra al Alumno que coincida con el DNI del campo TextField
     */
    private void findByDNI() {
        List<Profesores> profesores = new ArrayList<>();
        try {
            profesores = mySQLDAOFactory.getProfesoresDAO().getByDNI(txtBusqueda.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabAlumnos.getItems().setAll(profesores);

    }

    /**
     * Método que muestra el alumno por el nombre o apellido introducido
     *
     * @param row
     */
    private void findByRowLike(String row) {

        List<Profesores> als = new ArrayList<>();
        try {
            als =
                    mySQLDAOFactory.getProfesoresDAO().getByRowLike(row, txtBusqueda.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabAlumnos.getItems().setAll(als);
    }

    /**
     * Mëtodo que muestra a los Alumnos que coincidan con la fecha de nacimiento
     */
    private void findByAnho() {
        List<Profesores> als = new ArrayList<>();
        try {
            als =
                    mySQLDAOFactory.getProfesoresDAO().getByYear(txtBusqueda.getText());
        } catch (Exception e) {
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
            Profesores profesores = new Profesores(Integer.parseInt(txtDep.getText().toString()), Integer.parseInt(txtNum.getText().toString()), txtDNI.getText().toString(), txtNombre.getText().toString(), txtApellidos.getText().toString(), LocalDate.parse(fecha.getValue().toString()));
            profesoresService.save(profesores);
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
                mySQLDAOFactory.getProfesoresDAO().exportar(HibernateUtil.getSessionFactory().openSession(), path.getText().toString());
                estado.setText("Se ha exportado correctamente.");
            } catch (Exception e) {
                errores.muestraError(e);
            }
        } else {
            try {
                mySQLDAOFactory.getProfesoresDAO().exportar(HibernateUtil.getSessionFactory().openSession(), path.getText().toString());
                estado.setText("Se ha exportado correctamente.");
            } catch (Exception e) {
                errores.muestraError(e);
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
                mySQLDAOFactory.getProfesoresDAO().insertarLote(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (Exception e) {
                errores.muestraError(e);
            }
        } else {
            try {
                mySQLDAOFactory.getProfesoresDAO().insertarLote(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (Exception e) {
                errores.muestraError(e);
            }

        }
    }

}
