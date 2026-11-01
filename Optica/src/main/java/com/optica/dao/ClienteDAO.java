/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO Clientes (Nombre, DNI, Telefono, Correo, IdUsuario) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setInt(5, c.getIdUsuario());

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Cliente> listar() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("IdCliente"));
                c.setNombre(rs.getString("Nombre"));
                c.setDni(rs.getString("DNI"));
                c.setTelefono(rs.getString("Telefono"));
                c.setCorreo(rs.getString("Correo"));
                c.setIdUsuario(rs.getInt("IdUsuario"));
                lista.add(c);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE Clientes SET Nombre=?, DNI=?, Telefono=?, Correo=? WHERE IdCliente=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDni());
            ps.setString(3, c.getTelefono());
            ps.setString(4, c.getCorreo());
            ps.setInt(5, c.getIdCliente());

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Clientes WHERE IdCliente=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
