import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <pre>
 * 백준 17609번 회문
 * https://www.acmicpc.net/problem/17609
 * 
 * 실행시간: 224ms (백준)
 * 메모리: 42,484 KB
 * 
 * 주어진 문자열이 회문인지, 유사회문 (한 글자를 삭제하여 회문이 되는 경우)인지 둘 모두 해당하지 않는지 판별하는 문제입니다.
 * 
 * 투 포인터를 사용합니다.
 * 
 * 두 포인터가 가리키는 문자가 다른 경우 재귀 함수 호출을 줄이기 위해
 * (start+1)과 end가 같으면 judge(start+1, end)를,
 * start와 (end-1)이 같으면 judge(start, end-1)을 호출하도록 했는데
 * 이것이 오답의 원인이었습니다. (반례: abcddcdba #정답은 1이나 2가 출력됨)
 * 
 * chance가 2가 되면 리턴하기 때문에 (더 이상 콜 스택이 증가하지 않음) (start+1)과 end가 같던, start와 (end-1)이 같던
 * 비교하지 않고 둘 다 호출하여 둘 중 작은 수를 취하는 방식을 택하면 됩니다.
 * 
 * </pre>
 * 
 *  @author 이현호
 */

public class Main {
	
	/**
	 * 
	 * @param c: 회문인지 검사할 문자열
	 * @param start: 검사를 시작할 '앞' 부분
	 * @param end: 검사를 시작할 '뒤' 부분
	 * @param chance: 0이면 회문, 1이면 유사 회문, 2이면 둘 모두 해당하지 않음
	 * @return: chance (0이면 회문, 1이면 유사 회문, 2이면 둘 모두 해당하지 않음)
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