import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 5656. [모의 SW 역량테스트] 벽돌 깨기
 * 
 * 실행시간: 1,051ms (SWEA)
 * 메모리: 88,284 KB
 * 
 * 중복순열 + BFS 문제입니다.
 * 
 * H×W 크기의 벽돌 배열과 구슬을 쏠 수 있는 횟수 N이 주어집니다. 구슬이 벽돌에 맞으면 (폭발 범위 - 1)의 벽돌들이 연쇄적으로 동시에 (4방향) 깨집니다.
 * 구슬을 N번 쐈을 때 남는 최소한의 벽돌의 개수를 구해야 합니다.
 * N개의 중복순열을 뽑은 뒤 시뮬레이션 (연쇄 폭발 + Compaction)한 뒤 정답변수를 갱신합니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static int N, W, H;
	static int[][] originBricks = null;						//입력받은 벽돌 배열 (폭발의 범위 정보 저장)
	static int[][] copyBricks = null;						//시뮬레이션을 위한 벽돌 배열
	static int answer = Integer.MAX_VALUE;					//정답 (최대한 많은 벽돌을 깨뜨렸을 때 남는 벽돌의 개수)
	static ArrayDeque<int[]> queue = new ArrayDeque<>();	//벽돌이 깨졌을 때 연쇄반응이 일어날 벽돌의 위치와 폭발 범위를 저장할 ArrayDeque
	static int[] numbers = null;							//중복 순열 저장 배열
	//상, 하, 좌, 우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	//배열을 복사하는 메소드
	static void copyArray() {
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				copyBricks[i][j] = originBricks[i][j];
			}
		}
	}
	
	//④ 빈 공간이 있을 경우 벽돌은 밑으로 떨어지게 된다.
	static void compaction() {
		for(int i=0; i<W; i++) {							//열
			int bottom = H-1;								//값을 넣을 위치 (0이 아닌 위치)
			
			for(int top=H-1; top>=0; top--) {				//행
				if(copyBricks[top][i] >= 1) {				//top의 값이 1이상이면 (Compaction 할 대상)
					//bottom과 top의 위치가 다르면
					if(bottom != top) {
						copyBricks[bottom][i] = copyBricks[top][i];	//bottom에 top의 값 대입
						copyBricks[top][i] = 0;				//top의 값 0으로 초기화 (Compaction 되었으므로)
						
						//bottom위치 갱신
						while(bottom >= 1 && copyBricks[bottom--][i] == 0);
					}
					//bottom == top -> 바깥 if문에서 top이 1이상인 경우이므로 -> bottom이 1이상임 -> bottom이 0이 아닌 값을 만났으므로 위로 한 칸 올림
					else {
						bottom--;
					}
				}
			}
		}
	}
	
	/**
	 * 구슬을 떨어뜨리는 것을 시뮬레이션 하는 메소드 (연쇄반응 처리)
	 * @param initialCol: 구슬을 떨어뜨리는 열의 번호
	 */
	static void simulation(int initialCol) {
		int initialX = 0, initialY = initialCol;
		
		//구슬을 떨어뜨릴 벽돌의 행 위치 계산
		for(int i=0; i<H; i++) {
			if(copyBricks[i][initialCol] != 0) {
				initialX = i;
				
				break;
			}
		}
		
		queue.clear();																	//큐 초기화
		queue.offer(new int[] {initialX, initialY, copyBricks[initialX][initialY]});	//시작 위치 큐에 삽입
		copyBricks[initialX][initialY] = 0;												//첫 시작 위치 파괴 처리
		int[] start = null;																//처음 깨지는 벽돌의 위치
		//(startX, StartY): 처음 깨지는 벽돌의 위치, (currX, currY): 연쇄적으로 깨지는 벽돌의 위치
		int startX = 0, startY = 0, currX = 0, currY = 0;
		int destroyRange = 0;															//파괴 범위 (방문 처리를 위해 벽돌 배열의 파괴 범위 값을 0으로 수정하는데, 이후 처음 깨지는 벽돌의 폭발범위를 알기 위해 큐에 좌표와 함께 저장)
		
		while(!queue.isEmpty()) {
			//처음 깨지는 벽돌의 위치
			start = queue.poll();
			startX = start[0];
			startY = start[1];
			destroyRange = start[2] - 1;												//파괴 범위 (문제 조건: 구술이 명중한 벽돌은 상하좌우로 ( 벽돌에 적힌 숫자 - 1 ) 칸 만큼 같이 제거된다.)
			copyBricks[startX][startY] = 0;												//벽돌 파괴 처리
			
			for(int i=0; i<4; i++) {
				//파괴범위만큼 4방 탐색
				for(int j=1; j<=destroyRange; j++) {
					currX = startX + dx[i] * j;
					currY = startY + dy[i] * j;
					
					if(currX >= 0 && currX < H && currY >= 0 && currY < W) {
						//파괴 범위가 2 이상이면 연쇄반응을 일으키므로 큐에 추가
						if(copyBricks[currX][currY] >= 2) {
							queue.add(new int[] {currX, currY, copyBricks[currX][currY]});
						}
						
						copyBricks[currX][currY] = 0;									//벽돌 파괴 처리 (파괴 범위가 1인 벽돌들 포함)
					}
					
					//배열의 범위를 벗어나면
					else {
						break;															//다른 방향 탐색
					}
				}
			}
		}
		
		compaction();		//빈 공간 정리
	}
	
	/**
	 * 남은 벽돌의 수를 계산하는 메소드
	 * @return: 남은 벽돌의 수
	 */
	static int countLeftBricks() {
		int count = 0;
		
		for(int i=0; i<H; i++) {
			for(int j=0; j<W; j++) {
				if(copyBricks[i][j] >= 1) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	static void perm(int count) {
		if(count == N) {
			copyArray();				//시뮬레이션을 위한 배열 초기화
			
			//N번 시뮬레이션
			for(int i=0; i<N; i++) {
				simulation(numbers[i]);
			}
			
			//정답 변수 갱신
			answer = Math.min(answer, countLeftBricks());
			
			return;						//더 이상 재귀가 호출되지 않도록 리턴
		}
		
		//count == N이 될 때까지 중복을 허용하여 새로운 수 뽑기 (재귀)
		for(int i=0; i<W; i++) {
			numbers[count] = i;
			perm(count+1);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());		//테스트 케이스의 개수 T
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			answer = Integer.MAX_VALUE;					//정답변수 최댓값으로 초기화
			
			N = Integer.parseInt(st.nextToken());		//벽돌의 개수
			W = Integer.parseInt(st.nextToken());		//너비
			H = Integer.parseInt(st.nextToken());		//높이
			
			originBricks = new int[H][W];				//벽돌의 정보 (파괴 범위)를 저장하는 배열 할당
			copyBricks = new int[H][W];					//시뮬레이션을 위한 벽돌 정보 배열 사본 할당
			numbers = new int[N];						//중복 순열 (구슬을 떨어뜨릴 열의 번호)
			
			//벽돌의 정보 (파괴 범위) 입력받음
			for(int j=0; j<H; j++) {
				st = new StringTokenizer(br.readLine());
				
				for(int k=0; k<W; k++) {
					originBricks[j][k] =  Integer.parseInt(st.nextToken());
				}
			}
			
			//N번 시뮬레이션
			copyArray();
			perm(0);
			
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}