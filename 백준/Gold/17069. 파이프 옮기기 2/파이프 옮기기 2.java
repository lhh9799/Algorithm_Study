import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int N = Integer.parseInt(br.readLine());			//집의 크기 N
		boolean[][] house = new boolean[N+1][N+1];
		long[][][] ways = new long[3][N+1][N+1];			//이동할 수 있는 방법 (0번 행: 가로 이동 방법, 1번 행: 세로 이동 방법, 2번 행: 대각선 이동 방법)
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=N; j++) {
				//true: 벽 아님, false: 벽
				house[i][j] = Integer.parseInt(st.nextToken()) == 1 ? false : true;
			}
		}
		
		ways[0][1][2] = 1;							//가로 시작위치 1로 초기화
		
		//(i, j): 머리의 위치
		for(int i=1; i<=N; i++) {
			for(int j=2; j<=N; j++) {
				if(i==1 && j==2) continue;
				
				if(house[i][j]) {
					//1. 가로 이동
					ways[0][i][j] = ways[0][i][j-1] + ways[2][i][j-1];	//직전 가로 위치 + 대각선 위치
					//2. 세로 이동
					ways[1][i][j] = ways[1][i-1][j] + ways[2][i-1][j];
					
					//3. 대각선 이동 (가로 -> 대각 회전, 세로 -> 대각 회전)
					if(house[i-1][j] && house[i][j-1]) {
						ways[2][i][j] = ways[2][i-1][j-1] + ways[0][i-1][j-1] + ways[1][i-1][j-1];
					}
				}
			}
		}
		
		System.out.println(ways[0][N][N] + ways[1][N][N] + ways[2][N][N]);
	}

}