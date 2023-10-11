import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 8458. 원점으로 집합
 * 
 * 실행시간: 215ms (SWEA)
 * 메모리: 32,848 KB
 * 
 * N개의 2차원 좌표가 주어졌을 때 최소 몇 번의 움직임으로 모든 점을 원점에 모을 수 있는지 구하는 문제입니다. (i번째 움직임은 상하좌우 합쳐 반드시 i만큼의 거리 이동)
 * 
 * 1. 모든 점을 원점으로 이동시킬 수 없는 경우 (-1 출력 조건)
 * 두 개 이상의 점들의 좌표가 주어졌을 때, 그 거리의 차이가 짝수이어야 원점으로 돌아올 수 있습니다.
 * 	1-1. 둘의 거리 차이가 짝수이면 한 점은 제자리 이동(N/2만큼 양의 방향으로 이동 후 N/2만큼 다시 음의 방향으로 이동), 다른 한 점은 원점으로 이동할 수 있지만
 * 	1-2. 둘의 거리 차이가 홀수이면 한 점은 원점으로 이동할 수 있지만 다른 한 점은 되돌아 올 수 없고 계속해서 원점과 거리를 1만큼 유지하게 됩니다. (시작위치에서의 차이)
 * 
 * 이를 달리 말하면
 * 원점으로 되돌아올 수 있으려면 (한 점의 x좌표 절댓값과 y좌표 절댓값의 합)이 N개의 점들 모두 짝수이거나 모두 홀수이어야 합니다.
 * 
 * 따라서 N개의 점들의 좌표를 입력을 받으면서 한 점의 x좌표 절댓값과 y좌표 절댓값의 합을 구해 짝수인지 홀수인지 개수를 파악하고,
 * 짝수와 홀수의 개수 둘 다 0이 아니라면 모든 점을 원점으로 이동시킬 수 없으므로 -1을 출력합니다.
 * 
 * 
 * 2. i번째에 모든 점이 같은 방향으로 이동하지 않아도 됩니다. 가장 멀리있는 점이 원점에 도착할 때가 답이 됩니다.
 * 	2-1. i번째에 단번에 가장 먼 거리에 도달할 수 있는 경우 i를 출력합니다. (예: 0, 3) -> i가 0일 때 (0,3) / i가 1일 때 (0, 2) / i가 2일 때 (0, 0)
 * 	2-2. 또는 i번째에 가장 먼 거리를 초과해서 이동 후 되돌아왔을 때의 i를 출력합니다. (거리가 가까운 점들은 모두 원점으로 이동 후 제자리 걸음) (초과한 이동거리가 짝수일 때 가능 (다른 점들이 제자리걸음 해야 함))
 * 
 * 	2-1의 예) 2개의 점 (-5, 0), (2, 1) #(2, 1)에서 시작하는 점은 먼저 (0, 0)에 도착 후 제자리 걸음 (제자리걸음 하기 위해서는 i가 짝수가 되어야 함)
 * 		i=0 (-5, 0), (2, 1)
 * 		i=1 (-4, 0), (2, 0)
 * 		i=2 (-2, 0), (0, 0)
 * 		i=3 (1, 0), (1, 0)				#(0, 0) -> (1, 0) -> (0, 0) -> (1, 0)
 * 		i=4 (5 (≡ 1), 0), (-1, 0)		#초과한 이동거리: 5 (홀수)
 * 		i=5 (0, 0), (0, 0)				#초과한 이동거리는 i가 5이므로 0부터 5까지의 합 15이고, 시작할 때 원점으로부터 떨어진 거리는 5이므로 초과한 거리가 10 (짝수)이 됨
 * 
 *  2-2의 예) 1개의 점 (6, 0)	#i가 3으로 홀수이지만 단번에 이동할 수 있는 경우 i 출력
 *  	i=0 (6, 0)
 *  	i=1 (5, 0)
 *  	i=2 (3, 0)
 *  	i=3 (0, 0)
 * 		
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());			//테스트 케이스의 수 T
		
		for(int i=1; i<=T; i++) {
			int oddCount = 0, evenCount = 0;				//원점으로부터 떨어진 거리가 홀수, 짝수인 좌표들의 개수
			int longestDistance = Integer.MIN_VALUE;		//원점으로부터 떨어진 거리의 최댓값 저장 변수
			
			int N = Integer.parseInt(br.readLine());		//격자점의 개수
			int[] x = new int[N];							//xi
			int[] y = new int[N];							//yi
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				
				x[j] = Integer.parseInt(st.nextToken());	//xi 입력
				y[j] = Integer.parseInt(st.nextToken());	//yi 입력
				
				//원점으로부터 떨어진 거리가 짝수이면
				if((Math.abs(x[j]) + Math.abs(y[j])) % 2 == 0) {
					evenCount++;
				} else {
					oddCount++;
				}
				
				
				//가장 긴 거리 갱신
				longestDistance = Math.max(longestDistance, Math.abs(x[j]) + Math.abs(y[j]));
			}
			
			/**
			 * 원점으로부터의 거리가 짝수와 홀수가 혼재되어있으면 N개의 점이 원점에 모일 수 없음 (원점으로부터의 거리 = |x| + |y|)
			 * 
			 * 둘 다 0이 아니면 continue (다음 테스트 케이스 연산)
			 */
			if(evenCount != 0 && oddCount != 0) {
				sb.append("#" + i + " -1\n");
				
				continue;
			}
			
			int accSum = 0;									//코드의 j(=문제에서의 i)까지의 누적 이동 거리
			for(int j=0; ; j++) {
				accSum += j;								//누적 이동 거리 갱신
				
				//i번째에 가장 먼 거리를 초과해서 이동 후 되돌아올 수 있는 경우 (초과한 이동거리가 짝수이어야 함)
				if(accSum > longestDistance && (accSum - longestDistance) % 2 == 0) {
					sb.append("#" + i + " " + j + "\n");
					
					break;
				}
				
				//i번째에 단번에 가장 먼 거리에 도달할 수 있는 경우
				else if(accSum == longestDistance) {
					sb.append("#" + i + " " + j + "\n");
					
					break;
				}
			}
		}
		
		System.out.println(sb.toString());
	}

}