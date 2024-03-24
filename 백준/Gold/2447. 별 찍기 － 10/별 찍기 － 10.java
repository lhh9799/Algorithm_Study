import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * <pre>
 * 백준 2447번 별 찍기 - 10
 * https://www.acmicpc.net/problem/2447
 * 
 * 실행시간: 288ms (백준)
 * 메모리: 22,592 KB
 * 
 * 재귀 문제입니다.
 * N이 3의 거듭제곱 형태로 주어지고, 가운데를 제외한 모든 칸에 별이 하나씩 있는 패턴으로 출력하는 문제입니다.
 * 왼쪽 위를 (0,0)이라 했을 때 가운데인 (1,1)은 공백임을 이용합니다.
 * 
 * 1. 정답을 저장할 배열을 미리 할당합니다.
 * 2. 배열은 반드시 공백(' ')으로 초기화합니다. (IDE에서는 공백으로 출력되나 실제 값은 null이 할당됨)
 * 3. N이 1이 될 때까지 N/3을 인수로 재귀를 호출합니다.
 * 3-1. 가운데(1,1)가 공백인 경우 재귀를 호출하지 않습니다. (공백 유지)
 * 3-2. N이 1이 되면 배열에 별을 저장합니다.
 * 
 * </pre>
 * 
 *  @author 이현호
 */

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