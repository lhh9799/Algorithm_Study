import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int flipCount = 0;
		boolean isEqual = true;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//행렬의 크기 N
		int M = Integer.parseInt(st.nextToken());	//행렬의 크기 M
		
		int A[][] = new int[N][M];
		int B[][] = new int[N][M];
		
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			
			for(int j=0; j<M; j++) {
				A[i][j] = line.charAt(j) - '0';
			}
		}
		
		for(int i=0; i<N; i++) {
			String line = br.readLine();
			
			for(int j=0; j<M; j++) {
				B[i][j] = line.charAt(j) - '0';
			}
		}
		
		//왼쪽 위 (i, j)를 기준으로 하기 때문에 루프는 -2 (i < N-2; j < M-2)
		for(int i=0; i<N-2; i++) {
			for(int j=0; j<M-2; j++) {
				if(A[i][j] != B[i][j]) {
					//왼쪽 위 (i, j)를 기준으로 원소 바꾸기
					for(int k=i; k<i+3; k++) {
						for(int l=j; l<j+3; l++) {
							A[k][l] = 1 - A[k][l];
						}
					}
					
					flipCount++;
				}
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(A[i][j] != B[i][j]) {
					isEqual = false;
					
					break;
				}
			}
		}
		
		System.out.println(isEqual ? flipCount : -1);
	}

}