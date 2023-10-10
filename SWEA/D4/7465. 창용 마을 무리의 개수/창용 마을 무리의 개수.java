import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * 플로이드-워샬
 * @author SSAFY
 *
 */

public class Solution {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());
		
		for(int i=1; i<=T; i++) {
			st = new StringTokenizer(br.readLine());
			int groupNo = 1;
			TreeSet<Integer> groupNoSet = new TreeSet<>();
			TreeSet<Integer> aloneNoSet = new TreeSet<>();
			int a, b;
			
			int N = Integer.parseInt(st.nextToken());						//창용 마을에는 N명의 사람이 살고 있다.
			int M = Integer.parseInt(st.nextToken());						//이후 M개의 줄에 걸쳐서 서로를 알고 있는 두 사람의 번호가 주어진다.
			
			int[][] knowEachOther = new int[N][N];							//서로 알고 있으면 true, 아니면 false
			
			for(int j=0; j<M; j++) {
				st = new StringTokenizer(br.readLine());
				groupNoSet.clear();
				
				if(st.countTokens() == 1) {
					a = Integer.parseInt(st.nextToken()) - 1;
					
					knowEachOther[a][a] = groupNo++;
				} else {
					a = Integer.parseInt(st.nextToken()) - 1;
					b = Integer.parseInt(st.nextToken()) - 1;
					
					knowEachOther[a][b] = knowEachOther[b][a] = groupNo++;
				}
			}
			
			for(int j=0; j<N; j++) {										//경유지
				for(int k=0; k<N; k++) {									//출발지
					if(j==k) continue;										//경유지와 출발지 같으면 스킵
					
					for(int l=0; l<N; l++) {								//도착지
						if(j==l || k==l) continue;							//경유지와 도착지가 같거나 출발지와 도착지가 같으면 스킵
						
						if(knowEachOther[k][j] > 0 && knowEachOther[j][l] > 0) {
							//출발지와 경유지가 연결되어 있고 경유지와 도착지가 연결되어 있으면 출발지와 경유지는 연결된 것임
							int candidate = Math.min(knowEachOther[k][j], knowEachOther[j][l]);
							
							if(knowEachOther[k][l] > 0) {
								knowEachOther[k][l] = Math.min(knowEachOther[k][l], candidate);
							} else {
								knowEachOther[k][l] = candidate;
							}
							knowEachOther[k][j] = knowEachOther[j][l] = candidate;
						}
					}
				}
			}
			
			for(int j=0; j<N; j++) {
				aloneNoSet.add(j);
			}
			
			for(int j=0; j<N; j++) {
				for(int k=0; k<N; k++) {
					if(j==k) continue;
					
					if(knowEachOther[j][k] != 0) {
						groupNoSet.add(knowEachOther[j][k]);		
						aloneNoSet.remove(Integer.valueOf(j));
					}
				}
			}
			sb.append("#" + i + " " + (groupNoSet.size() + aloneNoSet.size()) + "\n");
			
		}
		
		System.out.println(sb.toString());
	}

}