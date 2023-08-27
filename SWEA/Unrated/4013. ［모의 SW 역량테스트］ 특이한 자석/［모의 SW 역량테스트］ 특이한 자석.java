import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	
	static boolean[][] blade;
	static int[] redIndex = {0, 0, 0, 0};
	static int[] nextRedIndex = new int[4];	//자석 별 빨간색 화살표 위치를 저장하는 배열
	
	//조건에 맞는 왼쪽 톱니바퀴들 회전
	public static void leftRotate(int startBladeNo, int rotationDirection) {
		for(int i=startBladeNo; i>0; i--) {
			if(blade[i][(redIndex[i] + 6) % 8] != blade[i-1][(redIndex[i-1] + 2) % 8]) {
				rotationDirection *= -1;
				nextRedIndex[i-1] = rotationDirection == 1 ? redIndex[i-1] - 1 : redIndex[i-1] + 1;
			}
			else {
				break;
			}
		}
	}
	
	//조건에 맞는 오른쪽 톱니바퀴들 회전
	public static void rightRotate(int startBladeNo, int rotationDirection) {
		for(int i=startBladeNo; i<3; i++) {
			if(blade[i][(redIndex[i] + 2) % 8] != blade[i+1][(redIndex[i+1] + 6) % 8]) {
				rotationDirection *= -1;
				nextRedIndex[i+1] = rotationDirection == 1 ? redIndex[i+1] - 1 : redIndex[i+1] + 1;
			}
			else {
				break;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());
		blade = new boolean[4][8];						//자석의 날 정보 (0 또는 1) #true: S극, false: N극
		
		for(int i=1; i<=T; i++) {
			int K = Integer.parseInt(br.readLine());//자석을 회전시키는 횟수 K
			Arrays.fill(redIndex, 0);
			System.arraycopy(redIndex, 0, nextRedIndex, 0, redIndex.length);
			
			//1번부터 4번 자석까지의 각각 8개 날의 자성정보
			for(int j=0; j<4; j++) {
				st = new StringTokenizer(br.readLine());
				
				for(int k=0; k<8; k++) {
					blade[j][k] = st.nextToken().charAt(0) == '1' ? true : false;
				}
			}
			
			//K개의 줄에는 자석을 1칸씩 회전시키는 회전 정보
			for(int j=0; j<K; j++) {
				st = new StringTokenizer(br.readLine());
				
				int magnetNo = Integer.parseInt(st.nextToken());
				int rotationDirection = Integer.parseInt(st.nextToken());
				
				leftRotate(magnetNo-1, rotationDirection);
				rightRotate(magnetNo-1, rotationDirection);
				//자기자신 회전
				nextRedIndex[magnetNo-1] = rotationDirection == 1 ? redIndex[magnetNo-1] - 1 : redIndex[magnetNo-1] + 1;
				
				for(int k=0; k<4; k++) {
					redIndex[k] = (nextRedIndex[k] + 8) % 8;
				}
			}
			
			int answer = 0;
			for(int j=0; j<blade.length; j++) {
				if(blade[j][redIndex[j]]) {
					answer += Math.pow(2, j);
				}
			}
			
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}
