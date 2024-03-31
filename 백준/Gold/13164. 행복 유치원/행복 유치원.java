import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		long answer = 0;
		
		st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		long[] child = new long[N];
		long[] gap = new long[N-1];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<N; i++) {
			child[i] = Long.parseLong(st.nextToken());
		}
		
		//N명의 원생들의 키 차이 (N-1)개 계산
		for(int i=1; i<N; i++) {
			gap[i-1] = child[i] - child[i-1];
		}
		
		Arrays.sort(gap);
		
		//K개의 그룹을 만들기 위해서 (K-1)개의 구분선 설정
		//(N-1)개의 키 차이 중 구분선을 통해 (K-1)개의 키 차이를 없앨 수 있음
		//(N-1) - (K-1) = N-K개 키 차이의 합
		for(int i=0; i<N-K; i++) {
			answer += gap[i];
		}
		
		System.out.println(answer);
	}

}