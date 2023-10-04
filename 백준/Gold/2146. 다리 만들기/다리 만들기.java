import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int[][] arr ;
	static boolean[][] isVisited ;
	static int N;
	static int[] dr = {1,-1,0,0};
	static int[] dc = {0,0,1,-1};
	static int result = Integer.MAX_VALUE;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		N = sc.nextInt();
		
		
		arr = new int[N][N] ;
		isVisited = new boolean[N][N];
		
		
		for(int r = 0 ; r < N ;r ++) {
			for(int c = 0 ; c < N ;c++) {
				arr[r][c] = sc.nextInt();
			}
		}
		
		int count = 1;
		
		for(int r = 0 ; r < N ;r ++) {
			for(int c = 0 ; c < N ;c++) {
				if(arr[r][c]==1 && !isVisited[r][c]) {
				dfs(r,c,count);
				count++;
				}
			}
		}
		
//		for(int r = 0 ; r < N ;r ++) {
//			for(int c = 0 ; c < N ;c++) {
//				System.out.print(arr[r][c]);
//				}
//			System.out.println();
//			}
		
		for(int r = 0 ; r < N ;r ++) {
			for(int c = 0 ; c < N ;c++) {
				if(arr[r][c] != 0) {
					isVisited = new boolean[N][N];
					bfs(r,c, arr[r][c]);
				}
			}
		}
		
		System.out.println(result);
		
	}
	
	
	
	
	
	
	
	private static void bfs(int r, int c, int island) {
		// TODO Auto-generated method stub
		
		Queue<int[]> q = new ArrayDeque<int[]>() ;
		
		q.add(new int[] {r,c,island , 0});
		isVisited[r][c] = true;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i = 0 ; i < 4 ;i++) {
				int nextR = cur[0] + dr[i];
				int nextC = cur[1] + dc[i];
				
				if(isRange(nextR,nextC) && !isVisited[nextR][nextC] ) {
					
					if(arr[nextR][nextC] == 0 && cur[3] < result) {
						isVisited[nextR][nextC] = true;
						q.add(new int[] {nextR, nextC , island , cur[3] +1 });
					}
						
					
					
					if(arr[nextR][nextC] !=0 && arr[nextR][nextC] != island) {
						result = Math.min(result, cur[3]);
						break;
					}
						
				}
			}
		}
	}







	private static void dfs(int r, int c , int count) {
		// TODO Auto-generated method stub
		isVisited[r][c] = true;
		arr[r][c] = count;
		
		
		for(int i = 0 ; i < 4 ; i++) {
			int nextR = r+ dr[i];
			int nextC = c + dc[i];
			
			if(isRange(nextR,nextC) && !isVisited[nextR][nextC] && arr[nextR][nextC] == 1) {
				dfs(nextR, nextC , count);
			}
		}
		
	}
	
	private static boolean isRange(int r , int c) {
		if(r >= N || r < 0 || c >=N || c < 0) 
			return false;
			
		
		return true;
	}

}