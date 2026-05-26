package apunab.vista;

import apunab.dao.ApuestaDAO;
import apunab.dao.LugarDAO;
import apunab.util.EstiloUNAB;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

/**
 * Panel Dashboard - Pantalla principal del sistema
 * Inspirado en el diseño verde del PDF SIAP/APUNAB
 */
public class DashboardPanel extends JPanel {

    private final ApuestaDAO apuestaDAO = new ApuestaDAO();
    private final LugarDAO lugarDAO = new LugarDAO();

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(EstiloUNAB.VERDE_FONDO);
        construirUI();
    }

    private void construirUI() {
        // ── ENCABEZADO ────────────────────────────────────────────────────────
        add(EstiloUNAB.crearPanelEncabezado(
            "📊  Dashboard APUNAB",
            "Resumen general del sistema de apuestas universitarias"), BorderLayout.NORTH);

        // ── CONTENIDO SCROLLABLE ──────────────────────────────────────────────
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(EstiloUNAB.VERDE_FONDO);
        contenido.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Fila 1: tarjetas de métricas
        contenido.add(crearFilaTarjetas());
        contenido.add(Box.createVerticalStrut(16));

        // Fila 2: ranking + estados
        JPanel fila2 = new JPanel(new GridLayout(1, 2, 14, 0));
        fila2.setOpaque(false);
        fila2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        fila2.add(crearPanelRanking());
        fila2.add(crearPanelEstados());
        contenido.add(fila2);
        contenido.add(Box.createVerticalStrut(16));

        // Fila 3: últimas apuestas
        contenido.add(crearPanelUltimasApuestas());

        JScrollPane scroll = new JScrollPane(contenido);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(EstiloUNAB.VERDE_FONDO);
        add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearFilaTarjetas() {
        JPanel fila = new JPanel(new GridLayout(1, 4, 12, 0));
        fila.setOpaque(false);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        int total = apuestaDAO.contarTodas();
        int pendientes = apuestaDAO.contarPorEstado("PENDIENTE");
        int ganadas = apuestaDAO.contarPorEstado("GANADA");
        double monto = apuestaDAO.totalMontos();

        fila.add(crearTarjeta("Total Apuestas", String.valueOf(total), "🎯", EstiloUNAB.VERDE_PRINCIPAL));
        fila.add(crearTarjeta("Pendientes", String.valueOf(pendientes), "⏳", EstiloUNAB.AMARILLO_ACENTO));
        fila.add(crearTarjeta("Ganadas", String.valueOf(ganadas), "🏆", new Color(0x27AE60)));
        fila.add(crearTarjeta("Monto Total", "$" + String.format("%.0f", monto), "💰", EstiloUNAB.NARANJA_ACENTO));

        return fila;
    }

    private JPanel crearTarjeta(String titulo, String valor, String icono, Color color) {
        JPanel card = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                // Barra superior de color
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), 6, 6, 6);
                g2.fillRect(0, 3, getWidth(), 3);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(18, 16, 14, 16));

        JLabel lblIcono = new JLabel(icono);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        lblIcono.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(EstiloUNAB.FUENTE_NUMERO);
        lblValor.setForeground(color);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lblTit.setForeground(new Color(0x777777));

        JPanel texto = new JPanel(new GridLayout(2, 1, 0, 2));
        texto.setOpaque(false);
        texto.add(lblValor);
        texto.add(lblTit);

        card.add(lblIcono, BorderLayout.EAST);
        card.add(texto, BorderLayout.CENTER);

        // Sombra simulada con borde
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0xE5EDE8), 1),
            new EmptyBorder(18, 16, 14, 16)
        ));

        return card;
    }

    private JPanel crearPanelRanking() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE),
            new EmptyBorder(0, 0, 10, 0)
        ));

        // Encabezado mini
        JPanel cabecera = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(EstiloUNAB.VERDE_PRINCIPAL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecera.setPreferredSize(new Dimension(0, 36));
        cabecera.setBorder(new EmptyBorder(6, 12, 6, 12));
        JLabel lbl = new JLabel("🏆  Ranking de Estudiantes");
        lbl.setFont(EstiloUNAB.FUENTE_BOLD); lbl.setForeground(Color.WHITE);
        cabecera.add(lbl);

        // Tabla ranking
        Map<String, Double> ranking = apuestaDAO.rankingEstudiantes();
        String[] cols = {"#", "Estudiante", "Puntos"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int pos = 1;
        for (Map.Entry<String, Double> e : ranking.entrySet()) {
            model.addRow(new Object[]{pos++, e.getKey(), (int)(double) e.getValue()});
        }

        JTable tabla = new JTable(model);
        EstiloUNAB.estilizarTabla(tabla);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(25);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(60);

        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla) {{ setBorder(BorderFactory.createEmptyBorder()); }},
                BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelEstados() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE));

        JPanel cabecera = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(EstiloUNAB.VERDE_PRINCIPAL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecera.setPreferredSize(new Dimension(0, 36));
        cabecera.setBorder(new EmptyBorder(6, 12, 6, 12));
        JLabel lbl = new JLabel("📈  Resumen por Estado");
        lbl.setFont(EstiloUNAB.FUENTE_BOLD); lbl.setForeground(Color.WHITE);
        cabecera.add(lbl);

        JPanel filas = new JPanel(new GridLayout(4, 1, 0, 0));
        filas.setBackground(EstiloUNAB.BLANCO);
        filas.setBorder(new EmptyBorder(10, 16, 10, 16));

        String[] estados = {"PENDIENTE", "GANADA", "PERDIDA", "CANCELADA"};
        String[] emojis  = {"⏳", "🏆", "❌", "🚫"};
        int total = apuestaDAO.contarTodas();

        for (int i = 0; i < estados.length; i++) {
            int cnt = apuestaDAO.contarPorEstado(estados[i]);
            int pct = total > 0 ? (cnt * 100 / total) : 0;
            Color color = EstiloUNAB.colorEstado(estados[i]);

            JPanel fila = new JPanel(new BorderLayout(8, 0));
            fila.setOpaque(false);
            fila.setBorder(new EmptyBorder(6, 0, 6, 0));

            JLabel lblEst = new JLabel(emojis[i] + "  " + estados[i]);
            lblEst.setFont(EstiloUNAB.FUENTE_BOLD);
            lblEst.setForeground(color);
            lblEst.setPreferredSize(new Dimension(120, 20));

            JProgressBar bar = new JProgressBar(0, 100);
            bar.setValue(pct);
            bar.setStringPainted(true);
            bar.setString(cnt + " (" + pct + "%)");
            bar.setForeground(color);
            bar.setBackground(new Color(0xEEEEEE));
            bar.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
            bar.setBorderPainted(false);
            bar.setPreferredSize(new Dimension(0, 22));

            fila.add(lblEst, BorderLayout.WEST);
            fila.add(bar, BorderLayout.CENTER);
            filas.add(fila);
        }

        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(filas, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelUltimasApuestas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(EstiloUNAB.BLANCO);
        panel.setBorder(BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel cabecera = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                g.setColor(EstiloUNAB.VERDE_PRINCIPAL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecera.setPreferredSize(new Dimension(0, 36));
        cabecera.setBorder(new EmptyBorder(6, 12, 6, 12));
        JLabel lbl = new JLabel("🕐  Últimas Apuestas Registradas");
        lbl.setFont(EstiloUNAB.FUENTE_BOLD); lbl.setForeground(Color.WHITE);
        cabecera.add(lbl);

        String[] cols = {"ID", "Estudiante", "Evento", "Lugar", "Monto", "Estado"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        java.util.List<apunab.modelo.Apuesta> lista = apuestaDAO.listarTodas();
        int desde = Math.max(0, lista.size() - 5);
        for (int i = desde; i < lista.size(); i++) {
            apunab.modelo.Apuesta a = lista.get(i);
            model.addRow(new Object[]{
                a.getId(), a.getNombreEstudiante(), a.getEvento(),
                a.getLugarNombre(), "$" + String.format("%.0f", a.getMonto()), a.getEstado()
            });
        }

        JTable tabla = new JTable(model);
        EstiloUNAB.estilizarTabla(tabla);

        panel.add(cabecera, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabla) {{ setBorder(BorderFactory.createEmptyBorder()); }},
                BorderLayout.CENTER);
        return panel;
    }
}
