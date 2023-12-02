import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	
	public static int R = 0;
	public static int C = 0;
	public static TreeMap<Character, Boolean> map;
	public static char[][] array;
	public static int answer = Integer.MIN_VALUE;
	public static int uniqueCharSize;
	
	//count: (재귀) 처리한 알파벳의 수
	public static void calculate(int row, int col, int count) {
		map.put(array[row][col], true);
		
		//상
		if(row-1 >= 0 && !map.getOrDefault(array[row-1][col], false)) {
			map.put(array[row-1][col], true);
			calculate(row-1, col, count+1);
			map.put(array[row-1][col], false);
		}
		
		//하
		if(row+1 < R && !map.getOrDefault(array[row+1][col], false)) {
			map.put(array[row+1][col], true);
			calculate(row+1, col, count+1);
			map.put(array[row+1][col], false);
		}
		
		//좌
		if(col-1 >= 0 && !map.getOrDefault(array[row][col-1], false)) {
			map.put(array[row][col-1], true);
			calculate(row, col-1, count+1);
			map.put(array[row][col-1], false);
		}
		
		//우
		if(col+1 < C && !map.getOrDefault(array[row][col+1], false)) {
			map.put(array[row][col+1], true);
			calculate(row, col+1, count+1);
			map.put(array[row][col+1], false);
		}
		
		answer = Math.max(answer, count);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		array = new char[R][];
		map = new TreeMap<>();	//탐색 가능 알파벳 판별 용 map
		uniqueCharSize = map.size();
		
		for(int i=0; i<R; i++) {
			array[i] = br.readLine().toCharArray();
		}
		
		calculate(0, 0, 1);
		System.out.println(answer);
	}
}