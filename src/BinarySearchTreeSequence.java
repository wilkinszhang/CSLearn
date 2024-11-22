import java.util.Stack;

public class BinarySearchTreeSequence {
    public static boolean canRepresentBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return true;
        return verify(sequence, 0, sequence.length - 1);
    }

    private static boolean verify(int[] sequence, int start, int end) {
        if (start >= end) return true;

        int root = sequence[end];
        int i = start;
        while (i < end && sequence[i] < root) i++;

        for (int j = i; j < end; j++) {
            if (sequence[j] < root) return false;
        }

        return verify(sequence, start, i - 1) && verify(sequence, i, end - 1);
    }

    public static void main(String[] args) {
        int[] sequence = {90, 17, 83, 24,85,71};
        System.out.println(canRepresentBST(sequence));  // 输出 true
    }
}
