/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Apartado;
import java.sql.*;
import java.util.ArrayList;

public class ApartadoDAO {

    public boolean insertar(Apartado a) {
        String sql = "INSERT INTO Apartados (IdCliente, IdProducto, Fecha, Estado) VALUES (?, ?, ?, ?)";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, a.getIdCliente());
            ps.setInt(2, a.getIdProducto());
            ps.setDate(3, a.getFecha());
            ps.setString(4, a.getEstado());

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar apartado: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Apartado> listarPorCliente(int idCliente) {
        ArrayList<Apartado> lista = new ArrayList<>();
        String sql = "SELECT * FROM Apartados WHERE IdCliente=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Apartado a = new Apartado();
                a.setIdApartado(rs.getInt("IdApartado"));
                a.setIdCliente(rs.getInt("IdCliente"));
                a.setIdProducto(rs.getInt("IdProducto"));
                a.setFecha(rs.getDate("Fecha"));
                a.setEstado(rs.getString("Estado"));
                lista.add(a);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar apartados: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizarEstado(int idApartado, String estado) {
        String sql = "UPDATE Apartados SET Estado=? WHERE IdApartado=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, estado);
            ps.setInt(2, idApartado);

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar apartado: " + e.getMessage());
            return false;
        }
    }
}
