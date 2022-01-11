package com.d4viddf.TablasDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Alumnos;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class AlumnosDAO {
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";
    private static FileWriter file;
    Errores errores = new Errores();

    /**
     * Método para devolver una Lista con todos los alumnos existentes en la base de
     * datos
     */
    public List<Alumnos> getAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<Alumnos> lista = (List<Alumnos>) session.createQuery(
                "FROM Alumnos").list();
        session.getTransaction().commit();
        session.close();
        return lista;
    }

    /**
     * Mñetodo que devuelve el alumno que coincida con el número de expediente
     * 
     * @param id Número de Expediente
     */
    public Alumnos get( int id, Session session) {

        return (Alumnos) session.get(Alumnos.class, id);
    }

    /**
     * Método que devuelve el alumno que coincida con el DNO introducido
     * 
     * @param con
     * @param query DNI
     * @return List<Alumnos>
     */
    public List<Alumnos> getByDNI(Connection con, String query) {
        List<Alumnos> lista = new ArrayList<>();
        try {
            PreparedStatement s = con.prepareStatement("select * from alumnos where dni = ?");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumnos al = new Alumnos();
                al.setExpediente(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setNacimiento(Date.valueOf(LocalDate.parse(rs.getString(5))));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que devuelve un ArrayList de Alumnos con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     * 
     * @param conn
     * @param row   El nombre de la columna por la cuál se quiere filtrar
     * @param query Dato del que se quiere tomar como condición
     * @return List<Alumnos>
     */
    public List<Alumnos> getByRowLike(Connection conn, String row, String query) {
        List<Alumnos> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where " + row + " like upper(?)");
            s.setString(1, "%" + query + "%");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumnos al = new Alumnos();
                al.setExpediente(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setNacimiento(Date.valueOf(LocalDate.parse(rs.getString(5))));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que devuelve un ArrayList con los Alumnos que coincidan con la fecha
     * de Nacimiento
     * 
     * @param conn
     * @param query Fecha de Nacimietno
     * @return List<Alumnos>
     */
    public List<Alumnos> getByYear(Connection conn, String query) {
        List<Alumnos> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement("select * from alumnos where nacimiento regexp ?");
            s.setString(1, query + "-[0-9][0-9]-[0-9][0-9]");
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumnos al = new Alumnos();
                al.setExpediente(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setNacimiento(Date.valueOf(LocalDate.parse(rs.getString(5))));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método que devuelve a los alumnos que tengan a un profesor
     * 
     * @param conn
     * @param query Cod_profesor
     * @return List<Alumnos>
     */
    public List<Alumnos> getByProfesor(Connection conn, String query) {
        List<Alumnos> lista = new ArrayList<>();
        try {
            PreparedStatement s = conn.prepareStatement(
                    "select * from alumnos a inner join imparten i on a.expediente = i.alumno inner join profesores p on i.profesor = p.cod_prof where p.cod_prof= ? group by a.expediente");
            s.setString(1, query);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Alumnos al = new Alumnos();
                al.setExpediente(rs.getInt(1));
                al.setDNI(rs.getString(2));
                al.setNombre(rs.getString(3));
                al.setApellidos(rs.getString(4));
                al.setNacimiento(Date.valueOf(rs.getString(5)));
                lista.add(al);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    /**
     * Método para insertar un alumno por lote proveniente de un archivo json
     * 
     * @param con
     * @param path
     * @throws Exception
     */
    public void insertarLote(Connection con, String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Alumnos (expediente,dni, nombre, apellidos, fecha_nacimiento) VALUES (?,?, ?, ?, ?);",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("alumnos");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    ps.setInt(1, Integer.parseInt(jsonObject.get("num_exp").toString()));
                    ps.setString(2, jsonObject.get("dni").toString());
                    ps.setString(3, jsonObject.get("nombre").toString());
                    ps.setString(4, jsonObject.get("apellidos").toString());
                    ps.setDate(5, Date.valueOf(jsonObject.get("fecha_nac").toString()));
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
        }

    }

    /**
     * Método para insertar un alumno a la base de datos
     * 
     * @param con
     * @param nombre
     * @param apellidos
     * @param DNI
     * @param expediente
     * @param fecha
     */
    public void insertar(Connection con, String nombre, String apellidos, String DNI, int expediente, LocalDate fecha) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Alumnos (expediente,dni, nombre, apellidos, fecha_nacimiento) VALUES (?,?, ?, ?, ?);");
            ps.setInt(1, expediente);
            ps.setString(2, DNI);
            ps.setString(3, nombre);
            ps.setString(4, apellidos);
            ps.setDate(5, Date.valueOf(fecha));
            ps.execute();
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
    }

    /**
     * Método para exportar los datos de la tabla Alumnos en un archivo de formato
     * json.
     * 
     * @param con
     * @param path
     */
    public void exportar(Connection con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Alumnos> list = this.getAll();
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("num_exp", item.getExpediente());
            obj.put("dni", item.getDNI());
            obj.put("nombre", item.getNombre());
            obj.put("apellidos", item.getApellidos());
            obj.put("fecha_nac", item.getNacimiento().toString());
            jsonArr.put(obj);
        });
        jsonObject.put("alumnos", jsonArr);

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
