import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	
	public static int N;
	public static char[] decidedOperators;
	public static char[] operator = {'+', '-', ' '};
	public static StringBuilder sb = new StringBuilder();
	
	public static void eval() {
		StringBuilder expression = new StringBuilder();
		int result = 0;
		
		for(int i=1; i<N; i++) {
			expression.append(i);
			
			if(decidedOperators[i-1] != ' ') {
				expression.append(decidedOperators[i-1]);
			}
		}
		expression.append(N);
		
		StringTokenizer st = new StringTokenizer(expression.toString(), "+|-", true);
		result = Integer.parseInt(st.nextToken());
		
		while(st.hasMoreTokens()) {
			char operator = st.nextToken().charAt(0);
			
			if(operator == '+') {
				result += Integer.parseInt(st.nextToken());
			} else if(operator == '-') {
				result -= Integer.parseInt(st.nextToken());
			}
		}
		
		if(result == 0) {
			for(int i=1; i<N; i++) {
				sb.append(i);
				sb.append(decidedOperators[i-1]);
			}
			sb.append(N + "\n");
		}
	}
	
	public static void decideOperator(int count) {
		if(count == N-1) {
			eval();
			
			return;
		}
		
		for(int k=0; k<operator.length; k++) {
			decidedOperators[count] = operator[k];
			
			decideOperator(count + 1);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Arrays.sort(operator);
		
		int T = Integer.parseInt(br.readLine());	//테스트 케이스의 개수 (<10)
		
		for(int i=0; i<T; i++) {
			N = Integer.parseInt(br.readLine());
			decidedOperators = new char[N-1];
			
			decideOperator(0);
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

}