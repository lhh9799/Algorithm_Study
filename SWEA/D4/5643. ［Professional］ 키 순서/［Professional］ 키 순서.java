import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 5643. [Professional] 키 순서
 * 
 * 실행시간: 1,847ms (SWEA)
 * 메모리: 102,012 KB
 * 
 * BFS / 플로이드-워셜문제입니다.
 * 
 * 1번부터 N번까지 번호가 붙여져 있는 학생들에 대하여 두 학생끼리의 키를 비교한 결과의 일부가 주어졌을 때, 자신의 키가 몇 번째인지 알 수 있는 학생들이 모두 몇 명인지 구하는 문제입니다.
 * 
 * 자신의 키가 몇 번째인지 알기 위해서는 자신을 제외한 N-1명과의 대소관계를 알아야 합니다.
 * BFS로 (자신보다 키가 작은 학생들의 수 + 자신보다 키가 큰 학생들의 수)가 N-1인 학생의 수를 출력합니다.
 * 
 * 플로이드-워셜로 풀면 약 1,200ms, BFS로 풀면 약 1,800ms정도 소요됩니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		ArrayDeque<Integer> queue = new ArrayDeque<>();		//자신보다 키가 작거나 큰 학생들을 탐색하는 큐
		int a = 0, b = 0;									//a: 키가 작은 학생의 번호, b: 키가 큰 학생의 번호
		
		int T = Integer.parseInt(br.readLine());			//테스트케이스의 개수 T
		for(int i=1; i<=T; i++) {
			int N = Integer.parseInt(br.readLine());		//학생들의 수 N
			int M = Integer.parseInt(br.readLine());		//두 학생의 키를 비교한 횟수 M
			int[][] map = new int[N][N];					//학생들의 키 상관관계
			int[] correlation = new int[N];					//진출차수 + 진입차수를 저장하는 배열
			boolean[] visited = new boolean[N];				//방문 배열
			int answer = 0;
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				
				a = Integer.parseInt(st.nextToken()) - 1;	//키가 작은 학생의 번호 (0부터 시작)
				b = Integer.parseInt(st.nextToken()) - 1;	//키가 큰 학생의 번호 (0부터 시작)
				
				map[a][b] = 1;								//키가 작은 학생 -> 키가 큰 학생: -1
				map[b][a] = -1;								//키가 큰 학생 -> 키가 작은 학생: 1
			}
			
			//자신보다 키가 큰 학생의 수 BFS 탐색
			for(int j=0; j<N; j++) {
				queue.clear();								//큐 초기화
				queue.add(j);								//자기 자신 큐에 삽입
				Arrays.fill(visited, false);				//방문 배열 초기화
				
				while(!queue.isEmpty()) {
					int start = queue.poll();
					
					for(int k=0; k<N; k++) {
						//큐에서 뺀 원소와 연결되어 있으면서 키가 큰 학생이 있는 경우
						if(!visited[k] && map[start][k] == 1) {
							queue.add(k);					//큐에 추가
							correlation[j]++;				//진출차수 증가
							visited[k] = true;				//방문한 것으로 표시
						}
					}
				}
			}
			
			//자신보다 키가 작은 학생의 수 BFS 탐색
			for(int j=0; j<N; j++) {
				queue.clear();								//큐 초기화
				queue.add(j);								//자기 자신 큐에 삽입
				Arrays.fill(visited, false);				//방문 배열 초기화
				
				while(!queue.isEmpty()) {
					int start = queue.poll();
					
					for(int k=0; k<N; k++) {
						//큐에서 뺀 원소와 연결되어 있으면서 키가 작은 학생이 있는 경우
						if(!visited[k] && map[start][k] == -1) {
							queue.add(k);					//큐에 추가
							correlation[j]++;				//진입차수 증가
							visited[k] = true;				//방문한 것으로 표시
						}
					}
				}
			}
			
			//(자신보다 키가 작은 학생들의 수 + 자신보다 키가 큰 학생들의 수)가 N-1인 학생의 수 카운트
			for(int j=0; j<N; j++) {
				if(correlation[j] == N-1) {
					answer++;
				}
			}
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}