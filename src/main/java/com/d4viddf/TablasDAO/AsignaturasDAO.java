package com.d4viddf.TablasDAO;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Asignaturas;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.List;
import java.util.Scanner;

public class AsignaturasDAO implements DAO<Asignaturas>{
    private static FileWriter file;
    Errores errores = new Errores();
    public static String ROW_NOMBRE = "nombre";
    public static String ROW_CURSO = "curso";
    static Scanner teclado = new Scanner(System.in);


    public void save(Asignaturas entity, Session session) {
        session.save(entity);
    }

    public void persist(Asignaturas entity,
                        Session session) {
        session.persist(entity);
    }

    public void update(Asignaturas entity, Session
            session) {
        session.update(entity);
    }

    public void delete(Asignaturas entity, Session
            session) {
        session.delete(entity);
    }

    /**
     * Método que devuelve la Asignatura que coincida con el ID introducido
     *
     * @param id
     * @return Asignaturas
     */
    public Asignaturas get(int id, Session session) {
        return (Asignaturas) session.get(Asignaturas.class, id);
    }

    /**
     * Método que devuelve un ArrayList con todas las asignaturas existentes en la
     * base de datos
     *
     * @return List<Asignaturas>
     */
    public List<Asignaturas> getAll(Session session) {
        return (List<Asignaturas>)
                session.createQuery("from Asignaturas").list();
    }

    public void deleteAll(Session sesion) {
        List<Asignaturas> lista = getAll(sesion);
        for (Asignaturas asignaturas : lista)
            delete(asignaturas, sesion);
    }

    /**
     * Método que inserta por batch los datos de un archivo json con la estructura
     * de datos de Asignaturas en la base de datos.
     *
     * @param con
     * @param path Ubicación del fichero .json
     */
    public void insertarLote(Session con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);

            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("asignaturas");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    Asignaturas asignaturas = new Asignaturas();
                    asignaturas.setCurso(jsonObject.get("curso").toString());
                    asignaturas.setId(Integer.parseInt(jsonObject.get("id").toString()));
                    asignaturas.setNombre(jsonObject.get("nombre").toString());
                    save(asignaturas, con);
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
        } catch (DateTimeException e) {
            errores.muestraErrorDate(e);
        } catch (IOException e1) {
            errores.muestraErrorIO(e1);
        } catch (ParseException e1) {
            errores.mostrar(e1.getMessage());
        } catch (Exception e) {
            errores.muestraError(e);
        }

    }

    /**
     * Método que devuelve un ArrayList de Asignaturas con todos los campos que
     * cooincidan con la búsqueda de tipo texto introducida. En caso de ocurrir una
     * SQLException la clase Errores() mostrará una ventana con la excepción
     * manejada
     *
     * @param row  El nombre de la columna por la cuál se quiere filtrar
     * @param text Dato del que se quiere tomar como condición
     * @return List<Asignaturas>
     */
    public List<Asignaturas> getByRowLike(String row, String text) {
        List<Asignaturas> lista = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            if (row.equals("nombre")) {
                Query q = s.createQuery("FROM Asignaturas WHERE nombre LIKE UPPER(:query) ")
                        .setParameter("query", "%" + text + "%")
                        .setReadOnly(true);
                lista = (List<Asignaturas>) q.getResultList();
            } else {
                Query q = s.createQuery("FROM Asignaturas WHERE curso LIKE UPPER(:query) ")
                        .setParameter("query", "%" + text + "%")
                        .setReadOnly(true);
                lista = (List<Asignaturas>) q.getResultList();
            }

        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }


    /**
     * Método que permite exportar la tabla Asiganturas en un fichero de formato
     * .json
     *
     * @param con
     * @param path Ubicación donde se quiere guardar el fichero
     */
    public void exportar(Session con, String path) {
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
