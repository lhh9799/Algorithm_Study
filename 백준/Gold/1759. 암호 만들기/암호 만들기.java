import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 * 백준 1759번 암호 만들기
 * https://www.acmicpc.net/problem/1759
 * 
 * 실행시간: 88ms (백준)
 * 메모리: 12,040 KB
 * 
 * 한 쪽의 길이가 N인 정사각형 배열이 주어졌을 때 0인 부분과 1인 부분을 압축한 결과를 출력하는 문제입니다.
 * 
 * 배열은 0 또는 1로 구성되어있기 때문에 한 구역의 합을 구해 0(흰 점)이면 0으로 압축, 격자의 크기이면 1로 압축할 수 있고, 그 외 경우는 흰점과 검은 점이 섞인 경우이므로 재귀호출로  4분할하여 답을 구합니다.
 * </pre>
 * 
 *  @author 이현호
 */

public class Main {
	
	static int L = 0;									//암호의 길이 (문제 조건: 암호는 서로 다른 L개의 알파벳 소문자들로 구성되며..) 
	static int C = 0;									//암호 조합에 사용될 서로 다른 알파벳 소문자들의 수
	static char[] usable;								//암호 조합에 사용할 수 있는 알파벳들을 저장하는 배열
	static char[] passwordCandidate;					//암호가 될 수 있는 문자들 조합 (최소 한 개의 모음, 최소 두 개의 자음, 오름차순)
	static char[] vowels = {'a', 'e', 'i', 'o', 'u'};	//모음을 저장하는 배열 (모음의 개수 계산 위함)
	static ArrayList<Character>	consonant = new ArrayList<Character>();	//자음을 저장하는 ArrayList (자음의 개수 계산 위함)
	static StringBuilder sb = new StringBuilder();		//가변 문자열 클래스 할당
	
	/**
	 * 조합한 암호가 유효한지 검사하는 메소드
	 * 기준: 최소 한 개의 모음(a, e, i, o, u)과 최소 두 개의 자음으로 구성, 알파벳이 증가하는 순서
	 * @return: true: 유효, false: 유효하지 않음 (가능성 없는 암호)
	 */
	public static boolean isValid() {
		int vowelCount = 0;						//모음의 개수 0으로 초기화
		int consonantCount = 0;					//자음의 개수 0으로 초기화
		
		//최소 한 개의 모음 (a, e, i, o, u)
		for(int i=0; i<vowels.length; i++) {
			//모음을 포함하면 (중복되는 알파벳은 입력되지 않으므로 한꺼번에 같은 알파벳이 1개로 카운트되지 않는다.)
			if(String.valueOf(passwordCandidate).contains(String.valueOf(vowels[i]))) {
				vowelCount++;					//모음의 개수 증가
			}
		}
		if(vowelCount < 1) {					//모음의 수가 0개이면
			return false;						//유효하지 않으므로 false 리턴
		}
		
		//최소 두 개의 자음
		for(char c1 : passwordCandidate) {		//알파벳 후보의 한 글자를 가져와
			for(char c2 : consonant) {			//자음의 한 글자를 가져와
				if(c1 == c2) {					//둘이 같다면
					consonantCount++;			//자음의 개수 증가
					
					if(consonantCount >= 2) {	//자음의 개수가 2 이상이면 유효하므로
						return true;			//true 리턴
					}
				}
			}
		}
		
		return false;							//모음의 개수는 1 이상이지만 자음의 개수가 2미만이므로 false 리턴
	}
	
	/**
	 * 입력받음 알파벳을 오름차순으로 조합하는 메소드
	 * @param count: 조합으로 만들어진 알파벳의 길이
	 * @param start: 조합에 사용될 시작 알파벳 인덱스 (중복을 피하고 오름차순 위함)
	 */
	public static void combination(int count, int start) {
		if(count == L) {	//조합한 알파벳의 길이가 목표로 한 길이에 도달한 경우
			if(isValid()) {	//암호가 유효하면
				//정답을 StringBuilder에 저장 (속도 향상 위함)
				sb.append(String.valueOf(passwordCandidate) + "\n");
			}
			
			return;	//재귀 리턴 (해당 호출에서 아래 코드를 실행하지 않음)
		}
		
		for(int i=start; i<C; i++) {				//C개의 알파벳 중에서 하나를 골라
			passwordCandidate[count] = usable[i];	//조합 중인 알파벳 배열에 저장
			combination(count+1, i+1);				//다음 알파벳 추가를 위해 재귀 호출
		}
	}

	public static void main(String[] args) throws IOException {
		//버퍼 사이즈가 Scanner (1024 Char)에 비해 8배 큰 BufferedReader (8192 Char) 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());	//문자열을 토큰 단위로 나누는 객체
		
		//L <= C임
		L = Integer.parseInt(st.nextToken());						//암호의 길이 입력받음
		C = Integer.parseInt(st.nextToken());						//암호 조합에 사용될 서로 다른 알파벳 소문자들의 수 입력받음
		passwordCandidate = new char[L];							//조합할 암호를 저장할 배열
		usable = br.readLine().replaceAll(" ", "").toCharArray();	//입력받은 한 줄을 char 배열에 저장
		Arrays.sort(usable);										//조합의 용이성을 위해 오름차순 정렬
		
		//자음을 계산하여 ArrayList에 추가
		for(char c = 'a'; c<='z'; c++) {	//'a'부터 'z'까지 반복하여
			//모음('a', 'e', 'i', 'o', 'u')이 아니면
			if(c != 'a' && c != 'e' && c!= 'i' && c != 'o' && c!= 'u') {
				consonant.add(c);			//ArrayList에 추가
			}
		}
		
		combination(0, 0);					//가능한 암호를 조합하여 StringBuilder에 추가
		System.out.println(sb.toString());	//정답 출력
	}

}
