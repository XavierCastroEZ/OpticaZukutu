/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Medicion;
import java.sql.*;
import java.util.ArrayList;

public class MedicionDAO {

    public boolean insertar(Medicion m) {
        String sql = "INSERT INTO Mediciones "
                + "(IdCliente, Fecha, Esfera, Cilindro, Eje, Adicion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, m.getIdCliente());
            ps.setDate(2, m.getFecha());
            ps.setDouble(3, m.getEsfera());
            ps.setDouble(4, m.getCilindro());
            ps.setInt(5, m.getEje());
            ps.setDouble(6, m.getAdicion());

            ps.executeUpdate();

            ps.close();
            con.close();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar medicion: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Medicion> listarPorCliente(int idCliente) {
        ArrayList<Medicion> lista = new ArrayList<>();

        String sql = "SELECT * FROM Mediciones "
                + "WHERE IdCliente = ? "
                + "ORDER BY Fecha DESC, IdMedicion DESC";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Medicion m = new Medicion();

                m.setIdMedicion(rs.getInt("IdMedicion"));
                m.setIdCliente(rs.getInt("IdCliente"));
                m.setFecha(rs.getDate("Fecha"));
                m.setEsfera(rs.getDouble("Esfera"));
                m.setCilindro(rs.getDouble("Cilindro"));
                m.setEje(rs.getInt("Eje"));
                m.setAdicion(rs.getDouble("Adicion"));

                lista.add(m);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar mediciones: " + e.getMessage());
        }

        return lista;
    }
}