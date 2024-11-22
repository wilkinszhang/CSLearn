import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class TreeNode {
    String value;
    List<TreeNode> children;

    TreeNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }
}

public class TreeParser {

    public static TreeNode parse(String input) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = null;
        StringBuilder buffer = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (ch == '(') {
                if (buffer.length() > 0) {
                    TreeNode node = new TreeNode(buffer.toString().trim());
                    if (!stack.isEmpty()) {
                        stack.peek().children.add(node);
                    } else {
                        root = node;
                    }
                    stack.push(node);
                    buffer.setLength(0);
                }
            } else if (ch == ')') {
                if (buffer.length() > 0) {
                    TreeNode node = new TreeNode(buffer.toString().trim());
                    stack.peek().children.add(node);
                    buffer.setLength(0);
                }
                stack.pop();
            } else if (ch == ',') {
                if (buffer.length() > 0) {
                    TreeNode node = new TreeNode(buffer.toString().trim());
                    stack.peek().children.add(node);
                    buffer.setLength(0);
                }
            } else {
                buffer.append(ch);
            }
        }

        return root;
    }

    public static void printTree(TreeNode node, String indent) {
        System.out.println(indent + node.value);
        for (TreeNode child : node.children) {
            printTree(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        String input = "A(B(E,F(O,P,Q),G),C(H,I,J(R,S),K),D(L,M(T,U,V,W),N(X,Y,Z)))";
        TreeNode root = parse(input);
        printTree(root, "");
        System.out.println();
    }
}