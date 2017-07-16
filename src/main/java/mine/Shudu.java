package mine;

import java.util.List;
import java.util.Map;

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
			if(isPerfect(testArr)) {
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
		return null;
	}

	private Map<Position, List<Integer>> suspecterNums() {
		
		for(int line = 0;line < LINES; line ++) {
			for(int column =0; column < COLUMNS;column++){
				if(initShudu[line][column] == EMPTY_NUM) {
					
				}
			}
		}
		
		
		
		return null;
	}

	private boolean isPerfect(int[][] arr) {
		return false;
	}

	private static class Position {
		public int x;
		public int y;
		public int num;
	}

}
