# 🔧 ImageInjector Technical Specifications

[![Back to README](https://img.shields.io/badge/←-Back%20to%20README-blue)](README.md)

---

## 📋 System Architecture

### Core Components

```
ImageInjector/
├── 🎯 Main.java                    # Application entry point
├── 🖥️ ImageInjectorGUI.java        # User interface controller
├── 📦 Jar.java                     # JAR file manipulation
├── 🔄 ASCIIArtTransformer.java     # Bytecode transformation
├── 🖼️ ImageToAscii.java            # Image conversion engine
├── 🔧 NameUtils.java               # Utility functions
└── 🎨 Transformer.java             # Base transformer interface
```

### Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 21+ |
| **GUI Framework** | Swing | Built-in |
| **Bytecode Manipulation** | ObjectWeb ASM | 9.6 |
| **Build System** | Gradle | 7.0+ |
| **Image Processing** | Java AWT | Built-in |

---

## 🏗️ Class Architecture

### Main Application Flow
```java
Main.java
├── UIManager.setLookAndFeel()
└── SwingUtilities.invokeLater()
    └── new ImageInjectorGUI().setVisible(true)
```

### GUI Component Hierarchy
```
JFrame (ImageInjectorGUI)
├── BorderLayout.NORTH
│   └── JAR File Selection Panel
├── BorderLayout.CENTER
│   ├── BorderLayout.WEST
│   │   ├── Images Panel (JList + Buttons)
│   │   ├── Classes Panel (JList + Buttons)
│   │   └── Settings Panel (Spinners + Checkboxes)
│   └── BorderLayout.CENTER
│       └── Log Panel (JTextArea)
└── BorderLayout.SOUTH
    └── Progress Bar
```

---

## 🔄 Processing Pipeline

### 1. JAR Loading Phase
```java
Jar.loadJar(File jarFile)
├── JarFile.entries() iteration
├── ClassReader.accept() for .class files
├── InputStream.readAllBytes() for resources
└── Storage in HashMap<String, ClassNode>
```

### 2. Image Conversion Phase
```java
ImageToAscii.convertImageToAscii()
├── ImageIO.read() - Load image
├── Smart resizing algorithm
│   ├── Check MAX_DIMENSION (800px)
│   ├── Check MAX_TOTAL_CHARS (400,000)
│   └── Apply scaling if needed
├── Graphics2D rendering with quality hints
└── Pixel-to-ASCII mapping
```

### 3. Bytecode Transformation Phase
```java
ASCIIArtTransformer.visit(ClassNode)
├── ASCII string splitting by lines
├── FieldNode creation for each line
│   ├── ACC_PUBLIC + ACC_STATIC + ACC_FINAL
│   ├── Random field name generation
│   └── String value assignment
└── ClassNode.fields.add()
```

### 4. JAR Output Phase
```java
Jar.saveJar(File outputFile)
├── JarOutputStream creation
├── ClassWriter.toByteArray() for classes
├── Resource copying
└── ZIP entry creation
```

---

## 🎨 ASCII Conversion Algorithm

### Character Mapping
```java
private static final String ASCII_CHARS = "@#S%?*+;:,. ";
// Density: High ────────────────────────► Low
//          @    #    S    %    ?    *    +    ;    :    ,    .    (space)
```

### Grayscale Calculation
```java
// Luminance formula (ITU-R BT.709)
int gray = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
gray = 255 - gray; // Invert for proper ASCII mapping
```

### Quality Enhancement
```java
Graphics2D g2d = resizedImage.createGraphics();
g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                     RenderingHints.VALUE_INTERPOLATION_BICUBIC);
g2d.setRenderingHint(RenderingHints.KEY_RENDERING, 
                     RenderingHints.VALUE_RENDER_QUALITY);
g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                     RenderingHints.VALUE_ANTIALIAS_ON);
```

---

## 🔧 Configuration Parameters

### Image Processing Limits
```java
public class ImageToAscii {
    private static final int MAX_DIMENSION = 800;      // Max width/height
    private static final int MAX_TOTAL_CHARS = 400000; // Max ASCII characters
    private static final int DEFAULT_WIDTH = 100;      // Default ASCII width
}
```

### Field Generation
```java
public class ASCIIArtTransformer {
    // Field modifiers for generated fields
    private static final int FIELD_MODIFIERS = 
        Opcodes.ACC_PUBLIC + 
        Opcodes.ACC_STATIC + 
        Opcodes.ACC_FINAL;
}
```

### GUI Settings
```java
public class ImageInjectorGUI {
    private static final int DEFAULT_ASCII_WIDTH = 200;
    private static final int MIN_ASCII_WIDTH = 50;
    private static final int MAX_ASCII_WIDTH = 1000;
    private static final int ASCII_WIDTH_STEP = 10;
}
```

---

## 📊 Performance Characteristics

### Memory Usage Patterns
```
Base Application:     ~50MB
Per Image Loading:    ~(width × height × 4) bytes
ASCII Conversion:     ~(ascii_length × 2) bytes
JAR Processing:       ~(jar_size × 2) bytes
```

### Processing Time Complexity
```
Image Conversion:     O(width × height)
Class Processing:     O(classes × ascii_lines)
JAR I/O:             O(jar_size)
Total:               O(images × pixels + classes × lines + jar_size)
```

### Optimization Strategies
1. **Lazy Loading**: Classes loaded only when JAR selected
2. **Background Processing**: SwingWorker for non-blocking operations
3. **Memory Management**: Automatic image resizing for large files
4. **Chunked Processing**: Large ASCII split into manageable pieces

---

## 🔒 Security Model

### File Access Permissions
```
Required Permissions:
├── READ: Input JAR file
├── READ: Image files
├── WRITE: Output directory
└── EXECUTE: Java reflection (ASM)

Not Required:
├── Network access
├── System property modification
└── Native library loading
```

### JAR Integrity
- **Original signatures**: Removed during processing
- **Manifest preservation**: MANIFEST.MF copied unchanged
- **Resource integrity**: Non-class files preserved exactly
- **Class structure**: Only static fields added, no method modification

---

## 🧪 Testing Framework

### Unit Test Categories
```java
// Image conversion tests
@Test void testImageToAsciiConversion()
@Test void testLargeImageResizing()
@Test void testInvalidImageHandling()

// JAR manipulation tests  
@Test void testJarLoading()
@Test void testClassExtraction()
@Test void testJarSaving()

// Transformation tests
@Test void testASCIIFieldInjection()
@Test void testFieldNameGeneration()
@Test void testMultipleImageDistribution()
```

### Integration Test Scenarios
1. **End-to-End Processing**: Complete workflow with sample files
2. **Large File Handling**: Performance with 10MB+ JARs
3. **Error Recovery**: Handling corrupted inputs gracefully
4. **Memory Stress**: Processing multiple large images

---

## 📈 Scalability Considerations

### Current Limitations
```
Max Image Size:       Limited by available memory
Max JAR Size:         Limited by available memory × 2
Max ASCII Length:     400,000 characters per image
Concurrent Processing: Single-threaded (GUI constraint)
```

### Future Enhancements
- **Streaming Processing**: For very large JARs
- **Parallel Image Conversion**: Multi-threaded ASCII generation
- **Disk-based Caching**: Temporary file usage for large operations
- **Progressive Loading**: Incremental class list population

---

## 🔌 API Reference

### Core Classes

#### `Jar.java`
```java
public class Jar {
    public void loadJar(File jarFile) throws IOException
    public void addTransformer(Transformer transformer)
    public void transform(Set<String> selectedClassNames)
    public void saveJar(File outputFile) throws IOException
    public List<String> getClassNamesList()
    public int getClassCount()
}
```

#### `ImageToAscii.java`
```java
public class ImageToAscii {
    public static String convertImageToAscii(File imageFile) throws IOException
    public static String convertImageToAscii(File imageFile, int width) throws IOException
    public static String convertImageToAsciiOriginalSize(File imageFile) throws IOException
    public static String[] convertImagesToAsciiArray(File[] imageFiles) throws IOException
}
```

#### `ASCIIArtTransformer.java`
```java
public class ASCIIArtTransformer extends Transformer {
    public ASCIIArtTransformer(Jar obf, String[] asciiImages)
    public ASCIIArtTransformer(Jar obf, String[] asciiImages, Set<String> selectedClassNames)
    public void visit(ClassNode classNode)
    public int getProcessedClassCount()
}
```

---

## 🔍 Debugging & Diagnostics

### Logging Levels
```java
// GUI log output includes:
[timestamp] JAR file selection
[timestamp] Image conversion progress  
[timestamp] Class processing status
[timestamp] Error messages with stack traces
[timestamp] Performance metrics
```

### Common Debug Points
1. **Image Loading**: Verify supported formats
2. **ASCII Generation**: Check character mapping
3. **Class Selection**: Validate class name matching
4. **Field Injection**: Confirm bytecode modification
5. **JAR Output**: Verify file integrity

---

## 📋 Build Configuration

### Gradle Dependencies
```gradle
dependencies {
    implementation 'org.ow2.asm:asm:9.6'
    implementation 'org.ow2.asm:asm-commons:9.6'
    implementation 'org.ow2.asm:asm-tree:9.6'
    implementation 'org.ow2.asm:asm-util:9.6'
}
```

### Shadow JAR Configuration
```gradle
shadowJar {
    archiveBaseName = 'ImageInjector'
    archiveVersion = '1.0'
    archiveClassifier = 'java21-with-dependencies'
    
    manifest {
        attributes 'Main-Class': 'git.jar2dll.Main'
    }
}
```

---

**For implementation details, see source code in `src/main/java/git/jar2dll/`**
