import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {

    public static int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][];
        }
        Arrays.sort(intervals, Comparator.comparingInt(row -> row[0]));

        List<int[]> merged = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= end) {
                end = Math.max(end, intervals[i][1]); // overlap: extend, don't emit
            } else {
                merged.add(new int[]{start, end});   // gap: emit, start fresh
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        merged.add(new int[]{start, end}); // don't forget the last one
        return merged.toArray(new int[merged.size()][]);
    }

    public static void main(String[] args) {
        check(new int[][]{{1,3},{2,6},{8,10},{15,18}}, new int[][]{{1,6},{8,10},{15,18}});
        check(new int[][]{{1,4},{4,5}}, new int[][]{{1,5}});          // touching merges
        check(new int[][]{{2,3},{4,5},{1,10}}, new int[][]{{1,10}});
        check(new int[][]{{1,2},{10,11},{3,4},{4,10}}, new int[][]{{1,2},{3,11}});
        check(new int[][]{{1,4}}, new int[][]{{1,4}});
        check(new int[][]{}, new int[][]{});
        System.out.println("All checks passed.");
    }

    private static void check(int[][] in, int[][] expected) {
        if (!Arrays.deepEquals(merge(in), expected)) {
            throw new AssertionError("for " + Arrays.deepToString(in)
                + " expected " + Arrays.deepToString(expected)
                + " but got " + Arrays.deepToString(merge(in)));
        }
    }
}
