import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	public static char fsm(char state, char nextChar) {
		switch(state) {
			case 'A':
				return nextChar == '1' ? 'B' : 'H';
			case 'B':
				return nextChar == '0' ? 'C' : 'Z';
			case 'C':
				return nextChar == '0' ? 'D' : 'Z';
			case 'D':
				return nextChar == '0' ? 'D' : 'E';
			case 'E':
				return nextChar == '1' ? 'F' : 'H';
			case 'F':
				return nextChar == '1' ? 'F' : 'G';
			case 'G':
				return nextChar == '0' ? 'D' : 'I';
			case 'H':
				return nextChar == '1' ? 'I' : 'Z';
			case 'I':
				return nextChar == '1' ? 'B' : 'H';
			}
		
		return 'Z';	//err
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());	//테스트 케이스의 개수 T
		
		for(int i=0; i<T; i++) {
			char[] line = br.readLine().toCharArray();
			char state = 'A';
			
			for(char c : line) {
				state = fsm(state, c);
			}
			
			sb.append(state == 'E' || state == 'F' || state == 'I' ? "YES" : "NO").append("\n");
		}
		
		System.out.println(sb.toString());
	}

}