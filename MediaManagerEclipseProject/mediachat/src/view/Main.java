
package view;

import controller.MediaController;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        String storage = "data";
        MediaController controller = new MediaController(storage);
        SwingUtilities.invokeLater(() -> {
            MediaView view = new MediaView(controller);
            view.setVisible(true);
        });
    }
}
