package co.edu.poli.Proyecto.servicios;

import co.edu.poli.Proyecto.modelo.Alimento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para gestión de alimentos espaciales
 * @author Manuel Morales, Darien Díaz
 * @since 2025
 */
public class ServicioAlimentos {
    private List<Alimento> alimentos;
    private static final String ARCHIVO = "alimentos.dat";

    public ServicioAlimentos() {
        this.alimentos = new ArrayList<>();
        cargarAlimentos();
    }

    public boolean agregarAlimento(Alimento alimento) {
        if (buscarAlimento(alimento.getId()) != null) {
            return false;
        }
        alimentos.add(alimento);
        return true;
    }

    public boolean eliminarAlimento(String id) {
        return alimentos.removeIf(a -> a.getId().equals(id));
    }

    public Alimento buscarAlimento(String id) {
        return alimentos.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void actualizarAlimento(Alimento alimentoActualizado) {
        for (int i = 0; i < alimentos.size(); i++) {
            if (alimentos.get(i).getId().equals(alimentoActualizado.getId())) {
                alimentos.set(i, alimentoActualizado);
                break;
            }
        }
    }

    public List<Alimento> getAlimentos() {
        return new ArrayList<>(alimentos);
    }

    public boolean serializar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(alimentos);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean deserializar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            alimentos = (List<Alimento>) ois.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }
    }

    private void cargarAlimentos() {
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            deserializar();
        }
    }
}