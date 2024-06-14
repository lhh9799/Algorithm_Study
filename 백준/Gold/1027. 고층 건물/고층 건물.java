import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		int answer = 0;
		
		int N = Integer.parseInt(br.readLine());	//빌딩의 수 N
		st = new StringTokenizer(br.readLine());
		
		int[] heights = new int[N];
		
		for(int i=0; i<N; i++) {
			heights[i] = Integer.parseInt(st.nextToken());
		}
		
		double prevSlope = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			int answerCandidate = 0;
			
			prevSlope = Integer.MAX_VALUE;
			for(int j=i-1; j>=0; j--) {
				double currentSlope = (double) (heights[i] - heights[j]) / (i - j);
				
				if(currentSlope < prevSlope) {
					answerCandidate++;
					prevSlope = currentSlope;
				}
			}
			
			prevSlope = Integer.MIN_VALUE;
			for(int j=i+1; j<N; j++) {
				double currentSlope = (double) (heights[i] - heights[j]) / (i - j);
				
				if(currentSlope > prevSlope) {
					answerCandidate++;
					prevSlope = currentSlope;
				}
			}
		
			answer = Math.max(answer, answerCandidate);
		}
		
		System.out.println(answer);
	}

}