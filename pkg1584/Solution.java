package pkg1584;

import java.util.*;

/**
 * 给你一个points 数组，表示 2D 平面上的一些点，其中 points[i] = [xi, yi] 。
 *
 * 连接点 [xi, yi] 和点 [xj, yj] 的费用为它们之间的 曼哈顿距离 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的绝对值。
 *
 * 请你返回将所有点连接的最小总费用。只有任意两点之间 有且仅有 一条简单路径时，才认为所有点都已连接。
 */
class DisjointSetUnion{
    int[] f;
    int[] rank;
    int n;
    public DisjointSetUnion(int n){
        this.n = n;
        this.rank = new int[n];
        Arrays.fill(this.rank,1);
        this.f = new int[n];
        for(int i=0;i<n;i++){
            f[i] = i;
        }
    }
    public int find(int x){
        if(x != f[x]){
            return find(f[x]);
        }
        return f[x];
    }
    public boolean union(int x,int y){
        int rootX = find(x);
        int rootY = find(y);
        if(rootX == rootY){
            return false;
        }
        if(rank[rootX]<rank[rootY]){
            int temp = rootX;
            rootX = rootY;
            rootY = temp;
        }
        rank[rootX] += rank[rootY];
        f[rootY] = rootX;
        return true;
    }
}
class Edge{
    int len,x,y;
    public Edge(int len,int x,int y){
        this.len = len;
        this.x = x;
        this.y = y;
    }
}
public class Solution {
    public static void main(String[] args) {

        int[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};
        System.out.println(minCostConnectPoints(points));
    }
    public static int minCostConnectPoints(int[][] points){
        int n = points.length;
        DisjointSetUnion union = new DisjointSetUnion(n);
        List<Edge> edges = new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                edges.add(new Edge(distance(points,i,j),i,j));
            }
        }
        Collections.sort(edges,(e1,e2)->(e1.len-e2.len));
        int ret = 0,num = 1;
        for(Edge e : edges){
            int len = e.len;
            int x = e.x;
            int y = e.y;
            if(union.union(x,y)){
                ret += len;
                num ++;
                if(num == n){
                    break;
                }
            }
        }
        return ret;
    }

    private static int distance(int[][]points, int x, int y) {
        return Math.abs(points[x][0]-points[y][0])+ Math.abs(points[x][1]-points[y][1]);
    }
}
