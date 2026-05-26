package apunab.dao;

import apunab.modelo.Lugar;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO para gestión de Lugares
 * Usa lista en memoria (para producción conectar a BD)
 * Sistema APUNAB - UNAB
 */
public class LugarDAO {

    private static List<Lugar> lugares = new ArrayList<>();
    private static int contadorId = 1;

    static {
        // Datos iniciales de ejemplo
        lugares.add(new Lugar(contadorId++, "Estadio Alfonso López", "Estadio principal de Bucaramanga", "Bucaramanga", "Estadio", true));
        lugares.add(new Lugar(contadorId++, "Cancha UNAB Norte", "Cancha de fútbol Campus Norte UNAB", "Bucaramanga", "Cancha", true));
        lugares.add(new Lugar(contadorId++, "Coliseo El Campín", "Coliseo cubierto para baloncesto", "Bucaramanga", "Coliseo", true));
        lugares.add(new Lugar(contadorId++, "Sala Virtual UNAB", "Plataforma virtual de apuestas UNAB", "Virtual", "Virtual", true));
        lugares.add(new Lugar(contadorId++, "Polideportivo UNAB", "Complejo deportivo universitario", "Bucaramanga", "Complejo", true));
    }

    // ── CREATE ────────────────────────────────────────────────────────────────
    public boolean insertar(Lugar lugar) {
        try {
            lugar.setId(contadorId++);
            lugares.add(lugar);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar lugar: " + e.getMessage());
            return false;
        }
    }

    // ── READ ──────────────────────────────────────────────────────────────────
    public List<Lugar> listarTodos() {
        return new ArrayList<>(lugares);
    }

    public List<Lugar> listarActivos() {
        return lugares.stream()
                .filter(Lugar::isActivo)
                .collect(Collectors.toList());
    }

    public Lugar buscarPorId(int id) {
        return lugares.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Lugar> buscarPorNombre(String nombre) {
        String filtro = nombre.toLowerCase().trim();
        return lugares.stream()
                .filter(l -> l.getNombre().toLowerCase().contains(filtro)
                          || l.getCiudad().toLowerCase().contains(filtro))
                .collect(Collectors.toList());
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Lugar lugar) {
        for (int i = 0; i < lugares.size(); i++) {
            if (lugares.get(i).getId() == lugar.getId()) {
                lugares.set(i, lugar);
                return true;
            }
        }
        return false;
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        return lugares.removeIf(l -> l.getId() == id);
    }

    public boolean desactivar(int id) {
        Lugar l = buscarPorId(id);
        if (l != null) {
            l.setActivo(false);
            return true;
        }
        return false;
    }

    public int contarTodos() {
        return lugares.size();
    }
}
