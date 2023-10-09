import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int count = 0;
		
		char[] T = br.readLine().toCharArray();		//문자열 T (전체 문자열)
		char[] P = br.readLine().toCharArray();		//문자열 P (전체에서 찾으려는 문자열 패턴)
		
		int[] pi = new int[P.length];				//부분일치 테이블 배열
		
		//부분일치 테이블 배열 만들기
		int j=0;
		for(int i=1; i<P.length;) {					//전체 문자열의 길이만큼 반복 (i == 0 to P.length 테이블 생성)
			if(P[i] == P[j]) {
				pi[i] = j + 1;
				i++;
				j++;
			} else if(j>0) {						//접두사 포인터 j 위치에서 불일치 발생 -> 직전 (j-1)위치까지는 일치 -> pi[j-1] = k을 이용하여 j=k
				j = pi[j-1];
			} else {								//더 이상 pi 테이블을 당겨와 검사할 수 없으면 i값 증가
				i++;
			}
		}
		
		//패턴 매칭
		j = 0;
		for(int i=0; i<T.length;) {
			if(T[i] == P[j]) {
				i++;
				j++;
				
				if(j == P.length) {
					count++;
					j = pi[j-1];
					sb.append((i - P.length + 1) + "\n");
				}
			} else if (j>0) {						//접두사 포인터 j 위치에서 불일치 발생 -> 직전 (j-1)위치까지는 일치 -> pi[j-1] = k을 이용하여 j=k
				j = pi[j-1];
			} else {								//더 이상 pi 테이블을 당겨와 검사할 수 없으면 i값 증가
				i++;
			}
		}
		
		sb.insert(0, count + "\n");
		System.out.println(sb.toString());
	}

}