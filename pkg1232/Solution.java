package pkg1232;

/**
 * 在一个 XY 坐标系中有一些点，我们用数组 coordinates 来分别记录它们的坐标，其中 coordinates[i] = [x, y] 表示横坐标为 x、纵坐标为 y 的点。
 *
 * 请你来判断，这些点是否在该坐标系中属于同一条直线上，是则返回 true，否则请返回 false。
 */
public class Solution {
    public static void main(String[] args) {

    }
    public static boolean checkStraightLine(int[][] coordinates) {
        int len = coordinates.length;
        if(len < 3) return true;

        int x0 = coordinates[0][0];
        int y0 = coordinates[0][1];
        int a = coordinates[1][1] - y0;
        int b = -(coordinates[1][0] - x0);
        for(int i=2;i<len;i++){
            int x = coordinates[i][0] - x0;
            int y = coordinates[i][1] - y0;
            if(a * x + b * y != 0){
                return false;
            }
        }
        return true;
    }
}
