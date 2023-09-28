import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
	
	static int[][] board;
	static ArrayList<int[]> blankCoords = null;
	
	//상, 하, 좌, 우
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	
	//상, 하, 좌, 우로 같은 값이 있는지 확인 후 사용할 수 있는 값인지를 리턴하는 메소드 (true: 사용 가능, false: 사용 불가)
	static boolean fourDirectionCheck(int row, int col, int value) {
		for(int i=0; i<4; i++) {
			int x = row, y = col;
			
			while(x + dx[i] >= 0 && x + dx[i] < 9 && y + dy[i] >= 0 && y + dy[i] < 9) {
				x += dx[i];
				y += dy[i];
				
				if(board[x][y] == value) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	//3×3짜리 사각형에 같은 값이 있는지 확인 후 사용할 수 있는 값인지를 리턴하는 메소드 (true: 사용 가능, false: 사용 불가)
	//행/열 번호 0 ~ 8 -> 3으로 나누었을 때 몫이 그룹의 번호 | 0, 1, 2 (=> 0) / 3, 4, 5 (=> 1) / 6, 7, 8 (=> 2)
	static boolean threeSquareCheck(int row, int col, int value) {
		for(int i=(row/3)*3; i<(row/3)*3+3; i++) {
			for(int j=(col/3)*3; j<(col/3)*3+3; j++) {
				if(board[i][j] == value) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	//4방 탐색, 3×3짜리 사각형 확인 후 사용할 수 있는 값인지를 리턴하는 메소드
	static boolean goodToFillValue(int row, int col, int value) {
		return fourDirectionCheck(row, col, value) && threeSquareCheck(row, col, value);
	}
	
	static void solution(int targetBlankIndex) {
		if(targetBlankIndex == blankCoords.size()) {
			return;
		}
		
		int[] blankCoord = blankCoords.get(targetBlankIndex);
		
		boolean inserted = false;
		
		int row = blankCoord[0];
		int col = blankCoord[1];
		
		for(int i=board[row][col] + 1; i<=9; i++) {
			if(goodToFillValue(row, col, i)) {
				board[row][col] = i;
				inserted = true;
				
				break;
			}
		}
		
		if(!inserted) {						//빈 칸에 1~9 사이의 값을 넣지 못한 경우
			board[row][col] = 0;			//현재 넣은 값 초기화 (초기화하지 않으면 직전에 값을 삽입할 때 사용할 수 있는 값인지 검사할 때 side effect 발생)
			solution(targetBlankIndex - 1);	//직전에 넣은 칸의 값 수정
		} else {							//값을 넣은 경우
			solution(targetBlankIndex + 1);	//다음 칸의 값 수정
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[9][9];
		blankCoords = new ArrayList<>();
		
		for(int i=0; i<9; i++) {
			String line = br.readLine();
			
			for(int j=0; j<9; j++) {
				board[i][j] = line.charAt(j) - '0';
				
				if(board[i][j] == 0) {
					blankCoords.add(new int[] {i, j});
				}
			}
		}
		
		solution(0);
		
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

}