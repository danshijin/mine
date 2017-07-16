package mine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Shudu {

    public static int LINES = 9;
    public static int COLUMNS = 9;
    public static int EMPTY_NUM = 0;

    private int[][] initShudu = new int[LINES][COLUMNS];

    public Shudu() {
        for (int[] line : initShudu) {

        }
    }

    private void caculate() {

        // 计算空白格可能数字
        Map<Position, List<Integer>> suspecterNums = suspecterNums();

        // 穷举出来所有可能的组合方式
        List<List<Position>> suspecters = suspecters(suspecterNums);

        // 找出正确的组合
        int[][] rlt = findTheRight(suspecters);

        // 打印结果
        print(rlt);
    }

    private int[][] findTheRight(List<List<Position>> suspecters) {

        for (List<Position> posList : suspecters) {
            int[][] testArr = copy();
            // 组合起来
            for (Position p : posList) {
                testArr[p.x][p.y] = p.num;
            }
            // 判断是否正确
            if (isPerfect(testArr)) {
                return testArr;
            }
        }
        return initShudu;
    }

    public int[][] copy() {
        int[][] dst = new int[LINES][COLUMNS];
        int line = 0;
        for (int[] lines : initShudu) {
            System.arraycopy(lines, 0, dst[line], 0, COLUMNS);
            line++;
        }
        return dst;
    }

    private void print(int[][] rlt) {
        for (int[] line : rlt) {
            for (int num : line) {
                System.out.println(num + "   ");
            }

        }
    }

    private List<List<Position>> suspecters(Map<Position, List<Integer>> suspecters) {
        List<List<Position>> rltList = new ArrayList<>();
        for (Entry<Position, List<Integer>> entry : suspecters.entrySet()) {

            Position position = entry.getKey();
            List<Integer> numList = entry.getValue();
            if (rltList.size() == 0) {
                for (Integer num : numList) {
                    List<Position> list = new ArrayList<>();
                    list.add(new Position(position.x, position.y, num));
                    rltList.add(list);
                }
            } else {
                
            }

        }

        return null;
    }

    private Map<Position, List<Integer>> suspecterNums() {
        Map<Position, List<Integer>> map = new HashMap<>();
        for (int line = 0; line < LINES; line++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (initShudu[line][column] == EMPTY_NUM) {
                    Set<Integer> set = new HashSet<>();
                    List<Integer> list = new ArrayList<>();
                    for (int sColumn = 0; sColumn <= COLUMNS; sColumn++) {
                        set.add(initShudu[line][sColumn]);
                    }
                    for (int sLine = 0; sLine <= LINES; sLine++) {
                        set.add(initShudu[sLine][column]);
                    }

                    for (int i = 1; i <= COLUMNS; i++) {
                        if (set.contains(i)) {
                            continue;
                        }
                        list.add(i);
                    }
                    Position p = new Position();
                    p.x = line;
                    p.y = column;
                    map.put(p, list);
                }
            }
        }

        return map;
    }

    private boolean isPerfect(int[][] arr) {
        return false;
    }

    private static class Position {
        public int x;
        public int y;
        public int num;

        public Position() {
        }

        public Position(int x, int y, int num) {
            this.x = x;
            this.y = y;
            this.num = num;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + num;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Position other = (Position) obj;
            if (num != other.num)
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

    }

}
