import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int[][] solution(final int[][] coord, int n) {
		int[][] coastMap = new int[n+2][n+2];
		final int maxBeerDistance = 50 * 20;
		
		//모든 장소 (락 페스티벌도 포함)에서 락 페스티벌까지의 거리 계산
		for(int i=0; i<n+2; i++) {
			for(int j=i+1; j<n+2; j++) {
				coastMap[i][j] = coastMap[j][i] = Math.abs(coord[i][0] - coord[j][0]) + Math.abs(coord[i][1] - coord[j][1]);
			}
		}
		
		for(int i=1; i<n+1; i++) {					//경유지는 0번 (집)과 n+1번 (락 페스티벌)을 제외
			for(int j=0; j<n+1; j++) {				//출발지는 0번 (집)과 1~n번 (편의점)을 포함하고 n+1번 (락 페스티벌)은 제외
				if(i == j) continue;				//경유지와 출발지가 같으면 스킵
				
				for(int k=1; k<n+2; k++) {			//도착지는 0번 (집)을 제외하고 1~n (편의점)과 n+1(락 페스티벌)을 포함함
					if(k == i || k == j) continue;	//경유지와 도착지가 같거나 출발지와 도착지가 같으면 스킵
					
					/**
					 * 아이디어: j(출발지)에서 k(도착지)까지의 거리(직통거리)가 1000이하이거나 | j(출발지)에서 i(경유지)까지의 거리가 1000 이하이고, i(경유지)에서 k(도착지)까지의 거리가 1000 이하이면 i(경유지)에서 k(도착지)까지의 거리(경유거리)로 마킹
					 */
					//직통 거리
					int directDistance = coastMap[j][k];
					
					//경유 거리 (최댓값으로 초기화)
					int stopoverDistance = Integer.MAX_VALUE;
					
					//j(출발지)에서 i(경유지)까지의 거리가 1000 이하이고, i(경유지)에서 k(도착지)까지의 거리가 1000 이하이면 i(경유지)에서 k(도착지)까지의 거리가 경유거리가 됨
					if(Math.max(coastMap[j][i], coastMap[i][k]) <= maxBeerDistance) {
						stopoverDistance = coastMap[i][k];
					}
					
					coastMap[j][k] = Math.min(directDistance, stopoverDistance);
				}
			}
		}
		
		return coastMap;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int t = Integer.parseInt(br.readLine());		//테스트 케이스의 개수 t (t ≤ 50)
		
		for(int i=0; i<t; i++) {
			int n = Integer.parseInt(br.readLine());	//맥주를 파는 편의점의 개수 n (0 ≤ n ≤ 100)
			int[][] coord = new int[n+2][2];			//0번 인덱스: 상근이네 집의 좌표, 1~n번 인덱스: 편의점의 좌표, n+1번 인덱스: 펜타포트 락 페스티벌의 좌표
			
			//상근이네 집의 좌표
			st = new StringTokenizer(br.readLine());
			coord[0][0] = Integer.parseInt(st.nextToken());
			coord[0][1] = Integer.parseInt(st.nextToken());
			
			//편의점의 좌표
			for(int j=1; j<n+1; j++) {
				st = new StringTokenizer(br.readLine());
				coord[j][0] = Integer.parseInt(st.nextToken());
				coord[j][1] = Integer.parseInt(st.nextToken());
			}
			
			//펜타포트 락 페스티벌의 좌표
			st = new StringTokenizer(br.readLine());
			coord[n+1][0] = Integer.parseInt(st.nextToken());
			coord[n+1][1] = Integer.parseInt(st.nextToken());
			
			//상근이네 집에서 모든 장소에 도착하는데 필요한 비용 계산
			int[][] coastMap = solution(coord, n);
			
			//상근이네 집에서 펜타포트 락 페스티벌에 도달할 수 있는 경우 happy, 아니면 sad 출력
			System.out.println(coastMap[0][n+1] <= 50 * 20 ? "happy" : "sad");
		}
	}

}