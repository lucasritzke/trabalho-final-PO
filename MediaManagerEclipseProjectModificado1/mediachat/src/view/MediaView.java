
package view;

import controller.MediaController;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

/**
 * Visual simples em Swing. Contém botões para adicionar/editar/remover e uma tabela para exibir mídias.
 */
public class MediaView extends JFrame {
    private MediaController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    public MediaView(MediaController controller) {
        super("Gerenciador de Mídias - TPOO");
        this.controller = controller;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        initComponents();
        loadTable(controller.listAll());
    }

    private void initComponents() {
        JPanel top = new JPanel();
        JButton addBtn = new JButton("Adicionar");
        JButton editBtn = new JButton("Editar");
        JButton removeBtn = new JButton("Remover");
        JButton refreshBtn = new JButton("Atualizar");
        JButton sortTitle = new JButton("Ordenar por título");
        JButton sortDuration = new JButton("Ordenar por duração");

        top.add(addBtn);
        top.add(editBtn);
        top.add(removeBtn);
        top.add(sortTitle);
        top.add(sortDuration);
        top.add(refreshBtn);

        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[] {"Título","Tipo","Categoria","Duração","Específico","Caminho","Tamanho (bytes)"}, 0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ações
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        removeBtn.addActionListener(e -> removeSelected());
        refreshBtn.addActionListener(e -> loadTable(controller.listAll()));
        sortTitle.addActionListener(e -> loadTable(controller.sortByTitle()));
        sortDuration.addActionListener(e -> loadTable(controller.sortByDuration()));
    }

    private void loadTable(List<Media> list) {
        tableModel.setRowCount(0);
        for (Media m : list) {
            tableModel.addRow(new Object[] {
                m.getTitle(),
                m.getClass().getSimpleName(),
                m.getCategory(),
                m.getDurationValue(),
                m.displaySpecificAttributes(),
                m.getLocalPath(),
                m.getSizeBytes()
            });
        }
    }

    private void showAddDialog() {
        AddEditDialog dlg = new AddEditDialog(this, null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            try {
                controller.addMedia(dlg.getMedia());
                loadTable(controller.listAll());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
            }
        }
    }

    private void showEditDialog() {
        int sel = table.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia."); return; }
        String title = (String) tableModel.getValueAt(sel, 0);
        Media original = null;
        for (Media m : controller.listAll()) if (m.getTitle().equals(title)) { original = m; break; }
        if (original == null) return;
        AddEditDialog dlg = new AddEditDialog(this, original);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            try {
                controller.editMedia(original, dlg.getMedia());
                loadTable(controller.listAll());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao editar: " + ex.getMessage());
            }
        }
    }

    private void removeSelected() {
        int sel = table.getSelectedRow();
        if (sel < 0) { JOptionPane.showMessageDialog(this, "Selecione uma mídia."); return; }
        String title = (String) tableModel.getValueAt(sel, 0);
        Media found = null;
        for (Media m : controller.listAll()) if (m.getTitle().equals(title)) { found = m; break; }
        if (found == null) return;
        int r = JOptionPane.showConfirmDialog(this, "Remover " + found.getTitle() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            controller.removeMedia(found);
            loadTable(controller.listAll());
        }
    }
}
