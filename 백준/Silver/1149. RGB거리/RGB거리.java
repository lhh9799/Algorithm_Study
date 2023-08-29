import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int N = Integer.parseInt(br.readLine());
		int[][] cost = new int[N+1][3];
		
		for(int i=1; i<N+1; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<3; j++) {
				//j = 0: 1, 2 | j = 1: 0, 2 | j = 2: 0, 1 => (j+1) % 3, (j+2) % 3 뺀 값
				cost[i][j] = Integer.parseInt(st.nextToken()) + Math.min(cost[i-1][(j+1)%3], cost[i-1][(j+2)%3]);
			}
		}
		
		System.out.println(Arrays.stream(cost[N]).min().getAsInt());
	}

}