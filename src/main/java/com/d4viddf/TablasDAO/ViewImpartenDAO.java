package com.d4viddf.TablasDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.ViewImparten;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 * Clase DAO de la vista ViewImparten que implementa la fábrica DAO
 */
public class ViewImpartenDAO {
    public static String ROW_COD_asg = "IDasignatura";
    public static String ROW_NOMBRE_Aasg = "Nombreasignatura";
    public static String ROW_NOMBRE_Alumnos = "Nombrealumno";
    public static String ROW_APELLIDOS_alumnos = "Apellidosalumno";
    public static String ROW_NOMBRE_profesor = "NombreProfesor";
    public static String ROW_APELLIDOS_profesor = "ApellidosProfesor";
    public static String ROW_DNI_profesor = "DNIprofesor";
    public static String ROW_DNI_alumno = "DNIalumno";
    public static String ROW_COD_alumno = "Expedientealumno";
    public static String ROW_COD_prof = "CodProf";
    public static String ROW_Curso_imparten = "CursoImparten";
    Errores errores = new Errores();

    /**
     * Método que retorna null ya que no es necesario su implementación y es
     * generado por la fábrica
     *
     * @param con
     * @param id
     * @return ViewImparten
     */
    public ViewImparten get(Connection con, int id) {

        return null;
    }

    /**
     * Método que devuelve un ArratList de ViewImparten con toda la información
     * disponible en la vista ViewImparten En caso de ocurrir una SQLException la
     * clase Errores() mostrará una ventana con la excepción manejada
     *
     * @param conn
     * @return List<ViewImparten>
     */

    public List<ViewImparten> getAll(Session conn) {
        List<ViewImparten> lista = null;
        try {
            lista = (List<ViewImparten>) conn.createQuery("FROM ViewImparten").getResultList();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }


}
