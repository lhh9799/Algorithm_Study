import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

/**
 * <pre>
 * SWEA 1868. 파핑파핑 지뢰찾기
 * 
 * 실행시간: 204ms (SWEA)
 * 메모리: 36,736 KB
 * 
 * 최소 몇 번 클릭해야 지뢰가 있는 칸을 제외한 모든 칸의 숫자들을 표시할 수 있는지 구하는 문제입니다.
 * 문제 조건: 근처의 8방향에 지뢰가 없다면 8방향의 칸도 자동으로 숫자를 표시해 준다. -> "근처 8방향에 지뢰가 없다면 계속해서 확장한다."로 바꿔 말할 수 있습니다.
 * 문제를 푸는데 인접한 좌표에 있는 지뢰의 개수는 중요하지 않습니다. 지뢰가 있으면 해당 좌표까지만 숫자를 마킹하고 큐에 더 이상 추가하지 않습니다.
 * 
 * [풀이]
 * (0, 0) 좌표부터 (N-1, N-1)좌표까지 8방향에 지뢰가 있는지 확인합니다.
 * 8방향에 지뢰가 없다면 자기 자신 (출발 좌표)을 클릭한 것으로 체크하고 자신의 좌표를 큐에 넣습니다. 이후 큐가 빌 때까지 다음을 반복합니다.
 * 큐에서 빼낸 좌표의 8방향 좌표 각각의 8방향에 지뢰가 있는지 확인합니다. 지뢰가 없다면 연쇄적으로 클릭된 것으로 간주하고(방문 표시) 큐에 넣습니다.
 * 
 * 연쇄적으로 숫자를 표시하고 남은 숫자들은 연쇄적으로 숫자를 표시할 수 없는 좌표들이기 때문에 모두 1번씩 클릭해야합니다.
 * 모든 좌표에 대해 지뢰가 아니면서 아직 클릭되지 않은 좌표에 대해 클릭횟수를 계산합니다.
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static int N;				//표의 크기 N
	static char[][] array;		//지뢰찾기를 하는 표
	//[0,1]부터 시계방향 회전
	static int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
	static int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

	/**
	 * 시작위치의 8방에 '*'이 있는지 여부를 리턴하는 메소드
	 * @param srcRow: 시작위치 행
	 * @param srcCol: 시작위치 열
	 * @return: true: 8방에 '*'이 있음, false: 8방에 '*'이 없음
	 */
	public static boolean surroundCheck(int srcRow, int srcCol) {
		int row = 0, col = 0;	//다음 탐색할 위치를 저장하는 변수
		
		//8방향 탐색
		for(int i=0; i<dx.length; i++) {
			row = srcRow + dx[i];
			col = srcCol + dy[i];
			
			if(row >= 0 && row < N && col >= 0 && col < N && array[row][col] == '*') {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 클릭한 위치로부터 지뢰가 없는 위치까지 클릭한 것으로 체크하는 메소드
	 * @param srcRow: 시작위치 행 (클릭 위치)
	 * @param srcCol: 시작위치 열 (클릭 위치)
	 */
	public static void chainReaction(int srcRow, int srcCol) {
		ArrayDeque<int[]> queue = new ArrayDeque<>();	//다음 방문할 위치를 저장하는 큐
		queue.offer(new int[] {srcRow, srcCol});		//시작위치 큐에 추가
		array[srcRow][srcCol] = 'c';					//자기자신도 클릭한 위치로 갱신
		int[] startPos = null;							//큐에서 빼낼 위치를 저장하는 변수
		int row = 0, col = 0;							//다음 탐색할 위치를 저장하는 변수
		
		while(!queue.isEmpty()) {
			startPos = queue.poll();					//새로운 시작위치 빼냄
			
			//8방향 탐색
			for(int i=0; i<dx.length; i++) {
				row = startPos[0] + dx[i];
				col = startPos[1] + dy[i];
				
				//지뢰가 없으면서 방문한 적 없는 좌표인 경우
				if(row >= 0 && row < N && col >= 0 && col < N && array[row][col] == '.') {
					array[row][col] = 'c';					//방문한 것으로 표시
					
					if(surroundCheck(row, col)) {			//다음 위치의 8방에 지뢰가 없으면
						queue.offer(new int[] {row, col});	//다음 위치를 큐에 추가
					}
				}
			}
		}
	}

	public static void main(String[] args) throws IOException{
		//버퍼 사이즈가 Scanner (1024 Char)에 비해 8배 큰 BufferedReader (8192 Char) 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();		//가변 문자열 클래스 할당
		int T = Integer.parseInt(br.readLine());	//테스트 케이스의 수 T 입력받음
		
		for(int i=1; i<=T; i++) {
			N = Integer.parseInt(br.readLine());	//표의 크기 N 입력받음
			array = new char[N][N];					//지뢰찾기를 하는 표 할당
			int answer = 0;							//지뢰가 있는 칸을 제외한 모든 칸의 숫자들을 표시하기 위해 필요한 최소 클릭 횟수
			
			//지뢰찾기를 하는 표 입력받음
			for(int j=0; j<N; j++) {
				array[j] = br.readLine().toCharArray();
			}
			
			//연쇄반응을 일으킬 수 있는 지뢰가 없는 칸의 수 계산
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					//현재 위치에 지뢰가 없으면서 탐색한 적이 없고 8방에 지뢰가 없는 경우
					if(array[j][k] == '.' && surroundCheck(j, k)) {
						chainReaction(j, k);		//연쇄반응 시작 (클릭 처리)
						answer++;					//클릭횟수 증가
					}
				}
			}
			
			//연쇄반응을 일으킬 수 없었던 지뢰가 없는 칸의 수 계산
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(array[j][k] == '.') {
						answer++;					//클릭횟수 증가
					}
				}
			}
			
			//정답을 StringBuilder에 저장 (속도 향상 위함)
			sb.append("#" + i + " " + answer + "\n");
		}
		//정답 출력
		System.out.println(sb.toString());
	}

}
