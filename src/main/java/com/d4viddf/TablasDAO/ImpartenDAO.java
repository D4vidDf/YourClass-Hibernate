package com.d4viddf.TablasDAO;

import java.sql.Connection;
import java.time.DateTimeException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Imparten;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ImpartenDAO{
    Errores errores = new Errores();
    static Scanner teclado = new Scanner(System.in);
    private static FileWriter file;

    /**
     * Método que devuelve null ya que no es utilizado
     * 
     * @param con
     * @param id
     * @return Imparten
     */
    public Imparten get(Connection con, int id) {
        return null;
    }

    /**
     * Método que devuelve todda la tabla Imparten en un ArrayList
     * 
     * @param conn
     * @return List<Imparten>
     */

    public List<Imparten> getAll(Connection conn) {
        List<Imparten> lista = null;
        try {
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery("SELECT * FROM Imparten;");
            int totalRows = 0;
            rs.last();
            totalRows = rs.getRow();
            rs.beforeFirst();
            lista = new ArrayList<Imparten>(totalRows);
            while (rs.next()) {
                Imparten as = new Imparten();
                as.setAlumno(rs.getInt(2));
                as.setAsignatura(rs.getInt(4));
                as.setCurso(rs.getString(1));
                as.setProfesor(rs.getInt(3));
                lista.add(as);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que inserta por batch los datos de un archivo json con la estructura
     * de datos de Imparten en la base de datos.
     * 
     * @param con
     * @param path Ubicación del fichero .json
     */
    public void insertarLote(Connection con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO imparten (curso,alumno, profesor, asignatura) VALUES (?,?, ?, ?);",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("imparten");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    ps.setString(1, jsonObject.get("curso").toString());
                    ps.setInt(2, Integer.parseInt(jsonObject.get("alumno").toString()));
                    ps.setInt(3, Integer.parseInt(jsonObject.get("profesor").toString()));
                    ps.setInt(4, Integer.parseInt(jsonObject.get("asignatura").toString()));
                    ps.addBatch();
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
            ps.executeBatch();
        } catch (DateTimeException e) {
            errores.muestraErrorDate(e);
        }

        catch (SQLException e) {
            errores.muestraErrorSQL(e);
        } catch (FileNotFoundException e1) {
            errores.mostrar(e1.getMessage());
        } catch (IOException e1) {
            errores.muestraErrorIO(e1);
        } catch (ParseException e1) {
            errores.mostrar(e1.getMessage());
        }

    }

    /**
     * Método que permite insertar en imparten la relación entre Profesor, Alumno y
     * asignatura
     * 
     * @param con
     * @param string    Curso
     * @param string2   Alumno
     * @param parseInt  Profesor
     * @param parseInt2 Asignatura
     */
    public void insertar(Connection con, String string, int string2, int parseInt, int parseInt2) {
        try {
            PreparedStatement ps = con
                    .prepareStatement("INSERT INTO imparten (curso,alumno, profesor, asignatura) VALUES (?,?, ?, ?);");
            ps.setString(1, string);
            ps.setInt(2, string2);
            ps.setInt(3, parseInt);
            ps.setInt(4, parseInt2);
            ps.execute();
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
    }

    /**
     * Método que permite exportar la tabla imparten en un fichero de formato .json
     * 
     * @param con
     * @param path Ubicación donde se quiere guardar el fichero
     */
    public void exportar(Connection con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Imparten> list = this.getAll(con);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("curso", item.getCurso());
            obj.put("alumno", item.getAlumno());
            obj.put("profesor", item.getProfesor());
            obj.put("asignatura", item.getAsignatura());
            jsonArr.put(obj);
        });
        jsonObject.put("imparten", jsonArr);

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
