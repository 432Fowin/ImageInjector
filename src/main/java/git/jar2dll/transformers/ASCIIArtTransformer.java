package git.jar2dll.transformers;

import git.jar2dll.Jar;
import git.jar2dll.utils.NameUtils;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

import java.util.Set;

public class ASCIIArtTransformer extends Transformer {
    private final String[] asciiImages;
    private final Set<String> selectedClassNames;
    private int currentImageIndex = 0;
    private int classCounter = 0;

    public ASCIIArtTransformer(Jar obf, String[] asciiImages) {
        super(obf);
        this.asciiImages = asciiImages;
        this.selectedClassNames = null;
    }

    public ASCIIArtTransformer(Jar obf, String[] asciiImages, Set<String> selectedClassNames) {
        super(obf);
        this.asciiImages = asciiImages;
        this.selectedClassNames = selectedClassNames;
    }

    public void addString(ClassNode classNode, String value) {
        classNode.fields.add(new FieldNode(
            org.objectweb.asm.Opcodes.ACC_PUBLIC + org.objectweb.asm.Opcodes.ACC_STATIC + org.objectweb.asm.Opcodes.ACC_FINAL,
            NameUtils.getRandomString(),
            "Ljava/lang/String;",
            null,
            value
        ));
    }

    @Override
    public void visit(ClassNode classNode) {
        if (asciiImages == null || asciiImages.length == 0) {
            return;
        }

        if (selectedClassNames != null && !shouldProcessClass(classNode)) {
            return;
        }

        String currentAscii = asciiImages[currentImageIndex];
        String[] lines = currentAscii.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                addString(classNode, line);
            }
        }

        currentImageIndex = (currentImageIndex + 1) % asciiImages.length;
        classCounter++;
    }

    private boolean shouldProcessClass(ClassNode classNode) {
        if (selectedClassNames == null) {
            return true;
        }

        String className = classNode.name + ".class";
        return selectedClassNames.contains(className);
    }

    public int getProcessedClassCount() {
        return classCounter;
    }
}
