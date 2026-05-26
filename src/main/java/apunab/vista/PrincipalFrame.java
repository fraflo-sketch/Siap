package apunab.vista;

import apunab.util.EstiloUNAB;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Ventana principal del sistema APUNAB
 * Incluye menú lateral y navegación entre módulos
 */
public class PrincipalFrame extends JFrame {

    private final String usuarioActual;
    private JPanel panelContenido;
    private JLabel lblUsuarioNav;

    // Botones del menú lateral
    private JButton btnDashboard, btnApuestas, btnLugares, btnSalir;
    private JButton btnActivo;

    public PrincipalFrame(String usuario) {
        this.usuarioActual = usuario;
        initComponents();
        mostrarDashboard();
    }

    private void initComponents() {
        setTitle("APUNAB – Sistema de Gestión de Apuestas UNAB");
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        // ── MENÚ LATERAL ──────────────────────────────────────────────────────
        JPanel menuLateral = crearMenuLateral();

        // ── PANEL CONTENIDO ───────────────────────────────────────────────────
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(EstiloUNAB.VERDE_FONDO);

        add(menuLateral, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, EstiloUNAB.VERDE_OSCURO,
                        0, getHeight(), new Color(0x0D3020));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setOpaque(false);

        // ── LOGO EN MENÚ ──────────────────────────────────────────────────────
        JPanel panelLogo = new JPanel(new GridBagLayout());
        panelLogo.setOpaque(false);
        panelLogo.setBorder(new EmptyBorder(24, 16, 20, 16));
        panelLogo.setPreferredSize(new Dimension(220, 110));

        JLabel lblEmoji = new JLabel("🎯");
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));

        JLabel lblNom = new JLabel("APUNAB");
        lblNom.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNom.setForeground(Color.WHITE);

        JLabel lblUniv = new JLabel("UNAB");
        lblUniv.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUniv.setForeground(new Color(0x80BF96));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.insets = new Insets(2, 0, 2, 0);
        gc.gridy = 0; panelLogo.add(lblEmoji, gc);
        gc.gridy = 1; panelLogo.add(lblNom, gc);
        gc.gridy = 2; panelLogo.add(lblUniv, gc);

        // ── SEPARADOR ─────────────────────────────────────────────────────────
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(new Color(0x2A6040));
        sep1.setBackground(new Color(0x2A6040));

        // ── BOTONES DE NAVEGACIÓN ─────────────────────────────────────────────
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setOpaque(false);
        navPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel lblMenu = new JLabel("  MENÚ PRINCIPAL");
        lblMenu.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblMenu.setForeground(new Color(0x60A07A));
        lblMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMenu.setBorder(new EmptyBorder(0, 4, 8, 0));

        btnDashboard = crearBotonMenu("📊   Dashboard", "dashboard");
        btnApuestas  = crearBotonMenu("🎯   Apuestas",  "apuestas");
        btnLugares   = crearBotonMenu("📍   Lugares",   "lugares");

        navPanel.add(lblMenu);
        navPanel.add(btnDashboard);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnApuestas);
        navPanel.add(Box.createVerticalStrut(4));
        navPanel.add(btnLugares);

        // ── USUARIO + SALIR ───────────────────────────────────────────────────
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(10, 10, 20, 10));

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(new Color(0x2A6040));
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel infoUser = new JPanel(new BorderLayout(8, 0));
        infoUser.setOpaque(false);
        infoUser.setBorder(new EmptyBorder(12, 6, 12, 6));

        JLabel lblAvatarEmoji = new JLabel("👤");
        lblAvatarEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        lblUsuarioNav = new JLabel("<html><b style='color:white'>" + usuarioActual + "</b><br>"
                + "<span style='color:#80BF96;font-size:10px'>Estudiante UNAB</span></html>");
        lblUsuarioNav.setFont(EstiloUNAB.FUENTE_PEQUEÑA);

        infoUser.add(lblAvatarEmoji, BorderLayout.WEST);
        infoUser.add(lblUsuarioNav, BorderLayout.CENTER);

        btnSalir = crearBotonMenu("🚪   Cerrar Sesión", "salir");

        panelInferior.add(sep2);
        panelInferior.add(infoUser);
        panelInferior.add(btnSalir);

        menu.add(panelLogo, BorderLayout.NORTH);
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);
        centro.add(new JScrollPane(navPanel) {{
            setOpaque(false); getViewport().setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
        }}, BorderLayout.CENTER);
        menu.add(centro, BorderLayout.CENTER);
        menu.add(panelInferior, BorderLayout.SOUTH);

        // Eventos
        btnDashboard.addActionListener(e -> { mostrarDashboard(); marcarActivo(btnDashboard); });
        btnApuestas.addActionListener(e -> { mostrarApuestas(); marcarActivo(btnApuestas); });
        btnLugares.addActionListener(e -> { mostrarLugares(); marcarActivo(btnLugares); });
        btnSalir.addActionListener(e -> cerrarSesion());

        return menu;
    }

    private JButton crearBotonMenu(String texto, String id) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(0xC8E6D4));
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn != btnActivo) {
                    btn.setBackground(new Color(255, 255, 255, 20));
                    btn.setOpaque(true);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn != btnActivo) {
                    btn.setOpaque(false);
                }
            }
        });
        return btn;
    }

    private void marcarActivo(JButton btn) {
        // Desmarcar anterior
        if (btnActivo != null) {
            btnActivo.setOpaque(false);
            btnActivo.setBackground(new Color(0, 0, 0, 0));
            btnActivo.setForeground(new Color(0xC8E6D4));
            btnActivo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        }
        // Marcar nuevo
        btnActivo = btn;
        btn.setBackground(new Color(255, 255, 255, 30));
        btn.setOpaque(true);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    private void mostrarDashboard() {
        panelContenido.removeAll();
        panelContenido.add(new DashboardPanel(), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
        marcarActivo(btnDashboard);
    }

    private void mostrarApuestas() {
        panelContenido.removeAll();
        panelContenido.add(crearPanelModulo("apuestas"), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
        marcarActivo(btnApuestas);
    }

    private void mostrarLugares() {
        panelContenido.removeAll();
        panelContenido.add(crearPanelModulo("lugares"), BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
        marcarActivo(btnLugares);
    }

    /**
     * Crea el panel embebido de cada módulo (no usa JFrame separado)
     */
    private JPanel crearPanelModulo(String tipo) {
        // Para Apuestas y Lugares embebemos sus componentes directamente
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(EstiloUNAB.VERDE_FONDO);

        switch (tipo) {
            case "apuestas":
                wrapper.add(new ApuestaEmbedPanel(), BorderLayout.CENTER);
                break;
            case "lugares":
                wrapper.add(new LugarEmbedPanel(), BorderLayout.CENTER);
                break;
            default:
                wrapper.add(new DashboardPanel(), BorderLayout.CENTER);
        }
        return wrapper;
    }

    private void cerrarSesion() {
        if (EstiloUNAB.confirmar(this, "¿Desea cerrar la sesión y salir?")) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }

    // ── CLASES INTERNAS PARA EMBEBER CRUDs ───────────────────────────────────
    /**
     * Versión embebida del CRUD de Apuestas (sin JFrame propio)
     */
    class ApuestaEmbedPanel extends JPanel {
        public ApuestaEmbedPanel() {
            setLayout(new BorderLayout());
            setBackground(EstiloUNAB.VERDE_FONDO);
            ApuestaFrame af = new ApuestaFrame();
            // Tomamos el content pane del JFrame interno y lo embebemos
            JPanel cp = (JPanel) af.getContentPane();
            cp.setBorder(BorderFactory.createEmptyBorder());
            add(cp, BorderLayout.CENTER);
        }
    }

    /**
     * Versión embebida del CRUD de Lugares (sin JFrame propio)
     */
    class LugarEmbedPanel extends JPanel {
        public LugarEmbedPanel() {
            setLayout(new BorderLayout());
            setBackground(EstiloUNAB.VERDE_FONDO);
            LugarFrame lf = new LugarFrame();
            JPanel cp = (JPanel) lf.getContentPane();
            cp.setBorder(BorderFactory.createEmptyBorder());
            add(cp, BorderLayout.CENTER);
        }
    }
}
