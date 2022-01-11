package com.d4viddf.TablasDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Profesores;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Clase ProfesoresDAO encargada de manejar la información de la base de datos
 */
public class ProfesoresDAO{
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";
    public static String ROW_DEPARTAMENTO = "departamentos";
    private static FileWriter file;
    Errores errores = new Errores();

    /**
     * Método que devuelve el Profesor que coincida con el código de Profesor
     * introducido
     * 
     * @param con Conexión
     * @param id  Código del Profesor
     * @return Profesores
     */
    public Profesores get(Connection con, int id) {
        Profesores al = new Profesores();
        try {
            PreparedStatement s = con.prepareStatement("select * from profesores where cod_prof = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            al.setCod_prof(rs.getInt(1));
            al.setDNI(rs.getString(2));
            al.setNombre(rs.getString(3));
            al.setApellidos(rs.getString(4));
            al.setFecha_nacimiento(LocalDate.parse(rs.getString(5)));
            al.setDepartamento(rs.getInt(6));
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return al;
    }

    /**
     * Método que devuelve un ArrayList con todos los profesores existentes en la
     * base de datos
     * 
     * @param con
     * @return List<Profesores>
     */
    public List<Profesores> getAll(Connection con) {
        List<Profesores> lista = null;
        try {
            Statement s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery("SELECT * FROM Profesores;");
            int totalRows = 0;
            rs.last();
            totalRows = rs.getRow();
            rs.beforeFirst();
            lista = new ArrayList<Profesores>(totalRows);
            while (rs.next()) {
                Profesores as = new Profesores();
                as.setDNI(rs.getString(1));
                as.setNombre(rs.getString(2));
                as.setApellidos(rs.getString(3));
                as.setFecha_nacimiento(LocalDate.parse(rs.getString(4)));
                as.setDepartamento(rs.getInt(5));
                as.setCod_prof(rs.getInt(6));
                lista.add(as);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Método que devuelve un ArrayList de Profesores con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     * 
     * @param conn
     * @param row   El nombre de la columna por la cuál se quiere filtrar
     * @param query Dato del que se quiere tomar como condición
     * @return List<Profesores>
     */
    public List<Profesores> getByRowLike(Connection conn, String row, String query) {
        List<Profesores> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where " + row + " like upper(?)");
            s.setString(1, "%" + query + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Profesores al = new Profesores();
                al.setCod_prof(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setFecha_nacimiento(LocalDate.parse(rs.getString(5)));
                al.setDepartamento(rs.getInt(6));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Devuelve todos los profesores que coincidan con la fecha de nacimiento
     * introducida
     * 
     * @param conn
     * @param query String con la fecha de nacimiento
     * @return List<Profesores>
     */
    public List<Profesores> getByYear(Connection conn, String query) {
        List<Profesores> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from profesores where fecha_nacimiento regexp ?");
            s.setString(1, query + "-[0-9][0-9]-[0-9][0-9]");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Profesores al = new Profesores();
                al.setCod_prof(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setFecha_nacimiento(LocalDate.parse(rs.getString(5)));
                al.setDepartamento(rs.getInt(6));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método para insertar datos a la tabla a partir de un archivo json.
     * 
     * @param con
     * @param path Ubicación del fichero json con los datos de Profesores
     */
    public void insertarLote(Connection con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO profesores (cod_prof,dni, nombre, apelidos, fecha_nacimiento,departamentos) VALUES (?, ?, ?,?,?,?);",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("profesores");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    ps.setInt(1, Integer.parseInt(jsonObject.get("cod_prof").toString()));
                    ps.setString(2, jsonObject.get("dni").toString());
                    ps.setString(3, jsonObject.get("nombre").toString());
                    ps.setString(4, jsonObject.get("apellidos").toString());
                    ps.setDate(5, Date.valueOf(jsonObject.get("fecha_nac").toString()));
                    ps.setInt(6, Integer.parseInt(jsonObject.get("departamentos").toString()));
                    ps.addBatch();
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
            ps.executeBatch();
        } catch (Exception e) {
            errores.muestraError(e);
        }
    }

    /**
     * Método para insertar un único profesor en la base de datos
     * 
     * @param con
     * @param string   DNI
     * @param string2  Nombre
     * @param string3  Apellidos
     * @param parseInt Cod_prof
     * @param value    Fecha_nacimiento
     * @param string4  Departamento
     */
    public void insertar(Connection con, String string, String string2, String string3, int parseInt, LocalDate value,
            String string4) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Profesores (cod_prof,dni, nombre, apellidos, fecha_nacimiento,departamentos) VALUES (?,?, ?, ?, ?,?);");
            ps.setInt(1, parseInt);
            ps.setString(2, string);
            ps.setString(3, string2);
            ps.setString(4, string3);
            ps.setDate(5, Date.valueOf(value));
            ps.setInt(6, Integer.parseInt(string4));
            ps.execute();
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
    }

    /**
     * Método que exporta toda la información de la tabla Profesores en un archivo
     * json en la ubicación indicada
     * 
     * @param con
     * @param path Ubicación donde guardar el archivo
     */
    public void exportar(Connection con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Profesores> list = this.getAll(con);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("cod_prof", item.getCod_prof());
            obj.put("dni", item.getDNI());
            obj.put("nombre", item.getNombre());
            obj.put("apellidos", item.getApellidos());
            obj.put("fecha_nac", item.getFecha_nacimiento().toString());
            obj.put("departamentos", item.getDepartamento());
            jsonArr.put(obj);
        });
        jsonObject.put("profesores", jsonArr);

        try {
            file = new FileWriter(path);
            file.write(jsonObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            errores.muestraErrorIO(e);
        }

    }

    /**
     * Método que devuelve el profesor que coincida con el DNI
     * 
     * @param con
     * @param text DNI
     * @return Profesores
     */
    public Profesores getByDNI(Connection con, String text) {
        Profesores al = new Profesores();
        try {
            PreparedStatement s = con.prepareStatement("select * from profesores where dni = ?");
            s.setString(1, text);
            ResultSet rs = s.executeQuery();
            rs.next();
            al.setCod_prof(rs.getInt(1));
            al.setDNI(rs.getString(2));
            al.setNombre(rs.getString(3));
            al.setApellidos(rs.getString(4));
            al.setFecha_nacimiento(LocalDate.parse(rs.getString(5)));
            al.setDepartamento(rs.getInt(6));
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return al;
    }
}