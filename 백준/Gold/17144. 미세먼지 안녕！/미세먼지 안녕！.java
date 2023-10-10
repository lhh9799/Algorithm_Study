import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	//상, 하, 좌, 우 (미세먼지 확산 시뮬레이션 때 사용)
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static int R, C, T;							//R: 행, C: 열, T: 시간
	static int[][] array, spreadingDust;		//array: 입력받은 원본 배열, spreadingDust: 확산하는 먼지의 양을 저장하는 배열
	static int purifierTop = 0;					//공기청정기 위쪽 좌표 (행 값)
	static int purifierBottom = 0;				//공기청정기 아래쪽 좌표 (행 값)
	
	//공기청정기 위쪽(간척 방향): 상, 우, 하, 좌 (시계 방향)
	static int[] topPurifyDx = {-1, 0, 1, 0};
	static int[] topPurifyDy = {0, 1, 0, -1};
	//공기청정기 아래쪽(간척 방향): 우, 하, 좌, 상 (반시계 방향)
	static int[] bottomPurifyDx = {0, 1, 0, -1};
	static int[] bottomPurifyDy = {1, 0, -1, 0};
	
	//확산하는 먼지의 양을 저장하는 배열을 초기화하는 메소드
	static void clearArray() {
		for(int i=0; i<spreadingDust.length; i++) {
			Arrays.fill(spreadingDust[i], 0);
		}
	}
	
	/**
	 * 공기청정기의 좌표를 찾는 메소드
	 */
	static void findPurifier() {
		for(int i=0; i<R; i++) {
			if(array[i][0] == -1) {
				purifierTop = i;
				purifierBottom = i+1;
				
				return;
			}
		}
	}
	
	/**
	 * 미세먼지 확산 시뮬레이션 메소드 (1. 미세먼지가 확산된다. 확산은 미세먼지가 있는 모든 칸에서 동시에 일어난다.)
	 */
	static void spreadDust() {
		clearArray();		//확산하는 먼지의 양을 저장하는 배열 초기화
		
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(array[i][j] > 0) {			//해당 좌표에 먼지가 있으면
					int count = 0;				//확산하는 구역의 개수 변수 0으로 초기화
					
					for(int k=0; k<4; k++) {
						int x = i + dx[k];
						int y = j + dy[k];
						
						//인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향으로는 확산이 일어나지 않는다.
						if(x >= 0 && x < R && y >= 0 && y < C && array[x][y] != -1) {
							spreadingDust[x][y] += array[i][j] / 5;		//인접한 네 방향에 Ar,c/5의 먼지 증가
							count++;									//먼지를 확산한 영역의 개수 증가
						}
					}
					
					spreadingDust[i][j] -= (array[i][j] / 5) * count;	//먼지를 확산한 양만큼 자신의 미세먼지의 양 감소
				}
			}
		}
		
		//퍼진 미세먼지 반영 (받은 미세먼지 증가, 보낸 미세먼지 감소)
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				array[i][j] += spreadingDust[i][j];
			}
		}
	}
	
	/**
	 * 공기청정기 시뮬레이션 메소드 (2. 공기청정기가 작동한다.)
	 * 
	 * 배열의 범위를 벗어나거나 공기 청정기의 좌표에 도달한 경우 방향을 전환한다.
	 */
	static void purify() {
		/** 공기청정기 위 시작 */
		//왼쪽 옆 (아래 당기기)
		for(int i=purifierTop-1; i>0; i--) {
			array[i][0] = array[i-1][0];
		}
		
		//위 (왼쪽 당기기)
		for(int i=1; i<C; i++) {
			array[0][i-1] = array[0][i];
		}
		
		//오른쪽 옆 (위 당기기)
		for(int i=1; i<=purifierTop; i++) {
			array[i-1][C-1] = array[i][C-1];
		}
				
		//아래 (오른쪽 당기기)
		for(int i=C-1; i>1; i--) {				//공기청정기는 범위에서 제외
			array[purifierTop][i] = array[purifierTop][i-1];
		}
		
		array[purifierTop][1] = 0;				//공기청정기로 들어간 미세먼지는 모두 정화된다.
		/** 공기청정기 위 끝 */
		
		/** 공기청정기 아래 시작 */
		//왼쪽 옆 (위 당기기)
		for(int i=purifierBottom+2; i<R; i++) {	//공기청정기는 범위에서 제외
			array[i-1][0] = array[i][0];
		}
		
		//아래 (왼쪽 당기기)
		for(int i=1; i<C; i++) {
			array[R-1][i-1] = array[R-1][i];
		}
		
		//오른쪽 옆 (아래 당기기)
		for(int i=R-1; i>purifierBottom; i--) {
			array[i][C-1] = array[i-1][C-1];
		}
				
		//위 (오른쪽 당기기)
		for(int i=C-1; i>1; i--) {
			array[purifierBottom][i] = array[purifierBottom][i-1];
		}
		
		array[purifierBottom][1] = 0;			//공기청정기로 들어간 미세먼지는 모두 정화된다.
		/** 공기청정기 아래 끝 */
	}
	
	/**
	 * 미세먼지의 양을 계산하는 메소드
	 * @return: 미세먼지의 양
	 */
	static int countDust() {
		int count = 0;
		
		for(int i=0; i<R; i++) {
			count += Arrays.stream(array[i]).sum();
		}
		
		return count + 2;	//공기청정기의 좌표는 -1 (총 2개 좌표 차지)로 기록되어 있으므로 2 증가시킨 값 리턴
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());		//행의 수
		C = Integer.parseInt(st.nextToken());		//열의 수
		T = Integer.parseInt(st.nextToken());		//시간 (T초가 지난 후 구사과의 방에 남아있는 미세먼지의 양을 구해보자.)
		array = new int[R][C];						//원본 방의 정보 배열
		spreadingDust = new int[R][C];				//확산으로 증가한 미세먼지 양
		
		//방의 정보 입력받음 (구역 별 미세먼지의 양)
		for(int i=0; i<R; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<C; j++) {
				array[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		findPurifier();								//공기청정기의 좌표 확인
		
		for(int i=0; i<T; i++) {
			spreadDust();							//미세먼지 확산 시뮬레이션
			purify();								//공기청정기가 작동 시뮬레이션
		}
		
		System.out.println(countDust());			//T초 후 방에 남아있는 미세먼지의 양 출력
	}

}