import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int[][] pascalTriangle;					//파스칼의 삼각형
	
	static void makeTriangle(int n, int k) {
		for(int i=1; i<n; i++) {
			pascalTriangle[i][0] = 1;				//왼쪽 가장자리 1로 채움
			
			for(int j=1; j<Math.min(i, k); j++) {	//1부터 시작!!!
				pascalTriangle[i][j] = pascalTriangle[i-1][j-1] + pascalTriangle[i-1][j];
			}
			//오른쪽 가장자리는 좌상 1과 상 0이 합쳐져서 처리하지 않아도 1이 됨
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());
		int[][] input = new int[T][2];
		int max_N = Integer.MIN_VALUE;
		int max_M = Integer.MIN_VALUE;
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			
			input[i][0] = Integer.parseInt(st.nextToken());
			input[i][1] = Integer.parseInt(st.nextToken());
			
			max_N = Math.max(max_N, input[i][0]);
			max_M = Math.max(max_M, input[i][1]);
		}
		
		//최대 행 길이 + 2 (기수 != 서수, 0번 행 빔)
		pascalTriangle = new int[max_M+2][max_N+2];
		makeTriangle(max_M+2, max_N+2);
		
		for(int i=0; i<T; i++) {
			sb.append(pascalTriangle[input[i][1]+1][input[i][0]] + "\n");	//행은 1칸 밀려서 시작하므로 1 추가
		}

		System.out.println(sb.toString());
	}

}