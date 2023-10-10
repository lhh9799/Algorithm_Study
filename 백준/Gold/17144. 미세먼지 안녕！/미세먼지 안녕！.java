import java.util.Scanner;

public class Main {
	/*
	 * 왜 16퍼에서 오답뜨는지 모르겠다 
	 * 찾아주는사람 천재
	 */
	static int R,C,T ;
	static int[][] arr;
	static int[][] copyArr;
	static int[] dR = {1,-1,0,0};
	static int[] dC = {0,0,-1,1};
 	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		R = sc.nextInt();
		C = sc.nextInt();
		T = sc.nextInt();
		arr = new int[R][C];
		copyArr = new int[R][C];
		
		for(int r = 0 ; r < R ; r ++) {
			for(int c = 0 ; c < C ;c++) {
				arr[r][c] = sc.nextInt();
				
			}
		}
		
		for(int i = 0 ; i < T ; i++) {
			diffuse(); //확산하다
			
			clean();
		}
		
		
		
		int result = 0 ;
		
		for(int r = 0 ; r < R ; r ++) {
			for(int c = 0 ; c < C ;c++) {
				//System.out.print(arr[r][c]);
				if(arr[r][c]>0) result += arr[r][c];
				
			}
			//System.out.println();
		}
		
		
		System.out.println(result);
	}
 	
 	
	private static void clean() {
		
		for(int i = 0 ; i < R ;i++) {
			if(copyArr[i][0] == -1) {
				upClean(i);
				downClean(i+1);
				break;
			} 
		}
		
		
		for(int r = 0 ; r < R ; r++) {
			for(int c= 0 ; c < C ; c++) {
				arr[r][c] = copyArr[r][c];
			}
		}
	}
	
	
	
	private static void downClean(int i) {
		int[] downWindR = {1,0,-1,0};
		int[] downWindC = {0,1,0,-1};
		
		int dir = 0;
		
		
		int curR = i+1;
		int curC = 0;
		
		int nextR = curR + downWindR[dir];
		int nextC = curC + downWindC[dir];

		while(true) {
			
			
			if(nextR == i && nextC == 0) {
				copyArr[curR][curC] = 0 ;
				break;
			}
			
			
			copyArr[curR][curC] = copyArr[nextR][nextC];
			
			
			
			if(!isRange(nextR+downWindR[dir],nextC+downWindC[dir])|| (nextR+downWindR[dir] == i-1 && nextC+downWindC[dir] == C-1 )) {
				dir++;
			}
			
			curR = nextR;
			curC = nextC;
			nextR += downWindR[dir];
			nextC += downWindC[dir];
		}
		
		
	}
	
	private static void upClean(int i) {
		int[] upWindR = {-1,0,1,0};
		int[] upWindC = {0,1,0,-1};
		
		int dir = 0;
		
		
		int curR = i-1;
		int curC = 0;
		
		int nextR = curR + upWindR[dir];
		int nextC = curC + upWindC[dir];

		while(true) {
			
			if(nextR == i && nextC == 0) {
				copyArr[curR][curC] = 0 ;
				break;
			}
		
			copyArr[curR][curC] = copyArr[nextR][nextC];
			
			
			if(!isRange(nextR+upWindR[dir],nextC+upWindC[dir]) || (nextR+upWindR[dir] == i+1 && nextC+upWindC[dir] == C-1 )) {
				dir++;
			}
			
			curR = nextR;
			curC = nextC;
			nextR += upWindR[dir];
			nextC += upWindC[dir];
			
			
			
			
		}
				
	}
	private static void diffuse() {
		
		for(int r = 0 ; r < R ;r++) {
			for(int c = 0 ; c < C ; c ++) {
				copyArr[r][c] = arr[r][c];
			}
		}
		
		
		for(int r = 0 ; r < R ; r++) {
			for(int c = 0 ; c < C ;c++) {
				if(arr[r][c] > 0) {
					play(r,c);
				}
			}
		}
		
	}
	
	
	private static void play(int r , int c) {
		// TODO Auto-generated method stub
		int now = arr[r][c]; // 현재 먼지량
		
		for(int i = 0 ; i < 4 ;i ++) {
			int nextR = r + dR[i];
			int nextC = c + dC[i];
			
			if(isRange(nextR,nextC) && arr[nextR][nextC] != -1) {
				copyArr[nextR][nextC] += now/5 ;
				copyArr[r][c] -= now/5 ; 
			}
			
		}
	}
	
	
	private static boolean isRange(int r , int c) {
		if(r >=R || r < 0 || c >=C || c < 0) return false;
		return true;
		
	}
}


/*
 * 
 * 00000000
00222320
-10000000
-10000000
01331240
03444240
00000000
 * */