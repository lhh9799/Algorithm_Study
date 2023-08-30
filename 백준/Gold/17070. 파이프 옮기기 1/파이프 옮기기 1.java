import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int N = Integer.parseInt(br.readLine());
		int[][] house = new int[N+1][N+1];
		//경우의 수를 저장하는 3차원 배열
		int[][][] array = new int[N+1][N+1][3];	//[마지막 인덱스] 0: 가로, 1: 세로, 2: 대각선 도착 경우의 수
		array[1][2][0] = 1;						//초기 가로의 위치 1로 초기화
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=N; j++) {
				house[i][j] = Integer.parseInt(st.nextToken()) == 1 ? -1 : 0;
			}
		}
		
		for(int i=1; i<=N; i++) {
			for(int j=2; j<=N; j++) {
				//현재 위치로 이동 가능
				if(house[i][j] != -1) {
					if(i == 1 && j == 2) continue;	//초기화된 값은 수정하지 않음
					
					//1. 가로 모양 도착 #가로: (r, c-1) -> (r, c), 대각선: (r-1, c-1) -> (r, c)
					array[i][j][0] = array[i][j-1][0] + array[i][j-1][2];
					//2. 세로 모양 도착 #세로: (r-1, c) -> (r, c), 대각선: (r-1, c-1) -> (r, c)
					array[i][j][1] = array[i-1][j][1] + array[i-1][j][2];
					//3. 대각선 모양 도착 #가로: (r-1, c-1) -> (r, c) 단, 가로, 세로, 대각선 배열에서 (r-1, c-1) 모두 -1이 아니어야 대각선 이동 가능
					if(house[i-1][j] != -1 && house[i][j-1] != -1) {
						array[i][j][2] = array[i-1][j-1][0] + array[i-1][j-1][1] + array[i-1][j-1][2];
					}
				}
			}
		}
		
		System.out.println(array[N][N][0] + array[N][N][1] + array[N][N][2]);
	}

}