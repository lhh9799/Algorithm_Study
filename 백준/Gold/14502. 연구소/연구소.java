import java.io.BufferedInputStream;
import java.util.Scanner;

import javax.imageio.ImageTypeSpecifier;

public class Main {
	static int N , M, result , max = 0 ;
	static int[][] arr , arr1;
	
	static boolean[][] isVisited;
	
	static int[] dr = {1,-1,0,0};
	static int[] dc = {0,0,-1,1};
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		 N = sc.nextInt();
		 M = sc.nextInt();
		arr = new int[N][M];
		arr1 = new int[N][M];
		
		for(int i = 0 ; i < N ;i++) {
			for(int j = 0 ; j < M ; j++) {
				arr[i][j] = sc.nextInt();
			}
		}
		
		
		for(int i =0 ; i < N*M ; i++) {
			for(int j = i+1 ; j < N*M ; j++ ) {
				for(int k = j+1 ; k < N*M ;k++) {
					
					
					
					 for(int r = 0 ; r < N ;r++) {
						 for(int c = 0 ; c < M ; c++ ) {
							 arr1[r][c] = arr[r][c];
						 }
					 }
					
					if(arr1[i/M][i%M] != 0)
						continue;
					
					if(arr1[j/M][j%M] != 0)
						continue;
					
					if(arr1[k/M][k%M] != 0 )
						continue;
					
					arr1[i/M][i%M] = 1 ;
					
					arr1[j/M][j%M] = 1;
					
					arr1[k/M][k%M] = 1;
					
					isVisited = new boolean[N][M];
					
					
					for(int r = 0 ; r < N ;r++) {
						for(int c = 0 ; c < M ;c++) {
							if(arr1[r][c] ==2)
								
								dfs(r,c);	
						}
					}
					
					result = 0 ;
					for(int r = 0 ; r < N ;r++) {
						for(int c = 0 ; c < M ;c++) {
							if(arr1[r][c]==0 && !isVisited[r][c])
								result++;
						}
					}
					
					
					max = Math.max(max, result);
					
				}
			}
		}
		
		System.out.println(max);
		
	}
	
	
	public static void dfs(int r , int c ) {
		
		isVisited[r][c] = true;
		
		for(int i =0 ; i< 4; i++) {
			
			int dR = r+ dr[i];
			int dC = c+ dc[i];
			
			if(isRangeCheck(dR, dC) && arr1[dR][dC] == 0 && !isVisited[dR][dC] ) {
				//arr1[r][c] = 3;
				dfs(dR, dC);
				
				
			}
		}
		
	}
	
	public static boolean isRangeCheck( int r, int c) {
		if(r >=N || r < 0 || c>=M || c <0 )
			return false;
		
		
		return true;
	}
}