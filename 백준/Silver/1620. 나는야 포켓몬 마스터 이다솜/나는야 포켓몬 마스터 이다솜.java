import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		HashMap<String, Integer> string2int = new HashMap<>();
		HashMap<Integer, String> int2string = new HashMap<>();
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());	//도감에 수록되어 있는 포켓몬의 개수 N
		int M = Integer.parseInt(st.nextToken());	//내가 맞춰야 하는 문제의 개수 M
		
		for(int i=1; i<=N; i++) {
			String line = br.readLine();
			
			string2int.put(line, i);
			int2string.put(i, line);
		}
		
		for(int i=0; i<M; i++) {
			String line = br.readLine();
			
			//1. 입력이 문자열
			if(Character.isAlphabetic(line.charAt(0))) {
				sb.append(string2int.get(line) + "\n");
			//2. 입력이 숫자
			} else {
				sb.append(int2string.get(Integer.parseInt(line)) + "\n");
			}
		}
		
		System.out.println(sb.toString());
	}

}