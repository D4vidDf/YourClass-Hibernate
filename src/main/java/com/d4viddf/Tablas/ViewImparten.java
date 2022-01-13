package com.d4viddf.Tablas;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
public class ViewImparten {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column
    public int Expedientealumno;
    @Column
    public int CodProf;
    @Column
    public int IDasignatura;
    @Column
    public String CursoImparten;
    @Column
    public String Nombrealumno;
    @Column
    public String Apellidosalumno;
    @Column
    public String DNIalumno;
    @Column
    public String DNIprofesor;
    @Column
    public String NombreProfesor;
    @Column
    public String ApellidosProfesor;
    @Column
    public String Nombredepartamento;
    @Column
    public String Nombreasignatura;
    @Column
    public String Cursoasignatura;

    
    /** 
     * @return int
     */
    public int getExpedientealumno() {
        return this.Expedientealumno;
    }

    
    /** 
     * @param Expedientealumno
     */
    public void setExpedientealumno(int Expedientealumno) {
        this.Expedientealumno = Expedientealumno;
    }

    
    /** 
     * @return int
     */
    public int getCodProf() {
        return this.CodProf;
    }

    
    /** 
     * @param CodProf
     */
    public void setCodProf(int CodProf) {
        this.CodProf = CodProf;
    }

    
    /** 
     * @return int
     */
    public int getIDasignatura() {
        return this.IDasignatura;
    }

    
    /** 
     * @param IDasignatura
     */
    public void setIDasignatura(int IDasignatura) {
        this.IDasignatura = IDasignatura;
    }

    
    /** 
     * @return String
     */
    public String getCursoImparten() {
        return this.CursoImparten;
    }

    
    /** 
     * @param CursoImparten
     */
    public void setCursoImparten(String CursoImparten) {
        this.CursoImparten = CursoImparten;
    }

    
    /** 
     * @return String
     */
    public String getNombrealumno() {
        return this.Nombrealumno;
    }

    
    /** 
     * @param Nombrealumno
     */
    public void setNombrealumno(String Nombrealumno) {
        this.Nombrealumno = Nombrealumno;
    }

    
    /** 
     * @return String
     */
    public String getApellidosalumno() {
        return this.Apellidosalumno;
    }

    
    /** 
     * @param Apellidosalumno
     */
    public void setApellidosalumno(String Apellidosalumno) {
        this.Apellidosalumno = Apellidosalumno;
    }

    
    /** 
     * @return String
     */
    public String getDNIalumno() {
        return this.DNIalumno;
    }

    
    /** 
     * @param DNIalumno
     */
    public void setDNIalumno(String DNIalumno) {
        this.DNIalumno = DNIalumno;
    }

    
    /** 
     * @return String
     */
    public String getDNIprofesor() {
        return this.DNIprofesor;
    }

    
    /** 
     * @param DNIprofesor
     */
    public void setDNIprofesor(String DNIprofesor) {
        this.DNIprofesor = DNIprofesor;
    }

    
    /** 
     * @return String
     */
    public String getNombreProfesor() {
        return this.NombreProfesor;
    }

    
    /** 
     * @param NombreProfesor
     */
    public void setNombreProfesor(String NombreProfesor) {
        this.NombreProfesor = NombreProfesor;
    }

    
    /** 
     * @return String
     */
    public String getApellidosProfesor() {
        return this.ApellidosProfesor;
    }

    
    /** 
     * @param ApellidosProfesor
     */
    public void setApellidosProfesor(String ApellidosProfesor) {
        this.ApellidosProfesor = ApellidosProfesor;
    }

    
    /** 
     * @return String
     */
    public String getNombredepartamento() {
        return this.Nombredepartamento;
    }

    
    /** 
     * @param Nombredepartamento
     */
    public void setNombredepartamento(String Nombredepartamento) {
        this.Nombredepartamento = Nombredepartamento;
    }

    
    /** 
     * @return String
     */
    public String getNombreasignatura() {
        return this.Nombreasignatura;
    }

    
    /** 
     * @param Nombreasignatura
     */
    public void setNombreasignatura(String Nombreasignatura) {
        this.Nombreasignatura = Nombreasignatura;
    }

    
    /** 
     * @return String
     */
    public String getCursoasignatura() {
        return this.Cursoasignatura;
    }

    
    /** 
     * @param Cursoasignatura
     */
    public void setCursoasignatura(String Cursoasignatura) {
        this.Cursoasignatura = Cursoasignatura;
    }

    public ViewImparten(int Expedientealumno, int CodProf, int IDasignatura, String CursoImparten, String Nombrealumno, String Apellidosalumno, String DNIalumno, String DNIprofesor, String NombreProfesor, String ApellidosProfesor, String Nombredepartamento, String Nombreasignatura, String Cursoasignatura) {
        this.Expedientealumno = Expedientealumno;
        this.CodProf = CodProf;
        this.IDasignatura = IDasignatura;
        this.CursoImparten = CursoImparten;
        this.Nombrealumno = Nombrealumno;
        this.Apellidosalumno = Apellidosalumno;
        this.DNIalumno = DNIalumno;
        this.DNIprofesor = DNIprofesor;
        this.NombreProfesor = NombreProfesor;
        this.ApellidosProfesor = ApellidosProfesor;
        this.Nombredepartamento = Nombredepartamento;
        this.Nombreasignatura = Nombreasignatura;
        this.Cursoasignatura = Cursoasignatura;
    }

    public ViewImparten() {
    }

    
}
