/**
 * Módulo principal para la aplicación de gestión de alimentos espaciales
 * @author Manuel Morales, Darien Díaz
 * @since 2025
 */
module co.edu.poli.Proyecto {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens co.edu.poli.Proyecto.controlador to javafx.fxml;
    
    exports co.edu.poli.Proyecto.vista;
    exports co.edu.poli.Proyecto.controlador;
    exports co.edu.poli.Proyecto.modelo;
    exports co.edu.poli.Proyecto.servicios;
}