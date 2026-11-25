/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package expedientesstreams;

/**
 *
 * @author VITG00M
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ExpedientesStreams extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContenedor;

    // Componentes del formulario
    private JTextField txtMatricula;
    private JTextField txtNombre;
    private JTextField txtAsignatura;
    private JTextField txtObservacion;
    private JTextArea txtAreaResultado;
    private JLabel lblArchivoSeleccionado;
    private JLabel lblVistaPreviaBinaria;

    private File archivoBinarioOrigen;
    private final String RUTA_TEXTO = "observaciones_academicas.txt";
    private final String CARPETA_ADJUNTOS = "adjuntos_expedientes";

    public ExpedientesStreams() {
        setTitle("Registro de observaciones académicas - Germán Olvera Tejero");
        setSize(850, 630);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        new File(CARPETA_ADJUNTOS).mkdirs();

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        panelContenedor.add(crearPanelInicio(), "INICIO");
        panelContenedor.add(crearPanelModulo(), "MODULO");

        add(panelContenedor);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Sistema de Gestión de Expedientes Escolares", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(20, 60, 110));

        // Imagen alusiva al módulo generada gráficamente
        JLabel lblImagen = new JLabel(crearIconoExpediente(), SwingConstants.CENTER);

        JPanel panelCentro = new JPanel(new GridLayout(3, 1, 5, 5));
        panelCentro.setBackground(Color.WHITE);
        JLabel lblSubtitulo = new JLabel("Módulo Asignado: Registro de observaciones académicas (M - P)", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JLabel lblAutor = new JLabel("Desarrollado por: Germán Olvera Tejero | Matrícula: ES172000419", SwingConstants.CENTER);
        lblAutor.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelCentro.add(lblImagen);
        panelCentro.add(lblSubtitulo);
        panelCentro.add(lblAutor);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEntrar = new JButton("Acceder al Módulo");
        btnEntrar.setBackground(new Color(20, 90, 50));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEntrar.setFocusPainted(false);
        btnEntrar.addActionListener(e -> cardLayout.show(panelContenedor, "MODULO"));

        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    private ImageIcon crearIconoExpediente() {
        BufferedImage img = new BufferedImage(130, 130, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Carpeta institucional azul
        g2.setColor(new Color(20, 60, 110));
        g2.fillRoundRect(15, 25, 100, 85, 14, 14);
        g2.fillRoundRect(15, 14, 46, 20, 8, 8);

        // Hoja blanca de observaciones académicas
        g2.setColor(Color.WHITE);
        g2.fillRect(28, 38, 74, 60);

        // Líneas de registro
        g2.setColor(new Color(140, 160, 180));
        g2.fillRect(38, 48, 54, 6);
        g2.fillRect(38, 61, 54, 6);
        g2.fillRect(38, 74, 34, 6);

        // Palomita de validación verde
        g2.setColor(new Color(25, 135, 84));
        g2.setStroke(new BasicStroke(4.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(74, 78, 82, 86);
        g2.drawLine(82, 86, 95, 70);

        g2.dispose();
        return new ImageIcon(img);
    }

    private JPanel crearPanelModulo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior: 4 JLabel y 4 JTextField
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de Observación Académica (Flujos de Caracteres)"));

        panelFormulario.add(new JLabel("Matrícula del Estudiante:"));
        txtMatricula = new JTextField();
        panelFormulario.add(txtMatricula);

        panelFormulario.add(new JLabel("Nombre Completo del Estudiante:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Asignatura / Unidad Didáctica:"));
        txtAsignatura = new JTextField();
        panelFormulario.add(txtAsignatura);

        panelFormulario.add(new JLabel("Observación Académica:"));
        txtObservacion = new JTextField();
        panelFormulario.add(txtObservacion);

        JButton btnGuardarTexto = new JButton("Guardar Texto (FileWriter)");
        btnGuardarTexto.setBackground(new Color(25, 110, 60));
        btnGuardarTexto.setForeground(Color.WHITE);
        btnGuardarTexto.addActionListener(e -> guardarObservacionTexto());

        JButton btnLeerTexto = new JButton("Leer Observaciones (FileReader)");
        btnLeerTexto.addActionListener(e -> leerObservacionesTexto());

        panelFormulario.add(btnGuardarTexto);
        panelFormulario.add(btnLeerTexto);

        // Panel central dividido: Izquierda (Texto) / Derecha (Binario)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.55);

        txtAreaResultado = new JTextArea();
        txtAreaResultado.setEditable(false);
        txtAreaResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollTexto = new JScrollPane(txtAreaResultado);
        scrollTexto.setBorder(BorderFactory.createTitledBorder("Registros Recuperados (Character Streams)"));
        splitPane.setLeftComponent(scrollTexto);

        // Panel binario (Byte Streams)
        JPanel panelBinario = new JPanel(new BorderLayout(5, 5));
        panelBinario.setBorder(BorderFactory.createTitledBorder("Documento / Evidencia Adjunta (Byte Streams)"));

        JPanel panelControlesBinarios = new JPanel(new GridLayout(3, 1, 4, 4));
        JButton btnSeleccionar = new JButton("1. Seleccionar Imagen o PDF");
        btnSeleccionar.addActionListener(e -> seleccionarArchivoBinario());

        JButton btnGuardarBinario = new JButton("2. Guardar Copia (FileOutputStream)");
        btnGuardarBinario.addActionListener(e -> guardarArchivoBinario());

        JButton btnLeerBinario = new JButton("3. Recuperar y Ver (FileInputStream)");
        btnLeerBinario.addActionListener(e -> leerArchivoBinario());

        panelControlesBinarios.add(btnSeleccionar);
        panelControlesBinarios.add(btnGuardarBinario);
        panelControlesBinarios.add(btnLeerBinario);

        lblArchivoSeleccionado = new JLabel("Sin archivo seleccionado", SwingConstants.CENTER);
        lblVistaPreviaBinaria = new JLabel("Vista previa / Estado del archivo", SwingConstants.CENTER);

        panelBinario.add(panelControlesBinarios, BorderLayout.NORTH);
        panelBinario.add(lblVistaPreviaBinaria, BorderLayout.CENTER);
        panelBinario.add(lblArchivoSeleccionado, BorderLayout.SOUTH);

        splitPane.setRightComponent(panelBinario);

        // Botón regresar
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRegresar = new JButton("Regresar al Menú Principal");
        btnRegresar.addActionListener(e -> cardLayout.show(panelContenedor, "INICIO"));
        panelSur.add(btnRegresar);

        panel.add(panelFormulario, BorderLayout.NORTH);
        panel.add(splitPane, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    // --- OPERACIONES DE CHARACTER STREAMS ---
    private void guardarObservacionTexto() {
        String mat = txtMatricula.getText().trim();
        String nom = txtNombre.getText().trim();
        String asig = txtAsignatura.getText().trim();
        String obs = txtObservacion.getText().trim();

        if (mat.isEmpty() || nom.isEmpty() || asig.isEmpty() || obs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos de texto son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (FileWriter writer = new FileWriter(RUTA_TEXTO, StandardCharsets.UTF_8, true)) {
            writer.write("========================================\n");
            writer.write("Matrícula: " + mat + "\n");
            writer.write("Estudiante: " + nom + "\n");
            writer.write("Asignatura: " + asig + "\n");
            writer.write("Observación: " + obs + "\n");
            writer.write("========================================\n\n");
            JOptionPane.showMessageDialog(this, "Observación guardada en " + RUTA_TEXTO + " mediante FileWriter.");
            txtObservacion.setText("");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al escribir archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void leerObservacionesTexto() {
        File archivo = new File(RUTA_TEXTO);
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "Aún no existe el archivo " + RUTA_TEXTO, "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder contenido = new StringBuilder();
        try (FileReader reader = new FileReader(archivo, StandardCharsets.UTF_8)) {
            int caracter;
            while ((caracter = reader.read()) != -1) {
                contenido.append((char) caracter);
            }
            txtAreaResultado.setText(contenido.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- OPERACIONES DE BYTE STREAMS ---
    private void seleccionarArchivoBinario() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (JPG, PNG) o PDF", "jpg", "jpeg", "png", "pdf"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            archivoBinarioOrigen = chooser.getSelectedFile();
            lblArchivoSeleccionado.setText("Seleccionado: " + archivoBinarioOrigen.getName());
        }
    }

    private void guardarArchivoBinario() {
        if (archivoBinarioOrigen == null || !archivoBinarioOrigen.exists()) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una imagen o documento PDF.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File destino = new File(CARPETA_ADJUNTOS, "respaldo_" + archivoBinarioOrigen.getName());
        try (FileInputStream fis = new FileInputStream(archivoBinarioOrigen);
             FileOutputStream fos = new FileOutputStream(destino)) {
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }
            JOptionPane.showMessageDialog(this, "Archivo binario guardado mediante FileOutputStream en:\n" + destino.getPath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar binario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void leerArchivoBinario() {
        if (archivoBinarioOrigen == null) {
            JOptionPane.showMessageDialog(this, "Selecciona primero un archivo para recuperar su respaldo.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File respaldo = new File(CARPETA_ADJUNTOS, "respaldo_" + archivoBinarioOrigen.getName());
        if (!respaldo.exists()) {
            JOptionPane.showMessageDialog(this, "Primero guarda el archivo binario en el expediente.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (FileInputStream fis = new FileInputStream(respaldo);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int leidos;
            while ((leidos = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, leidos);
            }
            byte[] datosBinarios = baos.toByteArray();

            if (respaldo.getName().toLowerCase().endsWith(".pdf")) {
                lblVistaPreviaBinaria.setIcon(null);
                lblVistaPreviaBinaria.setText("PDF recuperado (" + datosBinarios.length + " bytes). Abriendo...");
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(respaldo);
                }
            } else {
                ImageIcon icono = new ImageIcon(datosBinarios);
                Image escalada = icono.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
                lblVistaPreviaBinaria.setText("");
                lblVistaPreviaBinaria.setIcon(new ImageIcon(escalada));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al recuperar binario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ExpedientesStreams().setVisible(true));
    }
}
