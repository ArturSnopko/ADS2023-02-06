package by.it.group251003.snopko.lesson14;
import java.util.*;

class PointsA {
    static class Point {
        int x, y, z;
        Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    static class DSU {
        int root[];
        int size[];
        DSU(int Size){
            root = new int[Size];
            size = new int[Size];
            for (int i = 0; i < Size; i++) {
                root[i] = i;
                size[i] = 1;
            }
        }

        int getRoot(int v){
            if (v != root[v]){
                root[v] = getRoot(root[v]);
            }
            return root[v];
        }

        void union (int a, int b){
            a = getRoot(a);
            b = getRoot(b);
            if (a != b){
                if (size[a] < size[b]){
                    int temp = a;
                    a = b;
                    b = temp;
                }
                root[b] = a;
                size[a] += size[b];
            }
        }



    }

    private static boolean check(Point p1, Point p2, int distance) {
        return Math.hypot(Math.hypot(p1.x- p2.x, p1.y - p2.y), p1.z - p2.z) <= distance;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int distanceThreshold = scanner.nextInt();
        int numPoints = scanner.nextInt();

        Point[] points = new Point[numPoints];
        for (int i = 0; i < numPoints; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int z = scanner.nextInt();
            points[i] = new Point(x, y, z);
        }

        DSU dsu = new DSU(numPoints);

        for (int i = 0; i < numPoints; i++) {
            for (int j = i + 1; j < numPoints; j++) {
                if ( check(points[i], points[j], distanceThreshold)) {
                    dsu.union(i, j);
                }
            }
        }

        Map<Integer, Integer> clusterSizes = new TreeMap<>();
        boolean visited[] = new boolean[numPoints];

        for (int i = 0; i < numPoints; i++) {
            int root = dsu.getRoot(i);
            if (!visited[root]){
                visited[root] = true;
                clusterSizes.put(root, dsu.size[root]);
            }
        }

        List<Integer> sortedSizes = new ArrayList<>(clusterSizes.values());
        Collections.sort(sortedSizes, Collections.reverseOrder());

        for (int size : sortedSizes) {
            System.out.print(size);
            System.out.print(" ");
        }
    }
}
