/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarUsuario(String usuario, String contrasena) {
        Usuario u = null;

        String sql = "SELECT U.*, C.IdCliente FROM Usuarios U "
                + "LEFT JOIN Clientes C ON U.IdUsuario = C.IdUsuario "
                + "WHERE U.Usuario = ? AND U.Contrasena = ?";
        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setUsuario(rs.getString("Usuario"));
                u.setContrasena(rs.getString("Contrasena"));
                u.setRol(rs.getString("Rol"));
                u.setIdCliente(rs.getInt("IdCliente"));
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }

        return u;
    }
}