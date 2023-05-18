package controller;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileManager{

    /*
     * The action to export data.
     */
    public static void exportData(Component parentComponent) {
        try {
            File dataFolder = new File("data");
            File[] xmlFiles = dataFolder.listFiles((dir, name) -> name.endsWith(".xml"));

            if (xmlFiles.length == 0) {
                JOptionPane.showMessageDialog(parentComponent, "No XML files found in the data folder.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select Destination for Exported ZIP File");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setSelectedFile(new File("export.zip"));

            int result = fileChooser.showSaveDialog(parentComponent);

            if (result == JFileChooser.APPROVE_OPTION) {
                File exportFile = fileChooser.getSelectedFile();

                FileOutputStream fos = new FileOutputStream(exportFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                byte[] buffer = new byte[1024];

                for (File xmlFile : xmlFiles) {
                    ZipEntry zipEntry = new ZipEntry(xmlFile.getName());
                    zos.putNextEntry(zipEntry);

                    FileInputStream fis = new FileInputStream(xmlFile);
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();

                    zos.closeEntry();
                }

                zos.close();
                fos.close();

                JOptionPane.showMessageDialog(parentComponent, "Data exported successfully.", "Export",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(parentComponent, "An error occurred during export: " + e.getMessage(), "Export Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    /*
     * The action to import data.
     */
    public static void importData(Component parentComponent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Select Zip File to Import");
        int result = fileChooser.showOpenDialog(parentComponent);

        if (result == JFileChooser.APPROVE_OPTION) {
            File importFile = fileChooser.getSelectedFile();

            try {
                // Create a folder to extract the contents of the zip file
                File extractionFolder = new File("data");
                if (!extractionFolder.exists()) {
                    extractionFolder.mkdir();
                }

                // Extract the zip file contents
                FileInputStream fis = new FileInputStream(importFile);
                ZipInputStream zis = new ZipInputStream(fis);
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File extractedFile = new File(extractionFolder, fileName);

                    FileOutputStream fos = new FileOutputStream(extractedFile);
                    byte[] buffer = new byte[1024];
                    int length;

                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }

                    fos.close();
                    zipEntry = zis.getNextEntry();
                }

                zis.close();
                fis.close();

                JOptionPane.showMessageDialog(parentComponent, "Data imported successfully.", "Import",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(parentComponent, "An error occurred during import: " + e.getMessage(),
                        "Import Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}