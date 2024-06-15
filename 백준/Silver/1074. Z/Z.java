import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static int iterateCount = 0;
	public static int targetR = 0;
	public static int targetC = 0;
	
	public static void iterate(int startRow, int startCol, int size) {
		//특정 좌표를 찾은 경우
		if(startRow == targetR && startCol == targetC) {
			System.out.println(iterateCount);					//방문까지 걸린 이동 횟수 출력
			
			return;
		}
		
		//시작 좌표가 사분면에 있는지 확인
		else if(targetR >= startRow && targetR < startRow + size && targetC >= startCol && targetC < startCol + size) {
			int half = size/2;
			
			iterate(startRow, startCol, half);					//왼쪽 위
			iterate(startRow, startCol + half, half);			//오른쪽 위
			iterate(startRow + half, startCol, half);			//왼쪽 아래
			iterate(startRow + half, startCol + half, half);	//오른쪽 아래
		}
		
		//시작 좌표가 사분면에 없으면 빠르게 방문횟수 계산 (사분면 스킵)
		else {
			iterateCount += size * size;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		targetR = Integer.parseInt(st.nextToken());
		targetC = Integer.parseInt(st.nextToken());
		
		iterate(0, 0, 2 << N);
	}

}
