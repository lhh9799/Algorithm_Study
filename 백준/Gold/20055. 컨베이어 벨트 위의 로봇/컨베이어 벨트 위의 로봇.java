import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int answer = 0;
		int threshold = 0;							//내구도가 0인 칸의 개수
		
		st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());	//컨베이어 벨트의 길이 N
		int K = Integer.parseInt(st.nextToken());	//내구도가 0인 칸의 개수 기준(K개 이상 이면 과정 종료)
		int[] belt = new int[2*N];
		boolean[] isRobotOn = new boolean[N];		//로봇은 벨트 아래쪽에 있을 수 없음 -> N-1부터 시작
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<2*N; i++) {
			belt[i] = Integer.parseInt(st.nextToken());
			
			if(belt[i] == 0) {
				threshold++;						//내구도가 0인 칸의 개수 증가
			}
		}
		
		for(answer=0; threshold < K; answer++) {
			//1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
			//1-1. 벨트
			int temp = belt[2*N-1];
			
			for(int j=2*N-1; j>0; j--) {
				belt[j] = belt[j-1];
			}
			belt[0] = temp;
			
			//1-2. 로봇
			for(int j=N-1; j>0; j--) {
				isRobotOn[j] = isRobotOn[j-1];
			}
			//N-1위치의 로봇 내림, 0 위치의 로봇 1 위치로 이동 (true -> false / false -> false)
			isRobotOn[0] = false;
			isRobotOn[N-1] = false;
			
			//2. 올려져 있는 로봇 한 칸씩 이동
			for(int j=N-1; j>0; j--) {
				if(isRobotOn[j-1] && !isRobotOn[j] && belt[j] >= 1) {
					isRobotOn[j] = true;
					belt[j]--;
					isRobotOn[j-1] = false;
					
					if(belt[j] == 0) {
						threshold++;
					}
				}
			}

			//3. 로봇 올림
			if(!isRobotOn[0] && belt[0] > 0) {
				isRobotOn[0] = true;	//로봇 올림
				belt[0]--;				//내구도 감소
				
				if(belt[0] == 0) {
					threshold++;
				}
			}
			
			//4. 로봇 내림
			if(isRobotOn[N-1]) {
				isRobotOn[N-1] = false;
			}
		}
		
		System.out.println(answer);
	}

}