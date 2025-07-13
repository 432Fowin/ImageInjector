# 💡 ImageInjector Examples & Code Snippets

[![Back to README](https://img.shields.io/badge/←-Back%20to%20README-blue)](README.md)

---

## 🎨 Real-World Examples

### Example 1: Company Logo Watermarking

**Scenario**: Add your company logo to all JAR files before distribution.

**Input Files**:
```
📁 company-branding/
├── 📄 MyApplication.jar
├── 🖼️ company-logo.png (200x50px)
└── 📄 ImageInjector-1.0-java21-with-dependencies.jar
```

**Process**:
1. Load `MyApplication.jar`
2. Add `company-logo.png`
3. Set Custom ASCII Width: 200
4. Select main classes: `Main.class`, `Application.class`
5. Process → Output: `MyApplication_injected.jar`

**Generated Code**:
```java
// In your Main.class
public static final String logo_line1 = "  ██████╗ ██████╗ ███╗   ███╗██████╗  █████╗ ███╗   ██╗██╗   ██╗";
public static final String logo_line2 = " ██╔════╝██╔═══██╗████╗ ████║██╔══██╗██╔══██╗████╗  ██║╚██╗ ██╔╝";
public static final String logo_line3 = " ██║     ██║   ██║██╔████╔██║██████╔╝███████║██╔██╗ ██║ ╚████╔╝ ";
public static final String logo_line4 = " ██║     ██║   ██║██║╚██╔╝██║██╔═══╝ ██╔══██║██║╚██╗██║  ╚██╔╝  ";
public static final String logo_line5 = " ╚██████╗╚██████╔╝██║ ╚═╝ ██║██║     ██║  ██║██║ ╚████║   ██║   ";
public static final String logo_line6 = "  ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝   ";
```

---

### Example 2: Version-Specific Easter Eggs

**Scenario**: Add different ASCII art for development, testing, and production builds.

**Development Build**:
```java
// Generated in dev build
public static final String dev_marker1 = " ██████╗ ███████╗██╗   ██╗";
public static final String dev_marker2 = " ██╔══██╗██╔════╝██║   ██║";
public static final String dev_marker3 = " ██║  ██║█████╗  ██║   ██║";
public static final String dev_marker4 = " ██║  ██║██╔══╝  ╚██╗ ██╔╝";
public static final String dev_marker5 = " ██████╔╝███████╗ ╚████╔╝ ";
public static final String dev_marker6 = " ╚═════╝ ╚══════╝  ╚═══╝  ";
```

**Production Build**:
```java
// Generated in production build
public static final String prod_marker1 = " ██████╗ ██████╗  ██████╗ ██████╗ ";
public static final String prod_marker2 = " ██╔══██╗██╔══██╗██╔═══██╗██╔══██╗";
public static final String prod_marker3 = " ██████╔╝██████╔╝██║   ██║██║  ██║";
public static final String prod_marker4 = " ██╔═══╝ ██╔══██╗██║   ██║██║  ██║";
public static final String prod_marker5 = " ██║     ██║  ██║╚██████╔╝██████╔╝";
public static final String prod_marker6 = " ╚═╝     ╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ";
```

---

### Example 3: Team Member Signatures

**Scenario**: Each developer adds their ASCII signature to classes they work on.

**Alice's Signature** (alice-sig.png):
```java
public static final String alice_1 = "  █████╗ ██╗     ██╗ ██████╗███████╗";
public static final String alice_2 = " ██╔══██╗██║     ██║██╔════╝██╔════╝";
public static final String alice_3 = " ███████║██║     ██║██║     █████╗  ";
public static final String alice_4 = " ██╔══██║██║     ██║██║     ██╔══╝  ";
public static final String alice_5 = " ██║  ██║███████╗██║╚██████╗███████╗";
public static final String alice_6 = " ╚═╝  ╚═╝╚══════╝╚═╝ ╚═════╝╚══════╝";
```

**Bob's Signature** (bob-sig.png):
```java
public static final String bob_1 = " ██████╗  ██████╗ ██████╗ ";
public static final String bob_2 = " ██╔══██╗██╔═══██╗██╔══██╗";
public static final String bob_3 = " ██████╔╝██║   ██║██████╔╝";
public static final String bob_4 = " ██╔══██╗██║   ██║██╔══██╗";
public static final String bob_5 = " ██████╔╝╚██████╔╝██████╔╝";
public static final String bob_6 = " ╚═════╝  ╚═════╝ ╚═════╝ ";
```

---

## 🔧 Code Integration Examples

### Example 4: Accessing Injected ASCII Art

**Reflection-based Access**:
```java
import java.lang.reflect.Field;

public class ASCIIArtReader {
    public static void printInjectedArt(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == String.class &&
                java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                java.lang.reflect.Modifier.isFinal(field.getModifiers())) {

                try {
                    String value = (String) field.get(null);
                    if (value != null && value.length() > 10) {
                        System.out.println(value);
                    }
                } catch (IllegalAccessException e) {
                    // Handle access error
                }
            }
        }
    }

    // Usage
    public static void main(String[] args) {
        printInjectedArt(MyMainClass.class);
    }
}
```

**Direct Field Access** (if you know field names):
```java
public class DirectAccess {
    public static void showLogo() {
        // Access generated fields directly
        System.out.println(MyClass.logo_line1);
        System.out.println(MyClass.logo_line2);
        System.out.println(MyClass.logo_line3);
        // ... etc
    }
}
```

---

### Example 5: Build Script Integration

**Gradle Build Script**:
```gradle
// build.gradle
task injectAsciiArt(type: JavaExec) {
    description = 'Inject ASCII art into JAR'
    group = 'build'

    dependsOn jar

    classpath = configurations.runtimeClasspath
    main = 'git.jar2dll.Main'

    // Configure for headless operation (future CLI version)
    systemProperty 'java.awt.headless', 'false'

    doLast {
        println "ASCII art injection completed"
        println "Output: ${project.buildDir}/libs/${project.name}-${version}_injected.jar"
    }
}

// Run after main JAR creation
jar.finalizedBy injectAsciiArt
```

**Maven Integration** (future):
```xml
<!-- pom.xml -->
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.1.0</version>
    <executions>
        <execution>
            <id>inject-ascii-art</id>
            <phase>package</phase>
            <goals>
                <goal>java</goal>
            </goals>
            <configuration>
                <mainClass>git.jar2dll.Main</mainClass>
                <args>
                    <arg>--jar</arg>
                    <arg>${project.build.directory}/${project.build.finalName}.jar</arg>
                    <arg>--images</arg>
                    <arg>src/main/resources/logo.png</arg>
                </args>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## 🎯 Advanced Use Cases

### Example 6: Dynamic ASCII Art Display

**Runtime ASCII Art Viewer**:
```java
public class ASCIIArtViewer extends JFrame {
    private JTextArea artDisplay;

    public ASCIIArtViewer() {
        setTitle("Injected ASCII Art Viewer");
        setSize(800, 600);

        artDisplay = new JTextArea();
        artDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        artDisplay.setEditable(false);

        add(new JScrollPane(artDisplay));

        loadAndDisplayArt();
    }

    private void loadAndDisplayArt() {
        StringBuilder allArt = new StringBuilder();

        // Scan all classes in current JAR
        Package[] packages = Package.getPackages();
        for (Package pkg : packages) {
            try {
                // Use reflection to find ASCII art fields
                Class<?>[] classes = getClassesInPackage(pkg.getName());
                for (Class<?> clazz : classes) {
                    String art = extractASCIIArt(clazz);
                    if (!art.isEmpty()) {
                        allArt.append("=== ").append(clazz.getSimpleName()).append(" ===\n");
                        allArt.append(art).append("\n\n");
                    }
                }
            } catch (Exception e) {
                // Handle errors
            }
        }

        artDisplay.setText(allArt.toString());
    }

    private String extractASCIIArt(Class<?> clazz) {
        // Implementation to extract ASCII art from class fields
        // Similar to ASCIIArtReader example above
        return "";
    }
}
```

---

### Example 7: ASCII Art Validation

**Verify Injection Success**:
```java
public class InjectionValidator {
    public static boolean validateInjection(File jarFile) {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            int classesWithArt = 0;

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    if (hasASCIIArtFields(jar, entry)) {
                        classesWithArt++;
                    }
                }
            }

            System.out.println("Classes with ASCII art: " + classesWithArt);
            return classesWithArt > 0;

        } catch (IOException e) {
            return false;
        }
    }

    private static boolean hasASCIIArtFields(JarFile jar, JarEntry entry) {
        // Use ASM to check for injected fields
        try (InputStream is = jar.getInputStream(entry)) {
            ClassReader reader = new ClassReader(is);
            ClassNode classNode = new ClassNode();
            reader.accept(classNode, 0);

            // Check for static final String fields with ASCII-like content
            return classNode.fields.stream()
                .anyMatch(field ->
                    field.access == (ACC_PUBLIC | ACC_STATIC | ACC_FINAL) &&
                    field.desc.equals("Ljava/lang/String;") &&
                    field.value != null &&
                    field.value.toString().contains("█"));

        } catch (IOException e) {
            return false;
        }
    }
}
```

---

## 🔍 Debugging Examples

### Example 8: Field Name Analysis

**Analyze Generated Field Names**:
```java
public class FieldAnalyzer {
    public static void analyzeFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        System.out.println("=== Field Analysis for " + clazz.getSimpleName() + " ===");

        for (Field field : fields) {
            if (isInjectedField(field)) {
                System.out.printf("Field: %s, Length: %d chars%n",
                    field.getName(),
                    getFieldValue(field).length());
            }
        }
    }

    private static boolean isInjectedField(Field field) {
        return field.getType() == String.class &&
               Modifier.isStatic(field.getModifiers()) &&
               Modifier.isFinal(field.getModifiers()) &&
               field.getName().matches(".*[a-z0-9]{5,}.*"); // Random name pattern
    }

    private static String getFieldValue(Field field) {
        try {
            return (String) field.get(null);
        } catch (IllegalAccessException e) {
            return "";
        }
    }
}
```

---

## 📊 Performance Examples

### Example 9: Memory Usage Monitoring

**Monitor Processing Memory**:
```java
public class MemoryMonitor {
    public static void monitorProcessing() {
        Runtime runtime = Runtime.getRuntime();

        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory before: " + (beforeMemory / 1024 / 1024) + " MB");

        // Simulate ImageInjector processing
        processLargeImage();

        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory after: " + (afterMemory / 1024 / 1024) + " MB");
        System.out.println("Memory used: " + ((afterMemory - beforeMemory) / 1024 / 1024) + " MB");

        // Force garbage collection
        System.gc();

        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory after GC: " + (finalMemory / 1024 / 1024) + " MB");
    }
}
```

---

## 🎨 Creative Examples

### Example 10: ASCII Art Animation

**Create Animated ASCII Sequences**:
```java
public class ASCIIAnimation {
    public static void playAnimation() {
        // Collect all ASCII art from injected fields
        String[] frames = collectAnimationFrames();

        // Play animation in console
        for (int i = 0; i < frames.length; i++) {
            clearScreen();
            System.out.println(frames[i]);

            try {
                Thread.sleep(500); // 500ms per frame
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static String[] collectAnimationFrames() {
        // Implementation to collect ASCII art from multiple classes
        // Each class could represent a frame in the animation
        return new String[0];
    }

    private static void clearScreen() {
        System.out.print("\033[2J\033[H");
    }
}
```

---

**🎉 These examples demonstrate the versatility and creative potential of ImageInjector!**

**For more examples and community contributions, visit our [GitHub repository](https://github.com/432Fowin/ImageInjector).**