package com.d4viddf.TablasDAO;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Imparten;
import com.d4viddf.TablasService.AlumnosService;
import com.d4viddf.TablasService.AsignaturasService;
import com.d4viddf.TablasService.ProfesoresService;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.List;

public class ImpartenDAO implements DAO<Imparten>{
    Errores errores = new Errores();
    private static FileWriter file;

    public void save(Imparten entity, Session session) {
        session.save(entity);
    }

    public void persist(Imparten entity,
                        Session session) {
        session.persist(entity);
    }

    public void update(Imparten entity, Session
            session) {
        session.update(entity);
    }

    public void delete(Imparten entity, Session
            session) {
        session.delete(entity);
    }

    /**
     * Método que devuelve null ya que no es utilizado
     *
     * @param id
     * @return Imparten
     */
    public Imparten get(int id, Session session) {
        return (Imparten) session.get(Imparten.class, id);
    }

    /**
     * Método que devuelve todda la tabla Imparten en un ArrayList
     *
     * @return List<Imparten>
     */

    public List<Imparten> getAll(Session session) {

        return (List<Imparten>) session.createQuery("from Imparten").list();
    }

    public void deleteAll(Session sesion) {
        List<Imparten> lista = getAll(sesion);
        for (Imparten imparten : lista)
            delete(imparten, sesion);
    }

    /**
     * Método que inserta por batch los datos de un archivo json con la estructura
     * de datos de Imparten en la base de datos.
     *
     * @param con
     * @param path Ubicación del fichero .json
     */
    public void insertarLote(Session con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("imparten");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    Imparten imparten = new Imparten();
                    imparten.setAlumno(new AlumnosService().findById(Integer.parseInt(jsonObject.get("alumno").toString())));
                    imparten.setAsignatura(new AsignaturasService().findById(Integer.parseInt(jsonObject.get("asignatura").toString())));
                    imparten.setProfesor(new ProfesoresService().findById(Integer.parseInt(jsonObject.get("profesor").toString())));
                    imparten.setCurso(jsonObject.get("curso").toString());
                    save(imparten, con);
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
        } catch (DateTimeException e) {
            errores.muestraErrorDate(e);
        } catch (FileNotFoundException | ParseException e1) {
            errores.mostrar(e1.getMessage());
        } catch (IOException e1) {
            errores.muestraErrorIO(e1);
        } catch (Exception e) {
            errores.muestraError(e);
        }

    }

    /**
     * Método que permite exportar la tabla imparten en un fichero de formato .json
     *
     * @param con
     * @param path Ubicación donde se quiere guardar el fichero
     */
    public void exportar(Session con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Imparten> list = this.getAll(con);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("curso", item.getCurso());
            obj.put("alumno", item.getAlumnoExp());
            obj.put("profesor", item.getProfesorID());
            obj.put("asignatura", item.getAsignaturaID());
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
