package controller;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import view.FileView;
import model.FileModel;

public class FileController {

    private FileView view;
    private FileModel model;

    public FileController(FileView view) {
        this.view = view;
        this.model = new FileModel();
        this.view.setController(this);
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getBtnOpenPath().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFolder();
            }
        });

        view.getTree().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTree2MouseClicked(evt);
            }
        });
    }

    public void openFolder() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(view);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            view.getTxtPath().setText(selectedFile.getAbsolutePath());
            listFiles(selectedFile);
        }
    }

    public void listFiles(File file) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(file.getName());
        populateNode(file, rootNode);
        view.getTree().setModel(new DefaultTreeModel(rootNode));
    }

    private void populateNode(File file, DefaultMutableTreeNode parentNode) {
        for (File child : file.listFiles()) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child.getName());
            parentNode.add(childNode);
            if (child.isDirectory()) {
                populateNode(child, childNode);
            }
        }
    }

    private void jTree2MouseClicked(java.awt.event.MouseEvent evt) {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) view.getTree().getLastSelectedPathComponent();
        if (selectedNode != null) {
            Object nodeInfo = selectedNode.getUserObject();
            if (nodeInfo instanceof File) {
                File selectedFile = (File) nodeInfo;
                if (!selectedFile.isDirectory()) {
                    displayFileContent(selectedFile);
                }
            }
        }
    }

    private void displayFileContent(File selectedFile) {
        String fileContent = model.readFileContent(selectedFile);
        view.getTextArea().setText(fileContent);
    }
    public void displaySelectedFile() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) view.getTree().getLastSelectedPathComponent();
        if (selectedNode != null) {
            Object nodeInfo = selectedNode.getUserObject();
            if (nodeInfo instanceof File) {
                File selectedFile = (File) nodeInfo;
                if (!selectedFile.isDirectory()) {
                    displayFileContent(selectedFile);
                }
            }
        }
    }
    
}
