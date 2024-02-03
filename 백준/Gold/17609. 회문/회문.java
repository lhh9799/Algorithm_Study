import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	/**
	 * 
	 * @param c: 회문인지 검사할 문자열
	 * @param start: 검사를 시작할 '앞' 부분
	 * @param end: 검사를 시작할 '뒤' 부분
	 * @param chance: 0이면 회문, 1이면 유사 회문, 2이면 둘 모두 해당하지 않음
	 * @return: 0이면 회문, 1이면 유사 회문, 2이면 둘 모두 해당하지 않음
	 */
	static int judge(char[] c, int start, int end, int chance) {
		while (start <= end && chance <= 1) {
			if(c[start] != c[end]) {
				chance++;
				
				if(chance == 2) {
					return 2;
				}
				
				return Math.min(judge(c, start+1, end, chance), judge(c, start, end-1, chance));
			} else {
				start++;
				end--;
			}
		}
		
		return chance;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());	//주어지는 문자열의 개수를 나타내는 정수 T(1 ≤ T ≤ 30)
		
		for(int i=0; i<T; i++) {
			char[] c = br.readLine().toCharArray();
			
			sb.append(judge(c, 0, c.length-1, 0) + "\n");
		}
		
		System.out.println(sb.toString());
	}

}