import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] buttons = {300, 60, 10};				//버튼의 동작시간을 저장하는 배열 (A: 300초, B: 60초, C: 10초) 초 단위로 저장
		int T = Integer.parseInt(br.readLine());	//베이킹 레시피마다 오븐으로 구워야할 시간 T (초 단위)
		int[] buttonOperationCount = new int[3];	//버튼 별 최소버튼조작 횟수를 저장하는 배열
		
		for(int i=0; i<buttons.length; i++) {
			buttonOperationCount[i] += T / buttons[i];				//그리디하게 가장 많은 시간을 추가하는 버튼 먼저 사용
			T %= buttons[i];										//버튼의 시간으로 나눈 나머지가 남은 시간이 됨
		}
		
		if(T != 0) {												//나머지가 0이 아니면 (3개의 버튼으로 시간을 정확히 맞추지 못한 경우)
			System.out.println(-1);									//-1 출력
		}
		
		else {														//나머지가 0이면 (3개의 버튼으로 시간을 맞춘 경우)
			for(int i=0; i<buttonOperationCount.length; i++) {
				System.out.print(buttonOperationCount[i] + " ");	//버튼 별 최소버튼조작횟수를 저장하는 배열 출력
			}
		}
	}

}