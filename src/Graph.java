import java.util.*;

class Graph {
    private int V; // 顶点数量
    private List<List<int[]>> adj; // 邻接表表示图，存储边和权重

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    // 添加边，u和v是顶点，weight是边的权重
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new int[]{v, weight});
        adj.get(v).add(new int[]{u, weight}); // 无向图
    }

    // 使用Prim算法生成最小生成树
    public List<int[]> primMST() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2])); // 优先队列按边的权重排序
        boolean[] inMST = new boolean[V]; // 标记顶点是否在MST中
        List<int[]> mst = new ArrayList<>(); // 存储MST中的边

        pq.offer(new int[]{-1, 0, 0}); // 从任意起点开始，假设从0号顶点

        while (!pq.isEmpty() && mst.size() < V - 1) {
            int[] edge = pq.poll();
            int u = edge[1];

            if (inMST[u]) {
                continue; // 如果顶点已经在MST中，跳过
            }

            inMST[u] = true; // 将顶点加入MST
            if (edge[0] != -1) {
                mst.add(edge); // 将边加入MST
            }

            // 将与u相连的边加入优先队列
            for (int[] neighbor : adj.get(u)) {
                int v = neighbor[0];
                int weight = neighbor[1];
                if (!inMST[v]) {
                    pq.offer(new int[]{u, v, weight});
                }
            }
        }

        return mst;
    }

    // 比较两棵树是否相同，比较边的集合
    public boolean compareTrees(List<int[]> mst1, List<int[]> mst2) {
        if (mst1.size() != mst2.size()) {
            return false;
        }

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (int[] edge : mst1) {
            int u = edge[0], v = edge[1], w = edge[2];
            set1.add(u + "-" + v + "-" + w);
            set1.add(v + "-" + u + "-" + w);
        }

        for (int[] edge : mst2) {
            int u = edge[0], v = edge[1], w = edge[2];
            set2.add(u + "-" + v + "-" + w);
            set2.add(v + "-" + u + "-" + w);
        }

        return set1.equals(set2);
    }

    public static void main(String[] args) {
        // 假设我们有一个包含5个顶点的无向图
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 3);
        graph.addEdge(0, 2, 1);
        graph.addEdge(0, 3, 6);
        graph.addEdge(1, 2, 6);
        graph.addEdge(2, 3, 5);
        graph.addEdge(1, 4, 3);
        graph.addEdge(2, 4, 3);
        graph.addEdge(2, 5, 3);
        graph.addEdge(4, 5, 3);

        // 使用Prim算法生成最小生成树
        List<int[]> generatedMST = graph.primMST();

        // 给定的最小生成树，可以手动构建
        List<int[]> givenMST = new ArrayList<>();
        givenMST.add(new int[]{0, 1, 2});
        givenMST.add(new int[]{1, 2, 3});
        givenMST.add(new int[]{1, 4, 5});
        givenMST.add(new int[]{0, 3, 6});

        // 比较Prim算法生成的MST和给定的MST
        if (graph.compareTrees(generatedMST, givenMST)) {
            System.out.println("Prim算法可以生成给定的最小生成树");
        } else {
            System.out.println("Prim算法无法生成给定的最小生成树");
        }
    }
}
