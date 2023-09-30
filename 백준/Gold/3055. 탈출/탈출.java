import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {

	static void waterFillSimulation(final char[][] map, int[][] waterTimeMap, int R, int C) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[R][C];				//물 시물레이션을 위한 방문배열
		//상, 하, 좌, 우
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(map[i][j] == '*') {
					visited[i][j] = true;						//초기 물의 위치를 방문한 것으로 표시
					waterTimeMap[i][j] = 0;						//초기 위치 시간 기록 (0초에 물이 참)
					queue.offer(new int[] {i, j, 0});			//초기 물의 위치와 시간을 큐에 저장
				}
			}
		}
		
		while(!queue.isEmpty()) {
			int[] startInfo = queue.poll();
			int startX = startInfo[0];
			int startY = startInfo[1];
			int time = startInfo[2];
			
			//4방 탐색
			for(int i=0; i<4; i++) {
				int x = startX + dx[i];
				int y = startY + dy[i];
				
				//물 이동 가능
				if(x >= 0 && x < R && y >= 0 && y < C && (map[x][y] == '.' || map[x][y] == 'S') && !visited[x][y]) {
					visited[x][y] = true;						//방문한 것으로 표시
					waterTimeMap[x][y] = time + 1;				//물이 차는 시간을 별도 지도에 저장 (+물이 찬 것으로 표시)
					queue.offer(new int[] {x, y, time + 1});	//큐에 추가
				}
			}
		}
	}
	
	static int[] findHedgehogCoord(final char[][] map, int R, int C) {
		int[] hedgehogCoord = new int[2];
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(map[i][j] == 'S') {
					hedgehogCoord[0] = i;
					hedgehogCoord[1] = j;
					
					return hedgehogCoord;
				}
			}
		}
		
		System.err.println("고슴도치의 위치를 찾을 수 없음");
		
		return null;
	}
	
	static int hedgehogSimulation(final char[][] map, int[][] waterTimeMap, int R, int C) {
		int[] hedgehogCoord = findHedgehogCoord(map, R, C);
		
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		boolean[][] visited = new boolean[R][C];						//고슴도치 시물레이션을 위한 방문배열
		//상, 하, 좌, 우
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		visited[hedgehogCoord[0]][hedgehogCoord[1]] = true;				//초기 고슴도치의 위치를 방문한 것으로 표시
		queue.offer(new int[] {hedgehogCoord[0], hedgehogCoord[1], 0});	//초기 고슴도치의 위치와 시간을 큐에 저장
		
		while(!queue.isEmpty()) {
			int[] startInfo = queue.poll();
			int startX = startInfo[0];
			int startY = startInfo[1];
			int time = startInfo[2];
			
			//4방 탐색
			for(int i=0; i<4; i++) {
				int x = startX + dx[i];
				int y = startY + dy[i];
				
				//고슴도치 이동 가능
				if(x >= 0 && x < R && y >= 0 && y < C && (map[x][y] == '.' || map[x][y] == 'D' || map[x][y] == 'S') && !visited[x][y]) {
					if(map[x][y] == 'D') {
						return time + 1;
					}
					if(time + 1 < waterTimeMap[x][y] || waterTimeMap[x][y] == 0) {
						visited[x][y] = true;						//방문한 것으로 표시
						queue.offer(new int[] {x, y, time + 1});	//큐에 추가
					}
				}
			}
		}
		
		return -1;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line = br.readLine();
		int R = Integer.parseInt(line.split(" ")[0]);
		int C = Integer.parseInt(line.split(" ")[1]);
		
		char[][] map = new char[R][C];
		int[][] waterTimeMap = new int[R][C];
		
		for(int i=0; i<R; i++) {
			line = br.readLine();
			
			for(int j=0; j<C; j++) {
				map[i][j] = line.charAt(j);
			}
		}
		
		waterFillSimulation(map, waterTimeMap, R, C);
		
		int answer = hedgehogSimulation(map, waterTimeMap, R, C);
		System.out.println(answer == -1 ? "KAKTUS" : answer);
	}

}