import java.util.Arrays;
import java.util.List;
import java.io.PrintStream;

public class Tree {
    String node;
    List<Tree> children;

    public Tree(String node,  Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
        this.children = Arrays.asList();
    }

    public void print(PrintStream out, String prefix, boolean isTail) {
        out.println(prefix + (isTail ? "└── " : "├── ") + node);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(out, prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(out, prefix + (isTail ? "    " : "│   "), true);
        }
    }
}
