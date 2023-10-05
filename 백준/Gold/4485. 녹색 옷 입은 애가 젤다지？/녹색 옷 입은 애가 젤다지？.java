import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Main {
	static int N ;
	static int[][] arr ;
	static int[][] arr2;
	static boolean[][] isVisited;
	
	static int[] dR = {1,-1,0,0};
	static int[] dC = {0,0,-1,1};
	
 	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int count = 1 ;
		
		while(true) {
			 N = sc.nextInt();
			
			if(N ==0 ) break;
			
			arr = new int[N][N];
			arr2 = new int[N][N]; 
			
			for(int i = 0 ; i < N ; i++) {
				for(int j = 0 ; j < N ;j++) {
					arr[i][j] = sc.nextInt();
					arr2[i][j] = Integer.MAX_VALUE;
				}
			}
			
			arr2[0][0] = arr[0][0];
			
			//isVisited= new boolean[N][N];		
			bfs(0,0);
			
			
			System.out.println("Problem "+ count + ": "  + arr2[N-1][N-1]);
			count++;
		}
	}
	
	
	private static void bfs(int r, int c) {
		
		Queue<int[]> q = new ArrayDeque<int[]>();
		
		//isVisited[r][c] = true;
		
		q.add(new int[] {r , c});
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i =0 ; i < 4 ; i++) {
				int nextR = cur[0] + dR[i];
				int nextC = cur[1] + dC[i];
				
				if(isRange(nextR,nextC) &&  arr2[cur[0]][cur[1]] + arr[nextR][nextC] <arr2[nextR][nextC] ) {
					arr2[nextR][nextC] = arr2[cur[0]][cur[1]] + arr[nextR][nextC] ;
					q.add(new int[] {nextR,nextC});
					
					
				}
				
			}
			
			
			
			
		}
		
	}
	
	
	
	private static boolean isRange(int r , int c) {
		if(r >= N || r <0 || c >=N || c <0 ) return false;
		
		
		return true;
	}
}