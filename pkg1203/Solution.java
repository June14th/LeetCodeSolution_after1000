package pkg1203;

import java.util.*;

/**
 * 公司共有 n 个项目和  m 个小组，每个项目要不无人接手，要不就由 m 个小组之一负责。
 * group[i] 表示第 i 个项目所属的小组，如果这个项目目前无人接手，那么 group[i] 就等于 -1。（项目和小组都是从零开始编号的）小组可能存在没有接手任何项目的情况。
 * 请你帮忙按要求安排这些项目的进度，并返回排序后的项目列表：
 * 同一小组的项目，排序后在列表中彼此相邻。
 * 项目之间存在一定的依赖关系，我们用一个列表 beforeItems 来表示，其中 beforeItems[i] 表示在进行第 i 个项目前（位于第 i 个项目左侧）应该完成的所有项目。
 * 如果存在多个解决方案，只需要返回其中任意一个即可。如果没有合适的解决方案，就请返回一个 空列表 。
 */
public class Solution {
    public static void main(String[] args) {
        int n = 8;
        int m = 2;
        int[] group = {-1,-1,1,0,0,1,0,-1};
        List<List<Integer>> beforeItems = new ArrayList<>();
        beforeItems.add(new ArrayList<>());
        beforeItems.add(Arrays.asList(6));
        beforeItems.add(Arrays.asList(5));
        beforeItems.add(Arrays.asList(3,6));
        beforeItems.add(new ArrayList<>());
        beforeItems.add(new ArrayList<>());
        beforeItems.add(new ArrayList<>());
        for(List<Integer> list: beforeItems){
            System.out.println(list);
        }
        int[] res = sortItems(n,m,group,beforeItems);
        System.out.println(res);

    }

    public static int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        List<List<Integer>> groupItem = new ArrayList<>();
        for (int i = 0; i < n + m; i++) {
            groupItem.add(new ArrayList<Integer>());
        }
        //组间和组内依赖图
        List<List<Integer>> groupGragh = new ArrayList<>();
        for (int i = 0; i < n + m; i++) {
            groupGragh.add(new ArrayList<>());
        }
        List<List<Integer>> itemGraph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            itemGraph.add(new ArrayList<>());
        }

        //组间和组内入读数组
        int[] groupDegree = new int[n + m];
        int[] itemDegree = new int[n];

        List<Integer> id = new ArrayList<>();
        for (int i = 0; i < n + m; i++) {
            id.add(i);
        }
        int leftId = m;
        //给未分配的item分配一个groupId
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) {
                group[i] = leftId++;
            }
            groupItem.get(group[i]).add(i);
        }
        //依赖关系建图
        for (int i = 0; i < n; i++) {
            int curGroupId = group[i];
            for (int item : beforeItems.get(i)) {
                int beforeGroupId = group[item];
                if (beforeGroupId == curGroupId) {
                    itemDegree[i]++;
                    itemGraph.get(item).add(i);
                } else {
                    groupDegree[curGroupId]++;
                    groupGragh.get(beforeGroupId).add(curGroupId);
                }
            }
        }
        //组间拓扑关系排序
        List<Integer> groupTopSort = topSort(groupDegree, groupGragh, id);
        if (groupTopSort.size() == 0) {
            return new int[0];
        }
        int[] ans = new int[n];
        int index = 0;
        //组内拓扑关系排序
        for (int curGroupId : groupTopSort) {
            int size = groupItem.get(curGroupId).size();
            if (size == 0) {
                continue;
            }
            List<Integer> res = topSort(itemDegree, itemGraph, groupItem.get(curGroupId));
            if (res.size() == 0) {
                return new int[0];
            }
            for (int item : res) {
                ans[index++] = item;
            }
        }
        return ans;
    }

    public static List<Integer> topSort(int[] deg, List<List<Integer>> gragh, List<Integer> items) {
        Queue<Integer> queue = new LinkedList<>();
        for (int item : items) {
            if (deg[item] == 0) {
                queue.offer(item);
            }
        }
        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            res.add(u);
            for (int v : gragh.get(u)) {
                if (--deg[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        return res.size() == items.size() ? res : new ArrayList<Integer>();
    }

}
