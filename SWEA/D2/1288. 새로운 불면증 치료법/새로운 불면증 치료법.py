import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Solution {
	
	static int solution(int N) {
		int multiple;
		TreeSet<Integer> numberSlice = new TreeSet<Integer>();
		
		for(int i=1; ; i++) {
			multiple = N * i;
			
			for(char c : String.valueOf(multiple).toCharArray()) {
				numberSlice.add(c - '0');
			}
			
			if(numberSlice.size() == 10) {
				return N * i;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());		//테스트 케이스의 수 T
		
		for(int i=1; i<=T; i++) {
			int N = Integer.parseInt(br.readLine());	//최대: 1백만
			
			System.out.println("#" + i + " " + solution(N));
		}
	}

}