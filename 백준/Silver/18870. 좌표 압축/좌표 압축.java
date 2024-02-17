import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());	//좌표의 수 N
		TreeSet<Integer> inputSet = new TreeSet<>();
		TreeMap<Integer, Integer> answerMap = new TreeMap<>();
		ArrayList<Integer> inputKeys = new ArrayList<>();
		st = new StringTokenizer(br.readLine());
		int key = 0;
		
		for(int i=0; i<N; i++) {
			key = Integer.parseInt(st.nextToken());
			inputSet.add(key);
			inputKeys.add(key);
		}
		
		int accumulatSum = 0;
		for(int i : inputSet) {
			answerMap.put(i, accumulatSum++);
		}
		
		for(int i=0; i<N; i++) {
			sb.append(answerMap.get(inputKeys.get(i)) + " ");
		}
		
		System.out.println(sb.toString());
	}

}