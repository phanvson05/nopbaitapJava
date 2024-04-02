package view;

import javax.swing.*;
import java.awt.*;
import view.FileView;
import model.FileModel;
import controller.FileController;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Khởi tạo model, view và controller
            FileModel model = new FileModel();
            FileView view = new FileView();
            FileController controller = new FileController(view);

            // Hiển thị frame
            view.setVisible(true);
        });
    }
}

