package com.optica.dao;

import com.optica.conexion.ConexionDB;
import com.optica.modelo.Venta;
import java.sql.*;
import java.util.ArrayList;

public class VentaDAO {

    public boolean registrarVenta(Venta v, ArrayList<Object[]> detalles) {
        Connection con = null;

        try {
            con = ConexionDB.getConexion();
            con.setAutoCommit(false);

            if (detalles == null || detalles.isEmpty()) {
                return false;
            }

            String sqlProducto = "SELECT Precio, Stock FROM Productos WHERE IdProducto=?";
            PreparedStatement psProducto = con.prepareStatement(sqlProducto);

            double subtotal = 0;

            for (Object[] detalle : detalles) {
                int idProducto = Integer.parseInt(detalle[0].toString());
                int cantidad = Integer.parseInt(detalle[2].toString());

                psProducto.setInt(1, idProducto);
                ResultSet rs = psProducto.executeQuery();

                if (!rs.next() || cantidad <= 0 || rs.getInt("Stock") < cantidad) {
                    rs.close();
                    con.rollback();
                    return false;
                }

                subtotal += rs.getDouble("Precio") * cantidad;
                rs.close();
            }

            double impuesto = subtotal * 0.15;
            double total = subtotal + impuesto;

            String sqlVenta = "INSERT INTO Ventas "
                    + "(IdCliente, IdUsuario, IdMedicion, Fecha, Subtotal, Impuesto, Total) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement psVenta = con.prepareStatement(
                    sqlVenta, Statement.RETURN_GENERATED_KEYS);

            psVenta.setInt(1, v.getIdCliente());
            psVenta.setInt(2, v.getIdUsuario());
            psVenta.setInt(3, v.getIdMedicion());
            psVenta.setDate(4, v.getFecha());
            psVenta.setDouble(5, subtotal);
            psVenta.setDouble(6, impuesto);
            psVenta.setDouble(7, total);

            psVenta.executeUpdate();

            ResultSet rsVenta = psVenta.getGeneratedKeys();

            if (!rsVenta.next()) {
                con.rollback();
                return false;
            }

            int idVenta = rsVenta.getInt(1);
            rsVenta.close();

            String sqlDetalle = "INSERT INTO DetalleVenta "
                    + "(IdVenta, IdProducto, Cantidad, Precio, Subtotal) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);

            String sqlStock = "UPDATE Productos SET Stock = Stock - ? "
                    + "WHERE IdProducto = ? AND Stock >= ?";

            PreparedStatement psStock = con.prepareStatement(sqlStock);

            for (Object[] detalle : detalles) {
                int idProducto = Integer.parseInt(detalle[0].toString());
                int cantidad = Integer.parseInt(detalle[2].toString());

                psProducto.setInt(1, idProducto);
                ResultSet rs = psProducto.executeQuery();
                rs.next();

                double precio = rs.getDouble("Precio");
                rs.close();

                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, idProducto);
                psDetalle.setInt(3, cantidad);
                psDetalle.setDouble(4, precio);
                psDetalle.setDouble(5, precio * cantidad);
                psDetalle.executeUpdate();

                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.setInt(3, cantidad);

                if (psStock.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            System.out.println("Venta registrada correctamente");
            return true;

        } catch (SQLException | NumberFormatException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cancelar venta: " + ex.getMessage());
            }

            System.out.println("Error al registrar venta: " + e.getMessage());
            return false;

        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    public ArrayList<Object[]> consultarVentas(int idCliente) {
        ArrayList<Object[]> lista = new ArrayList<>();

        String sql = "SELECT DV.IdProducto, P.Nombre, DV.Cantidad, "
                + "DV.Precio, DV.Subtotal "
                + "FROM Ventas V "
                + "INNER JOIN DetalleVenta DV ON V.IdVenta = DV.IdVenta "
                + "INNER JOIN Productos P ON DV.IdProducto = P.IdProducto "
                + "WHERE V.IdCliente = ? "
                + "ORDER BY V.IdVenta DESC";

        try {
            Connection con = ConexionDB.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("IdProducto"),
                    rs.getString("Nombre"),
                    rs.getInt("Cantidad"),
                    rs.getDouble("Precio"),
                    rs.getDouble("Subtotal")
                });
            }

            rs.close();
            ps.close();
            con.close();

        } catch (SQLException e) {
            System.out.println("Error al consultar ventas: " + e.getMessage());
        }

        return lista;
    }
}