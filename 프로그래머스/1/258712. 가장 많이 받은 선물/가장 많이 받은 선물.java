import java.util.*;

class Solution {
    public int solution(String[] friends, String[] gifts) {
        int answer = 0;
        HashMap<String, Integer> number = new HashMap<>();
        int[][] exchange = new int[friends.length][friends.length];
        int[][] futureExchange = new int[friends.length][friends.length];
        int[][] giftIndex = new int[friends.length][3]; //0: 준 선물, 1: 받은 선물, 2: 선물 지수
        
        for(int i=0; i<friends.length; i++) {
            number.put(friends[i], i);
        }
        
        for(int i=0; i<gifts.length; i++) {
            String[] token = gifts[i].split(" ");
            int giver = number.get(token[0]);
            int receiver = number.get(token[1]);
            
            exchange[giver][receiver]++;
            // futureExchange[giver][receiver]++;
            giftIndex[giver][0]++;
            giftIndex[receiver][1]++;
        }
        
        //선물 지수 갱신
        for(int i=0; i<friends.length; i++) {
            giftIndex[i][2] = giftIndex[i][0] - giftIndex[i][1];
        }
        
        for(int i=0; i<friends.length; i++) {       //i: 선물을 준 사람
            for(int j=0; j<friends.length; j++) {   //j: 선물을 받는 사람
                if(exchange[i][j] > exchange[j][i]) {                   //두 사람이 선물을 주고받은 기록이 있다면
                    futureExchange[i][j]++;                             //이번 달까지 두 사람 사이에 더 많은 선물을 준 사람이 다음 달에 선물을 하나 받습니다.
                } else if(exchange[i][j] == 0 && exchange[j][i] == 0) { //두 사람이 선물을 주고받은 기록이 하나도 없으면
                    //선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 하나 받습니다.
                    if(giftIndex[i][2] > giftIndex[j][2]) {
                        futureExchange[i][j]++;
                    }
                    //만약 두 사람의 선물 지수도 같다면 다음 달에 선물을 주고받지 않습니다.
                    //Do nothing
                }
                //두 사람이 선물을 주고받은 수가 같다면
                //선물 지수가 더 큰 사람이 선물 지수가 더 작은 사람에게 선물을 하나 받습니다.
                else if(exchange[i][j] == exchange[j][i]) {
                    if(giftIndex[i][2] > giftIndex[j][2]) {
                        futureExchange[i][j]++;
                    }
                }
            }
        }
        
        int answerCandidate = 0;
        for(int i=0; i<friends.length; i++) {
            answerCandidate = 0;
            
            for(int j=0; j<friends.length; j++) {
                answerCandidate += futureExchange[i][j];
            }
            
            answer = Math.max(answer, answerCandidate);
        }
                
        return answer;
    }
}