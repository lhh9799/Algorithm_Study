import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 10726. 이진수 표현
 * 
 * 실행시간: 151ms (SWEA)
 * 메모리: 32,192 KB
 * 
 * 문제: 정수 N, M 이 주어질 때, M의 이진수 표현의 마지막 N 비트가 모두 1로 켜져 있는지 아닌지를 판별하여 출력하라.
 * 
 * 비트연산을 통해 N개의 비트가 1로 채워져 있는 수열을 만들고 M과 비트 And 연산을 한다.
 * 마지막 N개의 비트가 일치한다면 111111 (중략) N개의 1로 이루어진 결과가 나올 것이고
 * 일치하지 않는다면 101111과 같이 0인 (1이 아닌) 숫자가 채워져 있을 것이다.
 * 
 * 따라서 M과 비트 AND 연산을 한 결과가 마지막 N 비트가 모두 1인지 다시 한번 확인하여 답을 출력한다.
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());	//테스트 케이스의 수 TC
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		for(int i=1; i<=TC; i++) {
			st = new StringTokenizer(br.readLine());
			
			long N = Integer.parseInt(st.nextToken());
			long M = Integer.parseInt(st.nextToken());
			
			long lastBit = (1 << N) - 1;
			
			sb.append("#" + i + " " + (lastBit == (M & lastBit) ? "ON" : "OFF") + "\n");
		}
		
		System.out.println(sb.toString());
	}

}