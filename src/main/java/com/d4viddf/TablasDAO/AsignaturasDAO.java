package com.d4viddf.TablasDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Asignaturas;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AsignaturasDAO{
    private static FileWriter file;
    Errores errores = new Errores();
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_CURSO = "curso";
    static Scanner teclado = new Scanner(System.in);

    /**
     * Método que devuelve la Asignatura que coincida con el ID introducido
     * 
     * @param con
     * @param id
     * @return Asignaturas
     */
    public Asignaturas get(Connection con, int id) {
        Asignaturas as = new Asignaturas();
        try {
            PreparedStatement s = con.prepareStatement("select * from asignatura where id = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            as.setId(rs.getInt(1));
            as.setNombre(rs.getString(2));
            as.setCurso(rs.getString(3));
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return as;
    }

    /**
     * Método que devuelve un ArrayList con todas las asignaturas existentes en la
     * base de datos
     * 
     * @param conn
     * @return List<Asignaturas>
     */
    public List<Asignaturas> getAll(Connection conn) {
        List<Asignaturas> lista = null;
        try {
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery("SELECT * FROM Asignaturas;");
            int totalRows = 0;
            rs.last();
            totalRows = rs.getRow();
            rs.beforeFirst();
            lista = new ArrayList<Asignaturas>(totalRows);
            while (rs.next()) {
                Asignaturas as = new Asignaturas();
                as.setId(rs.getInt(1));
                as.setNombre(rs.getString(2));
                as.setCurso(rs.getString(3));
                lista.add(as);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que inserta por batch los datos de un archivo json con la estructura
     * de datos de Asignaturas en la base de datos.
     * 
     * @param con
     * @param path Ubicación del fichero .json
     */
    public void insertarLote(Connection con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            PreparedStatement ps = con.prepareStatement("INSERT INTO asignaturas (id,nombre, curso) VALUES (?,?, ?);",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("asignaturas");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    ps.setInt(1, Integer.parseInt(jsonObject.get("id").toString()));
                    ps.setString(2, jsonObject.get("nombre").toString());
                    ps.setString(3, jsonObject.get("curso").toString());
                    ps.addBatch();
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
            ps.executeBatch();
        } catch (DateTimeException e) {
            errores.muestraErrorDate(e);
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        } catch (IOException e1) {
            errores.muestraErrorIO(e1);
        } catch (ParseException e1) {
            errores.mostrar(e1.getMessage());
        }

    }

    /**
     * Método que devuelve un ArrayList de Asignaturas con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     * 
     * @param conn
     * @param row  El nombre de la columna por la cuál se quiere filtrar
     * @param text Dato del que se quiere tomar como condición
     * @return List<Asignaturas>
     */
    public List<Asignaturas> getByRowLike(Connection con, String row, String text) {
        List<Asignaturas> lista = null;
        try {
            PreparedStatement s = con.prepareStatement("select * from Asignaturas where " + row + " like upper(?)");
            s.setString(1, "%" + text + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Asignaturas as = new Asignaturas();
                as.setId(rs.getInt(1));
                as.setNombre(rs.getString(2));
                as.setCurso(rs.getString(3));
                lista.add(as);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que permite insertar una única asignatura
     * @param con
     * @param parseInt ID
     * @param string Nombre
     * @param string2 Curso al que pertenecen (Ejemplo: 4º ESO)
     */
    public void insertar(Connection con, int parseInt, String string, String string2) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO asignaturas (id,nombre, curso) VALUES (?,?, ?);");
            ps.setInt(1, parseInt);
            ps.setString(2, string);
            ps.setString(3, string2);
            ps.execute();
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
    }

    /**
     * Método que permite exportar la tabla Asiganturas en un fichero de formato
     * .json
     * 
     * @param con
     * @param path Ubicación donde se quiere guardar el fichero
     */
    public void exportar(Connection con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Asignaturas> list = this.getAll(con);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("nombre", item.getNombre());
            obj.put("curso", item.getCurso());
            jsonArr.put(obj);
        });
        jsonObject.put("asignaturas", jsonArr);

        try {
            file = new FileWriter(path);
            file.write(jsonObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }

    }

}
