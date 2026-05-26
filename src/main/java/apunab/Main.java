package apunab;

import apunab.util.EstiloUNAB;
import apunab.vista.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * ╔══════════════════════════════════════════════════════════════╗
 * ║         APUNAB - Sistema de Gestión de Apuestas             ║
 * ║         Universidad Autónoma de Bucaramanga (UNAB)          ║
 * ║                                                             ║
 * ║  Módulos:                                                   ║
 * ║   • Login / Autenticación                                   ║
 * ║   • Dashboard con métricas                                  ║
 * ║   • CRUD Lugares                                            ║
 * ║   • CRUD Apuestas (APUNAB)                                  ║
 * ║                                                             ║
 * ║  Credenciales demo:                                         ║
 * ║   Usuario: admin   |  Contraseña: 1234                      ║
 * ║                                                             ║
 * ║  © 2026 UNAB – Portal Estudiantil                           ║
 * ╚══════════════════════════════════════════════════════════════╝
 */
public class Main {

    public static void main(String[] args) {
        // Aplicar Look & Feel del sistema operativo
        EstiloUNAB.aplicarLookAndFeel();

        // Lanzar en el hilo de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}
