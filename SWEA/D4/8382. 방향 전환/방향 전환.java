import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 8382. 방향 전환
 * 
 * 실행시간: 116ms (SWEA)
 * 메모리: 16,080 KB
 * 
 * 규칙을 찾아 해결했습니다.
 * 
 * #케이스 1 (최대거리: 홀수 (5), 최소거리: 짝수 (2) 차: 3)
 * (1, 2) -> (6, 4)
 * 9번 필요 (1, 2) -> (2, 2) -> (2, 3) -> (3, 3) -> (3, 4) -> (4, 4) -> (4, 3) -> (5, 3) -> (5, 4) -> (6, 4)
 * 
 * #케이스 2 (최대거리: 짝수 (4), 최소거리: 홀수 (1) 차: 3)
 * (1, 2) -> (5, 3)
 * 7번 필요 (1, 2) -> (2, 2) -> (2, 3) -> (3, 3) -> (3, 4) -> (4, 4) -> (4, 3) -> (5, 3)
 * 
 * #케이스 3 (최대거리: 홀수 (3), 최소거리: 홀수 (1) 차: 2)
 * (1, 2) -> (4, 3)
 * 6번 필요 (1, 2) -> (2, 2) -> (2, 3) -> (3, 3) -> (3, 4) -> (4, 4) -> (4, 3)
 * 
 * #케이스 4 (최대거리: 짝수 (4), 최소거리: 짝수 (2) 차: 2)
 * (1, 2) -> (5, 4)
 * 8번 필요 (1, 2) -> (2, 2) -> (2, 3) -> (3, 3) -> (3, 4) -> (4, 4) -> (4, 5) -> (5, 5) -> (5, 4)
 * 
 * 거리가 (홀수, 홀수), (짝수, 짝수)인 경우 최대거리 * 2번이,
 * (홀수, 짝수), (짝수, 홀수)와 같이 홀수와 짝수가 혼재되면 최대거리 * 2 - 1이 답이 됩니다.
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
		int[] coord = new int[4];							//(x_1, y_1), (x_2, y_2)
		int x_gap = 0, y_gap = 0, max_gap = 0, answer = 0;	//x좌표끼리의 거리, y좌표끼리의 거리
		
		int T = Integer.parseInt(br.readLine());			//테스트 케이스의 수 T
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<4; j++) {
				coord[j] = Integer.parseInt(st.nextToken());
			}
				
			x_gap = Math.abs(coord[2] - coord[0]);			//x좌표 차이의 절댓값
			y_gap = Math.abs(coord[3] - coord[1]);			//y좌표 차이의 절댓값
			max_gap = Math.max(x_gap, y_gap);				//최대거리 계산
			
			//(홀수, 홀수), (짝수, 짝수)인 경우
			if((x_gap + y_gap) % 2 == 0) {
				answer = max_gap * 2;						//답: 최대거리 * 2
			}
			//(홀수, 짝수), (짝수, 홀수)와 같이 홀수와 짝수가 섞인 경우
			else {
				answer = max_gap * 2 - 1;					//답: 최대거리 * 2 - 1
			}
			
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}