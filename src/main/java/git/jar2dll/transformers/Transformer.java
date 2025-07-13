package git.jar2dll.transformers;

import git.jar2dll.Jar;
import org.objectweb.asm.tree.ClassNode;

public abstract class Transformer {
    protected final Jar jar;

    public Transformer(Jar jar) {
        this.jar = jar;
    }

    public abstract void visit(ClassNode classNode);
}
