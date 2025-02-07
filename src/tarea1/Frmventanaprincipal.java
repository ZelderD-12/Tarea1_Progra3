package tarea1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Frmventanaprincipal extends javax.swing.JFrame {

    //Listas de las Canciones
    List<String> listaCanciones = Arrays.asList(
            "music/claro.wav",
            "music/wiisport60.wav",
            "music/elevadorcus.wav"
    );

    //Parametros para crear el txt        
    String rutaCarpetanumeros = "numeros/";
    String nombredelArchivo = "numeros.txt";
    int cantidadenumero = 100000;

    //Invocacion de metodos
    Clasecrear manejador = new Clasecrear(rutaCarpetanumeros, nombredelArchivo, cantidadenumero);
    ReproducirCancion reproductor = new ReproducirCancion(listaCanciones);

    //Creacion de hilos
    Thread hiloReproduccion = new Thread(reproductor);
    Thread hilodegeneradordenumeros = new Thread(manejador);

    DefaultTableModel st = new DefaultTableModel();

    public Frmventanaprincipal() {
        hiloReproduccion.start();
        initComponents();
    }

    public void cargarTabla(ArrayList<Integer> numeros) {
        // Crear la tabla con el número total de filas que corresponde al número de elementos
        String[] title = {"No.", "Tamaño", "Bubble", "Counting", "Heap", "Insertion", "Merge", "Quick", "Select  ion", "Shell"};
        st = new DefaultTableModel(title, 0);
        Tabla.setModel(st);

        int num = 1;
        int currentSizeGroup = 1000;  // Empezamos con 1,000
        int totalSize = numeros.size();  // Total de números en la lista

        // Lista para almacenar los grupos generados
        ArrayList<Integer> grupos = new ArrayList<>();

        // Generar grupos hasta acercarnos a totalSize
        while (currentSizeGroup < totalSize) {
            grupos.add(currentSizeGroup);

            // Ajustar el tamaño del grupo para la siguiente iteración
            if (currentSizeGroup < 1000000) {
                currentSizeGroup *= 10;  // Escalamos en potencias de 10
            } else {
                currentSizeGroup += 1000000;  // A partir de 1 millón, incrementamos en 1 millón
            }
        }

        // Redondear el último grupo para que totalSize sea exacto
        if (!grupos.isEmpty() && grupos.get(grupos.size() - 1) != totalSize) {
            grupos.set(grupos.size() - 1, totalSize);
        }

        // Insertar los grupos en la tabla
        for (int size : grupos) {
            st.addRow(new Object[]{num++, size, null, null, null, null, null, null, null, null});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnCargar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnIniciar = new javax.swing.JButton();
        btnExtraer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCrear.setBackground(new java.awt.Color(255, 255, 102));
        btnCrear.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 200, -1));

        btnCargar.setBackground(new java.awt.Color(255, 255, 102));
        btnCargar.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        btnCargar.setText("Cargar archivo");
        btnCargar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCargar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 200, -1));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Universidad Mariano Galvéz");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 410, 30));

        Tabla.setFont(new java.awt.Font("Tw Cen MT", 0, 11)); // NOI18N
        Tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", "", null, null, null, null, null, null, null, null},
                {"", "", null, null, null, null, null, null, null, null},
                {"", "", null, null, null, null, null, null, null, null},
                {"", "", null, null, null, null, null, null, null, null},
                {"", "", null, null, null, null, null, null, null, null},
                {"", "", null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "Tamaño", "Bubble", "Counting", "Heap", "Insertion", "Merge", "Quick", "Selection", "Shell"
            }
        ));
        jScrollPane2.setViewportView(Tabla);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, 800, 120));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, 70));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 30, -1, -1));

        btnIniciar.setBackground(new java.awt.Color(255, 102, 0));
        btnIniciar.setFont(new java.awt.Font("Tw Cen MT", 0, 48)); // NOI18N
        btnIniciar.setText("Iniciar");
        jPanel1.add(btnIniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, -1, -1));

        btnExtraer.setBackground(new java.awt.Color(255, 204, 51));
        btnExtraer.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btnExtraer.setText("Extraer datos");
        jPanel1.add(btnExtraer, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 390, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        hilodegeneradordenumeros.start();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showOpenDialog(null);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            ArrayList<Integer> numeros = leerNumerosDesdeArchivo(archivo);

            // Mostrar solo el total de números en un cuadro de diálogo
            JOptionPane.showMessageDialog(null, "Archivo Cargado de Manera Exitosa\nTotal de números: " + numeros.size(), "Resultado", JOptionPane.INFORMATION_MESSAGE);

            // Llamar al método para cargar la tabla con los tamaños
            cargarTabla(numeros);
        }
    }//GEN-LAST:event_btnCargarActionPerformed

    private static ArrayList<Integer> leerNumerosDesdeArchivo(File archivo) {
        ArrayList<Integer> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim(); // Eliminar espacios en blanco
                if (!linea.isEmpty() && linea.endsWith(",")) {
                    linea = linea.substring(0, linea.length() - 1); // Quitar la coma final
                }
                try {
                    int numero = Integer.parseInt(linea);
                    numeros.add(numero); // Agregar número al ArrayList
                } catch (NumberFormatException e) {
                    System.err.println("Número inválido ignorado: " + linea);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return numeros;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frmventanaprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frmventanaprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frmventanaprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frmventanaprincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frmventanaprincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla;
    private javax.swing.JButton btnCargar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnExtraer;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
