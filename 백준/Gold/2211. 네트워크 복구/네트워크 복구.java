import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * @author kwang
 *
 */
public class Main {
	static class Dot implements Comparable<Dot>{
		int a; // A 컴퓨터
		int b; // B 컴퓨터
		int t; // 통신 시간
		
		public Dot(int a, int b, int t) {
			this.a = a;
			this.b = b;
			this.t = t;
		}

		@Override
		public int compareTo(Dot o) { // 통신 시간이 작은 순으로
			return Integer.compare(this.t, o.t);
		}
		
	}
	
	static int N;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		List<Dot>[] list = new ArrayList[N+1];
		for (int i = 0;i < N+1; i++) {
			list[i] = new ArrayList<>();
		}
		
		PriorityQueue<Dot> pq = new PriorityQueue<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			// 주어진 회선 정보 인접리스트에 넣기
			list[a].add(new Dot(a, b, t));
			list[b].add(new Dot(b, a, t));
		}
		
		int[] mindis = new int[N+1]; // 최소 거리 저장할 배열
		Arrays.fill(mindis, Integer.MAX_VALUE); // 최대값으로 초기값 설정
		boolean[] visit = new boolean[N+1]; // 방문체크할 배열
		pq.offer(new Dot(1, 1, 0)); // 시작점 넣기(1번 컴퓨터에서 자기 자신으로 가는 경우)
		
		mindis[1] = 0;
		int cnt = 0;
		// 다익스트라 (한 점에서 다른 모든 점까지의 최소거리 구하는 알고리즘)
		while (!pq.isEmpty()) {
			Dot tmp = pq.poll();
			visit[tmp.a] = true;
			
			if (!visit[tmp.b]) { // 방문 안했던 곳이면
				visit[tmp.b] = true;
				sb.append(tmp.a).append(" ").append(tmp.b).append("\n");
				cnt++;
			}
			
			for (int i = 0; i < list[tmp.b].size(); i++) { // 인접노드와의 거리 
				Dot next = list[tmp.b].get(i);
				if (mindis[next.b] > mindis[tmp.b] + next.t) { // 최소거리로 구해놓은 값보다 새로 나온 간선을 활용해 간 거리가 더 짧으면
					mindis[next.b] = mindis[tmp.b] + next.t; // 최소거리 바꿔주기
					pq.offer(new Dot(next.a, next.b, mindis[next.b])); // 새로운 최소거리 값 다시 우선순위 큐에 넣어주기					
				}
			}
		}
		//System.out.println(Arrays.toString(mindis));
		System.out.println(cnt);
		System.out.println(sb);
	}
}