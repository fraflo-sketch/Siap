package apunab.vista;

import apunab.util.EstiloUNAB;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Pantalla de inicio de sesión - Sistema APUNAB UNAB
 * Inspirada en el diseño SIAP del PDF
 */
public class LoginFrame extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private JLabel lblMensaje;

    public LoginFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("APUNAB - Sistema de Gestión de Apuestas UNAB");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo verde degradado
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, EstiloUNAB.VERDE_OSCURO,
                        0, getHeight(), new Color(0x0F3D22));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ── PANEL LOGO / SUPERIOR ─────────────────────────────────────────────
        JPanel panelLogo = new JPanel(new GridBagLayout());
        panelLogo.setOpaque(false);
        panelLogo.setBorder(new EmptyBorder(40, 20, 20, 20));

        // Ícono de dado/apuesta (ASCII art simple)
        JLabel lblIcono = new JLabel("🎯") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Círculo fondo blanco translúcido
                g2.setColor(new Color(255, 255, 255, 40));
                g2.fillOval(0, 0, getWidth()-1, getHeight()-1);
                g2.setColor(new Color(255, 255, 255, 80));
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(0, 0, getWidth()-1, getHeight()-1);
                super.paintComponent(g);
            }
        };
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        lblIcono.setHorizontalAlignment(SwingConstants.CENTER);
        lblIcono.setPreferredSize(new Dimension(90, 90));

        JLabel lblSistema = new JLabel("APUNAB");
        lblSistema.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblSistema.setForeground(Color.WHITE);
        lblSistema.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblSub = new JLabel("Sistema de Apuestas Universitarias");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(new Color(0xA8D5B8));
        lblSub.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUnab = new JLabel("Universidad Autónoma de Bucaramanga");
        lblUnab.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblUnab.setForeground(new Color(0x80BF96));
        lblUnab.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0; panelLogo.add(lblIcono, gbc);
        gbc.gridy = 1; panelLogo.add(lblSistema, gbc);
        gbc.gridy = 2; panelLogo.add(lblSub, gbc);
        gbc.gridy = 3; panelLogo.add(lblUnab, gbc);

        // ── PANEL FORMULARIO ──────────────────────────────────────────────────
        JPanel panelForm = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panelForm.setOpaque(false);
        panelForm.setBorder(new EmptyBorder(28, 32, 28, 32));

        GridBagConstraints gf = new GridBagConstraints();
        gf.gridx = 0; gf.fill = GridBagConstraints.HORIZONTAL;
        gf.weightx = 1;

        JLabel lblTituloForm = new JLabel("Iniciar Sesión");
        lblTituloForm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTituloForm.setForeground(EstiloUNAB.VERDE_OSCURO);

        JLabel lblUser = new JLabel("Código Estudiantil");
        lblUser.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lblUser.setForeground(EstiloUNAB.GRIS_TEXTO);

        txtUsuario = new JTextField();
        txtUsuario.setFont(EstiloUNAB.FUENTE_NORMAL);
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE),
            new EmptyBorder(8, 12, 8, 12)
        ));
        txtUsuario.setToolTipText("Ingrese su código estudiantil o 'admin'");

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lblPass.setForeground(EstiloUNAB.GRIS_TEXTO);

        txtContrasena = new JPasswordField();
        txtContrasena.setFont(EstiloUNAB.FUENTE_NORMAL);
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(EstiloUNAB.VERDE_BORDE),
            new EmptyBorder(8, 12, 8, 12)
        ));

        btnIngresar = EstiloUNAB.crearBotonPrimario("INGRESAR");
        btnIngresar.setPreferredSize(new Dimension(0, 42));
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(EstiloUNAB.FUENTE_PEQUEÑA);
        lblMensaje.setForeground(EstiloUNAB.ROJO_PELIGRO);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblDemo = new JLabel("Demo: usuario 'admin' / clave '1234'");
        lblDemo.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblDemo.setForeground(new Color(0x888888));
        lblDemo.setHorizontalAlignment(SwingConstants.CENTER);

        gf.gridy = 0; gf.insets = new Insets(0, 0, 16, 0);
        panelForm.add(lblTituloForm, gf);
        gf.gridy = 1; gf.insets = new Insets(0, 0, 3, 0);
        panelForm.add(lblUser, gf);
        gf.gridy = 2; gf.insets = new Insets(0, 0, 12, 0);
        panelForm.add(txtUsuario, gf);
        gf.gridy = 3; gf.insets = new Insets(0, 0, 3, 0);
        panelForm.add(lblPass, gf);
        gf.gridy = 4; gf.insets = new Insets(0, 0, 16, 0);
        panelForm.add(txtContrasena, gf);
        gf.gridy = 5; gf.insets = new Insets(0, 0, 8, 0);
        panelForm.add(btnIngresar, gf);
        gf.gridy = 6; gf.insets = new Insets(0, 0, 4, 0);
        panelForm.add(lblMensaje, gf);
        gf.gridy = 7; gf.insets = new Insets(0, 0, 0, 0);
        panelForm.add(lblDemo, gf);

        // ── PANEL INFERIOR ────────────────────────────────────────────────────
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(new EmptyBorder(0, 0, 20, 0));

        String[] iconos = {"🏆 Ranking", "📊 Estadísticas", "🎯 Apuestas"};
        for (String ic : iconos) {
            JLabel lbl = new JLabel(ic);
            lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
            lbl.setForeground(new Color(0x80BF96));
            panelInferior.add(lbl);
        }

        // ── ENSAMBLADO ────────────────────────────────────────────────────────
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(0, 40, 0, 40));
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL; gc.weightx = 1;
        gc.gridx = 0; gc.gridy = 0;
        centro.add(panelForm, gc);

        panelPrincipal.add(panelLogo, BorderLayout.NORTH);
        panelPrincipal.add(centro, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);

        // ── ACCIONES ──────────────────────────────────────────────────────────
        btnIngresar.addActionListener(e -> autenticar());
        txtContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) autenticar();
            }
        });
        txtUsuario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) txtContrasena.requestFocus();
            }
        });
    }

    private void autenticar() {
        String usuario = txtUsuario.getText().trim();
        String clave = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || clave.isEmpty()) {
            lblMensaje.setText("Por favor complete todos los campos");
            return;
        }

        // Autenticación simple (en producción usar BD + hash)
        if ((usuario.equals("admin") && clave.equals("1234"))
         || usuario.startsWith("219") && clave.equals("unab2026")) {
            lblMensaje.setText(" ");
            PrincipalFrame principal = new PrincipalFrame(usuario);
            principal.setVisible(true);
            this.dispose();
        } else {
            lblMensaje.setText("⚠ Usuario o contraseña incorrectos");
            txtContrasena.setText("");
            txtContrasena.requestFocus();
        }
    }
}
