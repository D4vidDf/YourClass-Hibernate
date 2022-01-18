package com.d4viddf.TablasDAO;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Profesores;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ProfesoresDAO encargada de manejar la información de la base de datos
 */
public class ProfesoresDAO implements DAO<Profesores>{
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_APELLIDOS = "apellidos";
    public static String ROW_DEPARTAMENTO = "departamentos";
    private static FileWriter file;
    Errores errores = new Errores();


    public void save(Profesores entity, Session session) {
        session.save(entity);
    }

    public void persist(Profesores entity,
                        Session session) {
        session.persist(entity);
    }

    public void update(Profesores entity, Session
            session) {
        session.update(entity);
    }

    public void delete(Profesores entity, Session
            session) {
        session.delete(entity);
    }

    /**
     * Método que devuelve el Profesor que coincida con el código de Profesor
     * introducido
     *
     * @param id Código del Profesor
     * @return Profesores
     */
    public Profesores get(int id, Session session) {
        return (Profesores) session.get(Profesores.class,id);
    }

    /**
     * Método que devuelve un ArrayList con todos los profesores existentes en la
     * base de datos
     *
     * @return List<Profesores>
     */
    public List<Profesores> getAll(Session session) {
        return (List<Profesores>)
                session.createQuery("from Profesores ").list();
    }

    public void deleteAll(Session sesion) {
        List<Profesores> lista = getAll(sesion);
        for (Profesores profesores : lista)
            delete(profesores, sesion);
    }

    /**
     * Método que devuelve un ArrayList de Profesores con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     *
     * @param row   El nombre de la columna por la cuál se quiere filtrar
     * @param query Dato del que se quiere tomar como condición
     * @return List<Profesores>
     */
    public List<Profesores> getByRowLike(String row, String query) {
        List<Profesores> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {

            if (row.equals(ROW_NOMBRE)) {
                Query q = s.createQuery("FROM Profesores WHERE nombre LIKE UPPER(:query) ")
                        .setParameter("query", "%" + query + "%")
                        .setReadOnly(true);
                lista = (List<Profesores>) q.getResultList();
            } else if (row.equals(ROW_APELLIDOS)) {
                Query q = s.createQuery("FROM Profesores WHERE apellidos LIKE UPPER(:query) ")
                        .setParameter("query", "%" + query + "%")
                        .setReadOnly(true);
                lista = (List<Profesores>) q.getResultList();
            } else if (row.equals(ROW_DEPARTAMENTO)) {
                Query q = s.createQuery("FROM Profesores WHERE departamento LIKE UPPER(:query) ")
                        .setParameter("query", "%" + query + "%")
                        .setReadOnly(true);
                lista = (List<Profesores>) q.getResultList();
            }


        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }

    /**
     * Devuelve todos los profesores que coincidan con la fecha de nacimiento
     * introducida
     *
     * @param query String con la fecha de nacimiento
     * @return List<Profesores>
     */
    public List<Profesores> getByYear( String query) {
        List<Profesores> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("FROM Profesores WHERE fecha_nacimiento LIKE :q")
                    .setParameter("q", LocalDate.parse(query)).
                    setReadOnly(true);
            lista = (List<Profesores>) q.getResultList();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }

    /**
     * Método para insertar datos a la tabla a partir de un archivo json.
     *
     * @param con
     * @param path Ubicación del fichero json con los datos de Profesores
     */
    public void insertarLote(Session con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("profesores");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    Profesores profesores = new Profesores();
                    profesores.setCod_prof(Integer.parseInt(jsonObject.get("cod_prof").toString()));
                    profesores.setApellidos(jsonObject.get("apellidos").toString());
                    profesores.setDepartamento(Integer.parseInt(jsonObject.get("departamentos").toString()));
                    profesores.setDNI(jsonObject.get("dni").toString());
                    profesores.setNombre(jsonObject.get("nombre").toString());
                    profesores.setFecha_nacimiento(LocalDate.parse(jsonObject.get("fecha_nac").toString()));
                    save(profesores, con);
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
        } catch (Exception e) {
            errores.muestraError(e);
        }
    }

    /**
     * Método que exporta toda la información de la tabla Profesores en un archivo
     * json en la ubicación indicada
     *
     * @param con
     * @param path Ubicación donde guardar el archivo
     */
    public void exportar(Session con, String path) {
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
     * @param text DNI
     * @return Profesores
     */
    public List<Profesores> getByDNI(String text) {
        List<Profesores> profesores = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("from Profesores where DNI like :dni")
                    .setParameter("dni","%"+text+"%")
                    .setReadOnly(true);
            profesores = (List<Profesores>) q.getResultList();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        return profesores;
    }
}