package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.Alumnos;
import com.d4viddf.TablasDAO.AlumnosDAO;

import java.util.List;

public class AlumnosService {
    private AlumnosDAO alumnosDAO;
    private static SesionFactory sesionFactory;
    public AlumnosService() {
        alumnosDAO = new AlumnosDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public void save(Alumnos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        alumnosDAO.save(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void persist(Alumnos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        alumnosDAO.persist(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void update(Alumnos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        alumnosDAO.update(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public Alumnos findById(int id1) {
        sesionFactory.openCurrentSession();
        Alumnos book = alumnosDAO.get(id1, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return book;
    }
    public void delete(int id3) {
        sesionFactory.openCurrentSessionwithTransaction();
        Alumnos book = alumnosDAO.get(id3, sesionFactory.getCurrentSession());
        alumnosDAO.delete(book, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public List<Alumnos> findAll() {
        sesionFactory.openCurrentSession();
        List<Alumnos> alumnos = alumnosDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return alumnos;
    }
    public void deleteAll() {
        sesionFactory.openCurrentSessionwithTransaction();
        alumnosDAO.deleteAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }

    public void insertAll(String path) throws Exception {
        sesionFactory.openCurrentSessionwithTransaction();
        alumnosDAO.insertarLote(sesionFactory.getCurrentSession(), path);
        sesionFactory.closeCurrentSessionwithTransaction();
    }

}
