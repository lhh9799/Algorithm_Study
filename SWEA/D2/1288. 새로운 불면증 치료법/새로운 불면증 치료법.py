import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * <pre>
 * SWEA 1288. 새로운 불면증 치료법
 * 
 * 실행시간: 122ms (SWEA)
 * 메모리: 19,380 KB
 * 
 * 임의의 숫자 N이 주어지고, 1 * N부터 k * N까지의 각 자리 수를 모두 추출하는데, 0에서 9까지 모든 수를 모을 때가 되는 최소 k를 구하는 문제입니다.
 * 
 * k * N을 Integer -> String -> Char[]로 바꾼 뒤 '0'값을 빼 Integer로 바꾸고
 * HashSet에 넣고 그 크기가 10이 되면 리턴합니다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static int solution(int N) {
		int multiple;
		HashSet<Integer> numberSlice = new HashSet<Integer>();
		
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