import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

class Ingredient {
	int taste;		//재료의 수 N
	int calorie;	//제한 칼로리 L
	
	Ingredient() {}
	Ingredient(int taste, int calorie) {
		this.taste = taste;
		this.calorie = calorie;
	}
}

class calorieAsc implements Comparator<Ingredient> {

	@Override
	public int compare(Ingredient o1, Ingredient o2) {
		return Integer.compare(o1.calorie, o2.calorie);
	}
}

public class Solution {
	
	static int N;	//재료의 수 N
	static int L;	//제한 칼로리 L
	static ArrayList<Ingredient> ingredientInfo;
	static int[] numbers;
	static int answer;
	
	public static void combination(int start, int tasteSum, int calorieSum) {
		for(int i=start; i<N; i++) {
			int newTasteSum = tasteSum + ingredientInfo.get(i).taste;
			int newCalorieSum = calorieSum + ingredientInfo.get(i).calorie;
			
			if(newCalorieSum < L) {
				combination(i+1, newTasteSum, newCalorieSum);
				answer = Math.max(answer, newTasteSum);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());		//테스트 케이스의 수 T 입력받음
		StringTokenizer st = null;
		int taste;
		int calorie;
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());	//재료의 수 N 입력받음
			L = Integer.parseInt(st.nextToken());	//제한 칼로리 L 입력받음
			ingredientInfo = new ArrayList<Ingredient>(N);	//재료에 대한 민기의 맛에 대한 점수와 칼로리 정보를 저장하는 ArrayList
			answer = Integer.MIN_VALUE;
			
			for(int j=0; j<N; j++) {
				st = new StringTokenizer(br.readLine());
				taste = Integer.parseInt(st.nextToken());
				calorie = Integer.parseInt(st.nextToken());
				
				ingredientInfo.add(new Ingredient(taste, calorie));
			}
			
			Collections.sort(ingredientInfo, new calorieAsc());
			combination(0, 0, 0);
			sb.append("#" + i + " " + answer + "\n");
		}
		
		System.out.println(sb.toString());
	}

}
