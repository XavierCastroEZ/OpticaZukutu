/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.optica.vista;

import com.optica.dao.ProductoDAO;
import com.optica.modelo.Producto;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LENOVO T14
 */
public class FrmInventario extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmInventario.class.getName());

    private boolean modoCliente = false;
    private java.util.ArrayList<Producto> listaAros;
    private javax.swing.JComboBox<String> cboMarca;
    private javax.swing.JComboBox<String> cboMaterial;
    private javax.swing.JComboBox<String> cboForma;
    private javax.swing.JTextField txtPrecioMin;
    private javax.swing.JTextField txtPrecioMax;
    private javax.swing.JButton btnFiltrar;
    private int idCliente;
    private javax.swing.JButton btnApartar;

    /**
     * Creates new form FrmInventario
     */
public FrmInventario() {
    initComponents();
    txtCodigo.setEnabled(false);
    cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(
            new String[]{"ARO", "LUNA", "ACCESORIO"}
    ));
    verificarStockBajo();
}
    public FrmInventario(boolean modoCliente) {
        this(modoCliente, 0);
    }

    public FrmInventario(boolean modoCliente, int idCliente) {
        initComponents();

        this.modoCliente = modoCliente;
        this.idCliente = idCliente;

        if (modoCliente) {
            configurarCatalogo();
        }
    }

private void configurarCatalogo() {

    jLabel1.setText("Catálogo de Aros");

    jLabel2.setVisible(false);
    jLabel3.setVisible(false);
    jLabel4.setVisible(false);
    jLabel5.setVisible(false);
    jLabel6.setVisible(false);
    jLabel7.setVisible(false);
    jLabel8.setVisible(false);

    txtCodigo.setVisible(false);
    txtNombre.setVisible(false);
    txtMarca.setVisible(false);
    txtPrecio.setVisible(false);
    txtExistencia.setVisible(false);
    cboCategoria.setVisible(false);
    txtBuscar.setVisible(false);

    btnNuevo.setVisible(false);
    btnGuardar.setVisible(false);
    btnActualizar.setVisible(false);
    btnEliminar.setVisible(false);
    btnLimpiar.setVisible(false);
    btnConsultar.setVisible(false);

    cboMarca = new javax.swing.JComboBox<>();
    cboMaterial = new javax.swing.JComboBox<>();
    cboForma = new javax.swing.JComboBox<>();

    txtPrecioMin = new javax.swing.JTextField();
    txtPrecioMax = new javax.swing.JTextField();

    btnFiltrar = new javax.swing.JButton("Filtrar");
    btnFiltrar.addActionListener(e -> aplicarFiltros());

    btnApartar = new javax.swing.JButton("Apartar aro seleccionado");
    btnApartar.addActionListener(e -> apartarAro());

    javax.swing.JPanel filtros = new javax.swing.JPanel();

    filtros.add(new javax.swing.JLabel("Marca:"));
    filtros.add(cboMarca);

    filtros.add(new javax.swing.JLabel("Material:"));
    filtros.add(cboMaterial);

    filtros.add(new javax.swing.JLabel("Forma:"));
    filtros.add(cboForma);

    filtros.add(new javax.swing.JLabel("Precio mínimo:"));
    txtPrecioMin.setColumns(7);
    filtros.add(txtPrecioMin);

    filtros.add(new javax.swing.JLabel("Precio máximo:"));
    txtPrecioMax.setColumns(7);
    filtros.add(txtPrecioMax);

    filtros.add(btnFiltrar);

    javax.swing.JPanel panelApartar = new javax.swing.JPanel();
    panelApartar.add(btnApartar);

    javax.swing.JPanel contenido = new javax.swing.JPanel(
            new java.awt.BorderLayout(5, 10));

    contenido.add(filtros, java.awt.BorderLayout.NORTH);
    contenido.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    contenido.add(panelApartar, java.awt.BorderLayout.SOUTH);

    setContentPane(contenido);

    tblInventario.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Código", "Nombre", "Marca", "Material",
                "Forma", "Precio", "Existencia"
            }
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });

    cboMarca.addItem("Todas");
    cboMaterial.addItem("Todos");
    cboForma.addItem("Todas");

    ProductoDAO dao = new ProductoDAO();
    listaAros = dao.listarAros();

    for (Producto p : listaAros) {

        if (p.getMarca() != null && !p.getMarca().trim().isEmpty()
                && !existeOpcion(cboMarca, p.getMarca())) {
            cboMarca.addItem(p.getMarca());
        }

        if (p.getMaterial() != null && !p.getMaterial().trim().isEmpty()
                && !existeOpcion(cboMaterial, p.getMaterial())) {
            cboMaterial.addItem(p.getMaterial());
        }

        if (p.getForma() != null && !p.getForma().trim().isEmpty()
                && !existeOpcion(cboForma, p.getForma())) {
            cboForma.addItem(p.getForma());
        }
    }

    aplicarFiltros();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    
}

private void verificarStockBajo() {

    ProductoDAO dao = new ProductoDAO();
    java.util.ArrayList<Producto> productos = dao.listar();

    String mensaje = "";

    for (Producto p : productos) {
        if (p.getStock() < 5) {
            mensaje += p.getNombre() + " - Existencias: "
                    + p.getStock() + "\n";
        }
    }

    if (!mensaje.isEmpty()) {
        JOptionPane.showMessageDialog(
                this,
                "Productos con stock menor a 5:\n\n" + mensaje,
                "Alerta de inventario",
                JOptionPane.WARNING_MESSAGE
        );
    }
}

    private boolean existeOpcion(javax.swing.JComboBox<String> combo, String valor) {

        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).equalsIgnoreCase(valor)) {
                return true;
            }
        }

        return false;
    }

    private void aplicarFiltros() {

        DefaultTableModel modelo =
                (DefaultTableModel) tblInventario.getModel();

        modelo.setRowCount(0);

        for (Producto p : listaAros) {

            if (p.getStock() <= 0) {
                continue;
            }

            String marca = cboMarca.getSelectedItem().toString();
            String material = cboMaterial.getSelectedItem().toString();
            String forma = cboForma.getSelectedItem().toString();

            if (!marca.equals("Todas")
                    && !marca.equals(p.getMarca())) {
                continue;
            }

            if (!material.equals("Todos")
                    && !material.equals(p.getMaterial())) {
                continue;
            }

            if (!forma.equals("Todas")
                    && !forma.equals(p.getForma())) {
                continue;
            }

            try {
                if (!txtPrecioMin.getText().trim().isEmpty()
                        && p.getPrecio() < Double.parseDouble(txtPrecioMin.getText())) {
                    continue;
                }

                if (!txtPrecioMax.getText().trim().isEmpty()
                        && p.getPrecio() > Double.parseDouble(txtPrecioMax.getText())) {
                    continue;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese valores numéricos para los precios");
                return;
            }

            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getMarca(),
                p.getMaterial(),
                p.getForma(),
                p.getPrecio(),
                p.getStock()
            });
        }
    }

    private void apartarAro() {

        int fila = tblInventario.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un aro de la tabla.");
            return;
        }

        int idProducto = Integer.parseInt(
                tblInventario.getValueAt(fila, 0).toString()
        );

        FrmApartado formulario = new FrmApartado(idCliente, idProducto);
        formulario.setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        txtExistencia = new javax.swing.JTextField();
        cboCategoria = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Inventario");

        jLabel2.setText("Código");
        jLabel2.setPreferredSize(new java.awt.Dimension(39, 25));

        jLabel3.setText("Nombre");
        jLabel3.setPreferredSize(new java.awt.Dimension(75, 25));

        jLabel4.setText("Categoría");
        jLabel4.setPreferredSize(new java.awt.Dimension(52, 25));

        jLabel5.setText("Marca");
        jLabel5.setPreferredSize(new java.awt.Dimension(75, 25));

        jLabel6.setText("Precio");
        jLabel6.setPreferredSize(new java.awt.Dimension(33, 25));

        jLabel7.setText("Existencia");
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 25));

        jLabel8.setText("Buscar");

        txtCodigo.setPreferredSize(new java.awt.Dimension(75, 25));

        txtPrecio.setPreferredSize(new java.awt.Dimension(75, 25));

        txtMarca.addActionListener(this::txtMarcaActionPerformed);

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboCategoria.setMinimumSize(new java.awt.Dimension(72, 25));
        cboCategoria.setPreferredSize(new java.awt.Dimension(72, 25));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(this::btnConsultarActionPerformed);

        tblInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Categoría", "Marca", "Precio", "Existencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInventario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(723, 723, 723)
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(btnNuevo)))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnGuardar)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnActualizar))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEliminar)
                        .addGap(7, 7, 7)
                        .addComponent(btnLimpiar)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConsultar))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        ProductoDAO dao = new ProductoDAO();
        java.util.ArrayList<Producto> lista = dao.listar();

        DefaultTableModel modelo = (DefaultTableModel) tblInventario.getModel();
        modelo.setRowCount(0);

        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getTipo(),
                p.getMarca(),
                p.getPrecio(),
                p.getStock()
            });
}
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
         String nombre = txtNombre.getText();
    String tipo = cboCategoria.getSelectedItem().toString();
    String marca = txtMarca.getText();

    if (nombre.isEmpty() || marca.isEmpty()
            || txtPrecio.getText().isEmpty()
            || txtExistencia.getText().isEmpty()) {

        JOptionPane.showMessageDialog(this, "Complete todos los campos");
        return;
    }

    try {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setTipo(tipo);
        p.setMarca(marca);
        p.setMaterial("");
        p.setForma("");
        p.setPrecio(Double.parseDouble(txtPrecio.getText()));
        p.setStock(Integer.parseInt(txtExistencia.getText()));

        ProductoDAO dao = new ProductoDAO();

        if (dao.insertar(p)) {
            JOptionPane.showMessageDialog(this, "Producto guardado correctamente");
            btnConsultarActionPerformed(evt);
            btnLimpiarActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Precio y existencia deben ser valores numéricos");
    }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        txtCodigo.setText("");
        txtNombre.setText("");
        txtMarca.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
        cboCategoria.setSelectedIndex(0);
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }

        try {
            Producto p = new Producto();
            p.setIdProducto(Integer.parseInt(txtCodigo.getText()));
            p.setNombre(txtNombre.getText());
            p.setTipo(cboCategoria.getSelectedItem().toString());
            p.setMarca(txtMarca.getText());
            p.setMaterial("");
            p.setForma("");
            p.setPrecio(Double.parseDouble(txtPrecio.getText()));
            p.setStock(Integer.parseInt(txtExistencia.getText()));

            ProductoDAO dao = new ProductoDAO();

            if (dao.actualizar(p)) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente");
                btnConsultarActionPerformed(evt);
                btnLimpiarActionPerformed(evt);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el producto");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Verifique los datos numéricos");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (txtCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
            this, "¿Desea eliminar este producto?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtCodigo.getText());
            ProductoDAO dao = new ProductoDAO();

            if (dao.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
                btnConsultarActionPerformed(evt);
                btnLimpiarActionPerformed(evt);
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        txtCodigo.setText("");
        txtNombre.setText("");
        txtMarca.setText("");
        txtPrecio.setText("");
        txtExistencia.setText("");
        cboCategoria.setSelectedIndex(0);
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void tblInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInventarioMouseClicked
        // TODO add your handling code here:
        int fila = tblInventario.getSelectedRow();
        txtCodigo.setText(tblInventario.getValueAt(fila, 0).toString());
        txtNombre.setText(tblInventario.getValueAt(fila, 1).toString());
        cboCategoria.setSelectedItem(tblInventario.getValueAt(fila, 2).toString());
        txtMarca.setText(tblInventario.getValueAt(fila, 3).toString());
        txtPrecio.setText(tblInventario.getValueAt(fila, 4).toString());
        txtExistencia.setText(tblInventario.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tblInventarioMouseClicked

    public static void main(String args[]) {
 
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
