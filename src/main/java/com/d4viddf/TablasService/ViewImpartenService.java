package com.d4viddf.TablasService;

import com.d4viddf.Factory.SesionFactory;
import com.d4viddf.Tablas.ViewImparten;
import com.d4viddf.TablasDAO.ViewImpartenDAO;

import java.util.List;

public class ViewImpartenService {
    private ViewImpartenDAO viewImpartenDAO;
    private static SesionFactory sesionFactory;
    public ViewImpartenService() {
        viewImpartenDAO = new ViewImpartenDAO();
        sesionFactory=SesionFactory.getSesionFactory();
    }
    public List<ViewImparten> findAll() {
        sesionFactory.openCurrentSession();
        List<ViewImparten> alumnos = viewImpartenDAO.getAll(sesionFactory.getCurrentSession());
        sesionFactory.closeCurrentSession();
        return alumnos;
    }
}
