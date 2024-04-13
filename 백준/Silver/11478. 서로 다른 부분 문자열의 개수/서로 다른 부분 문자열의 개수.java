import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		HashSet<String> subStrings = new HashSet<>();
		
		for(int i=1; i<=input.length(); i++) {			//i: 부분 문자열의 길이
			for(int j=0; i+j<=input.length(); j++) {	//j: 부분 문자열의 시작 인덱스, i+j: 부분 문자열의 끝 인덱스 (포함 X)
				subStrings.add(input.substring(i-1, i+j));
			}
		}
		
		System.out.println(subStrings.size());
	}

}