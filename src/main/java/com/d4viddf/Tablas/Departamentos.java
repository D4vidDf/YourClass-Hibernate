package com.d4viddf.Tablas;

import javax.persistence.*;

@Entity
@Table(name = "departamentos")
public class Departamentos {
    @Id
    public int id;
    @Column(name = "nombre", nullable = false)
    public String nombre;
    @Column(name = "presupuesto", nullable = false)
    public float presupuesto;
    @Column(name = "descripcion")
    public String desc;


    /**
     * @return String
     */
    public String getDesc() {
        return desc;
    }


    /**
     * @param desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }


    /**
     * @return int
     */
    public int getId() {
        return this.id;
    }


    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * @return String
     */
    public String getNombre() {
        return this.nombre;
    }


    /**
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * @return float
     */
    public float getPresupuesto() {
        return this.presupuesto;
    }


    /**
     * @param presupuesto
     */
    public void setPresupuesto(float presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Departamentos(int id, String nombre, float presupuesto, String desc) {
        this.id = id;
        this.nombre = nombre;
        this.presupuesto = presupuesto;
        this.desc = desc;
    }

    public Departamentos() {
    }

}
