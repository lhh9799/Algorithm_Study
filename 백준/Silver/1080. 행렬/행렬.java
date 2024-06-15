import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 1080번 행렬
 * https://www.acmicpc.net/problem/1080
 * 
 * 실행시간: 84ms (백준)
 * 메모리: 11,620 KB
 * 
 * 그리디 문제입니다.
 * 
 * <q>현재 뒤집는 시도가 이전에 뒤집어 맞춰놓은 배열에 영향을 끼쳐서는 안된다.</q>
 * <cite>https://dev-minjeong.tistory.com/23</cite>
 * 
 * <q>왜 이렇게 하는것이 최소의 연산 횟수가 나오는지 의문이 생깁니다. 이유는, 3*3 행렬이 이동하면서, 이미 뒤집은 요소는 다시는 뒤집지 않기에 최소 연산횟수가 나옵니다.</q>
 * <cite>https://passionfruit200.tistory.com/m/644</cite>
 * 
 * (0, 0)부터 (N-2, M-2)까지 값을 비교해 일치하지 않으면 뒤집습니다. (왼쪽 위 (i, j)를 기준으로 3×3 크기의 행렬을 뒤집기 때문에 -2까지를 범위로 함)
 * 두 행렬이 일치하면 뒤집은 횟수를, 그렇지 않으면 -1을 출력합니다. 
 * 
 * </pre>
 * 
 *  @author 이현호
 */

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
		
		//왼쪽 위 (i, j)를 기준으로 3×3 크기의 행렬을 뒤집기 때문에 루프는 -2 (i < N-2; j < M-2)
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