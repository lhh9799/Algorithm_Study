import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int N, cost[][], min = Integer.MAX_VALUE;
	static boolean visit[];
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		visit = new boolean[N];
		cost = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++)
				cost[i][j] = Integer.parseInt(st.nextToken());
		}
		
		visit[0] = true;				//이거 없으면 틀림. 중간에 얘 방문 할 수도 있어서..
		backTracking(0, 0, 0, 0);
		System.out.println(min == Integer.MAX_VALUE ? 0 : min);
	}

	private static void backTracking(int start, int now, int depth, int sum) {
		if (depth == N - 1) {
			if (cost[now][start] != 0)	//시작점으로 돌아가는 경로가 존재한다면 
				min = Math.min(min, sum + cost[now][start]);
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if (visit[i] || cost[now][i] == 0) continue;
			
			if (sum > min) return;
			
			visit[i] = true;
			backTracking(start, i, depth + 1, sum + cost[now][i]);
			visit[i] = false;
		}
	}
}