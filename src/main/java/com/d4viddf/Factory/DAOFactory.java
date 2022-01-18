package com.d4viddf.Factory;

import java.sql.Connection;

import com.d4viddf.Tablas.Imparten;
import com.d4viddf.TablasDAO.AlumnosDAO;
import com.d4viddf.TablasDAO.AsignaturasDAO;
import com.d4viddf.TablasDAO.DepartamentosDAO;
import com.d4viddf.TablasDAO.ImpartenDAO;
import com.d4viddf.TablasDAO.ProfesoresDAO;
import com.d4viddf.TablasDAO.ViewImpartenDAO;

public abstract class DAOFactory {
    public DAOFactory(){
        super();
    }
    public AlumnosDAO getAlumnosDAO(){
        return new AlumnosDAO();
    }
    public ProfesoresDAO getProfesoresDAO(){
        return new ProfesoresDAO();
    }
    public ImpartenDAO getImpartenDAO(){
        return new ImpartenDAO();
    }

    public ViewImpartenDAO getViewImpartenDAO(){
        return new ViewImpartenDAO();
    }

    public DepartamentosDAO getDepartamentosDAO(){
        return new DepartamentosDAO();
    }

    public AsignaturasDAO getAsignaturasDAO(){
        return new AsignaturasDAO();
    }

}