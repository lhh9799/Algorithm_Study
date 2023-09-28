import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	
	//충돌 여부를 리턴하는 메소드 (true: 충돌, false: 충돌 아님)
	static boolean isCollide(int N, int headX, int headY, ArrayDeque<int[]> snake) {
		if(headX < 0 || headX >= N || headY < 0 || headY >= N) {
			return true;
		}
		
		for(int[] body : snake) {
			if(headX == body[0] && headY == body[1]) {
				return true;
			}
		}
		
		return false;
	}
	
	//이동한 칸에 사과가 있는지 여부를 리턴하는 메소드 (true: 사과가 있음, false: 사과가 없음) + 사과를 만난 경우 사과 위치 삭제 (소비)
	static boolean encounterApple(int headX, int headY, ArrayList<int[]> apple) {
		int index = 0;
		
		for(int[] singleApple : apple) {
			if(headX == singleApple[0] && headY == singleApple[1]) {
				apple.remove(index);
				return true;
			}
			
			index++;
		}
		
		return false;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		ArrayDeque<int[]> snake = new ArrayDeque<>();	//뱀의 머리를 제외한 위치를 저장하는 ArrayDeque
		ArrayList<int[]> apple = new ArrayList<>();	//사과의 위치
		int time = 1;
		int turnCount = 0;
		int direction = 0;
		//우, 하, 좌, 상
		int[] dx = {0, 1, 0, -1};
		int[] dy = {1, 0, -1, 0};
		
		int N = Integer.parseInt(br.readLine());	//보드의 크기 N
		int K = Integer.parseInt(br.readLine());	//사과의 개수 K
		snake.add(new int[] {0, 0});
		
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			
			int row = Integer.parseInt(st.nextToken());	//사과 위치 (행)
			int col = Integer.parseInt(st.nextToken());	//사과 위치 (열)
			
			apple.add(new int[] {row - 1, col - 1});
		}
		
		int L = Integer.parseInt(br.readLine());		//뱀의 방향 변환 횟수
		int[] X = new int[L];							//뱀의 방향 변환 정보 배열 (초)
		char[] C = new char[L];							//뱀의 방향 변환 정보 (방향)
		
		for(int i=0; i<L; i++) {
			st = new StringTokenizer(br.readLine());
			
			X[i] = Integer.parseInt(st.nextToken());	//뱀의 방향 변환 정보 (초)
			C[i] = st.nextToken().charAt(0);			//뱀의 방향 변환 정보 (L: 왼쪽, D: 오른쪽)
		}
		
		//X[L-1]: 뱀의 마지막 방향 변환 정보
		int headX = 0, headY = 0;						//뱀의 머리 위치
		
		
		//벽 또는 자기 자신의 몸과 부딪힌 경우 게임 종료
		for(;;time++) {
			//머리의 위치 갱신
			headX += dx[direction];
			headY += dy[direction];
			
			//1. 벽 또는 자기 자신의 몸과 부딪힌 경우 -> 게임 종료
			if(isCollide(N, headX, headY, snake)) {
				break;
			}
			
			//2-1. 이동한 칸에 사과가 있는 경우
			if(encounterApple(headX, headY, apple)) {
				snake.offerFirst(new int[] {headX, headY});	//현재 머리의 위치를 ArrayDeque에 추가
				
			}
			//2-2. 이동한 칸에 사과가 없는 경우
			else {
				snake.pollLast();	//꼬리가 위치한 칸을 비움
				snake.offerFirst(new int[] {headX, headY});	//현재 머리의 위치를 ArrayDeque에 추가
			}
			
			//뱀이 방향전환을 하는 시점
			if(time == X[turnCount]) {
				if(C[turnCount] == 'L') {
					direction = (direction + 4 - 1) % 4;
				} else if(C[turnCount] == 'D') {
					direction = (direction + 1) % 4;
				} else {
					System.out.println("Turn error at " + time);
				}
				
				if(turnCount + 1 < L) {
					turnCount++;
				}
			}
		}
		
		System.out.println(time);
	}

}