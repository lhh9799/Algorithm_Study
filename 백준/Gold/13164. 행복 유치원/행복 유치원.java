import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 13164번 행복 유치원
 * https://www.acmicpc.net/problem/13164
 * 
 * 실행시간: 588ms (백준)
 * 메모리: 66,400 KB
 * 
 * 그리디 문제입니다.
 * N명의 원생들을 키 순서대로 일렬로 줄 세우고, 총 K개의 조로 나누었을 때, (조에서 가장 키가 큰 원생과 가장 키가 작은 원생의 키 차이)의 합의 최소를 구하는 문제입니다.
 * 
 * 문제 예) N = 5, K = 3
 * 원생들의 키: 1 3 5 6 10
 * 
 * [로직]
 * 1. N명의 원생들을 K개의 조로 나누어봅니다.
 * 		(1) 1 | 3 | 5 6 10 => 5
 * 		(2) 1 | 3 5 | 6 10 => 2 + 4 = 6
 * 		(3) 1 | 3 5 6 | 10 => 3	(최소!)
 * 		(4) 1 3 | 5 | 6 10 => 2 + 4 = 6
 * 		(5) 1 3 | 5 6 | 10 => 2 + 1 => 3 (최소!)
 * 		(6) 1 3 5 | 6 | 10 => 4
 * 2. 조를 나누는 기준은 키 차이가 가장 크게 나는 구간이 되어야 합니다.
 * 												//* (): 나누어진 조의 경계
 * 		(1) 1 | 3 | 5 6 10 => 5					//(2) (2) 1 4
 * 		(2) 1 | 3 5 | 6 10 => 2 + 4 = 6			//(2) 2 (1) 4
 * 		(3) 1 | 3 5 6 | 10 => 3	(최소!)			//(2) 2 1 (4)
 * 		(4) 1 3 | 5 | 6 10 => 2 + 4 = 6			//2 (2) (1) 4
 * 		(5) 1 3 | 5 6 | 10 => 2 + 1 => 3 (최소!)	//2 (2) 1 (4)
 * 		(6) 1 3 5 | 6 | 10 => 4					//2 2 (1) (4)
 * 3. 키 차이 (N-1)개에서 큰 (그룹의 수 - 1) (= K-1) 개의 숫자를 제외하고 남은 숫자들을 합하면 문제의 답이 됩니다.
 * 		2 2 1 4에서 큰 숫자인 2, 4 제외 -> 1, 2의 합이 답 (2개를 제외한 이유는 그룹이 3개이므로 칸막이의 수는 2가 됨)
 * 4. (K-1)개의 큰 숫자를 제외하고 합하기 위해서 키 차이를 오름차순으로 정렬하고 전체 키 차이 개수인 (N-1)에서 큰 수 (K-1)개를 뺀 (N-K)개의 키 차이를 합하면 됩니다.
 * 
 * 1. N명의 원생들의 키 차이 (N-1)개를 구합니다.
 * 		원생들의 키 차이: 2 2 1 4
 * 2. 조를 나누어봅니다.
 * 		(1) 2 | 2 | 1 4
 * 		(2) 2 | 2 1 | 4
 * 		(3) 2 2 | 1 | 4
 * 		(4) 2 2 1 4
 * 
 * [구현]
 * 1. N명의 원생들의 키 차이 (N-1)개를 구합니다.
 * 		원생들의 키 차이: 2 2 1 4
 * 2. 키 차이 배열을 정렬합니다.
 * 		원생들의 키 차이 (정렬됨): 1 2 2 4
 * 3. 내림차순으로 정렬한 키 차이 배열에서 (N-K)개 까지의 키 차이의 합을 구합니다.
 * 		1 + 2 = 3 (2, 4 제외됨)
 * 
 * </pre>
 * 
 *  @author 이현호
 */

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