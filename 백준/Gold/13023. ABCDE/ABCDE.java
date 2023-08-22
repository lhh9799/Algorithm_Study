import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {
	
	static TreeSet<Integer>[] friends;
	static boolean[] visited;
	
	public static void clearVisited() {
		Arrays.fill(visited, false);
	}
	
	public static void dfs(int start, int depth) {
		if(depth == 5) {
			System.out.println(1);
			System.exit(0);
		}
		
		for(int j : friends[start]) {
			if(!visited[j]) {
				visited[j] = true;
				dfs(j, depth+1);
				visited[j] = false;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String line = "";
		
		int N = Integer.parseInt(st.nextToken());	//캠프의 참가자 수
		int M = Integer.parseInt(st.nextToken());	//친구 관계의 수
		friends = new TreeSet[N];					//참가자 별 친구 정보를 저장하는 TreeSet
		visited = new boolean[N];
		int friend1 = 0;
		int friend2 = 0;
		
		for(int i=0; i<N; i++) {
			friends[i] = new TreeSet<Integer>();
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			friend1 = Integer.parseInt(st.nextToken());
			friend2 = Integer.parseInt(st.nextToken());
			
			//양방향 추가
			friends[friend1].add(friend2);
			friends[friend2].add(friend1);
		}
		
		for(int i=0; i<N; i++) {
			clearVisited();
			visited[i] = true;
			dfs(i, 1);
		}
		
		System.out.println(0);
	}

}