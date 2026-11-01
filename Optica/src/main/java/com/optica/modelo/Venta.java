/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.modelo;

import java.sql.Date;
public class Venta {

    private int idVenta;
    private int idCliente;
    private int idUsuario;
    private int idMedicion;
    private Date fecha;
    private double subtotal;
    private double impuesto;
    private double total;

    public Venta() {
    }

    public Venta(int idVenta, int idCliente, int idUsuario, int idMedicion, Date fecha, double subtotal, double impuesto, double total) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.idMedicion = idMedicion;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.impuesto = impuesto;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(int idMedicion) {
        this.idMedicion = idMedicion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}