import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Solution {
	
	      //테스트 케이스의 개수 T
	static int N , W , H , min_Count ;
	
	static int[][] arr , copyArr;
	static int[] ball;
	
	static int[] dR = {0,0,1,-1} , dC = {1,-1,0,0};
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());  
		
		for(int tc = 1 ; tc < T+1 ;tc++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());        //벽돌의 개수
			W = Integer.parseInt(st.nextToken());        //너비
			H = Integer.parseInt(st.nextToken());        //높이
			
			arr = new int[H][W];
			copyArr = new int[H][W];
			ball = new int[N];
			
			min_Count = Integer.MAX_VALUE;
			//값 입력받기  
			//벽돌의 정보 (파괴 범위) 입력받음
			for(int j=0; j<H; j++) {
			    st = new StringTokenizer(br.readLine());
			    
			    for(int k=0; k<W; k++) {
			        arr[j][k] =  Integer.parseInt(st.nextToken());
			    }
			}
			permu(0);
			
			
			sb.append("#" + tc + " " + min_Count + "\n");
			
			
		}
		System.out.println(sb.toString());
	}
	private static void permu(int ballCount) {
		if(ballCount == N) {
			
			// 조합 돌릴때마다 배열 생
			for(int r = 0 ; r < H ; r ++) {
				for(int c = 0 ; c < W ;c++) {
					copyArr[r][c] = arr[r][c];
				}
			}
			
			
			//구슬 떨어 뜨리기 
			for(int i = 0 ; i < N ;i++) {
				bfs(ball[i]);
			}
			
			
			//개수 카운트 
			int count = 0 ;
			
			for(int r = 0 ; r < H ; r ++) {
				for(int c = 0 ; c < W ;c++) {
					if(copyArr[r][c] != 0) count++;
				}
			}
			min_Count = Math.min(count, min_Count);
			
			return;
		}
		
		for(int i = 0 ; i < W ; i++) {
			ball[ballCount] = i;
			permu(ballCount+1);
		}
		
		
		
		
		
	}
	private static void bfs(int w) {
		// TODO Auto-generated method stub
		
		Queue<int[]> q = new ArrayDeque<int[]>();
		
		for(int r = 0 ; r < H ; r++) {
			if(copyArr[r][w] != 0) {
				q.add(new int[] {r,w});
				break;
			}
		}
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			int curR = cur[0]; //
			int curC = cur[1];
			
			int power = copyArr[curR][curC]; 
			
			copyArr[curR][curC] = 0 ; //값 0으로 변경
			
			
			
			
			for(int i = 1 ;  i < power ; i++) {
				
				
				for(int d = 0 ; d < 4 ; d++) {
					
					int nextR = curR + dR[d]*i;
					int nextC = curC + dC[d]*i;
					
					if(isRange(nextR,nextC) && copyArr[nextR][nextC] ==1) {
						copyArr[nextR][nextC] = 0 ;
						continue;
					}
					if(isRange(nextR,nextC) && copyArr[nextR][nextC] !=0) {
						
						
						q.add(new int[] {nextR,nextC});
					}
					
				}
			}
			
			
		}
		
		down();
		
		

	}
	
	
	private static void down() {
		
		Stack<Integer> stack = new Stack<>();
	    for(int a=0; a<W; a++) { // 열을 기준으로
	    	for(int b=0; b<H; b++) {
	        	if(copyArr[b][a] != 0) { // 빈칸이 아닌 벽돌이 있으면
	        		stack.push(copyArr[b][a]); // 스택에 push
	        		copyArr[b][a] = 0;
	        	}
	        }
	    	

	        int r = H-1; // 배열의 마지막 행 (여기서부터 위로 벽돌을 쌓아줄 것임)
	        while(!stack.isEmpty()) { // 스택에 있는 벽돌을
	        	copyArr[r--][a] = stack.pop(); // 다시 밑에서부터 쌓아주기
	        }
	        

	        
	    }
		}
		
		
		
		
	
	
	
	private static boolean isRange(int nextR, int nextC) {
		// TODO Auto-generated method stub
		
		if(nextR >= H || nextR <0 || nextC >=W || nextC <0)
		return false;
		
		
		return true;
	}
	
	
	
    

}