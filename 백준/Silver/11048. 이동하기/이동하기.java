import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] maze = new int[N+1][M+1];
		
		for(int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=1; j<=M; j++) {
				int prevMaxCount = Integer.max(Integer.max(maze[i-1][j], maze[i][j-1]), maze[i-1][j-1]);
				
				maze[i][j] = Integer.parseInt(st.nextToken()) + prevMaxCount;
			}
		}
		
		System.out.println(maze[N][M]);
	}

}