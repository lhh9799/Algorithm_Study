import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Main {
	
	static int N;
	static char[][] array;
	static boolean[][] visited;
	static ArrayDeque<int[]> queue = new ArrayDeque<>();
	static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	static int newRow = 0, newCol = 0;
	static char currentColor = '\0';
//	static int notDyschromatopsie = 0;
//	static int dyschromatopsie = 0;
	
	public static void clearVistedArray() {
		for(int i=0; i<visited.length; i++) {
			Arrays.fill(visited[i], false);
		}
	}
	
	public static void switchArray() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(array[i][j] == 'G') {
					array[i][j] = 'R';
				}
			}
		}
	}
	
	public static void dfs(int row, int col) {
		int[] current = null;
		
		visited[row][col] = true;
		queue.add(new int[] {row, col});
		
		while(!queue.isEmpty()) {
			current = queue.pollFirst();
			currentColor = array[current[0]][current[1]];
			
			for(int k=0; k<directions.length; k++) {
				newRow = current[0] + directions[k][0];
				newCol = current[1] + directions[k][1];
				
				while(newRow >= 0 && newRow < N && newCol >= 0 && newCol < N && array[newRow][newCol] == currentColor &&!visited[newRow][newCol]) {
					visited[newRow][newCol] = true;
					queue.add(new int[] {newRow, newCol});
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		N = Integer.parseInt(br.readLine());
		array = new char[N][N];
		visited = new boolean[N][N];
		
		for(int i=0; i<N; i++) {
			line = br.readLine();
			
			for(int j=0; j<N; j++) {
				array[i] = line.toCharArray();
			}
		}
		
		int notDyschromatopsie = 0;
		for (int i = 0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(!visited[i][j]) {
					dfs(i, j);
					notDyschromatopsie++;
				}
			}
		}
		
		int dyschromatopsie = 0;
		switchArray();
		clearVistedArray();
		
		for (int i = 0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(!visited[i][j]) {
					dfs(i, j);
					dyschromatopsie++;
				}
			}
		}
		
		System.out.println(notDyschromatopsie + " " + dyschromatopsie);
	}
}