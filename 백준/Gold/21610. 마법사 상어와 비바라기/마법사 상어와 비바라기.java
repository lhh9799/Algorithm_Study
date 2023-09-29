import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		//구름 이동 방향 (←, ↖, ↑, ↗, →, ↘, ↓, ↙)
		int[] moveX = {0, -1, -1, -1, 0, 1, 1, 1};
		int[] moveY = {-1, -1, 0, 1, 1, 1, 0, -1};
		
		//구름 이동 후 대각선 4방향 검사 (↖, ↗, ↘, ↙)
		int[] diagX = {-1, -1, 1, 1};
		int[] diagY = {-1, 1, 1, -1};
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());				//격자의 크기
		int M = Integer.parseInt(st.nextToken());				//명령의 수
		int[][] grid = new int[N][N];
		int d = 0;												//명령의 방향
		int s = 0;												//명령의 거리
		int answer = 0;											//격자 내 전체 물의 양
		ArrayList<int[]> prevSelected = new ArrayList<>();		//직전 비구름의 좌표
		ArrayList<int[]> currentSelected = new ArrayList<>();	//현재 비구름의 좌표
		
		//첫 구름은 정해진 위치 ((N, 1), (N, 2), (N-1, 1), (N-1, 2))에 생김
		currentSelected.add(new int[] {N-2, 0});
		currentSelected.add(new int[] {N-2, 1});
		currentSelected.add(new int[] {N-1, 0});
		currentSelected.add(new int[] {N-1, 1});
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<N; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			
			d = Integer.parseInt(st.nextToken()) - 1;			//배열의 인덱스는 0부터 시작
			s = Integer.parseInt(st.nextToken()) % N;
			
			//구름 이동 + 비 내림 (1)
			for(int[] pos : currentSelected) {
				//구름 이동
				pos[0] = (pos[0] + moveX[d] * s + N) % N;
				pos[1] = (pos[1] + moveY[d] * s + N) % N;
				
				//비 내림
				grid[pos[0]][pos[1]] += 1;
			}
			
			//3. 구름이 모두 사라진다.
			
			//대각선 4개 방향 체크 후 물이 있는 방향의 수 만큼 자신의 물 증가 
			for(int[] pos : currentSelected) {
				for(int j=0; j<4; j++) {
					int adjX = pos[0] + diagX[j];
					int adjY = pos[1] + diagY[j];
					
					if(adjX >= 0 && adjX < N && adjY >= 0 && adjY < N && grid[adjX][adjY] > 0) {
						grid[pos[0]][pos[1]]++;
					}
				}
			}
			
			for(int[] pos : currentSelected) {
				grid[pos[0]][pos[1]] *= -1;			//대각선 4개 방향 확인 후 물이 증가한 지역은 음수로 표기 (연속해서 다시 비구름이 생성되는 것 방지)
			}
			
			prevSelected = currentSelected;			//비구름 ArrayList 스왑
			currentSelected = new ArrayList<>();	//비구름의 좌표들 초기화
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(grid[j][k] >= 2) {
						grid[j][k] -= 2;			//구름이 생기면 물의 양이 2만큼 줄어든다
						currentSelected.add(new int[] {j, k});
					}
				}
			}
			
			for(int[] pos : prevSelected) {
				grid[pos[0]][pos[1]] *= -1;			//직전에 물이 증가한 지역은 다시 양수로 변환
			}
			
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				answer += grid[i][j];
			}
		}
		
		System.out.println(answer);
	}

}