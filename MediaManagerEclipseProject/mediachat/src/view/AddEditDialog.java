
package view;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Book;
import model.Film;
import model.Media;
import model.Music;


public class AddEditDialog extends JDialog {
  
	private static final long serialVersionUID = 1L;
	private boolean saved = false;
    private Media media;

    private JTextField pathField = new JTextField(30);
    private JTextField sizeField = new JTextField(10);
    private JTextField titleField = new JTextField(20);
    private JTextField categoryField = new JTextField(15);

   
    private JTextField spec1 = new JTextField(15);
    private JTextField spec2 = new JTextField(15);

    private JComboBox<String> typeCombo = new JComboBox<>(new String[] {"Filme","Musica","Livro"});

    public AddEditDialog(Frame owner, Media existing) {
        super(owner, true);
        setTitle(existing == null ? "Adicionar Mídia" : "Editar Mídia");
        setSize(692,400);
        setLocationRelativeTo(owner);
        init(existing);
    }
    
    private void init(Media existing) {
        JPanel p = new JPanel();
        p.setLayout(null);
        JLabel lblTipoArquivo = new JLabel("Tipo arquivo:");
        lblTipoArquivo.setBounds(0, 3, 338, 46);
        p.add(lblTipoArquivo); typeCombo.setModel(new DefaultComboBoxModel<String>(new String[] {"Filme", "Musica", "Livro"}));
 typeCombo.setBounds(338, 7, 338, 38);
 p.add(typeCombo);
        JLabel lblBuscarSeuArquivo = new JLabel("Buscar seu arquivo:");
        lblBuscarSeuArquivo.setBounds(0, 49, 338, 46);
        p.add(lblBuscarSeuArquivo); 
        JPanel pathPanel = new JPanel();
        pathPanel.setBounds(338, 45, 338, 61);
        pathPanel.add(pathField);
        JButton browse = new JButton("Procurar");
        browse.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            
            String[] docs = new String[] {"pdf", "Epub"};
            FileNameExtensionFilter filterLivro = new FileNameExtensionFilter("Livros", docs);
            fc.setFileFilter(filterLivro);
            
            String[] docs1 = new String[] {"MP3"};
            FileNameExtensionFilter filterMusica = new FileNameExtensionFilter("Musicas", docs1);
            fc.setFileFilter(filterMusica);
            
            String[] docs2 = new String[] {"MP4", "MKV"};
            FileNameExtensionFilter filterFilme = new FileNameExtensionFilter("Filmes", docs2);
            fc.setFileFilter(filterFilme);
            
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                pathField.setText(f.getAbsolutePath());
                sizeField.setText(String.valueOf(f.length()));
                }});
        pathPanel.add(browse);
        p.add(pathPanel);

        JLabel label_2 = new JLabel("Tamanho (bytes):");
        label_2.setBounds(0, 95, 338, 46);
        p.add(label_2); sizeField.setBounds(338, 103, 338, 30);
 p.add(sizeField);
        JLabel label_3 = new JLabel("Título:");
        label_3.setBounds(0, 141, 338, 46);
        p.add(label_3); titleField.setBounds(338, 149, 338, 30);
 p.add(titleField);
        JLabel label_4 = new JLabel("Categoria:");
        label_4.setBounds(0, 187, 338, 46);
        p.add(label_4); categoryField.setBounds(338, 195, 338, 30);
 p.add(categoryField);

        JLabel lblTempoduraoOu = new JLabel("Tempo (duração ou páginas):");
        lblTempoduraoOu.setBounds(0, 233, 338, 46);
        p.add(lblTempoduraoOu); spec1.setBounds(338, 241, 338, 30);
 p.add(spec1);
        JLabel lblInformaesAdicionaisidiomaartistaautores = new JLabel("Informações adicionais (idioma/artista/autores):");
        lblInformaesAdicionaisidiomaartistaautores.setBounds(0, 279, 338, 46);
        p.add(lblInformaesAdicionaisidiomaartistaautores); spec2.setBounds(338, 287, 338, 30);
 p.add(spec2);

        getContentPane().add(p, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton save = new JButton("Salvar");
        JButton cancel = new JButton("Cancelar");
        bottom.add(save); bottom.add(cancel);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        if (existing != null) {
            pathField.setText(existing.getLocalPath());
            sizeField.setText(String.valueOf(existing.getSizeBytes()));
            titleField.setText(existing.getTitle());
            categoryField.setText(existing.getCategory());
            spec1.setText(String.valueOf(existing.getDurationValue()));
            spec2.setText(existing.displaySpecificAttributes());
            typeCombo.setSelectedItem(existing.getClass().getSimpleName());
            typeCombo.setEnabled(false); // Serve para não alterar o tipo quando edita novamente
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
