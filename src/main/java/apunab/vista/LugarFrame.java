package apunab.vista;

import apunab.dao.LugarDAO;
import apunab.modelo.Lugar;
import apunab.util.EstiloUNAB;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * CRUD completo para la entidad Lugar
 * Sistema APUNAB - UNAB
 */
public class LugarFrame extends JFrame {

    private final LugarDAO dao = new LugarDAO();

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Formulario
    private JTextField txtNombre, txtCiudad, txtBuscar;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbTipo;
    private JCheckBox chkActivo;
    private JLabel lblIdOculto;

    // Botones
    private JButton btnNuevo, btnGuardar, btnEliminar, btnCancelar, btnBuscar;

    private boolean modoEdicion = false;

    public LugarFrame() {
        initComponents();
        cargarTabla(dao.listarTodos());
    }

    private void initComponents() {
        setTitle("Gestión de Lugares - APUNAB UNAB");
        setSize(1000, 640);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(EstiloUNAB.VERDE_FONDO);

        // ── ENCABEZADO ────────────────────────────────────────────────────────
        add(EstiloUNAB.crearPanelEncabezado(
            "📍  Gestión de Lugares",
            "Administra los lugares donde se realizan los eventos de apuestas"), BorderLayout.NORTH);

        // ── BARRA DE HERRAMIENTAS ─────────────────────────────────────────────
        JPanel barraHerr = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraHerr.setBackground(EstiloUNAB.BLANCO);
        barraHerr.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloUNAB.VERDE_BORDE));

        btnNuevo    = EstiloUNAB.crearBotonPrimario("+ Nuevo Lugar");
        btnGuardar  = EstiloUNAB.crearBotonPrimario("💾 Guardar");
        btnEliminar = EstiloUNAB.crearBotonPeligro("🗑 Eliminar");
        btnCancelar = EstiloUNAB.crearBotonSecundario("✖ Cancelar");
        btnBuscar   = EstiloUNAB.crearBotonSecundario("🔍 Buscar");

        txtBuscar = EstiloUNAB.crearCampoTexto(20);
        txtBuscar.setPreferredSize(new Dimension(200, 36));
        txtBuscar.setToolTipText("Buscar por nombre o ciudad...");

        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        btnEliminar.setEnabled(false);

        barraHerr.add(btnNuevo);
        barraHerr.add(btnGuardar);
        barraHerr.add(btnEliminar);
        barraHerr.add(btnCancelar);
        barraHerr.add(Box.createHorizontalStrut(20));
        barraHerr.add(new JLabel("Buscar:"));
        barraHerr.add(txtBuscar);
        barraHerr.add(btnBuscar);

        // ── PANEL CENTRAL (tabla + formulario) ────────────────────────────────
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabla(), crearPanelFormulario());
        splitPane.setDividerLocation(580);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        splitPane.setBackground(EstiloUNAB.VERDE_FONDO);

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(barraHerr, BorderLayout.NORTH);
        centro.add(splitPane, BorderLayout.CENTER);
        centro.setBackground(EstiloUNAB.VERDE_FONDO);

        add(centro, BorderLayout.CENTER);

        // ── PIE ───────────────────────────────────────────────────────────────
        JLabel lblPie = new JLabel("  © 2026 UNAB – Sistema APUNAB  |  Total registros: " + dao.contarTodos());
        lblPie.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lblPie.setForeground(new Color(0x888888));
        lblPie.setBorder(new EmptyBorder(4, 8, 4, 8));
        lblPie.setBackground(EstiloUNAB.BLANCO);
        lblPie.setOpaque(true);
        add(lblPie, BorderLayout.SOUTH);

        registrarEventos();
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 10, 10, 5),
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE)
        ));

        String[] columnas = {"ID", "Nombre", "Ciudad", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        EstiloUNAB.estilizarTabla(tabla);

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(80);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JLabel lblTabla = new JLabel("  Lista de Lugares");
        lblTabla.setFont(EstiloUNAB.FUENTE_SUBTITULO);
        lblTabla.setForeground(EstiloUNAB.VERDE_OSCURO);
        lblTabla.setBorder(new EmptyBorder(10, 10, 8, 10));

        panel.add(lblTabla, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 5, 10, 10),
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE)
        ));

        JLabel lblForm = new JLabel("  Detalle del Lugar");
        lblForm.setFont(EstiloUNAB.FUENTE_SUBTITULO);
        lblForm.setForeground(EstiloUNAB.VERDE_OSCURO);
        lblForm.setBorder(new EmptyBorder(10, 10, 8, 10));

        // Campos
        lblIdOculto = new JLabel("0");
        txtNombre = EstiloUNAB.crearCampoTexto(20);
        txtCiudad = EstiloUNAB.crearCampoTexto(20);
        txtDescripcion = EstiloUNAB.crearAreaTexto(4, 20);
        cmbTipo = new JComboBox<>(new String[]{"Estadio", "Cancha", "Coliseo", "Virtual", "Complejo", "Sala", "Otro"});
        cmbTipo.setFont(EstiloUNAB.FUENTE_NORMAL);
        chkActivo = new JCheckBox("Lugar activo");
        chkActivo.setFont(EstiloUNAB.FUENTE_NORMAL);
        chkActivo.setSelected(true);
        chkActivo.setBackground(EstiloUNAB.BLANCO);

        // Layout formulario
        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBackground(EstiloUNAB.BLANCO);
        campos.setBorder(new EmptyBorder(10, 16, 10, 16));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(5, 0, 5, 0);
        g.weightx = 1;

        agregarCampo(campos, g, 0, "Nombre del Lugar *", txtNombre);
        agregarCampo(campos, g, 1, "Ciudad *", txtCiudad);
        agregarCampoComp(campos, g, 2, "Tipo", cmbTipo);
        agregarCampo(campos, g, 3, "Descripción", new JScrollPane(txtDescripcion) {{
            setPreferredSize(new Dimension(0, 80));
        }});
        g.gridy = 8; g.gridx = 0;
        campos.add(chkActivo, g);

        setFormularioHabilitado(false);

        panel.add(lblForm, BorderLayout.NORTH);
        panel.add(new JScrollPane(campos) {{
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(EstiloUNAB.BLANCO);
        }}, BorderLayout.CENTER);

        return panel;
    }

    private void agregarCampo(JPanel p, GridBagConstraints g, int fila, String etiqueta, Component campo) {
        g.gridy = fila * 2; g.gridx = 0;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lbl.setForeground(EstiloUNAB.GRIS_TEXTO);
        p.add(lbl, g);
        g.gridy = fila * 2 + 1;
        p.add(campo, g);
    }

    private void agregarCampoComp(JPanel p, GridBagConstraints g, int fila, String etiqueta, JComboBox<?> combo) {
        g.gridy = fila * 2; g.gridx = 0;
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lbl.setForeground(EstiloUNAB.GRIS_TEXTO);
        p.add(lbl, g);
        g.gridy = fila * 2 + 1;
        p.add(combo, g);
    }

    private void registrarEventos() {
        btnNuevo.addActionListener(e -> nuevoRegistro());
        btnGuardar.addActionListener(e -> guardarRegistro());
        btnEliminar.addActionListener(e -> eliminarRegistro());
        btnCancelar.addActionListener(e -> cancelar());
        btnBuscar.addActionListener(e -> buscar());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buscar();
            }
        });

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarEnFormulario();
                btnEliminar.setEnabled(true);
            }
        });
    }

    private void nuevoRegistro() {
        modoEdicion = false;
        limpiarFormulario();
        setFormularioHabilitado(true);
        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
        btnNuevo.setVisible(false);
        tabla.clearSelection();
        txtNombre.requestFocus();
    }

    private void guardarRegistro() {
        if (!validarFormulario()) return;

        Lugar lugar = new Lugar();
        lugar.setId(Integer.parseInt(lblIdOculto.getText()));
        lugar.setNombre(txtNombre.getText().trim());
        lugar.setCiudad(txtCiudad.getText().trim());
        lugar.setDescripcion(txtDescripcion.getText().trim());
        lugar.setTipo((String) cmbTipo.getSelectedItem());
        lugar.setActivo(chkActivo.isSelected());

        boolean ok = modoEdicion ? dao.actualizar(lugar) : dao.insertar(lugar);
        if (ok) {
            EstiloUNAB.mostrarExito(this, modoEdicion ? "Lugar actualizado correctamente." : "Lugar registrado correctamente.");
            cargarTabla(dao.listarTodos());
            cancelar();
        } else {
            EstiloUNAB.mostrarError(this, "No se pudo guardar el registro.");
        }
    }

    private void eliminarRegistro() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (EstiloUNAB.confirmar(this, "¿Eliminar el lugar seleccionado?")) {
            if (dao.eliminar(id)) {
                EstiloUNAB.mostrarExito(this, "Lugar eliminado.");
                cargarTabla(dao.listarTodos());
                cancelar();
            } else {
                EstiloUNAB.mostrarError(this, "No se pudo eliminar.");
            }
        }
    }

    private void cancelar() {
        limpiarFormulario();
        setFormularioHabilitado(false);
        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        btnNuevo.setVisible(true);
        btnEliminar.setEnabled(false);
        modoEdicion = false;
    }

    private void buscar() {
        String texto = txtBuscar.getText().trim();
        List<Lugar> resultado = texto.isEmpty() ? dao.listarTodos() : dao.buscarPorNombre(texto);
        cargarTabla(resultado);
    }

    private void cargarTabla(List<Lugar> lista) {
        modeloTabla.setRowCount(0);
        for (Lugar l : lista) {
            modeloTabla.addRow(new Object[]{
                l.getId(), l.getNombre(), l.getCiudad(),
                l.getTipo(), l.isActivo() ? "✔ Activo" : "✖ Inactivo"
            });
        }
    }

    private void cargarEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        Lugar l = dao.buscarPorId(id);
        if (l == null) return;

        modoEdicion = true;
        lblIdOculto.setText(String.valueOf(l.getId()));
        txtNombre.setText(l.getNombre());
        txtCiudad.setText(l.getCiudad());
        txtDescripcion.setText(l.getDescripcion());
        cmbTipo.setSelectedItem(l.getTipo());
        chkActivo.setSelected(l.isActivo());
        setFormularioHabilitado(true);
        btnGuardar.setVisible(true);
        btnCancelar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    private boolean validarFormulario() {
        if (txtNombre.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "El nombre del lugar es obligatorio.");
            txtNombre.requestFocus(); return false;
        }
        if (txtCiudad.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "La ciudad es obligatoria.");
            txtCiudad.requestFocus(); return false;
        }
        return true;
    }

    private void limpiarFormulario() {
        lblIdOculto.setText("0");
        txtNombre.setText("");
        txtCiudad.setText("");
        txtDescripcion.setText("");
        cmbTipo.setSelectedIndex(0);
        chkActivo.setSelected(true);
    }

    private void setFormularioHabilitado(boolean habilitado) {
        txtNombre.setEnabled(habilitado);
        txtCiudad.setEnabled(habilitado);
        txtDescripcion.setEnabled(habilitado);
        cmbTipo.setEnabled(habilitado);
        chkActivo.setEnabled(habilitado);
    }
}
