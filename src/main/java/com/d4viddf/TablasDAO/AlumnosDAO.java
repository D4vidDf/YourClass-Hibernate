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

import com.d4viddf.TablasService.AlumnosService;
import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class AlumnosDAO implements DAO<Alumnos> {
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";
    private static FileWriter file;
    Errores errores = new Errores();

    public void save (Alumnos entity, Session session){
        session.save(entity);
    }

    public void persist(Alumnos entity,
                        Session session) {
        session.persist(entity);
    }

    public void update(Alumnos entity, Session
            session) {
        session.update(entity);
    }

    public void delete(Alumnos entity, Session
            session) {
        session.delete(entity);
    }

    /**
     * Método para devolver una Lista con todos los alumnos existentes en la base de
     * datos
     */
    @SuppressWarnings("unchecked")
    public List<Alumnos> getAll(Session session) {
        return (List<Alumnos>)
                session.createQuery("from Alumnos").list();
    }

    public void deleteAll(Session sesion) {
        List<Alumnos> lista = getAll(sesion);
        for (Alumnos alumnos : lista)
            delete(alumnos, sesion);
    }

    /**
     * Mñetodo que devuelve el alumno que coincida con el número de expediente
     *
     * @param id Número de Expediente
     */
    public Alumnos get(int id, Session session) {

        return (Alumnos) session.get(Alumnos.class, id);
    }

    /**
     * Método que devuelve el alumno que coincida con el DNO introducido
     *
     * @param query DNI
     * @return List<Alumnos>
     */
    public List<Alumnos> getByDNI(String query) {
        List<Alumnos> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("FROM Alumnos WHERE DNI LIKE :dni")
                    .setParameter("dni", "%" + query + "%").
                    setReadOnly(true);
            lista = (List<Alumnos>) q.getResultList();

        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }

    /**
     * Método que devuelve un ArrayList de Alumnos con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     *
     * @param row   El nombre de la columna por la cuál se quiere filtrar
     * @param query Dato del que se quiere tomar como condición
     * @return List<Alumnos>
     */
    public List<Alumnos> getByRowLike(String row, String query) {
        List<Alumnos> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            if (row.equals("nombre")){
                Query q = s.createQuery("FROM Alumnos WHERE nombre LIKE UPPER(:query) ")
                        .setParameter("query", "%" + query + "%")
                        .setReadOnly(true);
                lista = (List<Alumnos>) q.getResultList();
            } else {
                Query q = s.createQuery("FROM Alumnos WHERE apellidos LIKE UPPER(:query) ")
                        .setParameter("query", "%" + query + "%")
                        .setReadOnly(true);
                lista = (List<Alumnos>) q.getResultList();
            }

        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }

    /**
     * Método que devuelve un ArrayList con los Alumnos que coincidan con la fecha
     * de Nacimiento
     *
     * @param query Fecha de Nacimietno
     * @return List<Alumnos>
     */
    public List<Alumnos> getByYear(String query) {
        List<Alumnos> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("FROM Alumnos WHERE nacimiento LIKE :q")
                    .setParameter("q", LocalDate.parse(query)).
                    setReadOnly(true);
            lista = (List<Alumnos>) q.getResultList();

        } catch (Exception e) {
            errores.muestraError(e);
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
    public void insertarLote(Session con, String path) throws Exception {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("alumnos");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    Alumnos alumnos = new Alumnos();
                    alumnos.setExpediente(Integer.parseInt(jsonObject.get("num_exp").toString()));
                    alumnos.setNombre(jsonObject.get("nombre").toString());
                    alumnos.setDNI(jsonObject.get("dni").toString());
                    alumnos.setNacimiento(LocalDate.parse(jsonObject.get("fecha_nac").toString()));
                    alumnos.setApellidos(jsonObject.get("apellidos").toString());
                    save(alumnos, con);
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
        } catch (DateTimeException e) {
            errores.muestraErrorDate(e);
        } catch (Exception e) {
            errores.muestraError(e);
        }

    }



    /**
     * Método para exportar los datos de la tabla Alumnos en un archivo de formato
     * json.
     *
     * @param path
     */
    public void exportar(Session session, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Alumnos> list = this.getAll(session);
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
