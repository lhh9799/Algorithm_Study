import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    
    static int N;                            //구역의 개수 N
    static int[] populations;
    static boolean[] selected;
    static ArrayList<ArrayList<Integer>> city;
    static int answer = Integer.MAX_VALUE;
    
    static boolean isConnectedDistrict(ArrayList<Integer> district) {
        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        int visitCount = 0;
        int currentDistrict = 0;
        boolean[] visited = new boolean[N];
        
        queue.offer(district.get(0));        //첫 지역구를 큐에 삽입
        visitCount++;                        //방문한 도시의 수 증가
        visited[district.get(0)] = true;    //방문한 것으로 표시
        
        while(!queue.isEmpty()) {
            currentDistrict = queue.poll();
            
            for(int adjacentDistrict : city.get(currentDistrict)) {
                if(!visited[adjacentDistrict] && district.contains(Integer.valueOf(adjacentDistrict))) {
                    queue.offer(adjacentDistrict);
                    visited[adjacentDistrict] = true;
                    visitCount++;
                }
            }
        }
        
        return visitCount == district.size();
    }
    
    //한 선거구에 포함되어 있는 구역이 모두 연결되어있는지 여부를 리턴하는 메소드 (true: 모두 연결되어 있음, false: 연결되어 있지 않음)
    static boolean adjacencyCheck() {
        ArrayList<Integer> selectedIndex = new ArrayList<>();
        ArrayList<Integer> notSelectedIndex = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
            if(selected[i]) {
                selectedIndex.add(i);
            }
            else {
                notSelectedIndex.add(i);
            }
        }
        
        if(selectedIndex.size() == 0 || selectedIndex.size() == N) {
            return false;
        }
        
        return isConnectedDistrict(selectedIndex) && isConnectedDistrict(notSelectedIndex);
    }
    
    static void calculate() {
        int selectedSum = 0;
        int notSelectedSum = 0;
        
        for(int i=0; i<selected.length; i++) {
            if(selected[i]) {
                selectedSum += populations[i];
            }
            else {
                notSelectedSum += populations[i];
            }
        }
        
        answer = Math.min(answer, Math.abs(selectedSum - notSelectedSum));
    }
    
    static void combination(int start, int count) {
        //2개로 나누기 때문에 한 쪽의 크기가 N/2이하이면 다른 쪽이 더 크거나 같음 (백트래킹)
        //count == N/2 -> N: 4 (1개,3개), (2개,2개), N: 5 (1개, 4개), (2개, 3개)
        if(count >= 1 && count <= N/2) {
            if(adjacencyCheck()) {
                calculate();
            }
            
            if(count == N/2) {
                return;
            }
        }
        
        for(int i=start; i<N; i++) {
            selected[i] = true;
            combination(i+1, count+1);
            selected[i] = false;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        N = Integer.parseInt(br.readLine());    //구역의 개수 N
        populations = new int[N];
        selected = new boolean[N];
        city = new ArrayList<ArrayList<Integer>>(N);
        
        st = new StringTokenizer(br.readLine());
        
        for(int i=0; i<N; i++) {
            populations[i] = Integer.parseInt(st.nextToken());
            city.add(new ArrayList<Integer>());
        }
        
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            
            st.nextToken();    //인접한 구역의 수 (입력 버림)
            
            while(st.hasMoreTokens()) {
                city.get(i).add(Integer.parseInt(st.nextToken()) - 1);
            }
        }
        
        combination(0, 0);
        
        System.out.println((answer == Integer.MAX_VALUE) ? -1 : answer);
    }

}