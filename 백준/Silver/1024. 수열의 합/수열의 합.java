import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());	//1,000,000,000보다 작거나 같은 자연수
		int L = Integer.parseInt(st.nextToken());	//2보다 크거나 같고, 100보다 작거나 같은 자연수
		
		for(int i=L; ; i++) {						//i: 수열의 길이
			int center = N / i;
			int start = 0;
			
			if(i % 2 == 0) {						//i가 짝수인 경우 가운데 수: 3 4 5 6 -> 4
				start = center - ((i / 2) - 1);
			} else {								//i가 홀수인 경우 가운데 수: 4 5 6 -> 5
				start = center - (i / 2);
			}
			
			if(start < 0 || i > 100) {				//시작하는 수는 음이 아닌 정수이어야 하고 길이가 100보다 작거나 같아야 함
				break;
			}
			
			if(i % 2 == 1 && N % i == 0) {			//18 2 -> 5 6 7
				//3 % 2 == 1 && 18 % 3 == 0
				for(int j=start; j<start+i; j++) {
					bw.write(j + " ");
				}
				
				bw.flush();
				System.exit(0);
			} else if(((double) N / i) % 1 == 0.5) {//18 4 -> 3 4 5 6
				//18 % 4 == 4.5 -> 4.5 % 0.5 == 0
				for(int j=start; j<start+i; j++) {
					bw.write(j + " ");
				}
				
				bw.flush();
				System.exit(0);
			}
		}
		
		//위 for문에서 답을 찾지 못한 경우 -1 출력
		System.out.println(-1);
	}

}