import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 5215. 햄버거 다이어트
 * 
 * 실행시간: 176ms (SWEA)
 * 메모리: 20,140 KB
 * 
 * 재료의 정보(맛, 칼로리) 리스트가 주어졌을 때, 주어진 칼로리 제한을 넘지 않으면서 가능한 맛의 합의 최댓값을 구하는 문제입니다.
 * 
 * 재귀 메소드의 기저조건을 없애고 누적 칼로리가 칼로리 제한(L) 미만인 경우만 재귀를 호출하도록 해 연산량을 줄였습니다. (백트래킹)
 * </pre>
 * 
 * @author 이현호
 */

/**
 * 재료의 정보(맛, 칼로리)를 저장하기 위한 클래스
 * @author 이현호
 */
class Ingredient {
	int taste;		//맛에 대한 점수
	int calorie;	//칼로리
	
	Ingredient() {}
	Ingredient(int taste, int calorie) {
		this.taste = taste;
		this.calorie = calorie;
	}
}

/**
 * 재료가 저장된 ArrayList를 칼로리를 기준으로 오름차순 정렬하기 위한 클래스
 * @author 이현호
 */
class calorieAsc implements Comparator<Ingredient> {

	@Override
	public int compare(Ingredient o1, Ingredient o2) {
		return Integer.compare(o1.calorie, o2.calorie);
	}
}

public class Solution {
	
	static int N;									//재료의 수 N
	static int L;									//제한 칼로리 L
	static ArrayList<Ingredient> ingredientInfo;	//재료의 정보(맛, 칼로리)를 저장하는 ArrayList 선언
	static int answer;								//정답 변수 (가능한 맛의 합의 최댓값)
	
	/**
	 * 재료를 제한 칼로리 이하로 조합해 맛을 계산하는 메소드
	 * @param start: 조합할 재료의 시작 번호
	 * @param tasteSum: 조합된 누적 맛 점수
	 * @param calorieSum: 조합된 누적 칼로리
	 */
	public static void combination(int start, int tasteSum, int calorieSum) {
		for(int i=start; i<N; i++) {										//새로운 재료를 골라
			int newTasteSum = tasteSum + ingredientInfo.get(i).taste;		//맛의 점수 누적 계산
			int newCalorieSum = calorieSum + ingredientInfo.get(i).calorie;	//칼로리 누적 계산
			
			//새로 조합한 재료의 칼로리 누적이 L 미만이면
			if(newCalorieSum < L) {
				answer = Math.max(answer, newTasteSum);			//정답 변수 갱신
				combination(i+1, newTasteSum, newCalorieSum);	//재귀 (새로운 재료 조합)
			}
			
			//새로 조합한 재료의 칼로리 누적이 L이면
			else if(newCalorieSum == L) { 
				answer = Math.max(answer, newTasteSum);			//정답 변수 갱신 (새로운 재료는 조합하지 않음)
			}
		}
	}

	public static void main(String[] args) throws IOException {
		//버퍼 사이즈가 Scanner (1024 Char)에 비해 8배 큰 BufferedReader (8192 Char) 사용
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();				//가변 문자열 클래스 할당
		int T = Integer.parseInt(br.readLine());			//테스트 케이스의 수 T 입력받음
		StringTokenizer st = null;							//문자열을 토큰 단위로 나누는 객체
		int taste;											//입력받을 재료의 맛 점수
		int calorie;										//입력받을 재료의 칼로리
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());			//재료의 수 N 입력받음
			L = Integer.parseInt(st.nextToken());			//제한 칼로리 L 입력받음
			ingredientInfo = new ArrayList<Ingredient>(N);	//재료의 정보(맛, 칼로리)를 저장하는 ArrayList 할당
			answer = Integer.MIN_VALUE;						//정답 변수 최솟값으로 초기화 (최댓값 찾기 위함)
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				taste = Integer.parseInt(st.nextToken());	//맛 점수를 입력받아
				calorie = Integer.parseInt(st.nextToken());	//칼로리를 입력받아
				
				//재료의 정보(맛, 칼로리)를 저장하는 ArrayList에 객체 생성 후 추가
				ingredientInfo.add(new Ingredient(taste, calorie));
			}
			
			//재료 ArrayList를 칼로리 오름차순으로 정렬 (백트래킹 위함) #조합 중인 칼로리가 L 이상이면 새로운 재료를 추가하지 않음
			Collections.sort(ingredientInfo, new calorieAsc());
			combination(0, 0, 0);							//재료들을 조합해 맛 점수 최댓값을 구하는 메소드 호출
			sb.append("#" + i + " " + answer + "\n");		//정답을 StringBuilder에 저장 (속도 향상 위함)
		}
		
		//정답 출력
		System.out.println(sb.toString());
	}

}
