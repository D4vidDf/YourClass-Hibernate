package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.Asignaturas;
import com.d4viddf.TablasDAO.AsignaturasDAO;

import java.util.List;

public class AsignaturasService {
    private AsignaturasDAO asignaturasDAO;
    private static SesionFactory sesionFactory;
    public AsignaturasService() {
        asignaturasDAO = new AsignaturasDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public void save(Asignaturas entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        asignaturasDAO.save(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void persist(Asignaturas entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        asignaturasDAO.persist(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void update(Asignaturas entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        asignaturasDAO.update(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public Asignaturas findById(int id1) {
        sesionFactory.openCurrentSession();
        Asignaturas asignaturas = asignaturasDAO.get(id1, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return asignaturas;
    }
    public void delete(int id3) {
        sesionFactory.openCurrentSessionwithTransaction();
        Asignaturas asignaturas = asignaturasDAO.get(id3, sesionFactory.getCurrentSession());
        asignaturasDAO.delete(asignaturas, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public List<Asignaturas> findAll() {
        sesionFactory.openCurrentSession();
        List<Asignaturas> asignaturas = asignaturasDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return asignaturas;
    }
    public void deleteAll() {
        sesionFactory.openCurrentSessionwithTransaction();
        asignaturasDAO.deleteAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }

    public void insertAll(String path) throws Exception {
        sesionFactory.openCurrentSessionwithTransaction();
        asignaturasDAO.insertarLote(sesionFactory.getCurrentSession(), path);
        sesionFactory.closeCurrentSessionwithTransaction();
    }

}