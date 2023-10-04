import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static int N;									//지도의 크기 N
	static int[][] map;								//지도
	static boolean[][] visited;						//방문 배열
	static int answer = Integer.MAX_VALUE;			//가장 짧은 다리의 길이 (최댓값으로 초기화)
	//상, 하, 좌, 우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	static void clearVisitedArray() {
		for(int i=0; i<visited.length; i++) {
			Arrays.fill(visited[i], false);
		}
	}
	
	static void markIslandNumber(int row, int col, int islandNo) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		clearVisitedArray();
		int[] currentCoord = null;
		int adjX = 0, adjY = 0;
		
		queue.offer(new int[] {row, col});
		map[row][col] = islandNo;
		visited[row][col] = true;
		
		while(!queue.isEmpty()) {
			currentCoord = queue.poll();
			
			for(int i=0; i<4; i++) {
				adjX = currentCoord[0] + dx[i];
				adjY = currentCoord[1] + dy[i];
				
				//이번 메소드 호출 때 방문한 적 없으면서 섬 번호가 배정되지 않은 육지(값이 1)인 경우 큐에 추가
				if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && !visited[adjX][adjY] && map[adjX][adjY] == 1) {
					queue.offer(new int[] {adjX, adjY});
					map[adjX][adjY] = islandNo;
					visited[adjX][adjY] = true;
				}
			}
		}
	}
	
	static void findMinBridgeLength(int row, int col) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		clearVisitedArray();
		int[] currentCoord = null;
		int adjX = 0, adjY = 0;
		int distance = 0;
		
		queue.offer(new int[] {row, col});
		visited[row][col] = true;
		
		while(!queue.isEmpty()) {
			currentCoord = queue.poll();
			
			for(int i=0; i<4; i++) {
				adjX = currentCoord[0] + dx[i];
				adjY = currentCoord[1] + dy[i];
				
				//이번 메소드 호출 때 방문한 적이 없으면
				if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && !visited[adjX][adjY]) {
					visited[adjX][adjY] = true;
					distance = Math.abs(adjX - row) + Math.abs(adjY - col) - 1;
					
					//바다이면 큐에 추가 (육지를 찾을 때 까지 탐색) +이전 최소거리보다 작은 경우 큐에 추가
					if(map[adjX][adjY] == 0 && distance < answer) {
						queue.offer(new int[] {adjX, adjY});
					}
					//자신의 섬 번호와 다른 섬 번호를 만난 경우 경로 계산
					else if(map[adjX][adjY] != map[row][col]) {
						if(distance < answer) {
							answer = Math.min(answer, distance);
							
							break;
						}
						
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());		//지도의 크기 N
		map = new int[N][N];						//지도
		visited = new boolean[N][N];				//방문 배열
		int islandNo = 2;							//섬의 번호는 2부터 시작 (1: (입력 값) 육지)
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//섬 번호 지정 또는 다리의 길이 계산
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				//1. 입력받은 칸이 1 (육지)이면 섬의 번호 지정
				if(map[i][j] == 1) {
					markIslandNumber(i, j, islandNo++);
				}
				if(map[i][j] > 0) {
					findMinBridgeLength(i, j);
				}
			}
		}
		
		System.out.println(answer);
	}

}