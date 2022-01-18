package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.Departamentos;
import com.d4viddf.TablasDAO.DepartamentosDAO;

import java.util.List;

public class DepartamentosService {
    private DepartamentosDAO departamentosDAO;
    private static SesionFactory sesionFactory;
    public DepartamentosService() {
        departamentosDAO = new DepartamentosDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public void save(Departamentos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        departamentosDAO.save(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void persist(Departamentos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        departamentosDAO.persist(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public void update(Departamentos entity) {
        sesionFactory.openCurrentSessionwithTransaction();
        departamentosDAO.update(entity, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public Departamentos findById(int id1) {
        sesionFactory.openCurrentSession();
        Departamentos book = departamentosDAO.get(id1, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return book;
    }
    public void delete(int id3) {
        sesionFactory.openCurrentSessionwithTransaction();
        Departamentos book = departamentosDAO.get(id3, sesionFactory.getCurrentSession());
        departamentosDAO.delete(book, sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }
    public List<Departamentos> findAll() {
        sesionFactory.openCurrentSession();
        List<Departamentos> departamentos = departamentosDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return departamentos;
    }
    public void deleteAll() {
        sesionFactory.openCurrentSessionwithTransaction();
        departamentosDAO.deleteAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSessionwithTransaction();
    }

    public void insertAll(String path) throws Exception {
        sesionFactory.openCurrentSessionwithTransaction();
        departamentosDAO.insertarLote(sesionFactory.getCurrentSession(), path);
        sesionFactory.closeCurrentSessionwithTransaction();
    }

}
