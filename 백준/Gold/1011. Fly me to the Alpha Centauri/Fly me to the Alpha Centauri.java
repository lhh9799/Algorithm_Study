import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());	//테스트케이스의 개수 T
		
		for(int i=0; i<T; i++) {
			st = new StringTokenizer(br.readLine());
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int sqrt = (int) Math.sqrt(y - x);
			int answer = 0;
			
			//1. sqrt ^ 2 == y - x -> 최소 작동횟수 == sqrt * 2- 1
			if(sqrt * sqrt == y - x) {
				answer = sqrt * 2 - 1;
			}
			
			//2. sqrt ^ 2 + sqrt >= y - x -> 최소 작동횟수 == sqrt * 2
			else if(sqrt * sqrt + sqrt >= y - x) {
				answer = (int) sqrt * 2;
			}
			
			//3. sqrt ^ 2 + sqrt < y - x -> 최소 작동횟수 == sqrt * 2 + 1
			else if(sqrt * sqrt + sqrt < y - x) {
				answer = (int) sqrt * 2 + 1;
			}

			sb.append(answer).append("\n");
		}
		
		System.out.println(sb.toString());
	}

}