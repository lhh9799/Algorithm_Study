import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		long min = Long.parseLong(st.nextToken());
		long max = Long.parseLong(st.nextToken());
		boolean[] isSpecialNumber = new boolean[(int) (max - min + 1)];
		Arrays.fill(isSpecialNumber, true);
		int specialNumberCount = (int) (max - min + 1);
		
		//1은 예외 처리
		for(long i=2; i*i<=max; i++) {
			long squaredNumber = i * i;
			
			for(long j = min % squaredNumber == 0 ? min / squaredNumber : min / squaredNumber + 1; j<=max/squaredNumber; j++) {
				int index = (int) (squaredNumber * j - min);
				
				if(isSpecialNumber[index]) {
					isSpecialNumber[index] = false;
					specialNumberCount--;
				}
			}
		}
		
		System.out.println(specialNumberCount);
	}
}