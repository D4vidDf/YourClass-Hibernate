package com.d4viddf.Tablas;

import javax.persistence.*;

@Entity
@Table (name = "imparten")
@IdClass(ImpartenId.class)
public class Imparten {
    @Id
    @ManyToOne(targetEntity = Profesores.class)
    @JoinColumn(name = "profesor", nullable = false)
    public Profesores profesor;
    @Id
    @ManyToOne(targetEntity = Asignaturas.class)
    @JoinColumn(name = "asignatura", nullable = false)
    public Asignaturas asignatura;
    @Id
    @ManyToOne(targetEntity = Alumnos.class)
    @JoinColumn(name = "alumno", nullable = false)
    public Alumnos alumno;
    @Id
    public String curso;

    public Imparten() {
    }

    public Imparten(String curso, Profesores profesor, Asignaturas asignatura, Alumnos alumno) {
        this.curso = curso;
        this.profesor = profesor;
        this.asignatura = asignatura;
        this.alumno = alumno;
    }


    /**
     * @return String
     */
    public String getCurso() {
        return this.curso;
    }


    /**
     * @param curso
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }


    /**
     * @return int
     */
    public Profesores getProfesor() {
        return this.profesor;
    }


    /**
     * @param profesor
     */
    public void setProfesor(Profesores profesor) {
        this.profesor = profesor;
    }


    /**
     * @return int
     */
    public Asignaturas getAsignatura() {
        return this.asignatura;
    }


    /**
     * @param asignatura
     */
    public void setAsignatura(Asignaturas asignatura) {
        this.asignatura = asignatura;
    }


    /**
     * @return int
     */
    public Alumnos getAlumno() {
        return this.alumno;
    }


    /**
     * @param alumno
     */
    public void setAlumno(Alumnos alumno) {
        this.alumno = alumno;
    }

    public int  getAlumnoExp (){
        return alumno.getExpediente();
    }
    public int  getProfesorID (){
        return profesor.getCod_prof();
    }
    public int  getAsignaturaID (){
        return asignatura.getId();
    }
}
