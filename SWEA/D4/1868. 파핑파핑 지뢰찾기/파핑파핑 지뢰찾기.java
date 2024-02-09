import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Solution {
	
	//좌상, 상, 우상, 왼, 우, 좌하, 하, 우하
	static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
	static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
	
	static int N;
	static char[][] map;
	static int clickCount;
	
	static boolean isMineThere(int startX, int startY) {
		int x, y;
		
		//8방 탐색
		for(int i=0; i<8; i++) {
			x = startX + dx[i];
			y = startY + dy[i];
			
			if(x >= 0 && x < N && y >= 0 && y < N && map[x][y] == '*') {
				return true;
			}
		}
		
		return false;
	}
	
	static void explore(int startX, int startY) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		int[] coord = new int[2];
		int adjX, adjY;
		
		queue.offer(new int[] {startX, startY});
		map[startX][startY] = 'c';
		
		while(!queue.isEmpty()) {
			coord = queue.poll();
			
			//8방 탐색
			for(int i=0; i<8; i++) {
				adjX = coord[0] + dx[i];
				adjY = coord[1] + dy[i];
				
				if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && map[adjX][adjY] == '.') {
					map[adjX][adjY] = 'c';
					
					if(!isMineThere(adjX, adjY)) {
						queue.add(new int[] {adjX, adjY});
					}
				}
			}
		}
		
		clickCount++;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());	//테스트 케이스의 수 T
		
		for(int i=1; i<=T; i++) {
			N = Integer.parseInt(br.readLine());	//N(1 ≤ N ≤ 300) #지뢰 찾기를 하는 표의 크기
			map = new char[N][N];
			clickCount = 0;
			
			for(int j=0; j<N; j++) {
				map[j] = br.readLine().toCharArray();
			}
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(map[j][k] == '.' && !isMineThere(j, k)) {
						explore(j, k);
					}
				}
			}
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(map[j][k] == '.') {
						clickCount++;
					}
				}
			}
			
			
			sb.append("#" + i + " " + clickCount + "\n");
		}
		System.out.println(sb.toString());
	}

}