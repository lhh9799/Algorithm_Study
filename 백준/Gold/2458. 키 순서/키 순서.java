import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		ArrayDeque<Integer> queue = new ArrayDeque<>();		//자신보다 키가 작거나 큰 학생들을 탐색하는 큐
		int a = 0, b = 0;									//a: 키가 작은 학생의 번호, b: 키가 큰 학생의 번호
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());			//학생들의 수 N
		int M = Integer.parseInt(st.nextToken());			//두 학생의 키를 비교한 횟수 M
		int[][] map = new int[N][N];						//학생들의 키 상관관계
		int[] correlation = new int[N];						//진출차수 + 진입차수를 저장하는 배열
		boolean[] visited = new boolean[N];					//방문 배열
		int answer = 0;
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			a = Integer.parseInt(st.nextToken()) - 1;		//키가 작은 학생의 번호 (0부터 시작)
			b = Integer.parseInt(st.nextToken()) - 1;		//키가 큰 학생의 번호 (0부터 시작)
			
			map[a][b] = 1;									//키가 작은 학생 -> 키가 큰 학생: -1
			map[b][a] = -1;									//키가 큰 학생 -> 키가 작은 학생: 1
		}
		
		//자신보다 키가 큰 학생의 수 BFS 탐색
		for(int i=0; i<N; i++) {
			queue.clear();									//큐 초기화
			queue.add(i);									//자기 자신 큐에 삽입
			Arrays.fill(visited, false);					//방문 배열 초기화
			
			while(!queue.isEmpty()) {
				int start = queue.poll();
				
				for(int j=0; j<N; j++) {
					//큐에서 뺀 원소와 연결되어 있으면서 키가 큰 학생이 있는 경우
					if(!visited[j] && map[start][j] == 1) {
						queue.add(j);						//큐에 추가
						correlation[i]++;					//진출차수 증가
						visited[j] = true;					//방문한 것으로 표시
					}
				}
			}
		}
		
		//자신보다 키가 작은 학생의 수 BFS 탐색
		for(int i=0; i<N; i++) {
			queue.clear();									//큐 초기화
			queue.add(i);									//자기 자신 큐에 삽입
			Arrays.fill(visited, false);					//방문 배열 초기화
			
			while(!queue.isEmpty()) {
				int start = queue.poll();
				
				for(int j=0; j<N; j++) {
					//큐에서 뺀 원소와 연결되어 있으면서 키가 작은 학생이 있는 경우
					if(!visited[j] && map[start][j] == -1) {
						queue.add(j);						//큐에 추가
						correlation[i]++;					//진입차수 증가
						visited[j] = true;					//방문한 것으로 표시
					}
				}
			}
		}
		
		//(자신보다 키가 작은 학생들의 수 + 자신보다 키가 큰 학생들의 수)가 N-1인 학생의 수 카운트
		for(int i=0; i<N; i++) {
			if(correlation[i] == N-1) {
				answer++;
			}
		}
		
		System.out.println(answer);
	}

}