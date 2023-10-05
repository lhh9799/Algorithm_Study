import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	static void fillMaxVal(int[][] array) {
		for(int i=0; i<array.length; i++) {
			Arrays.fill(array[i], Integer.MAX_VALUE);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ArrayDeque<int[]> queue = new ArrayDeque<>();
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int[] start = null;									//큐에서 빼낸 새로운 시작 좌표를 저장하는 배열
		//상, 하, 좌, 우
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		
		for(int i=1; ; i++) {
			int N = Integer.parseInt(br.readLine());		//동굴의 크기
			int[][] finalCave = new int[N][N];				//원본 동굴 지도 (값을 수정하지 않을 지도)
			int[][] accSumCave = new int[N][N];				//수정 동굴 지도 (값을 수정할 지도 -누적합)
			fillMaxVal(accSumCave);							//수정 동굴 지도 (값을 수정할 지도 -누적합) 초기화
			
			//문제 조건: N = 0인 입력이 주어지면 전체 입력이 종료된다. 
			if(N == 0) {
				System.out.println(sb.toString());			//정답 출력 후
				
				System.exit(0);								//프로그램 종료
			}
			
			//동굴 지도 입력받음
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				
				for(int k=0; k<N; k++) {
					finalCave[j][k] = Integer.parseInt(st.nextToken());
				}
			}
			
			//입구에서 (0, 0) 출구로 (N-1, N-1) 탐색
			queue.offer(new int[] {0, 0, finalCave[0][0]});		//입구 좌표를 큐에 삽입
			accSumCave[0][0] = finalCave[0][0];					//시작위치의 누적 비용 입력
			
			while(!queue.isEmpty()) {
				start = queue.poll();
				
				for(int j=0; j<4; j++) {
					int x = start[0] + dx[j];
					int y = start[1] + dy[j];
					
					if(x >= 0 && x < N && y >= 0 && y < N) {
						//누적 비용의 최솟값 갱신
						if(accSumCave[x][y] > start[2] + finalCave[x][y]) {
							accSumCave[x][y] = start[2] + finalCave[x][y];
							queue.offer(new int[] {x, y, accSumCave[x][y]});	//다음 탐색할 좌표 큐 추가
						}
					}
				}
			}
			
			sb.append("Problem " + i + ": " + accSumCave[N-1][N-1] + "\n");
		}
	}

}