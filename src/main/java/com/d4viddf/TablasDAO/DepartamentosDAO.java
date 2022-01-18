package com.d4viddf.TablasDAO;

import com.d4viddf.Connections.HibernateUtil;
import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Departamentos;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartamentosDAO implements DAO<Departamentos>{
    private static FileWriter file;
    Errores errores = new Errores();
    static Scanner teclado = new Scanner(System.in);

    public void save(Departamentos entity, Session session) {
        session.save(entity);
    }

    public void persist(Departamentos entity,
                        Session session) {
        session.persist(entity);
    }

    public void update(Departamentos entity, Session
            session) {
        session.update(entity);
    }

    public void delete(Departamentos entity, Session
            session) {
        session.delete(entity);
    }

    /**
     * Método que devuelve el departamento que coincide con el ID de Departamentos que se le pasa
     *
     * @param id ID de departamento
     * @return Departamentos
     */
    public Departamentos get(int id, Session session) {

        return (Departamentos) session.get(Departamentos.class, id);
    }


    /**
     * Método que devuelve un ArrayList de Departamentos de toda la tabla Departamentos
     *
     * @param con
     * @return List<Departamentos>
     */
    public List<Departamentos> getAll(Session con) {

        return (List<Departamentos>) con.createQuery("from Departamentos ").list();
    }


    public void deleteAll(Session sesion) {
        List<Departamentos> lista = getAll(sesion);
        for (Departamentos departamentos : lista)
            delete(departamentos, sesion);
    }
    /**
     * Método que devuelve un ArrayList de los Departamentos que coincidan con el presupuesto introducido
     *
     * @param presupuesto Presupuesto
     * @return List<Departamentos>
     */
    public List<Departamentos> getByPresupuesto(float presupuesto) {
        List<Departamentos> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("FROM Departamentos where presupuesto = :q")
                    .setParameter("q", presupuesto).
                    setReadOnly(true);
            lista = (List<Departamentos>) q.getResultList();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }


    /**
     * Método que añadde por batch en la tabla Departamentos con la información de un fichero JSON que se le pasa
     *
     * @param con
     * @param path Ubicación del ficherp
     */
    public void insertarLote(Session con, String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("departamentos");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    Departamentos departamentos = new Departamentos();
                    departamentos.setDesc(jsonObject.get("descripcion").toString());
                    departamentos.setPresupuesto(Integer.parseInt(jsonObject.get("presupuesto").toString()));
                    departamentos.setNombre(jsonObject.get("nombre").toString());
                    departamentos.setId(Integer.parseInt(jsonObject.get("id").toString()));
                    save(departamentos, con);
                } catch (Exception e) {
                    errores.muestraError(e);
                }
            });
        } catch (Exception e) {
            errores.muestraError(e);
        }

    }




    /**
     * Método para exportar los datos de la tabla Departamentos en un archivo de formato
     * json.
     *
     * @param con
     * @param path
     */
    public void exportar(Session con, String path) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        List<Departamentos> list = this.getAll(con);
        list.forEach(item -> {
            JSONObject obj = new JSONObject();
            obj.put("id", item.getId());
            obj.put("nombre", item.getNombre());
            obj.put("presupuesto", item.getPresupuesto());
            obj.put("descripcion", item.getDesc());
            jsonArr.put(obj);
        });
        jsonObject.put("departamentos", jsonArr);

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
     * Método que devuelve un ArrayList de los Departamentos que coincidan con el nombre a buscar
     *
     * @param string Nombre
     * @return List<Departamentos>
     */
    public List<Departamentos> getByName(String string) {
        List<Departamentos> lista = new ArrayList<>();
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query q = s.createQuery("FROM Departamentos where nombre like :q")
                    .setParameter("q", "%"+string+"%")
                    .setReadOnly(true);
            lista = (List<Departamentos>) q.getResultList();
        } catch (Exception e) {
            errores.muestraError(e);
        }
        return lista;
    }



}
