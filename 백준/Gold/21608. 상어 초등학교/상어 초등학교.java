import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

class Seat implements Comparable<Seat> {
	int row, col, like, empty;
	
	Seat() {}
	Seat(int row, int col, int like, int empty) {
		this.row = row;
		this.col = col;
		this.like = like;
		this.empty = empty;
	}

	@Override
	public int compareTo(Seat o) {
		if(this.like != o.like) {
			return Integer.compare(o.like, this.like);
		} else if(this.empty != o.empty) {
			return Integer.compare(o.empty, this.empty);
		} else if(this.row != o.row) {
			return Integer.compare(this.row, o.row);
		} else {
			return Integer.compare(this.col, o.col);
		}
	}
}

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		TreeMap<Integer, TreeSet<Integer>> pair = new TreeMap<>();
		PriorityQueue<Seat> pq = new PriorityQueue<>();
		int[][] classroom = null;
		int[] studentSeatOrder = null;
		//상, 하, 좌, 우
		int[] dx = {-1, 1, 0, 0};
		int[] dy = {0, 0, -1, 1};
		int answer = 0;										//최대 400,000 (20 * 20 * 1,000)
		
		int N = Integer.parseInt(br.readLine());			//교실의 길이 (크기: N×N)
		classroom = new int[N][N];							//교실 할당 (학생들의 자리 저장)
		studentSeatOrder = new int[N*N];					//자리 배치를 할 학생들의 순서 (번호)
		
		for(int i=0; i<N*N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int key = Integer.parseInt(st.nextToken());		//학생의 번호
			studentSeatOrder[i] = key;
			TreeSet<Integer> set = new TreeSet<>();
			while(st.hasMoreTokens()) {						//좋아하는 학생의 번호 (최대 4명)
				set.add(Integer.parseInt(st.nextToken()));
			}
			
			pair.put(key, set);
		}
		
		for(int i=0; i<N*N; i++) {							//i: 학생의 번호
			pq.clear();
			int targetStudentNo = studentSeatOrder[i];
			
			for(int j=0; j<N; j++) {						//j: 행 번호
				for(int k=0; k<N; k++) {					//k: 열 번호
					if(classroom[j][k] == 0) {
						//4방 탐색
						int like = 0;
						int empty = 0;
						for(int l=0; l<4; l++) {
							int x = j + dx[l];
							int y = k + dy[l];
							
							if(x >= 0 && x < N && y >= 0 && y < N) {
								if(pair.get(targetStudentNo).contains(Integer.valueOf(classroom[x][y]))) {
									like++;
								}
								if(classroom[x][y] == 0) {
									empty++;
								}
							}
						}
						pq.add(new Seat(j, k, like, empty));
					}
				}
			}
			
			classroom[pq.peek().row][pq.peek().col] = targetStudentNo;
			pq.poll();
		}
		
		//만족도 계산
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				int satisfaction = 0;
				
				for(int k=0; k<4; k++) {
					int x = i + dx[k];
					int y = j + dy[k];
					
					if(x >= 0 && x < N && y >= 0 && y < N && pair.get(classroom[i][j]).contains(classroom[x][y])) {
						satisfaction++;
					}
				}
				
				answer += (int) Math.pow(10, satisfaction - 1);
			}
		}
		System.out.println(answer);
	}
}