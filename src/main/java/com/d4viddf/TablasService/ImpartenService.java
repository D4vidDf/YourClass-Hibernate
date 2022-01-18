package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.Imparten;
import com.d4viddf.TablasDAO.ImpartenDAO;

import java.util.List;

public class ImpartenService {
    private ImpartenDAO impartenDAO;
    private static SesionFactory sesionFactory;
    public ImpartenService() {
        impartenDAO = new ImpartenDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public void save(Imparten entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        impartenDAO.save(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void persist(Imparten entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        impartenDAO.persist(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void update(Imparten entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        impartenDAO.update(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public Imparten findById(int id1) {
        sesionFactory.openCurrentSession();
        Imparten book = impartenDAO.get(id1, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return book;
    }
    public void delete(int id3) {
        sesionFactory.openCurrentSessionwithTransaction();
        Imparten book = impartenDAO.get(id3, sesionFactory.getCurrentSession());
        impartenDAO.delete(book, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public List<Imparten> findAll() {
        sesionFactory.openCurrentSession();
        List<Imparten> alumnos = impartenDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return alumnos;
    }
    public void deleteAll() {
        sesionFactory.openCurrentSessionwithTransaction();
        impartenDAO.deleteAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }

    public void insertAll(String path) throws Exception {
        sesionFactory.openCurrentSessionwithTransaction();
        impartenDAO.insertarLote(sesionFactory.getCurrentSession(), path);
        sesionFactory.closeCurrentSessionwithTransaction();
    }
}
