import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	
	static int N;
	static ArrayList<TreeSet<Integer>> graph;
	static boolean[] visited;
	static TreeMap<Integer, Integer> order = new TreeMap<>();
	static int sequence = 1;
	static StringBuilder sb = new StringBuilder();
	
	static void dfs(int vertex) {
		order.put(vertex, sequence++);
		visited[vertex] = true;
		
		//내림차순으로 그래프 탐색
		for(int connected : graph.get(vertex)) {
			if(!visited[connected]) {
				dfs(connected);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());		//정점의 수 N (5 ≤ N ≤ 100,000)
		int M = Integer.parseInt(st.nextToken());	//간선의 수 M (1 ≤ M ≤ 200,000)
		int R = Integer.parseInt(st.nextToken());	//시작 정점 R (1 ≤ R ≤ N)
		
		visited = new boolean[N+1];
		graph = new ArrayList<>();
		
		//N+1개 초기화
		for(int i=0; i<N+1; i++) {
			graph.add(new TreeSet<Integer>(Collections.reverseOrder()));
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			//양방향 간선 연결 정보 저장
			graph.get(start).add(end);
			graph.get(end).add(start);
		}
		
		dfs(R);
		
		for(int i=1; i<N+1; i++) {
			sb.append(order.getOrDefault(i, 0) + "\n");
		}
		
		System.out.println(sb.toString());
	}

}