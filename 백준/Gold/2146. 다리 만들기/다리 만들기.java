import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 2146번 다리 만들기
 * https://www.acmicpc.net/problem/2146
 * 
 * 실행시간: 204ms (백준)
 * 메모리: 16,312 KB
 * 
 * BFS 문제입니다.
 * 
 * 한 섬과 다른 섬을 잇는 최단 다리의 길이를 구하는 문제입니다.
 * 
 * 1. 문제의 입력을 모두 수행합니다.
 * 2. 지도를 순회하며
 * 2-1. 섬의 번호가 부여되지 않은 육지인 경우 (배열의 값이 1) -> 한 섬에서 자기 자신으로 돌아오는 다리를 설치하는 경우를 막기 위해 각 섬마다 BFS로 고유 번호를 부여합니다.
 * 2-2. 지도를 순회하며 섬의 번호가 부여된 육지인 경우 -> BFS로 다른 육지를 찾을 때까지 탐색합니다. 최솟값을 찾은 경우 정답변수를 갱신합니다.
 * 
 * -메모리를 할당하는 시간을 절약하기 위해 선언한 visited 배열을 재사용했습니다.
 * -메모리 및 시간을 절약하기 위해 거리를 계산할 때 큐에 거리를 넣지 않고 (맨해튼 거리 - 1)값을 사용했습니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Main {
	
	static int N;									//지도의 크기 N
	static int[][] map;								//지도
	static boolean[][] visited;						//방문 배열
	static int answer = Integer.MAX_VALUE;			//가장 짧은 다리의 길이 (최댓값으로 초기화)
	//상, 하, 좌, 우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	//방문배열을 초기화하는 메소드
	static void clearVisitedArray() {
		for(int i=0; i<visited.length; i++) {
			Arrays.fill(visited[i], false);
		}
	}
	
	/**
	 * 섬의 번호를 부여하는 메소드 (번호는 2부터 시작)
	 * @param row: 행 번호
	 * @param col: 열 번호
	 * @param islandNo: 섬의 번호 (BFS로 섬의 구역 확장)
	 */
	static void markIslandNumber(int row, int col, int islandNo) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();	//첫 좌표와 인접한 영역(육지)을 탐색하기 위한 큐
		clearVisitedArray();							//방문배열 초기화
		int[] currentCoord = null;						//큐에서 꺼낸 좌표 정보를 저장할 임시 배열
		int adjX = 0, adjY = 0;							//4방 탐색 좌표를 저장하는 변수 (adjX: 행, adjY: 열)
		
		queue.offer(new int[] {row, col});				//시작 위치 큐에 삽입
		map[row][col] = islandNo;						//시작 위치에 섬의 번호 부여
		visited[row][col] = true;						//방문한 것으로 표시
		
		while(!queue.isEmpty()) {						//큐가 빌 때까지 (더 이상 인접한 영역(육지)이 없을 때까지) 반복
			currentCoord = queue.poll();				//새로운 출발 위치 설정
			
			//4방 탐색
			for(int i=0; i<4; i++) {
				adjX = currentCoord[0] + dx[i];
				adjY = currentCoord[1] + dy[i];
				
				//이번 메소드 호출 때 방문한 적이 없으면서 섬 번호가 배정되지 않은 육지(값이 1)인 경우
				if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && !visited[adjX][adjY] && map[adjX][adjY] == 1) {
					queue.offer(new int[] {adjX, adjY});	//4방탐색 결과 인접한 영역 큐에 추가
					map[adjX][adjY] = islandNo;				//출발지의 섬의 번호와 같은 번호 부여
					visited[adjX][adjY] = true;				//방문한 것으로 표시
				}
			}
		}
	}
	
	/**
	 * 한 섬과 다른 섬을 잇는 최단 다리의 길이를 구하는 메소드
	 * @param row: 행 번호
	 * @param col: 열 번호
	 */
	static void findMinBridgeLength(int row, int col) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();	//한 섬 (출발지)으로부터 뻗어나가 다른 섬 (자신과 다른 번호를 부여받은 섬)을 찾기 위해 다음 방문할 좌표들을 저장하는 큐
		clearVisitedArray();							//방문배열 초기화
		int[] currentCoord = null;						//큐에서 꺼낸 좌표 정보를 저장할 임시 배열
		int adjX = 0, adjY = 0;							//4방 탐색 좌표를 저장하는 변수 (adjX: 행, adjY: 열)
		int distance = 0;								//한 섬에서 다른 섬까지 연결한 다리의 길이를 저장하는 변수 (문제의 해가 아닐 수 있음)
		
		queue.offer(new int[] {row, col});				//시작 위치 큐에 삽입
		visited[row][col] = true;						//방문한 것으로 표시
		
		while(!queue.isEmpty()) {						//큐가 빌 때까지 (더 이상 인접한 영역(육지)이 없을 때까지) 반복
			currentCoord = queue.poll();				//새로운 출발 위치 설정
			
			//4방 탐색
			for(int i=0; i<4; i++) {
				adjX = currentCoord[0] + dx[i];
				adjY = currentCoord[1] + dy[i];
				
				//이번 메소드 호출 때 방문한 적이 없으면
				if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && !visited[adjX][adjY]) {
					visited[adjX][adjY] = true;			//방문한 것으로 표시
					distance = Math.abs(adjX - row) + Math.abs(adjY - col) - 1;	//출발지로부터의 거리 계산
					
					//이전 최소거리보다 작으면서 바다이면 큐에 추가 (유망함) #이전에 구한 최소거리 이상이면 육지를 찾아도 그 거리는 최소거리를 초과함 -> 가지치기
					if(map[adjX][adjY] == 0 && distance < answer) {
						queue.offer(new int[] {adjX, adjY});	//큐에 추가
					}
					//자신의 섬 번호와 다른 섬 번호를 만난 경우 거리 계산
					else if(map[adjX][adjY] != map[row][col]) {
						if(distance < answer) {					//이전에 구한 문제의 해보다 거리가 작은 경우
							answer = distance;					//정답 변수 갱신
							
							break;								//(BFS) 큐의 앞쪽에 있는 원소가 답인 경우 그 이후 원소는 볼 필요 없음
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
		
		//지도 입력받음
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