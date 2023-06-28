package sexta__actividad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EditorArchivoTextoGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField campoNombre;
    private JTextField campoNumero;
    private JTextArea textArea;
    private JButton botonCargar;
    private JButton botonGuardar;
    private JButton botonEliminar;
    private JButton botonLimpiar;

    private File archivo;

    public EditorArchivoTextoGUI() {
        frame = new JFrame("Editor de Archivo de Texto");
        panel = new JPanel();
        campoNombre = new JTextField(20);
        campoNumero = new JTextField(20);
        textArea = new JTextArea(10, 30);
        botonCargar = new JButton("Cargar");
        botonGuardar = new JButton("Guardar");
        botonEliminar = new JButton("Eliminar");
        botonLimpiar = new JButton("Limpiar");

        archivo = new File("archivo.txt");

        botonCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });

        botonEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarArchivo();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiarTextArea();
            }
        });
    }

    public void crearYMostrarGUI() {
        panel.add(new JLabel("Nombre: "));
        panel.add(campoNombre);
        panel.add(new JLabel("Número: "));
        panel.add(campoNumero);
        panel.add(botonCargar);
        panel.add(botonGuardar);
        panel.add(botonEliminar);
        panel.add(botonLimpiar);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void cargarArchivo() {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(archivo.getAbsolutePath())));
            textArea.setText(contenido);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error al cargar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarArchivo() {
        String nombre = campoNombre.getText();
        String numero = campoNumero.getText();

        if (nombre.isEmpty() || numero.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Por favor ingresa nombre y número.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String contenido = "Nombre: " + nombre + "\nNúmero: " + numero;
            FileWriter escritor = new FileWriter(archivo);
            escritor.write(contenido);
            escritor.close();
            JOptionPane.showMessageDialog(frame, "Archivo guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error al guardar el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarArchivo() {
        if (archivo.exists()) {
            if (archivo.delete()) {
                JOptionPane.showMessageDialog(frame, "Archivo eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                textArea.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Error al eliminar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "El archivo no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarTextArea() {
        textArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                EditorArchivoTextoGUI editor = new EditorArchivoTextoGUI();
                editor.crearYMostrarGUI();
            }
        });
    }
}
