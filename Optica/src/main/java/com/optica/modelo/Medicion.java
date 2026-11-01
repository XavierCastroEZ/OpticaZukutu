/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.modelo;

import java.sql.Date;

public class Medicion {

    private int idMedicion;
    private int idCliente;
    private Date fecha;
    private double esfera;
    private double cilindro;
    private int eje;
    private double adicion;

    public Medicion() {
    }

    public Medicion(int idMedicion, int idCliente, Date fecha, double esfera, double cilindro, int eje, double adicion) {
        this.idMedicion = idMedicion;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.esfera = esfera;
        this.cilindro = cilindro;
        this.eje = eje;
        this.adicion = adicion;
    }

    public int getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(int idMedicion) {
        this.idMedicion = idMedicion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getEsfera() {
        return esfera;
    }

    public void setEsfera(double esfera) {
        this.esfera = esfera;
    }

    public double getCilindro() {
        return cilindro;
    }

    public void setCilindro(double cilindro) {
        this.cilindro = cilindro;
    }

    public int getEje() {
        return eje;
    }

    public void setEje(int eje) {
        this.eje = eje;
    }

    public double getAdicion() {
        return adicion;
    }

    public void setAdicion(double adicion) {
        this.adicion = adicion;
    }
}
