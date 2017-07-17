package mine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Shudu {

    public static int LINES = 9;
    public static int COLUMNS = 9;
    public static int EMPTY_NUM = 0;

    // private int[][] initShudu = new int[LINES][COLUMNS];
    private int[][] initShudu = { { 5, 6, 0, 0, 7, 0, 0, 0, 8 }, { 0, 0, 0, 8, 0, 9, 6, 0, 5 },
            { 0, 1, 0, 0, 0, 0, 0, 7, 0 }, { 0, 0, 8, 0, 2, 3, 0, 1, 0 }, { 0, 4, 0, 0, 6, 0, 0, 8, 0 },
            { 0, 3, 0, 7, 8, 0, 2, 0, 0 }, { 0, 8, 0, 0, 0, 0, 0, 5, 0 }, { 2, 0, 1, 5, 0, 8, 0, 0, 0 },
            { 4, 0, 0, 0, 3, 0, 0, 2, 1 } };
    public Shudu() {
    }

    public static void main(String[] args) {
        
        Shudu sd = new Shudu();
        sd.caculate();
    }

    private void caculate() {

        // 计算空白格可能数字
        List<NumberInfer> suspecterNums = suspecterNums();

        // 穷举出来所有可能的组合方式
        Set<int[][]> suspecters = suspecters(suspecterNums);

        // 找出正确的组合
        int[][] rlt = findTheRight(suspecters);

        // 打印结果
        print(rlt);
    }

    private int[][] findTheRight(Set<int[][]> suspecters) {

        for (int[][] arr : suspecters) {
            // 判断是否正确
            if (perfect(arr)) {
                return arr;
            }
        }
        return initShudu;
    }

    public int[][] copy(int[][] src) {
        int[][] dst = new int[LINES][COLUMNS];
        int line = 0;
        for (int[] lines : src) {
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

    private Set<int[][]> suspecters(List<NumberInfer> suspecterNums) {


        Set<int[][]> rltSet = new HashSet<>();

        for (NumberInfer infer : suspecterNums) {

            List<Integer> numList = infer.num;
            if (rltSet.size() == 0) {
                for (Integer num : numList) {
                    int[][] square = copy(initShudu);
                    square[infer.x][infer.y] = num;
                    rltSet.add(square);
                }
            } else {

                Set<int[][]> total = new HashSet<>();
                int size = numList.size();

                for (int[][] square : rltSet) {
                    Set<int[][]> expandSet = new HashSet<>();
                    for (int copyNum = 0; copyNum < size - 1; copyNum++) {
                        int[][] squareCopy = copy(square);
                        expandSet.add(squareCopy);
                    }
                    square[infer.x][infer.y] = numList.get(0);
                    Iterator<int[][]> it = expandSet.iterator();
                    for (int numIndex = 1; it.hasNext(); numIndex++) {
                        int[][] next = it.next();
                        next[infer.x][infer.y] = numList.get(numIndex);
                    }
                    
                    for(int[][] expand : expandSet) {
                        if(!abort(expand)) {
                            total.add(expand);
                        }
                    }
                }
                
                rltSet.addAll(total);
            }
        }

        return rltSet;
    }

    private List<NumberInfer> suspecterNums() {
        List<NumberInfer> rltList = new ArrayList<>();
        for (int line = 0; line < LINES; line++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (initShudu[line][column] == EMPTY_NUM) {
                    Set<Integer> set = new HashSet<>();
                    NumberInfer infer = new NumberInfer();
                    for (int sColumn = 0; sColumn < COLUMNS; sColumn++) {
                        set.add(initShudu[line][sColumn]);
                    }
                    for (int sLine = 0; sLine < LINES; sLine++) {
                        set.add(initShudu[sLine][column]);
                    }

                    int fromx = line / 3 * 3;
                    int fromy = column / 3 * 3;
                    for (int x = fromx; x < fromx + 3; x++) {
                        for (int y = fromy; y < fromy + 3; y++) {
                            set.add(initShudu[x][y]);
                        }
                    }
                    set.remove(0);

                    for (int i = 1; i <= COLUMNS; i++) {
                        if (set.contains(i)) {
                            continue;
                        }
                        infer.num.add(i);
                    }
                    rltList.add(infer);
                }
            }
        }

        return rltList;
    }

    private boolean perfect(int[][] arr) {
        final Set<Integer> rightSet = new HashSet<>();
        for (int i = 1; i <= LINES; i++) {
            rightSet.add(i);
        }
        for (int[] line : arr) {
            Set<Integer> testSet = new HashSet<>();
            for (int num : line) {
                testSet.add(num);
            }
            if (!testSet.containsAll(rightSet)) {
                return false;
            }
        }

        for (int column = 0; column < COLUMNS; column++) {
            Set<Integer> testSet = new HashSet<>();
            for (int line = 0; line < LINES; line++) {
                testSet.add(arr[line][column]);
            }
            if (!testSet.containsAll(rightSet)) {
                return false;
            }
        }
        for (int line = 0; line < LINES; line += 3) {
            Set<Integer> testSet = new HashSet<>();
            for (int column = 0; column < COLUMNS; column += 3) {
                for (int x = line; x < line + 3; x++) {
                    for (int y = column; y < column + 3; y++) {
                        testSet.add(arr[x][y]);
                    }
                }
            }
            if (!testSet.containsAll(rightSet)) {
                return false;
            }
        }

        return true;
    }

    private boolean abort(int[][] arr) {
        for (int[] line : arr) {
            int[] lineCopy = new int[line.length];
            System.arraycopy(line, 0, lineCopy, 0, line.length);
            Arrays.sort(lineCopy);
            int previous = 0;
            for (int num : lineCopy) {
                if (num == 0) {
                    continue;
                }
                if (previous != 0 && previous == num) {
                    return true;
                }
                previous = num;
            }

        }

        for (int column = 0; column < COLUMNS; column++) {
            List<Integer> list = new ArrayList<>();
            for (int line = 0; line < LINES; line++) {
                list.add(arr[line][column]);
            }
            list.sort((n1, n2) -> n1.compareTo(n2));
            int previous = 0;
            for (int num : list) {
                if (num == 0) {
                    continue;
                }
                if (previous != 0 && previous == num) {
                    return true;
                }
                previous = num;
            }
            
        }
        for (int line = 0; line < LINES; line += 3) {
            
            for (int column = 0; column < COLUMNS; column += 3) {
                for (int x = line; x < line + 3; x++) {
                    List<Integer> list = new ArrayList<>();
                    for (int y = column; y < column + 3; y++) {
                        list.add(arr[x][y]);
                    }
                    list.sort((n1, n2) -> n1.compareTo(n2));
                    int previous = 0;
                    for (int num : list) {
                        if (num == 0) {
                            continue;
                        }
                        if (previous != 0 && previous == num) {
                            return true;
                        }
                        previous = num;
                    }
                }
            }
        }

        return false;
    }

    private static class NumberInfer {
        public int x;
        public int y;
        public List<Integer> num = new ArrayList<>();

        public NumberInfer() {
        }

        // public NumberInfer(int x, int y, List<Integer> num) {
        // this.x = x;
        // this.y = y;
        // this.num = num;
        // }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((num == null) ? 0 : num.hashCode());
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
            NumberInfer other = (NumberInfer) obj;
            if (num == null) {
                if (other.num != null)
                    return false;
            } else if (!num.equals(other.num))
                return false;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "PositionPre [x=" + x + ", y=" + y + ", num=" + num + "]";
        }

    }

    // private static class Position {
    // public int x;
    // public int y;
    // public int num;
    //
    // public Position() {
    // }
    //
    // public Position(int x, int y, int num) {
    // this.x = x;
    // this.y = y;
    // this.num = num;
    // }
    //
    // @Override
    // public String toString() {
    // return "Position [x=" + x + ", y=" + y + ", num=" + num + "]";
    // }
    //
    // @Override
    // public int hashCode() {
    // final int prime = 31;
    // int result = 1;
    // result = prime * result + num;
    // result = prime * result + x;
    // result = prime * result + y;
    // return result;
    // }
    //
    // @Override
    // public boolean equals(Object obj) {
    // if (this == obj)
    // return true;
    // if (obj == null)
    // return false;
    // if (getClass() != obj.getClass())
    // return false;
    // Position other = (Position) obj;
    // if (num != other.num)
    // return false;
    // if (x != other.x)
    // return false;
    // if (y != other.y)
    // return false;
    // return true;
    // }
    //
    // }

}
