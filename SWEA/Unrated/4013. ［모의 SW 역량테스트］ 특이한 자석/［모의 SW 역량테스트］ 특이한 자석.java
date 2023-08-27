import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 4013. [모의 SW 역량테스트] 특이한 자석
 * 
 * 실행시간: 104ms (SWEA)
 * 메모리: 18,908 KB
 * 
 * 8개의 날을 가진 4개의 자석을 입력받아 K번 자석을 회전시켰을 때 획득하는 점수의 총합을 구하는 문제입니다.
 * 
 * 이 자석들은 연쇄적으로 회전이 일어나지 않습니다. (옆 자석이 회전하면 영향을 받는 것이 아니라 회전하기 전의 자석 상태에 영향을 받음)
 * 따라서 회전이 일어나면 즉시 반영하지 않고 모든 자석의 극을 비교한 뒤 일괄적으로 상태를 수정합니다.
 * 
 * 큐를 사용하지 않고 빨간색 화살표 위치를 따로 저장해두고 이 값만 수정하는 방법을 취했습니다.
 * 자석의 수 (4), 날의 수 (8), 회전시키는 횟수(1이상 20이하)가 적어서 그런지 시간 차이는 크지 않았습니다. 자석과 날의 수가 크고 회전시키는 횟수가 많으면 큐 (IO 횟수 증가)보다 유리할 것이라 생각합니다.
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static boolean[][] blade;				//4개 자석 각각의 8개 날의 정보(N극, S극)를 저장하는 배열
	static int[] redIndex = {0, 0, 0, 0};	//빨간색 화살표의 위치를 저장하는 배열
	static int[] nextRedIndex = new int[4];	//1번 회전시킨 뒤 갱신될 빨간색 화살표 위치를 저장하는 배열
	
	//왼쪽 톱니바퀴 회전
	public static void leftRotate(int startBladeNo, int rotationDirection) {
		for(int i=startBladeNo; i>0; i--) {
			//자성이 다른 경우 회전 (오른쪽 톱니바퀴의 6번 인덱스와 왼쪽 톱니바퀴의 2번 인덱스가 다르면 자성이 다름)
			if(blade[i][(redIndex[i] + 6) % 8] != blade[i-1][(redIndex[i-1] + 2) % 8]) {
				rotationDirection *= -1;	//회전 방향 반전
				//회전방향이 1이면 (시계방향) 새로운 인덱스는 (기존 인덱스-1), -1이면 (반시계방향) 새로운 인덱스는 (기존 인덱스+1)
				nextRedIndex[i-1] = rotationDirection == 1 ? redIndex[i-1] - 1 : redIndex[i-1] + 1;
			}
			//자성이 같은 경우 리턴 (자성이 다르더라도 회전시키지 않았거나 영향을 받는 자석이 아니면 회전하지 않음 -Fig. 1)
			else {
				return;
			}
		}
	}
	
	//조건에 맞는 오른쪽 톱니바퀴들 회전
	public static void rightRotate(int startBladeNo, int rotationDirection) {
		for(int i=startBladeNo; i<3; i++) {
			//자성이 다른 경우 회전 (왼쪽 톱니바퀴의 2번 인덱스와 오른쪽 톱니바퀴의 6번 인덱스가 다르면 자성이 다름)
			if(blade[i][(redIndex[i] + 2) % 8] != blade[i+1][(redIndex[i+1] + 6) % 8]) {
				rotationDirection *= -1;	//회전 방향 반전
				//회전방향이 1이면 (시계방향) 새로운 인덱스는 (기존 인덱스-1), -1이면 (반시계방향) 새로운 인덱스는 (기존 인덱스+1)
				nextRedIndex[i+1] = rotationDirection == 1 ? redIndex[i+1] - 1 : redIndex[i+1] + 1;
			}
			//자성이 같은 경우 리턴 (자성이 다르더라도 회전시키지 않았거나 영향을 받는 자석이 아니면 회전하지 않음 -Fig. 1)
			else {
				return;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		//버퍼 사이즈가 Scanner (1024 Char)에 비해 8배 큰 BufferedReader (8192 Char) 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();			//가변 문자열 클래스 할당
		StringTokenizer st = null;						//문자열을 토큰 단위로 나누는 객체
		
		int T = Integer.parseInt(br.readLine());		//테스트 케이스의 수 입력받음
		blade = new boolean[4][8];						//자석의 날 정보 (0 또는 1) #true: S극, false: N극
		
		for(int i=1; i<=T; i++) {
			int K = Integer.parseInt(br.readLine());	//자석을 회전시키는 횟수 K 입력받음
			Arrays.fill(redIndex, 0);					//초기 빨간색 화살표 위치를 저장하는 배열 초기화
			Arrays.fill(nextRedIndex, 0);				//다음 빨간색 화살표 위치를 저장하는 배열 초기화
			
			//1번부터 4번 자석까지의 각각 8개 날의 자성정보 입력받음
			for(int j=0; j<4; j++) {
				st = new StringTokenizer(br.readLine());
				
				for(int k=0; k<8; k++) {
					blade[j][k] = st.nextToken().charAt(0) == '1' ? true : false;
				}
			}
			
			//K개의 줄에는 자석을 1칸씩 회전시키는 회전 정보
			for(int j=0; j<K; j++) {
				st = new StringTokenizer(br.readLine());
				
				int magnetNo = Integer.parseInt(st.nextToken());			//회전시킬 자석의 번호 입력받음
				int rotationDirection = Integer.parseInt(st.nextToken());	//회전시킬 방향 입력받음
				
				leftRotate(magnetNo-1, rotationDirection);					//회전시킬 자석의 왼쪽 자석들 회전 (회전시킬 자석이 1번이라면 for문 조건문을 만족하지 않아 수행하지 않음
				rightRotate(magnetNo-1, rotationDirection);					//회전시킬 자석의 오른쪽 자석들 회전 (회전시킬 자석이 4번이라면 for문 조건문을 만족하지 않아 수행하지 않음
				//자기자신 회전
				nextRedIndex[magnetNo-1] = rotationDirection == 1 ? redIndex[magnetNo-1] - 1 : redIndex[magnetNo-1] + 1;
				
				//자석들의 빨간색 화살표 위치 일괄갱신
				for(int k=0; k<4; k++) {
					redIndex[k] = (nextRedIndex[k] + 8) % 8;				//빨간색 화살표 위치가 음수가 될 수 있으므로(시계방향 회전) +8 후 모듈로 연산 수행
				}
			}
			
			int answer = 0;
			//자석들의 점수 계산 (빨간색 화살표 위치에 있는 날의 자성이 N극이면 0점, S극이면 2^(i-1)점 (자석의 번호 1번부터 시작))
			for(int j=0; j<blade.length; j++) {
				if(blade[j][redIndex[j]]) {
					answer += Math.pow(2, j);
				}
			}
			
			//정답을 StringBuilder에 저장 (속도 향상 위함)
			sb.append("#" + i + " " + answer + "\n");
		}
		
		//정답 출력
		System.out.println(sb.toString());
	}

}
