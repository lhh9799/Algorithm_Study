import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int lastIndex = -1;
		
		char[] charArray = br.readLine().toCharArray();
		
		for(char c : charArray) {
			sb.append(c);
			lastIndex++;
			
			if(lastIndex >= 4 && sb.charAt(lastIndex-3) == 'P' && sb.charAt(lastIndex-2) == 'P' && sb.charAt(lastIndex-1) == 'A' && sb.charAt(lastIndex) == 'P') {
				sb.replace(lastIndex-3, lastIndex+1, "");
				lastIndex -= 4;
				
				sb.append('P');
				lastIndex++;
			}
		}
		
		if(sb.toString().equals("PPAP") || sb.toString().equals("P")) {
			System.out.println("PPAP");
		} else {
			System.out.println("NP");
		}
	}

}