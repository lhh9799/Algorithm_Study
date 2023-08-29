import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		if(N == 1) {
			System.out.println(0);
			
			return;
		}
			
		int[] operationCount = new int[N+1];
		Arrays.fill(operationCount, 1);
		
		
		for(int i=4; i<=N; i++) {
			int countCandidate = Integer.MAX_VALUE;
			
			if(i % 3 == 0) {
				countCandidate = operationCount[i / 3];
			}
			
			if(i % 2 == 0) {
				countCandidate = Math.min(countCandidate, operationCount[i / 2]);
			}
			
			countCandidate = Math.min(countCandidate, operationCount[i-1]);
			
			operationCount[i] = countCandidate + 1;
		}
		
		System.out.println(operationCount[N]);
	}

}
