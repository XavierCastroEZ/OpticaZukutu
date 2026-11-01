/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO {

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO Productos (Nombre, Tipo, Marca, Material, Forma, Precio, Stock) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getMaterial());
            ps.setString(5, p.getForma());
            ps.setDouble(6, p.getPrecio());
            ps.setInt(7, p.getStock());

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Producto> listar() {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("IdProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setTipo(rs.getString("Tipo"));
                p.setMarca(rs.getString("Marca"));
                p.setMaterial(rs.getString("Material"));
                p.setForma(rs.getString("Forma"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                lista.add(p);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public boolean actualizar(Producto p) {
        String sql = "UPDATE Productos SET Nombre=?, Tipo=?, Marca=?, Material=?, Forma=?, Precio=?, Stock=? WHERE IdProducto=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getMarca());
            ps.setString(4, p.getMaterial());
            ps.setString(5, p.getForma());
            ps.setDouble(6, p.getPrecio());
            ps.setInt(7, p.getStock());
            ps.setInt(8, p.getIdProducto());

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Productos WHERE IdProducto=?";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ps.executeUpdate();
            con.close();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Producto> listarAros() {
        ArrayList<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos WHERE Tipo='ARO'";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("IdProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setTipo(rs.getString("Tipo"));
                p.setMarca(rs.getString("Marca"));
                p.setMaterial(rs.getString("Material"));
                p.setForma(rs.getString("Forma"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                lista.add(p);
            }

            con.close();

        } catch (SQLException e) {
            System.out.println("Error al listar aros: " + e.getMessage());
        }

        return lista;
    }
}
