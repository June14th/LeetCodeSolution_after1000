package pkg1018;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定由若干 0 和 1 组成的数组 A。我们定义 N_i：从 A[0] 到 A[i] 的第 i 个子数组被解释为一个二进制数（从最高有效位到最低有效位）。
 *
 * 返回布尔值列表 answer，只有当 N_i 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
 */
public class Solution {
    public static void main(String[] args) {
        int[] A = {0,1,1};
        System.out.println(prefixesDivBy5(A));
    }
    public static List<Boolean> prefixesDivBy5(int[] A) {
        int len = A.length;
        List<Boolean> res = new ArrayList<>();
        int mid = 0;
        for(int i=0;i<len;i++){
            mid = (mid * 2 + A[i]) % 5;
            res.add(mid == 0 ? true : false);
        }
        return res;
    }
}
