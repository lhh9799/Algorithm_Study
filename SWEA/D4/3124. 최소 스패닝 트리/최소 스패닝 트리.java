import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Edge implements Comparable<Edge> {
	int connectedVertex;
	int weight;
	
	Edge() {}
	Edge(int connectedVertex, int weight) {
		this.connectedVertex = connectedVertex;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edge o) {
		return Integer.compare(this.weight, o.weight);
	}
}

public class Solution {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int T = Integer.parseInt(br.readLine());	//전체 test case의 수 T (1 <= T <= 10)
		int A = 0, B = 0, C = 0;					//각 간선에 대한 정보를 나타내는 세 정수 A, B, C (A번 정점과 B번 정점이 가중치 C인 간선으로 연결되어 있다는 의미)
		PriorityQueue<Edge> pq;
		boolean[] visited;
		int V = 0, E = 0;
		int count = 0;	//연결한 간선의 수
		long answer = 0;
		Edge edge = null;
		ArrayList<Edge>[] graph;
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			V = Integer.parseInt(st.nextToken());	//정점의 개수
			E = Integer.parseInt(st.nextToken());	//간선의 개수
			
			count = 0;
			answer = 0;
			graph = (ArrayList<Edge>[]) new ArrayList[V+1];
			visited = new boolean[V+1];
			pq = new PriorityQueue<>();
			
			for(int j=0; j<graph.length; j++) {
				graph[j] = new ArrayList<>();
			}
			
			for(int j=0; j<E; j++) {
				st = new StringTokenizer(br.readLine());
				
				A = Integer.parseInt(st.nextToken());
				B = Integer.parseInt(st.nextToken());
				C = Integer.parseInt(st.nextToken());
				
				graph[A].add(new Edge(B, C));
				graph[B].add(new Edge(A, C));
			}
			
			for(Edge e : graph[1]) {
				pq.offer(e);
			}
			visited[1] = true;
			while(!pq.isEmpty()) {
				edge = pq.poll();
				
				if(!visited[edge.connectedVertex]) {
					visited[edge.connectedVertex] = true;
					answer += edge.weight;
					
					for(Edge e : graph[edge.connectedVertex]) {
						if(!visited[e.connectedVertex]) {
							pq.offer(e);
						}
					}
				}
			}
			
			sb.append("#" + i + " " + answer + "\n");
		}
		System.out.println(sb.toString());
	}

}