package git.jar2dll.gui;

import git.jar2dll.Jar;
import git.jar2dll.transformers.ASCIIArtTransformer;
import git.jar2dll.utils.ImageToAscii;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageInjectorGUI extends JFrame {
    private JTextField jarFileField;
    private JList<String> imageList;
    private DefaultListModel<String> imageListModel;
    private JList<String> classList;
    private DefaultListModel<String> classListModel;
    private JButton selectJarButton;
    private JButton addImagesButton;
    private JButton removeImageButton;
    private JButton clearImagesButton;
    private JButton selectAllClassesButton;
    private JButton deselectAllClassesButton;
    private JButton processButton;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JSpinner asciiWidthSpinner;
    private JCheckBox useOriginalSizeCheckBox;

    private File selectedJarFile;
    private List<File> selectedImages;
    private Jar currentJar;

    public ImageInjectorGUI() {
        selectedImages = new ArrayList<>();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Image Injector - ASCII Art to JAR | github.com/432Fowin/ImageInjector");
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        jarFileField = new JTextField();
        jarFileField.setEditable(false);
        selectJarButton = new JButton("Select JAR File");

        imageListModel = new DefaultListModel<>();
        imageList = new JList<>(imageListModel);
        imageList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addImagesButton = new JButton("Add Images");
        removeImageButton = new JButton("Remove Selected");
        clearImagesButton = new JButton("Clear All");

        classListModel = new DefaultListModel<>();
        classList = new JList<>(classListModel);
        classList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        selectAllClassesButton = new JButton("Select All");
        deselectAllClassesButton = new JButton("Deselect All");

        asciiWidthSpinner = new JSpinner(new SpinnerNumberModel(200, 50, 1000, 10));

        useOriginalSizeCheckBox = new JCheckBox("Use Original Image Size (1:1 pixel mapping)", true);

        processButton = new JButton("Process JAR");
        processButton.setEnabled(false);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel jarPanel = new JPanel(new BorderLayout());
        jarPanel.setBorder(new TitledBorder("JAR File"));
        jarPanel.add(jarFileField, BorderLayout.CENTER);
        jarPanel.add(selectJarButton, BorderLayout.EAST);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        topPanel.add(jarPanel, gbc);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel imagesPanel = new JPanel(new BorderLayout());
        imagesPanel.setBorder(new TitledBorder("Selected Images"));
        imagesPanel.add(new JScrollPane(imageList), BorderLayout.CENTER);

        JPanel imageButtonsPanel = new JPanel(new BorderLayout());
        imageButtonsPanel.add(addImagesButton, BorderLayout.NORTH);

        JPanel bottomButtonsPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        bottomButtonsPanel.add(removeImageButton);
        bottomButtonsPanel.add(clearImagesButton);

        imageButtonsPanel.add(bottomButtonsPanel, BorderLayout.SOUTH);
        imagesPanel.add(imageButtonsPanel, BorderLayout.SOUTH);

        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBorder(new TitledBorder("Settings"));
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(useOriginalSizeCheckBox, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.NONE;
        settingsPanel.add(new JLabel("Custom ASCII Width:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(asciiWidthSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(processButton, gbc);

        JPanel classesPanel = new JPanel(new BorderLayout());
        classesPanel.setBorder(new TitledBorder("Classes to Inject"));
        classesPanel.add(new JScrollPane(classList), BorderLayout.CENTER);

        JPanel classButtonsPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        classButtonsPanel.add(selectAllClassesButton);
        classButtonsPanel.add(deselectAllClassesButton);
        classesPanel.add(classButtonsPanel, BorderLayout.SOUTH);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(imagesPanel, BorderLayout.NORTH);
        leftPanel.add(classesPanel, BorderLayout.CENTER);
        leftPanel.add(settingsPanel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel, BorderLayout.WEST);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(new TitledBorder("Log"));
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        centerPanel.add(logPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        selectJarButton.addActionListener(e -> selectJarFile());
        addImagesButton.addActionListener(e -> addImages());
        removeImageButton.addActionListener(e -> removeSelectedImages());
        clearImagesButton.addActionListener(e -> clearAllImages());
        selectAllClassesButton.addActionListener(e -> selectAllClasses());
        deselectAllClassesButton.addActionListener(e -> deselectAllClasses());
        processButton.addActionListener(e -> processJar());

        useOriginalSizeCheckBox.addActionListener(e -> {
            boolean useOriginal = useOriginalSizeCheckBox.isSelected();
            asciiWidthSpinner.setEnabled(!useOriginal);
        });

        classList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateProcessButtonState();
            }
        });

        asciiWidthSpinner.setEnabled(!useOriginalSizeCheckBox.isSelected());
    }

    private void selectJarFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JAR Files", "jar"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedJarFile = fileChooser.getSelectedFile();
            jarFileField.setText(selectedJarFile.getAbsolutePath());
            log("Selected JAR file: " + selectedJarFile.getName());
            loadClassesFromJar();
        }
    }

    private void loadClassesFromJar() {
        if (selectedJarFile == null) {
            return;
        }

        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Loading classes from JAR...");

                currentJar = new Jar();
                currentJar.loadJar(selectedJarFile);

                List<String> classNames = currentJar.getClassNamesList();

                SwingUtilities.invokeLater(() -> {
                    classListModel.clear();
                    for (String className : classNames) {
                        classListModel.addElement(className);
                    }
                    classList.setSelectionInterval(0, classNames.size() - 1);
                });

                publish("Loaded " + classNames.size() + " classes");
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    log(message);
                }
            }

            @Override
            protected void done() {
                updateProcessButtonState();
                try {
                    get();
                } catch (Exception e) {
                    log("Error loading classes: " + e.getMessage());
                    JOptionPane.showMessageDialog(ImageInjectorGUI.this,
                        "Error loading classes from JAR: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private void addImages() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif", "bmp"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            int addedCount = 0;

            for (File file : files) {
                if (!selectedImages.contains(file)) {
                    selectedImages.add(file);
                    imageListModel.addElement(file.getName());
                    addedCount++;
                }
            }

            updateProcessButtonState();
            log("Added " + addedCount + " image(s). Total: " + selectedImages.size());
        }
    }

    private void removeSelectedImages() {
        int[] selectedIndices = imageList.getSelectedIndices();
        if (selectedIndices.length == 0) {
            JOptionPane.showMessageDialog(this, "Please select images to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = selectedIndices.length - 1; i >= 0; i--) {
            int index = selectedIndices[i];
            selectedImages.remove(index);
            imageListModel.remove(index);
        }

        updateProcessButtonState();
        log("Removed " + selectedIndices.length + " image(s). Remaining: " + selectedImages.size());
    }

    private void clearAllImages() {
        if (selectedImages.isEmpty()) {
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear all images?",
            "Clear All Images",
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            selectedImages.clear();
            imageListModel.clear();
            updateProcessButtonState();
            log("Cleared all images");
        }
    }

    private void selectAllClasses() {
        if (classListModel.getSize() > 0) {
            classList.setSelectionInterval(0, classListModel.getSize() - 1);
            log("Selected all " + classListModel.getSize() + " classes");
        }
    }

    private void deselectAllClasses() {
        classList.clearSelection();
        log("Deselected all classes");
    }

    private void updateProcessButtonState() {
        boolean hasJar = selectedJarFile != null;
        boolean hasImages = !selectedImages.isEmpty();
        boolean hasSelectedClasses = classList.getSelectedIndices().length > 0;

        processButton.setEnabled(hasJar && hasImages && hasSelectedClasses);
    }

    private void processJar() {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                processButton.setEnabled(false);
                progressBar.setIndeterminate(true);

                publish("Starting image processing...");

                boolean useOriginalSize = useOriginalSizeCheckBox.isSelected();
                int asciiWidth = (Integer) asciiWidthSpinner.getValue();
                String[] asciiImages = new String[selectedImages.size()];

                for (int i = 0; i < selectedImages.size(); i++) {
                    File imageFile = selectedImages.get(i);
                    publish("Converting image " + (i + 1) + "/" + selectedImages.size() + ": " + imageFile.getName());

                    try {
                        if (useOriginalSize) {
                            asciiImages[i] = ImageToAscii.convertImageToAsciiOriginalSize(imageFile);
                            publish("Using original size for " + imageFile.getName());
                        } else {
                            asciiImages[i] = ImageToAscii.convertImageToAscii(imageFile, asciiWidth);
                            publish("Using custom width " + asciiWidth + " for " + imageFile.getName());
                        }

                        if (asciiImages[i].length() > 100000) {
                            publish("Warning: " + imageFile.getName() + " produced large ASCII (" +
                                   (asciiImages[i].length() / 1000) + "KB) - will be split into chunks");
                        }
                    } catch (Exception e) {
                        publish("Error converting " + imageFile.getName() + ": " + e.getMessage());
                        throw e;
                    }
                }

                Set<String> selectedClassNames = new HashSet<>();
                int[] selectedIndices = classList.getSelectedIndices();
                for (int index : selectedIndices) {
                    selectedClassNames.add(classListModel.getElementAt(index));
                }

                publish("Found " + currentJar.getClassCount() + " classes in JAR");
                publish("Selected " + selectedClassNames.size() + " classes for injection");

                ASCIIArtTransformer transformer = new ASCIIArtTransformer(currentJar, asciiImages, selectedClassNames);
                currentJar.addTransformer(transformer);

                publish("Injecting ASCII art into selected classes...");
                currentJar.transform(selectedClassNames);

                String outputPath = selectedJarFile.getAbsolutePath();
                String baseName = outputPath.substring(0, outputPath.lastIndexOf('.'));
                String extension = outputPath.substring(outputPath.lastIndexOf('.'));
                File outputFile = new File(baseName + "_injected" + extension);

                publish("Saving modified JAR...");
                currentJar.saveJar(outputFile);

                publish("Process completed successfully!");
                publish("Output file: " + outputFile.getAbsolutePath());
                publish("Processed " + transformer.getProcessedClassCount() + " classes");

                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String message : chunks) {
                    log(message);
                }
            }

            @Override
            protected void done() {
                processButton.setEnabled(true);
                progressBar.setIndeterminate(false);
                progressBar.setValue(100);

                try {
                    get();
                    JOptionPane.showMessageDialog(ImageInjectorGUI.this,
                        "JAR processing completed successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    log("Error: " + e.getMessage());
                    JOptionPane.showMessageDialog(ImageInjectorGUI.this,
                        "Error processing JAR: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + java.time.LocalTime.now().toString() + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
}
