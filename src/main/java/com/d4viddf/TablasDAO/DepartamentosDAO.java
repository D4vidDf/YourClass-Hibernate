package com.d4viddf.TablasDAO;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.d4viddf.Error.Errores;
import com.d4viddf.Tablas.Departamentos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class DepartamentosDAO{
    private static FileWriter file;
    Errores errores = new Errores();
    static Scanner teclado = new Scanner(System.in);

    
    /** 
     * Método que devuelve el departamento que coincide con el ID de Departamentos que se le pasa
     * @param con
     * @param id ID de departamento
     * @return Departamentos
     */
    public Departamentos get(Connection con, int id) {
        Departamentos departamentos = new Departamentos();
        try {
            PreparedStatement s = con.prepareStatement("select * from departamentos where id = ?");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            rs.next();
            departamentos.setId(rs.getInt(1));
            departamentos.setNombre(rs.getString(2));
            departamentos.setPresupuesto(rs.getInt(3));
            departamentos.setDesc(rs.getString(4));
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return departamentos;
    }

    
    /** 
     * Método que devuelve un ArrayList de Departamentos de toda la tabla Departamentos
     * @param con
     * @return List<Departamentos>
     */
    public List<Departamentos> getAll(Connection con) {
        List <Departamentos> lista = new ArrayList<>();
        try {
            Statement s =  con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery("SELECT * FROM departamentos;");
            int totalRows = 0;
            rs.last();
            totalRows = rs.getRow();
            rs.beforeFirst();
            lista = new ArrayList<Departamentos>(totalRows);
            while(rs.next()) {
                Departamentos as = new Departamentos();
                as.setId(rs.getInt(1));
                as.setNombre(rs.getString(2));
                as.setPresupuesto(rs.getFloat(3));
                as.setDesc(rs.getString(4));
                lista.add(as);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    
    /** 
     * Método que devuelve un ArrayList de los Departamentos que coincidan con el presupuesto introducido
     * @param con
     * @param presupuesto Presupuesto
     * @return List<Departamentos>
     */
    public List<Departamentos> getByPresupuesto(Connection con, int presupuesto){
        List <Departamentos> lista = new ArrayList<>();
        try {
            PreparedStatement s = con.prepareStatement("select * from departamentos where presupuesto = ?");
            s.setInt(1, presupuesto);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                Departamentos as = new Departamentos();
                as.setId(rs.getInt(1));
                as.setNombre(rs.getString(2));
                as.setPresupuesto(rs.getFloat(3));
                as.setDesc(rs.getString(4));
                lista.add(as);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    
    /** 
     * Método que añadde por batch en la tabla Departamentos con la información de un fichero JSON que se le pasa
     * @param con
     * @param path Ubicación del ficherp
     */
    public void insertarLote (Connection con,String path) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader file = new FileReader(path);
            org.json.simple.JSONObject obj = (org.json.simple.JSONObject) jsonParser.parse(file);
            PreparedStatement ps = con.prepareStatement("INSERT INTO departamentos (id, nombre, presupuesto, descripcion) VALUES (?, ?, ?,?);" , ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) obj.get("departamentos");

            jsonArray.forEach(alm -> {
                org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) ((org.json.simple.JSONObject) alm);
                try {
                    ps.setInt(1, Integer.parseInt(jsonObject.get("id").toString()));
                    ps.setString(2, jsonObject.get("nombre").toString());
                    ps.setInt(3, Integer.parseInt(jsonObject.get("presupuesto").toString()));
                    ps.setString(4, jsonObject.get("descripcion").toString());
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
     * Método que permite insertar un único Departamento 
     * @param con
     * @param id
     * @param nombre
     * @param presupuesto
     * @param desc
     */
    public void insertar(Connection con, int id, String nombre, int presupuesto, String desc) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO Departamentos (id, nombre, presupuesto, descripcion) VALUES (?,?, ?, ?);");
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setInt(3, presupuesto);
            ps.setString(4, desc);
            ps.execute();
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
    }

    /**
     * Método para exportar los datos de la tabla Departamentos en un archivo de formato
     * json.
     * 
     * @param con
     * @param path
     */
    public void exportar(Connection con, String path) {
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
     * @param con
     * @param string Nombre
     * @return List<Departamentos>
     */
    public List<Departamentos> getByName(Connection con, String string) {
        List <Departamentos> lista = new ArrayList<>();
        try {
            PreparedStatement s = con.prepareStatement("select * from departamentos where nombre like ?");
            s.setString(1, string);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                Departamentos de = new Departamentos();
                de.setId(rs.getInt(1));
                de.setNombre(rs.getString(2));
                de.setPresupuesto(rs.getFloat(3));
                de.setDesc(rs.getString(4));
                lista.add(de);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }

    
    /** 
     * Método que devuelve los departamentos que tenga un profesor.
     * @param con
     * @param string Cod_profesor del que se quiere obtener los departamentos
     * @return List<Departamentos>
     */
    public List<Departamentos> getByProfesor(Connection con, String string) {
        List <Departamentos> lista = new ArrayList<>();
        try {
            PreparedStatement s = con.prepareStatement("select * from departamentos d inner join profesores p on p.departamentos=d.id where p.cod_prof like ?");
            s.setString(1, string);
            ResultSet rs = s.executeQuery();
            while(rs.next()) {
                Departamentos de = new Departamentos();
                de.setId(rs.getInt(1));
                de.setNombre(rs.getString(2));
                de.setPresupuesto(rs.getFloat(3));
                de.setDesc(rs.getString(4));
                lista.add(de);
            }
        } catch (SQLException e) {
            errores.muestraErrorSQL(e);
        }
        return lista;
    }
    
}
