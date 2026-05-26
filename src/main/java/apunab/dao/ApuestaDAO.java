package apunab.dao;

import apunab.modelo.Apuesta;
import java.util.*;
import java.util.stream.Collectors;

/**
 * DAO para gestión de Apuestas
 * Sistema APUNAB - UNAB
 */
public class ApuestaDAO {

    private static List<Apuesta> apuestas = new ArrayList<>();
    private static int contadorId = 1;

    static {
        // Datos iniciales de ejemplo
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date proxFecha = cal.getTime();

        apuestas.add(new Apuesta(contadorId++, "2190001", "Ana García López",
                1, "Estadio Alfonso López", "Fútbol: UNAB vs UIS",
                "Partido semifinal torneo universitario", 50.0,
                "PENDIENTE", new Date(), proxFecha, "Victoria UNAB", 0));

        apuestas.add(new Apuesta(contadorId++, "2190002", "Carlos Martínez",
                2, "Cancha UNAB Norte", "Baloncesto: Torneo Interno",
                "Torneo interfacultades", 30.0,
                "GANADA", new Date(), new Date(), "Victoria Ingeniería", 150));

        apuestas.add(new Apuesta(contadorId++, "2190003", "Valentina Ruiz",
                3, "Coliseo El Campín", "Voleibol: Liga Universitaria",
                "Fase de grupos liga universitaria", 40.0,
                "PERDIDA", new Date(), new Date(), "Empate", 0));

        apuestas.add(new Apuesta(contadorId++, "2190004", "Miguel Torres",
                4, "Sala Virtual UNAB", "eSports: FIFA Tournament",
                "Torneo virtual de FIFA 2026", 25.0,
                "PENDIENTE", new Date(), proxFecha, "Victoria Medicina", 0));

        apuestas.add(new Apuesta(contadorId++, "2190005", "Laura Gómez",
                1, "Estadio Alfonso López", "Fútbol: Copa UNAB",
                "Final copa universitaria", 80.0,
                "CANCELADA", new Date(), new Date(), "Victoria Derecho", 0));
    }

    // ── CREATE ────────────────────────────────────────────────────────────────
    public boolean insertar(Apuesta apuesta) {
        try {
            apuesta.setId(contadorId++);
            apuestas.add(apuesta);
            return true;
        } catch (Exception e) {
            System.err.println("Error al insertar apuesta: " + e.getMessage());
            return false;
        }
    }

    // ── READ ──────────────────────────────────────────────────────────────────
    public List<Apuesta> listarTodas() {
        return new ArrayList<>(apuestas);
    }

    public Apuesta buscarPorId(int id) {
        return apuestas.stream()
                .filter(a -> a.getId() == id)
                .findFirst().orElse(null);
    }

    public List<Apuesta> buscarPorEstudiante(String codigo) {
        return apuestas.stream()
                .filter(a -> a.getCodigoEstudiante().equalsIgnoreCase(codigo))
                .collect(Collectors.toList());
    }

    public List<Apuesta> buscarPorEstado(String estado) {
        return apuestas.stream()
                .filter(a -> a.getEstado().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    public List<Apuesta> buscarPorLugar(int lugarId) {
        return apuestas.stream()
                .filter(a -> a.getLugarId() == lugarId)
                .collect(Collectors.toList());
    }

    public List<Apuesta> buscarGeneral(String texto) {
        String q = texto.toLowerCase().trim();
        return apuestas.stream()
                .filter(a -> a.getNombreEstudiante().toLowerCase().contains(q)
                          || a.getCodigoEstudiante().toLowerCase().contains(q)
                          || a.getEvento().toLowerCase().contains(q)
                          || a.getLugarNombre().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean actualizar(Apuesta apuesta) {
        for (int i = 0; i < apuestas.size(); i++) {
            if (apuestas.get(i).getId() == apuesta.getId()) {
                apuestas.set(i, apuesta);
                return true;
            }
        }
        return false;
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        return apuestas.removeIf(a -> a.getId() == id);
    }

    // ── ESTADÍSTICAS ──────────────────────────────────────────────────────────
    public int contarTodas() { return apuestas.size(); }

    public int contarPorEstado(String estado) {
        return (int) apuestas.stream()
                .filter(a -> a.getEstado().equalsIgnoreCase(estado)).count();
    }

    public double totalMontos() {
        return apuestas.stream().mapToDouble(Apuesta::getMonto).sum();
    }

    public double totalPuntos() {
        return apuestas.stream().mapToDouble(Apuesta::getPuntos).sum();
    }

    // Ranking de estudiantes por puntos
    public Map<String, Double> rankingEstudiantes() {
        Map<String, Double> ranking = new LinkedHashMap<>();
        apuestas.stream()
            .collect(Collectors.groupingBy(Apuesta::getNombreEstudiante,
                     Collectors.summingDouble(Apuesta::getPuntos)))
            .entrySet().stream()
            .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
            .forEach(e -> ranking.put(e.getKey(), e.getValue()));
        return ranking;
    }
}
