package git.jar2dll;

import git.jar2dll.transformers.Transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Jar {
    private final List<Transformer> transformers = new ArrayList<>();
    private final Map<String, ClassNode> classes = new HashMap<>();
    private final Map<String, byte[]> resources = new HashMap<>();

    public void addTransformer(Transformer transformer) {
        transformers.add(transformer);
    }

    public void loadJar(File jarFile) throws IOException {
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    try (InputStream is = jar.getInputStream(entry)) {
                        ClassReader reader = new ClassReader(is);
                        ClassNode classNode = new ClassNode();
                        reader.accept(classNode, 0);
                        classes.put(entry.getName(), classNode);
                    }
                } else if (!entry.isDirectory()) {
                    try (InputStream is = jar.getInputStream(entry)) {
                        resources.put(entry.getName(), is.readAllBytes());
                    }
                }
            }
        }
    }

    public void transform() {
        for (ClassNode classNode : classes.values()) {
            for (Transformer transformer : transformers) {
                transformer.visit(classNode);
            }
        }
    }

    public void transform(Set<String> selectedClassNames) {
        for (Map.Entry<String, ClassNode> entry : classes.entrySet()) {
            if (selectedClassNames.contains(entry.getKey())) {
                for (Transformer transformer : transformers) {
                    transformer.visit(entry.getValue());
                }
            }
        }
    }

    public void saveJar(File outputFile) throws IOException {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputFile))) {
            for (Map.Entry<String, ClassNode> entry : classes.entrySet()) {
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                entry.getValue().accept(writer);

                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                jos.putNextEntry(zipEntry);
                jos.write(writer.toByteArray());
                jos.closeEntry();
            }

            for (Map.Entry<String, byte[]> entry : resources.entrySet()) {
                ZipEntry zipEntry = new ZipEntry(entry.getKey());
                jos.putNextEntry(zipEntry);
                jos.write(entry.getValue());
                jos.closeEntry();
            }
        }
    }

    public Collection<ClassNode> getClasses() {
        return classes.values();
    }

    public int getClassCount() {
        return classes.size();
    }

    public Set<String> getClassNames() {
        return new HashSet<>(classes.keySet());
    }

    public List<String> getClassNamesList() {
        List<String> classNames = new ArrayList<>(classes.keySet());
        Collections.sort(classNames);
        return classNames;
    }
}
