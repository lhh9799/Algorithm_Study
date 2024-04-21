import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M;
	static int[][] office;
	static int[][] simulatedOffice;
	static ArrayList<int[]> cctvList = new ArrayList<>();
	static int answer = Integer.MAX_VALUE;
	static int[] directions;
	static int numberOfCctv;
	static HashMap<Integer, Integer> possibleDirections = new HashMap<>();
	
	static int blindSpotCounter() {
		int blindSpot = 0;
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(simulatedOffice[i][j] == 0) {
					blindSpot++;
				}
			}
		}
		
		return blindSpot;
	}
	
	static void up(int x, int y) {
		for(int i=x-1; i>=0; i--) {
			if(simulatedOffice[i][y] == 6) {
				return;
			}
			
			simulatedOffice[i][y] = -1;	//감시할 수 있는 영역으로 표시
		}
	}
	
	static void down(int x, int y) {
		for(int i=x+1; i<N; i++) {
			if(simulatedOffice[i][y] == 6) {
				return;
			}
			
			simulatedOffice[i][y] = -1;	//감시할 수 있는 영역으로 표시
		}
	}
	
	static void left(int x, int y) {
		for(int i=y-1; i>=0; i--) {
			if(simulatedOffice[x][i] == 6) {
				return;
			}
			
			simulatedOffice[x][i] = -1;	//감시할 수 있는 영역으로 표시
		}
	}
	
	static void right(int x, int y) {
		for(int i=y+1; i<M; i++) {
			if(simulatedOffice[x][i] == 6) {
				return;
			}
			
			simulatedOffice[x][i] = -1;	//감시할 수 있는 영역으로 표시
		}
	}
	
	static void initSimulatedOffice() {
		for(int i=0; i<N; i++) {
			System.arraycopy(office[i], 0, simulatedOffice[i], 0, office[i].length);
		}
	}
	
	static void permutation(int depth) {
		if(depth == numberOfCctv) {
			//계산
			initSimulatedOffice();
			
			for(int i=0; i<numberOfCctv; i++) {
				int[] coord = cctvList.get(i);
				int x = coord[0];
				int y = coord[1];
				int cctvType = office[x][y];
				int direction = directions[i];
				simulatedOffice[x][y] = cctvType;	//CCTV가 차지하는 공간 초기화
				
				if(cctvType == 1) {
					//1. direction == 0 -> 우
					if(direction == 0) {
						right(x, y);
					}
					//2. direction == 1 -> 하
					else if(direction == 1) {
						down(x, y);
					}
					//3. direction == 2 -> 좌
					else if(direction == 2) {
						left(x, y);
					}
					//4. direction == 3 -> 상
					else if(direction == 3) {
						up(x, y);
					}
				} else if(cctvType == 2) {
					//1. direction == 0 -> 좌, 우
					if(direction == 0) {
						left(x, y);
						right(x, y);
					}
					//2. direction == 1 -> 상, 하
					else if(direction == 1) {
						up(x, y);
						down(x, y);
					}
				} else if(cctvType == 3) {
					//1. direction == 0 -> 상, 우
					if(direction == 0) {
						up(x, y);
						right(x, y);
					}
					//2. direction == 1 -> 우, 하
					else if(direction == 1) {
						right(x, y);
						down(x, y);
					}
					//3. direction == 2 -> 하, 좌
					else if(direction == 2) {
						down(x, y);
						left(x, y);
					}
					//4. direction == 3 -> 좌, 상
					else if(direction == 3) {
						left(x, y);
						up(x, y);
					}
				} else if(cctvType == 4) {
					//1. direction == 0 -> 좌, 상, 우 #사각 지대: 하
					if(direction == 0) {
						left(x, y);
						up(x, y);
						right(x, y);
					}
					//2. direction == 1 -> 상, 우, 하 #사각지대: 좌
					else if(direction == 1) {
						up(x, y);
						right(x, y);
						down(x, y);
					}
					//3. direction == 2 -> 우, 하, 좌 #사각지대: 상
					else if(direction == 2) {
						right(x, y);
						down(x, y);
						left(x, y);
					}
					//4. direction == 3 -> 하, 좌, 상 #사각지대: 우
					else if(direction == 3) {
						down(x, y);
						left(x, y);
						up(x, y);
					}
				} else if(cctvType == 5) {
					up(x, y);
					down(x, y);
					left(x, y);
					right(x, y);
				}
			}
			
			int blindSpot = blindSpotCounter();
			answer = Math.min(answer, blindSpot);
			
			return;
		}
		
		int[] coord = cctvList.get(depth);
		int cctvType = office[coord[0]][coord[1]];
		int maxDirectionCount = possibleDirections.get(cctvType);
		
		for(int i=0; i<maxDirectionCount; i++) {
			directions[depth] = i;
			
			permutation(depth + 1);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());	//사무실의 세로 크기
		M = Integer.parseInt(st.nextToken());	//사무실의 가로 크기
		office = new int[N][M];
		simulatedOffice = new int[N][M];
		
		possibleDirections.put(1, 4);
		possibleDirections.put(2, 2);
		possibleDirections.put(3, 4);
		possibleDirections.put(4, 4);
		possibleDirections.put(5, 1);
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j=0; j<M; j++) {
				office[i][j] = Integer.parseInt(st.nextToken());
				
				if(office[i][j] >= 1 && office[i][j] <= 5) {
					cctvList.add(new int[] {i, j});
				}
			}
		}
		
		directions = new int[cctvList.size()];
		numberOfCctv = cctvList.size();
		
		permutation(0);
		
		System.out.println(answer);
	}

}