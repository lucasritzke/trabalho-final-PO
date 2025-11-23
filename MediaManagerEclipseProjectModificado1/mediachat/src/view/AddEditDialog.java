
package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Dialog para adicionar/editar mídias. Suporta Film, Music, Book.
 */
public class AddEditDialog extends JDialog {
    private boolean saved = false;
    private Media media;

    private JTextField pathField = new JTextField(30);
    private JTextField sizeField = new JTextField(10);
    private JTextField titleField = new JTextField(20);
    private JTextField categoryField = new JTextField(15);

    // específicos
    private JTextField spec1 = new JTextField(15);
    private JTextField spec2 = new JTextField(15);

    private JComboBox<String> typeCombo = new JComboBox<>(new String[] {"Film","Music","Book"});

    public AddEditDialog(Frame owner, Media existing) {
        super(owner, true);
        setTitle(existing == null ? "Adicionar Mídia" : "Editar Mídia");
        setSize(600,300);
        setLocationRelativeTo(owner);
        init(existing);
    }

    private void init(Media existing) {
        JPanel p = new JPanel(new GridLayout(0,2));
        p.add(new JLabel("Tipo:")); p.add(typeCombo);
        p.add(new JLabel("Caminho do arquivo:")); 
        JPanel pathPanel = new JPanel();
        pathPanel.add(pathField);
        JButton browse = new JButton("Procurar");
        browse.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                pathField.setText(f.getAbsolutePath());
                sizeField.setText(String.valueOf(f.length()));
            }
        });
        pathPanel.add(browse);
        p.add(pathPanel);

        p.add(new JLabel("Tamanho (bytes):")); p.add(sizeField);
        p.add(new JLabel("Título:")); p.add(titleField);
        p.add(new JLabel("Categoria:")); p.add(categoryField);

        p.add(new JLabel("Específico 1 (duração ou páginas):")); p.add(spec1);
        p.add(new JLabel("Específico 2 (artista/idioma/autores):")); p.add(spec2);

        add(p, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton save = new JButton("Salvar");
        JButton cancel = new JButton("Cancelar");
        bottom.add(save); bottom.add(cancel);
        add(bottom, BorderLayout.SOUTH);

        if (existing != null) {
            // preencher campos
            pathField.setText(existing.getLocalPath());
            sizeField.setText(String.valueOf(existing.getSizeBytes()));
            titleField.setText(existing.getTitle());
            categoryField.setText(existing.getCategory());
            spec1.setText(String.valueOf(existing.getDurationValue()));
            spec2.setText(existing.displaySpecificAttributes());
            typeCombo.setSelectedItem(existing.getClass().getSimpleName());
            typeCombo.setEnabled(false); // não alterar tipo no editar
        }

        save.addActionListener(e -> {
            try {
                String tipo = (String) typeCombo.getSelectedItem();
                String path = pathField.getText();
                long size = Long.parseLong(sizeField.getText());
                String title = titleField.getText();
                String cat = categoryField.getText();
                if (tipo.equals("Film")) {
                    int dur = Integer.parseInt(spec1.getText());
                    String lang = spec2.getText();
                    media = new Film(path, size, title, cat, dur, lang);
                } else if (tipo.equals("Music")) {
                    int dur = Integer.parseInt(spec1.getText());
                    String artist = spec2.getText();
                    media = new Music(path, size, title, cat, dur, artist);
                } else {
                    int pages = Integer.parseInt(spec1.getText());
                    String authors = spec2.getText();
                    media = new Book(path, size, title, cat, pages, authors);
                }
                saved = true;
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        cancel.addActionListener(e -> {
            saved = false;
            setVisible(false);
        });
    }

    public boolean isSaved() { return saved; }
    public Media getMedia() { return media; }
}
