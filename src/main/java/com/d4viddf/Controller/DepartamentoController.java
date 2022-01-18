package com.d4viddf.Controller;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Factory.DAOFactory;
import com.d4viddf.Tablas.Departamentos;
import com.d4viddf.TablasDAO.DepartamentosDAO;

import com.d4viddf.TablasService.DepartamentosService;
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

public class DepartamentoController extends DAOFactory implements Initializable {
    DepartamentosService departamentosService = new DepartamentosService();
    Errores errores = new Errores();
    @FXML
    private TableView<Departamentos> tabDepartamentos;
    @FXML
    private TableColumn<Departamentos, Integer> colNum;
    @FXML
    private TableColumn<Departamentos, String> colNombre;
    @FXML
    private TableColumn<Departamentos, String> colPresupuesto;
    @FXML
    private TableColumn<Departamentos, String> colDesc;
    @FXML
    private ComboBox<String> cbxBuscarPor;
    @FXML
    private TextField txtBusqueda, txtID, txtPresupuesto, txtNombre, path;
    @FXML
    private TextArea estado, txtDesc;

    private String selectedItem = "";

    /**
     * Método que inicializa la tabla con las columnas asigandas para cada atributo
     * de la clase Alumno
     */
    @FXML
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colNum.setCellValueFactory(new PropertyValueFactory<Departamentos, Integer>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Departamentos, String>("nombre"));
        colPresupuesto.setCellValueFactory(new PropertyValueFactory<Departamentos, String>("presupuesto"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Departamentos, String>("desc"));

        cbxBuscarPor.getItems().setAll("Número departamento", "Nombre", "Presupuesto", "Todos");
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
                    case "Número departamento":
                        findByDepartamento();
                        break;
                    case "Nombre":
                        findByName();
                        break;
                    case "Presupuesto":
                        findByPresupuesto();
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
     * Método que muestra todos los departamentos existentes en la tabla.
     */
    private void mostrar() {
        List<Departamentos> als = new ArrayList<>();
        try {
            als = departamentosService.findAll();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        tabDepartamentos.getItems().setAll(als);
    }

    /**
     * Método que muestra el departamento que coincida con el id del TextField en la
     * tabla
     */
    private void findByDepartamento() {
        if (txtBusqueda.getText().isEmpty()) {
            errores.mostrar("Por favor,\nAñade el Número del departamento para poder buscar");

        } else {
            int id = Integer.parseInt(txtBusqueda.getText());
            Departamentos departamentos = new Departamentos();

            try {
                departamentos = departamentosService.findById(id);
            } catch (Exception e) {
                errores.muestraError(e);
            }
            tabDepartamentos.getItems().setAll(departamentos);
        }
    }

    /**
     * Método que muestra el departamento que coincida con el presupuesto del
     * TextField en la tabla
     */
    private void findByPresupuesto() {
        float id = Float.parseFloat(txtBusqueda.getText());
        List<Departamentos> als = new ArrayList<>();
        try {
            als = getDepartamentosDAO().getByPresupuesto( id);
        } catch (Exception e) {
            errores.muestraError(e);
        }
        tabDepartamentos.getItems().setAll(als);
    }

    /**
     * Método que muestra el departamento por el nombre introducido
     */
    private void findByName() {
        List<Departamentos> dep = new ArrayList<>();
        try {
            dep = getDepartamentosDAO().getByName(txtBusqueda.getText().toString());
        }catch (Exception e) {
            errores.muestraError(e);
        }
        tabDepartamentos.getItems().setAll(dep);
    }



    /**
     * Método para crear un departamento
     *
     * @param ae
     */
    @FXML
    private void crear(ActionEvent ae) {
        if (txtNombre.getText().toString().isBlank() || txtID.getText().toString().isBlank()
                || txtPresupuesto.getText().toString().isBlank() || txtDesc.getText().toString().isBlank()) {
            errores.mostrar("Por favor,\nRellene todos los datos del alumno");
        } else
            try {
                Departamentos departamentos = new Departamentos();
                departamentos.setId(Integer.parseInt(txtID.getText().toString()));
                departamentos.setNombre(txtNombre.getText().toString());
                departamentos.setPresupuesto(Float.parseFloat(txtPresupuesto.getText().toString()));
                departamentos.setDesc(txtDesc.getText().toString());
                departamentosService.save(departamentos);
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
     * Método para exportar la tabla Departamentos en un fichero JSON
     *
     * @param ae
     */
    @FXML
    private void exportar(ActionEvent ae) {
        if (path.getText().isEmpty()) {
            guardar();
            try {
                getDepartamentosDAO().exportar(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
                estado.setText("Se ha exportado correctamente.");
            } catch (Exception e) {
                errores.muestraError(e);
            }
        } else {
            try {
                getDepartamentosDAO().exportar(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
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
     * Método para importar desde un fichero JSON los datos de un departamento para
     * la tabla departamentos
     *
     * @param ae
     */
    @FXML
    private void importar(ActionEvent ae) {
        if (path.getText().isEmpty()) {
            abrir(ae);
            try {
                getDepartamentosDAO().insertarLote(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (Exception e) {
                errores.muestraError(e);
            }
        } else {
            try {
                getDepartamentosDAO().insertarLote(HibernateUtil.getSessionFactory().openSession(),
                        path.getText().toString());
                estado.setText("Se han importado correctamente los datos.");
            } catch (Exception se) {
                errores.muestraError(se);
            }

        }
    }

}
