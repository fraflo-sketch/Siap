package apunab.vista;

import apunab.dao.ApuestaDAO;
import apunab.dao.LugarDAO;
import apunab.modelo.Apuesta;
import apunab.modelo.Lugar;
import apunab.util.EstiloUNAB;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * CRUD completo para la entidad Apuesta (APUNAB)
 * Sistema APUNAB - UNAB
 */
public class ApuestaFrame extends JFrame {

    private final ApuestaDAO dao = new ApuestaDAO();
    private final LugarDAO lugarDAO = new LugarDAO();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Tabla
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Formulario
    private JTextField txtCodigo, txtNombreEst, txtEvento, txtPronostico, txtMonto, txtBuscar;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbEstado, cmbLugar;
    private JLabel lblIdOculto;

    // Botones
    private JButton btnNuevo, btnGuardar, btnEliminar, btnCancelar, btnBuscar;
    private JComboBox<String> cmbFiltroEstado;

    private boolean modoEdicion = false;

    public ApuestaFrame() {
        initComponents();
        cargarTabla(dao.listarTodas());
    }

    private void initComponents() {
        setTitle("Catálogo de Apuestas - APUNAB UNAB");
        setSize(1150, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(EstiloUNAB.VERDE_FONDO);

        add(EstiloUNAB.crearPanelEncabezado(
            "🎯  Catálogo de Apuestas",
            "Gestiona todas las apuestas de los estudiantes UNAB"), BorderLayout.NORTH);

        // ── BARRA DE HERRAMIENTAS ─────────────────────────────────────────────
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 7));
        barra.setBackground(EstiloUNAB.BLANCO);
        barra.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, EstiloUNAB.VERDE_BORDE));

        btnNuevo    = EstiloUNAB.crearBotonPrimario("+ Nueva Apuesta");
        btnGuardar  = EstiloUNAB.crearBotonPrimario("💾 Guardar");
        btnEliminar = EstiloUNAB.crearBotonPeligro("🗑 Eliminar");
        btnCancelar = EstiloUNAB.crearBotonSecundario("✖ Cancelar");
        btnBuscar   = EstiloUNAB.crearBotonSecundario("🔍 Buscar");

        txtBuscar = EstiloUNAB.crearCampoTexto(18);
        txtBuscar.setPreferredSize(new Dimension(180, 36));
        txtBuscar.setToolTipText("Buscar por nombre, código o evento...");

        cmbFiltroEstado = new JComboBox<>(new String[]{"Todos", "PENDIENTE", "GANADA", "PERDIDA", "CANCELADA"});
        cmbFiltroEstado.setFont(EstiloUNAB.FUENTE_NORMAL);
        cmbFiltroEstado.setPreferredSize(new Dimension(130, 36));

        btnGuardar.setVisible(false);
        btnCancelar.setVisible(false);
        btnEliminar.setEnabled(false);

        barra.add(btnNuevo); barra.add(btnGuardar);
        barra.add(btnEliminar); barra.add(btnCancelar);
        barra.add(Box.createHorizontalStrut(10));
        barra.add(new JLabel("Estado:")); barra.add(cmbFiltroEstado);
        barra.add(Box.createHorizontalStrut(5));
        barra.add(new JLabel("Buscar:")); barra.add(txtBuscar); barra.add(btnBuscar);

        // ── SPLIT PRINCIPAL ───────────────────────────────────────────────────
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                crearPanelTabla(), crearPanelFormulario());
        split.setDividerLocation(640);
        split.setBorder(BorderFactory.createEmptyBorder());

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(barra, BorderLayout.NORTH);
        centro.add(split, BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        // PIE
        JLabel pie = new JLabel("  © 2026 UNAB – APUNAB  |  Total apuestas: " + dao.contarTodas()
            + "  |  Monto total: $" + String.format("%.0f", dao.totalMontos()));
        pie.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        pie.setForeground(new Color(0x888888));
        pie.setBorder(new EmptyBorder(4, 8, 4, 8));
        pie.setBackground(EstiloUNAB.BLANCO); pie.setOpaque(true);
        add(pie, BorderLayout.SOUTH);

        registrarEventos();
    }

    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 10, 10, 5),
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE)));

        String[] cols = {"ID", "Estudiante", "Código", "Evento", "Monto", "Estado", "Puntos"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        EstiloUNAB.estilizarTabla(tabla);

        // Renderer de colores para estado
        tabla.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                String estado = v != null ? v.toString() : "";
                setForeground(sel ? EstiloUNAB.VERDE_OSCURO : EstiloUNAB.colorEstado(estado));
                setFont(EstiloUNAB.FUENTE_BOLD);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (!sel) setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xF5FAF6));
                return this;
            }
        });

        int[] anchos = {35, 160, 85, 180, 70, 90, 60};
        for (int i = 0; i < anchos.length; i++)
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JLabel lbl = new JLabel("  Lista de Apuestas");
        lbl.setFont(EstiloUNAB.FUENTE_SUBTITULO);
        lbl.setForeground(EstiloUNAB.VERDE_OSCURO);
        lbl.setBorder(new EmptyBorder(10, 10, 8, 10));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 5, 10, 10),
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE)));

        JLabel lbl = new JLabel("  Detalle de Apuesta");
        lbl.setFont(EstiloUNAB.FUENTE_SUBTITULO);
        lbl.setForeground(EstiloUNAB.VERDE_OSCURO);
        lbl.setBorder(new EmptyBorder(10, 10, 8, 10));

        lblIdOculto = new JLabel("0");
        txtCodigo = EstiloUNAB.crearCampoTexto(12);
        txtNombreEst = EstiloUNAB.crearCampoTexto(20);
        txtEvento = EstiloUNAB.crearCampoTexto(20);
        txtPronostico = EstiloUNAB.crearCampoTexto(20);
        txtMonto = EstiloUNAB.crearCampoTexto(10);
        txtDescripcion = EstiloUNAB.crearAreaTexto(3, 20);
        cmbEstado = new JComboBox<>(new String[]{"PENDIENTE", "GANADA", "PERDIDA", "CANCELADA"});
        cmbEstado.setFont(EstiloUNAB.FUENTE_NORMAL);

        // Cargar lugares en combo
        List<Lugar> lugares = lugarDAO.listarActivos();
        String[] nombresLugares = new String[lugares.size()];
        for (int i = 0; i < lugares.size(); i++)
            nombresLugares[i] = lugares.get(i).getId() + " - " + lugares.get(i).getNombre();
        cmbLugar = new JComboBox<>(nombresLugares);
        cmbLugar.setFont(EstiloUNAB.FUENTE_NORMAL);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setBackground(EstiloUNAB.BLANCO);
        campos.setBorder(new EmptyBorder(10, 16, 10, 16));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1;

        agregarF(campos, g, 0, "Código Estudiante *", txtCodigo);
        agregarF(campos, g, 1, "Nombre Completo *", txtNombreEst);
        agregarF(campos, g, 2, "Evento *", txtEvento);
        agregarFC(campos, g, 3, "Lugar del Evento *", cmbLugar);
        agregarF(campos, g, 4, "Pronóstico *", txtPronostico);
        agregarF(campos, g, 5, "Monto ($) *", txtMonto);
        agregarF(campos, g, 6, "Descripción", new JScrollPane(txtDescripcion) {{
            setPreferredSize(new Dimension(0, 70));
        }});
        agregarFC(campos, g, 7, "Estado", cmbEstado);

        setFormHabilitado(false);

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(new JScrollPane(campos) {{
            setBorder(BorderFactory.createEmptyBorder());
        }}, BorderLayout.CENTER);
        return panel;
    }

    private void agregarF(JPanel p, GridBagConstraints g, int fila, String etiqueta, Component campo) {
        g.gridy = fila * 2; g.gridx = 0; g.insets = new Insets(4, 0, 1, 0);
        JLabel l = new JLabel(etiqueta); l.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        l.setForeground(EstiloUNAB.GRIS_TEXTO); p.add(l, g);
        g.gridy = fila * 2 + 1; g.insets = new Insets(0, 0, 6, 0); p.add(campo, g);
    }

    private void agregarFC(JPanel p, GridBagConstraints g, int fila, String etiqueta, JComboBox<?> combo) {
        g.gridy = fila * 2; g.gridx = 0; g.insets = new Insets(4, 0, 1, 0);
        JLabel l = new JLabel(etiqueta); l.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        l.setForeground(EstiloUNAB.GRIS_TEXTO); p.add(l, g);
        g.gridy = fila * 2 + 1; g.insets = new Insets(0, 0, 6, 0); p.add(combo, g);
    }

    private void registrarEventos() {
        btnNuevo.addActionListener(e -> nuevoReg());
        btnGuardar.addActionListener(e -> guardarReg());
        btnEliminar.addActionListener(e -> eliminarReg());
        btnCancelar.addActionListener(e -> cancelar());
        btnBuscar.addActionListener(e -> buscar());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buscar();
            }
        });
        cmbFiltroEstado.addActionListener(e -> filtrarPorEstado());
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarEnForm();
                btnEliminar.setEnabled(true);
            }
        });
    }

    private void nuevoReg() {
        modoEdicion = false;
        limpiarForm();
        setFormHabilitado(true);
        btnGuardar.setVisible(true); btnCancelar.setVisible(true);
        btnNuevo.setVisible(false);
        tabla.clearSelection();
        txtCodigo.requestFocus();
    }

    private void guardarReg() {
        if (!validar()) return;
        Apuesta a = new Apuesta();
        a.setId(Integer.parseInt(lblIdOculto.getText()));
        a.setCodigoEstudiante(txtCodigo.getText().trim());
        a.setNombreEstudiante(txtNombreEst.getText().trim());
        a.setEvento(txtEvento.getText().trim());
        a.setDescripcion(txtDescripcion.getText().trim());
        a.setPronostico(txtPronostico.getText().trim());
        a.setEstado((String) cmbEstado.getSelectedItem());
        a.setFechaApuesta(new Date());

        try {
            a.setMonto(Double.parseDouble(txtMonto.getText().trim()));
        } catch (NumberFormatException ex) {
            EstiloUNAB.mostrarError(this, "El monto debe ser un número válido."); return;
        }

        // Extraer lugar del combo
        String itemLugar = (String) cmbLugar.getSelectedItem();
        if (itemLugar != null) {
            int idLugar = Integer.parseInt(itemLugar.split(" - ")[0]);
            a.setLugarId(idLugar);
            a.setLugarNombre(itemLugar.split(" - ", 2)[1]);
        }

        // Calcular puntos si ganada
        if ("GANADA".equals(a.getEstado())) a.setPuntos(a.getMonto() * 3);

        boolean ok = modoEdicion ? dao.actualizar(a) : dao.insertar(a);
        if (ok) {
            EstiloUNAB.mostrarExito(this, modoEdicion ? "Apuesta actualizada." : "Apuesta registrada correctamente.");
            cargarTabla(dao.listarTodas());
            cancelar();
        } else {
            EstiloUNAB.mostrarError(this, "No se pudo guardar la apuesta.");
        }
    }

    private void eliminarReg() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        if (EstiloUNAB.confirmar(this, "¿Eliminar esta apuesta?")) {
            if (dao.eliminar(id)) {
                EstiloUNAB.mostrarExito(this, "Apuesta eliminada.");
                cargarTabla(dao.listarTodas());
                cancelar();
            } else {
                EstiloUNAB.mostrarError(this, "No se pudo eliminar.");
            }
        }
    }

    private void cancelar() {
        limpiarForm(); setFormHabilitado(false);
        btnGuardar.setVisible(false); btnCancelar.setVisible(false);
        btnNuevo.setVisible(true); btnEliminar.setEnabled(false);
        modoEdicion = false;
    }

    private void buscar() {
        String t = txtBuscar.getText().trim();
        cargarTabla(t.isEmpty() ? dao.listarTodas() : dao.buscarGeneral(t));
    }

    private void filtrarPorEstado() {
        String estado = (String) cmbFiltroEstado.getSelectedItem();
        if ("Todos".equals(estado)) cargarTabla(dao.listarTodas());
        else cargarTabla(dao.buscarPorEstado(estado));
    }

    private void cargarTabla(List<Apuesta> lista) {
        modeloTabla.setRowCount(0);
        for (Apuesta a : lista) {
            modeloTabla.addRow(new Object[]{
                a.getId(), a.getNombreEstudiante(), a.getCodigoEstudiante(),
                a.getEvento(), "$" + String.format("%.0f", a.getMonto()),
                a.getEstado(), (int) a.getPuntos()
            });
        }
    }

    private void cargarEnForm() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) modeloTabla.getValueAt(fila, 0);
        Apuesta a = dao.buscarPorId(id);
        if (a == null) return;

        modoEdicion = true;
        lblIdOculto.setText(String.valueOf(a.getId()));
        txtCodigo.setText(a.getCodigoEstudiante());
        txtNombreEst.setText(a.getNombreEstudiante());
        txtEvento.setText(a.getEvento());
        txtPronostico.setText(a.getPronostico());
        txtMonto.setText(String.valueOf(a.getMonto()));
        txtDescripcion.setText(a.getDescripcion());
        cmbEstado.setSelectedItem(a.getEstado());

        // Seleccionar lugar en combo
        for (int i = 0; i < cmbLugar.getItemCount(); i++) {
            if (cmbLugar.getItemAt(i).startsWith(a.getLugarId() + " - ")) {
                cmbLugar.setSelectedIndex(i); break;
            }
        }

        setFormHabilitado(true);
        btnGuardar.setVisible(true); btnCancelar.setVisible(true);
        btnNuevo.setVisible(false);
    }

    private boolean validar() {
        if (txtCodigo.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "El código del estudiante es obligatorio.");
            txtCodigo.requestFocus(); return false;
        }
        if (txtNombreEst.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "El nombre del estudiante es obligatorio.");
            txtNombreEst.requestFocus(); return false;
        }
        if (txtEvento.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "El evento es obligatorio.");
            txtEvento.requestFocus(); return false;
        }
        if (txtMonto.getText().trim().isEmpty()) {
            EstiloUNAB.mostrarError(this, "El monto es obligatorio.");
            txtMonto.requestFocus(); return false;
        }
        try { Double.parseDouble(txtMonto.getText().trim()); }
        catch (NumberFormatException e) {
            EstiloUNAB.mostrarError(this, "El monto debe ser numérico.");
            txtMonto.requestFocus(); return false;
        }
        return true;
    }

    private void limpiarForm() {
        lblIdOculto.setText("0");
        txtCodigo.setText(""); txtNombreEst.setText("");
        txtEvento.setText(""); txtPronostico.setText("");
        txtMonto.setText(""); txtDescripcion.setText("");
        cmbEstado.setSelectedIndex(0);
        if (cmbLugar.getItemCount() > 0) cmbLugar.setSelectedIndex(0);
    }

    private void setFormHabilitado(boolean h) {
        txtCodigo.setEnabled(h); txtNombreEst.setEnabled(h);
        txtEvento.setEnabled(h); txtPronostico.setEnabled(h);
        txtMonto.setEnabled(h); txtDescripcion.setEnabled(h);
        cmbEstado.setEnabled(h); cmbLugar.setEnabled(h);
    }
}
