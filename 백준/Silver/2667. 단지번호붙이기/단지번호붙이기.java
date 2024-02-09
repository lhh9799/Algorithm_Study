import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
	
	//상, 하, 좌, 우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static int N;					//지도의 크기 N
	static int[][] map;				//지도
	static boolean[][] visited;		//방문 배열
	static int group = 1;			//그룹 번호 1부터 시작 (개수는 -1 임)
	static ArrayList<Integer> houseList = new ArrayList<>();
	
	static void BFS(int x, int y) {
		int houseCount = 0;
		
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {x, y});
		int[] start = new int[2];
		int startX, startY;
		
		while(!queue.isEmpty()) {
			start = queue.poll();
			startX = start[0];
			startY = start[1];
			map[startX][startY] = group;
			houseCount++;
			
			for(int i=0; i<4; i++) {
				int adjacentX = startX + dx[i];
				int adjacentY = startY + dy[i];
				
				if(adjacentX >= 0 && adjacentX < N && adjacentY >= 0 && adjacentY < N && map[adjacentX][adjacentY] == -1 && !visited[adjacentX][adjacentY]) {
					queue.add(new int[] {adjacentX, adjacentY});
					visited[adjacentX][adjacentY] = true;
				}
			}
		}
		
		group++;
		houseList.add(houseCount);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());		//지도의 크기 N (정사각형이므로 가로와 세로의 크기는 같으며 5≤N≤25)
		map = new int[N][N];
		visited = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			char[] c = br.readLine().toCharArray();
			
			for(int j=0; j<N; j++) {
				if(c[j] == '1') {
					map[i][j] = -1;
				}
			}
		}
		
		for(int j=0; j<N; j++) {
			for(int k=0; k<N; k++) {
				if(map[j][k] == -1) {
					BFS(j, k);
				}
			}
		}
		
		Collections.sort(houseList);
		
		System.out.println(group - 1);
		for(int i : houseList) {
			System.out.println(i);
		}
	}

}