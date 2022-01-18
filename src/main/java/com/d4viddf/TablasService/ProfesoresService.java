package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.Profesores;
import com.d4viddf.TablasDAO.ProfesoresDAO;

import java.util.List;

public class ProfesoresService {
    private ProfesoresDAO profesoresDAO;
    private static SesionFactory sesionFactory;
    public ProfesoresService() {
        profesoresDAO = new ProfesoresDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public void save(Profesores entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        profesoresDAO.save(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void persist(Profesores entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        profesoresDAO.persist(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void update(Profesores entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        profesoresDAO.update(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public Profesores findById(int id1) {
        sesionFactory.openCurrentSession();
        Profesores prof = profesoresDAO.get(id1, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return prof;
    }
    public void delete(int id3) {
        sesionFactory.openCurrentSessionwithTransaction();
        Profesores profesores = profesoresDAO.get(id3, sesionFactory.getCurrentSession());
        profesoresDAO.delete(profesores, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public List<Profesores> findAll() {
        sesionFactory.openCurrentSession();
        List<Profesores> Profesores = profesoresDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return Profesores;
    }
    public void deleteAll() {
        sesionFactory.openCurrentSessionwithTransaction();
        profesoresDAO.deleteAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }

    public void insertAll(String path) throws Exception {
        sesionFactory.openCurrentSessionwithTransaction();
        profesoresDAO.insertarLote(sesionFactory.getCurrentSession(), path);
        sesionFactory.closeCurrentSessionwithTransaction();
    }
}