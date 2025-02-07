package tarea1;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DAtosTabala {
    JTable ventana;

    public DAtosTabala(JTable vetanaprincipal) {
        this.ventana = vetanaprincipal;
    }

    // Método para agregar elementos a la tabla en una posición específica
    public void agregarElementoEnPosicion(String algoritmo, int tamaño, long tiempo, int posicion) {
        DefaultTableModel model = (DefaultTableModel) ventana.getModel();
        Object[] rowData = {algoritmo, tamaño, tiempo};
        model.insertRow(posicion, rowData);  // Agregar en una posición específica
    }

    // Método para agregar elementos a la tabla
    public void agregarElemento(String algoritmo, int tamaño, long tiempo) {
        DefaultTableModel model = (DefaultTableModel) ventana.getModel();
        Object[] rowData = {algoritmo, tamaño, tiempo};
        model.addRow(rowData);  // Agregar al final de la tabla
    }
}
