import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 * SWEA 1230. 암호문3
 * 
 * 실행시간: 167ms (SWEA)
 * 메모리: 34,808 KB
 * 
 * 여러 개의 숫자로 이루어진 암호문을 삽입, 삭제, 추가하고 앞 10개 암호문을 출력하는 문제입니다.
 * 
 * 삽입이 이루어지는 x번째에 대한 설명 (범위)이 부족합니다.
 * java.util 패키지의 LinkedList 클래스를 사용하면 쉽게 풀 수 있지만, B형 대비이므로 노드를 구현했습니다. (여러 개의 숫자가 Insert되는 경우 속도 이점 기대)
 * 
 * </pre>
 * 
 * @author 이현호
 */

public class Solution {
	
	static class Node {
		int value;
		Node nextNode;
		
		Node() {}
		
		Node(int value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st1 = null, st2 = null;
		Node head, tail, prev = null;
		StringBuilder sb = new StringBuilder();
		
		for(int i=1; i<= 10; i++) {								//테스트 케이스 10개
			int N = Integer.parseInt(br.readLine());			//첫 번째 줄 : 원본 암호문 뭉치 속 암호문의 개수 N ( 2000 ≤ N ≤ 4000 의 정수)
			st1 = new StringTokenizer(br.readLine());			//두 번째 줄 : 원본 암호문 뭉치
			int commandCount = Integer.parseInt(br.readLine());	//세 번째 줄 : 명령어의 개수 ( 250 ≤ M ≤ 500 의 정수)
			st2 = new StringTokenizer(br.readLine());			//네 번째 줄 : 명령어
			
			head = prev = new Node(Integer.parseInt(st1.nextToken()));
			
			//1. 원본 암호문 뭉치 생성
			while(st1.hasMoreTokens()) {
				Node n = new Node(Integer.parseInt(st1.nextToken()));
				prev.nextNode = n;								//이전 노드에 새로 생성한 노드의 정보 추가
				prev = n;										//새로 생성한 노드는 다음 루프에서 이전 노드가 됨
			}
			tail = prev;										//꼬리 노드 갱신
			
			//2. 명령어 처리
			while(st2.hasMoreTokens()) {
				char command = st2.nextToken().charAt(0);
				
				if(command == 'I') {
					int index = Integer.parseInt(st2.nextToken());
					int count = Integer.parseInt(st2.nextToken());
					
					if(index == 0) {
						Node prev_head = head;					//새로운 노드가 추가되기 전 헤드
						
						Node n = new Node(Integer.parseInt(st2.nextToken()));
						head = n;
						prev = n;
						
						for(int j=0; j<count-1; j++) {
							n = new Node(Integer.parseInt(st2.nextToken()));
							n.nextNode = prev.nextNode;
							prev.nextNode = n;
							prev = n;
						}
						prev.nextNode = prev_head;
					}
					
					else {
						prev = head;
						
						for(int j=1; j<index; j++) {
							prev = prev.nextNode;
						}
						
						for(int j=0; j<count; j++) {
							Node n = new Node(Integer.parseInt(st2.nextToken()));
							n.nextNode = prev.nextNode;
							prev.nextNode = n;
							prev = n;
						}
						
						if(prev.nextNode == null) {
							tail = prev;
						}
					}
				} else if(command == 'D') {
					int index = Integer.parseInt(st2.nextToken());
					int count = Integer.parseInt(st2.nextToken());
					
					if(index == 0) {
						prev = head;
						
						for(int j=0; j<count; j++) {
							prev = prev.nextNode;
						}
						head = prev;
					}
					
					else {
						Node cutFront = head, cutEnd = null;
						
						for(int j=1; j<index; j++) {
							cutFront = cutFront.nextNode;
						}
						
						for(int j=0; j<count; j++) {
							cutEnd = cutFront.nextNode;
						}
						cutFront.nextNode = cutEnd.nextNode;
						
						if(cutFront.nextNode == null) {
							tail = cutEnd;
						}
					}
				} else if(command == 'A') {
					int count = Integer.parseInt(st2.nextToken());
					
					for(int j=0; j<count; j++) {
						Node n = new Node(Integer.parseInt(st2.nextToken()));
						tail.nextNode = n;
						tail = n;
					}
				}
			}
			
			//수정된 암호문 뭉치의 앞 10개 암호문을 StringBuilder에 추가
			sb.append("#" + i);
			prev = head;
			
			for(int j=0; j<10; j++) {
				sb.append(" " + prev.value);
				prev = prev.nextNode;
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

}