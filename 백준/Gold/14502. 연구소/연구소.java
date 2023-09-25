import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M;
	static int[][] origin;
	static int[][] copy;
	static ArrayList<int[]> blank = new ArrayList<>();
	static ArrayList<int[]> virus = new ArrayList<>();
	static ArrayDeque<int[]> virusQueue = new ArrayDeque<>();
	static int[] selected = new int[3];
	static int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static int answer = Integer.MIN_VALUE;
	
	static void copyArray(final int[][] origin, int[][] dest) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				dest[i][j] = origin[i][j];
			}
		}
	}
	
	static void simulate() {
		copyArray(origin, copy);
		
		for(int i=0; i<selected.length; i++) {
			int[] newWallPosition = blank.get(selected[i]);
			
			copy[newWallPosition[0]][newWallPosition[1]] = 1;
		}
		
		ArrayDeque<int[]> queue = virusQueue.clone();
		
		while(!queue.isEmpty()) {
			int[] startPoint = queue.poll();
			int startX = startPoint[0];
			int startY = startPoint[1];
			
			for(int j=0; j<directions.length; j++) {
				int x = startX + directions[j][0];
				int y = startY + directions[j][1];
				
				if(x >= 0 && x < N && y >= 0 && y < M && copy[x][y] == 0) {
					copy[x][y] = 2;
					
					queue.offer(new int[] {x, y});
				}
				
			}
		}
		
		
		for(int i=0; i<virus.size(); i++) {
			int[] startPoint = virus.get(i);
			
			int x = startPoint[0];
			int y = startPoint[1];
			
			for(int j=0; j<directions.length; j++) {
				x += directions[j][0];
				y += directions[j][1];
				
				while(x >= 0 && x < N && y >= 0 && y < M && copy[x][y] == 0) {
					copy[x][y] = 2;
				}
			}
		}
		
		countSafeZone();
	}
	
	static void combination(int start, int count) {
		if(count == 3) {
			simulate();
			
			return;
		}
		
		for(int i=start; i<blank.size(); i++) {
			selected[count] = i;
			
			combination(i+1, count+1);
		}
	}
	
	static void countSafeZone() {
		int count = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(copy[i][j] == 0) {
					count++;
				}
			}
		}
		
		answer = Math.max(answer, count);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		origin = new int[N][M];
		copy = new int[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<M; j++) {
				origin[i][j] = Integer.parseInt(st.nextToken());
				
				if(origin[i][j] == 0) {				//빈 칸
					blank.add(new int[] {i, j});
				}
				else if(origin[i][j] == 2) {		//바이러스
					virus.add(new int[] {i, j});
					virusQueue.add(new int[] {i, j});
				}
			}
		}
		
		combination(0, 0);
		System.out.println(answer);
	}

}