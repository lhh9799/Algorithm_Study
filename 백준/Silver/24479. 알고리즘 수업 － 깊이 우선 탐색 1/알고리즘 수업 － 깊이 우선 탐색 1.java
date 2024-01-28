import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	
	static boolean[] visited;
	static TreeSet<Integer>[] linkedVertex;
	static int[] order;
	static int sequence = 1;
	
	static void dfs(int start) {
		order[start] = sequence++;
		visited[start] = true;
		
		for(int linked : linkedVertex[start]) {
			if(!visited[linked]) {
				dfs(linked);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//정점의 수 N (5 ≤ N ≤ 100,000)
		int M = Integer.parseInt(st.nextToken());	//간선의 수 M (1 ≤ M ≤ 200,000)
		int R = Integer.parseInt(st.nextToken());	//시작 정점 R (1 ≤ R ≤ N)
		
		visited = new boolean[N+1];					//정점 방문 배열
		linkedVertex = new TreeSet[N+1];
		order = new int[N+1];
		
		for(int i=0; i<N+1; i++) {
			linkedVertex[i] = new TreeSet<>();
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			linkedVertex[start].add(end);
			linkedVertex[end].add(start);
		}
		
		dfs(R);
		
		for(int i=1; i<N+1; i++) {
			System.out.println(order[i]);
		}
	}

}