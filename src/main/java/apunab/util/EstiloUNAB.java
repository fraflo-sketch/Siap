package apunab.util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * Utilidades de interfaz gráfica - Paleta UNAB
 * Sistema APUNAB - UNAB
 */
public class EstiloUNAB {

    // ── PALETA DE COLORES UNAB ────────────────────────────────────────────────
    public static final Color VERDE_PRINCIPAL  = new Color(0x1B6B3A);
    public static final Color VERDE_OSCURO     = new Color(0x14522D);
    public static final Color VERDE_CLARO      = new Color(0x2E8B57);
    public static final Color VERDE_FONDO      = new Color(0xF0F7F2);
    public static final Color VERDE_BORDE      = new Color(0xA8D5B8);
    public static final Color AMARILLO_ACENTO  = new Color(0xF5A623);
    public static final Color NARANJA_ACENTO   = new Color(0xE07B39);
    public static final Color BLANCO           = Color.WHITE;
    public static final Color GRIS_TEXTO       = new Color(0x4A4A4A);
    public static final Color GRIS_CLARO       = new Color(0xF5F5F5);
    public static final Color ROJO_PELIGRO     = new Color(0xC0392B);
    public static final Color AZUL_INFO        = new Color(0x2980B9);
    public static final Color PURPURA_EVENTO   = new Color(0x8E44AD);

    // ── FUENTES ───────────────────────────────────────────────────────────────
    public static final Font FUENTE_TITULO     = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO  = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FUENTE_NORMAL     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_PEQUEÑA    = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_BOLD       = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_NUMERO     = new Font("Segoe UI", Font.BOLD, 28);

    // ── DIMENSIONES ───────────────────────────────────────────────────────────
    public static final int RADIO_BORDE = 10;
    public static final Insets PADDING_CAMPO = new Insets(8, 12, 8, 12);

    /**
     * Aplica Look and Feel moderno
     */
    public static void aplicarLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.arc", 8);
            UIManager.put("Component.arc", 8);
            UIManager.put("TextComponent.arc", 8);
        } catch (Exception e) {
            // Usa L&F por defecto si no está disponible
        }
    }

    /**
     * Crea botón con estilo UNAB verde
     */
    public static JButton crearBotonPrimario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(VERDE_PRINCIPAL);
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_BOLD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(VERDE_CLARO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(VERDE_PRINCIPAL);
            }
        });
        return btn;
    }

    /**
     * Crea botón secundario (contorno verde)
     */
    public static JButton crearBotonSecundario(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(BLANCO);
        btn.setForeground(VERDE_PRINCIPAL);
        btn.setFont(FUENTE_BOLD);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE_PRINCIPAL, 1),
            new EmptyBorder(6, 14, 6, 14)
        ));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(VERDE_FONDO);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(BLANCO);
            }
        });
        return btn;
    }

    /**
     * Crea botón de peligro (rojo)
     */
    public static JButton crearBotonPeligro(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(ROJO_PELIGRO);
        btn.setForeground(BLANCO);
        btn.setFont(FUENTE_BOLD);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(0xE74C3C));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(ROJO_PELIGRO);
            }
        });
        return btn;
    }

    /**
     * Crea campo de texto con estilo UNAB
     */
    public static JTextField crearCampoTexto(int columnas) {
        JTextField campo = new JTextField(columnas);
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(VERDE_BORDE, 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        campo.setBackground(BLANCO);
        return campo;
    }

    /**
     * Crea área de texto con estilo UNAB
     */
    public static JTextArea crearAreaTexto(int filas, int columnas) {
        JTextArea area = new JTextArea(filas, columnas);
        area.setFont(FUENTE_NORMAL);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(6, 10, 6, 10));
        return area;
    }

    /**
     * Estiliza un JTable con colores UNAB
     */
    public static void estilizarTabla(JTable tabla) {
        tabla.setRowHeight(34);
        tabla.setFont(FUENTE_NORMAL);
        tabla.setGridColor(new Color(0xE0EDE6));
        tabla.setSelectionBackground(new Color(0xD4ECD9));
        tabla.setSelectionForeground(VERDE_OSCURO);
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(VERDE_PRINCIPAL);
        header.setForeground(BLANCO);
        header.setFont(FUENTE_BOLD);
        header.setPreferredSize(new Dimension(0, 38));
        header.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                if (!sel) {
                    setBackground(row % 2 == 0 ? BLANCO : new Color(0xF5FAF6));
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                return this;
            }
        };
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    /**
     * Crea panel con encabezado verde estilo UNAB
     */
    public static JPanel crearPanelEncabezado(String titulo, String subtitulo) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, VERDE_OSCURO, getWidth(), 0, VERDE_CLARO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setBorder(new EmptyBorder(18, 24, 18, 24));
        panel.setPreferredSize(new Dimension(0, 80));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(BLANCO);

        if (subtitulo != null && !subtitulo.isEmpty()) {
            JLabel lblSub = new JLabel(subtitulo);
            lblSub.setFont(FUENTE_PEQUEÑA);
            lblSub.setForeground(new Color(0xB8DFC8));
            JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
            textos.setOpaque(false);
            textos.add(lblTitulo);
            textos.add(lblSub);
            panel.add(textos, BorderLayout.CENTER);
        } else {
            panel.add(lblTitulo, BorderLayout.CENTER);
        }
        return panel;
    }

    /**
     * Devuelve color según estado de apuesta
     */
    public static Color colorEstado(String estado) {
        switch (estado.toUpperCase()) {
            case "GANADA":    return new Color(0x27AE60);
            case "PERDIDA":   return ROJO_PELIGRO;
            case "PENDIENTE": return AMARILLO_ACENTO;
            case "CANCELADA": return new Color(0x7F8C8D);
            default:          return GRIS_TEXTO;
        }
    }

    /**
     * Muestra diálogo de confirmación estilizado
     */
    public static boolean confirmar(Component parent, String mensaje) {
        return JOptionPane.showConfirmDialog(parent, mensaje,
                "Confirmar acción - APUNAB",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Muestra mensaje de éxito
     */
    public static void mostrarExito(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje,
                "✔ Operación exitosa - APUNAB",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra mensaje de error
     */
    public static void mostrarError(Component parent, String mensaje) {
        JOptionPane.showMessageDialog(parent, mensaje,
                "✘ Error - APUNAB",
                JOptionPane.ERROR_MESSAGE);
    }
}
