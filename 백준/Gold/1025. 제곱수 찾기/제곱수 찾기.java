import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int answer = -1;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//N행
		int M = Integer.parseInt(st.nextToken());	//M열
		int[][] matrix = new int[N][M];
		
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			
			for(int j=0; j<M; j++) {
				matrix[i][j] = line.charAt(j) - '0';
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				for(int di=-N; di<N; di++) {
					for(int dj=-M; dj<M; dj++) {
						if(di == 0 && dj == 0) {
							continue;
						}
						
						int num = 0;
						int selectedRow = i;
						int selectedCol = j;
						
						while(selectedRow >= 0 && selectedRow < N && selectedCol >= 0 && selectedCol < M) {
							num = num * 10 + matrix[selectedRow][selectedCol];
							
							selectedRow += di;
							selectedCol += dj;
							
							if(Math.sqrt(num) % 1 == 0) {
								answer = Math.max(answer, num);
							}
						}
					}
				}
			}
		}
		
		System.out.println(answer);
	}

}