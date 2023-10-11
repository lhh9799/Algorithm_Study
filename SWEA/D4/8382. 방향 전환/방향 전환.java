import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		int[] coord = new int[4];
		int x_gap = 0, y_gap = 0, max_gap = 0, answer = 0;
		
		int T = Integer.parseInt(br.readLine());			//테스트 케이스의 수 T
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<4; j++) {
				coord[j] = Integer.parseInt(st.nextToken());
			}
				
			x_gap = Math.abs(coord[2] - coord[0]);			//x좌표 차이의 절댓값
			y_gap = Math.abs(coord[3] - coord[1]);			//y좌표 차이의 절댓값
			max_gap = Math.max(x_gap, y_gap);
			
			if((x_gap + y_gap) % 2 == 0) {
				answer = max_gap * 2;
			}
			else {
				answer = max_gap * 2 - 1;
			}
			
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}