import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	
	static char[][] map;
	
	static void starPrinter(int N, int x, int y) throws IOException {
		if(N == 1) {
			map[x][y] = '*';
			
			return;
		}
			
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(i != 1 || j != 1) {
					starPrinter(N/3, x + i * (N/3), y + j * (N/3));
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(br.readLine());
		map = new char[N][N];
		
		for(int i=0; i<N; i++) {
			Arrays.fill(map[i], ' ');
		}
		
		starPrinter(N, 0, 0);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				bw.write(map[i][j]);
			}
			bw.write("\n");
		}
		
		bw.flush();
	}

}