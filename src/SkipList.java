class SkipListNode {
    int value;
    SkipListNode[] forward; // 前向指针数组

    public SkipListNode(int value, int level) {
        this.value = value;
        this.forward = new SkipListNode[level];
    }
}

class SkipList {
    private static final int MAX_LEVEL = 16; // 最大层级
    private int levelCount = 1; // 当前层级数
    private SkipListNode head = new SkipListNode(-1, MAX_LEVEL); // 头节点

    // 随机生成层级
    private int randomLevel() {
        int level = 1;
        while (Math.random() < 0.5 && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    // 插入节点
    public void insert(int value) {
        int level = randomLevel();
        SkipListNode newNode = new SkipListNode(value, level);
        SkipListNode[] update = new SkipListNode[level];//跟踪每层需要更新的节点
        SkipListNode p = head;

        for (int i = level - 1; i >= 0; i--) {
            while (p.forward[i] != null && p.forward[i].value < value) {
                p = p.forward[i];
            }
            update[i] = p;
        }

        for (int i = 0; i < level; i++) {
            newNode.forward[i] = update[i].forward[i];
            update[i].forward[i] = newNode;
        }

        if (level > levelCount) {
            levelCount = level;
        }
    }

    // 查找节点
    public SkipListNode find(int value) {
        SkipListNode p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forward[i] != null && p.forward[i].value < value) {
                p = p.forward[i];
            }
        }
        if (p.forward[0] != null && p.forward[0].value == value) {
            return p.forward[0];
        } else {
            return null;
        }
    }
}