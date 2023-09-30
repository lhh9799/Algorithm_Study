import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int N;
	static int[][] S;
	static boolean[] selected;
	static int answer = Integer.MAX_VALUE;
	
	static void calculate() {
		int selectedSynergy = 0;
		int notSelectedSynergy = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=i+1; j<N; j++) {
				if(selected[i] && selected[j]) {
					selectedSynergy += S[i][j] + S[j][i];
				} else if(!selected[i] && !selected[j]) {
					notSelectedSynergy += S[i][j] + S[j][i];
				}
			}
		}
		
		answer = Math.min(answer, Math.abs(selectedSynergy - notSelectedSynergy));
	}
	
	static void teamCombination(int start, int count) {
		if(count == N/2) {
			calculate();
			
			return;
		}
		
		for(int i=start; i<N; i++) {
			selected[i] = true;
			teamCombination(i+1, count+1);
			selected[i] = false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		S = new int[N][N];
		selected = new boolean[N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<N; j++) {
				S[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		teamCombination(0, 0);
		System.out.println(answer);
	}

}