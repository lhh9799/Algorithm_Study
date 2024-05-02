import java.util.*;

class Solution {
    public int[] solution(String today, String[] terms, String[] privacies) {
        int[] answer;
        StringTokenizer st = null;
        ArrayList<Integer> answerList = new ArrayList<>();
        HashMap<Character, Integer> termMap = new HashMap<>();
        String[] todayToken = today.split("\\.");
        StringBuilder sb;
        
        String todayYear = todayToken[0];
        String todayMonth = todayToken[1];
        String todayDays = todayToken[2];
        
        for(String s : terms) {
            String[] tokens = s.split(" ");
            
            termMap.put(tokens[0].charAt(0), Integer.parseInt(tokens[1]));    //ex) ('A', 6)
        }
        
        for(int i=0; i<privacies.length; i++) {
            String[] tokens = privacies[i].split(" ");
            
            String date = tokens[0];            //"2021.05.02"
            char type = tokens[1].charAt(0);    //'A'
            
            st = new StringTokenizer(date, ".");
            
            int year = Integer.parseInt(st.nextToken());
            int month = Integer.parseInt(st.nextToken());
            int days = Integer.parseInt(st.nextToken());
            
            int dueYear, dueMonth, dueDays;
            int leftMonth = termMap.get(type);
            
            dueYear = year;
            dueMonth = month + leftMonth;
            dueDays = days - 1;
            
            if(dueDays == 0) {
                dueDays = 28;
                dueMonth--;
            }
            
            if(dueMonth == 0) {
                dueMonth = 12;
                dueYear--;
            }
            
            if(dueMonth > 12) {
                dueYear += (dueMonth / 12);
                dueMonth = dueMonth % 12;
            }
            
            if(dueMonth == 0) {
                dueMonth = 12;
                dueYear--;
            }
            
            sb = new StringBuilder();
            
            sb.setLength(0);
            String dueDate = sb.append(dueYear).append(String.format("%02d", dueMonth)).append(String.format("%02d", dueDays)).toString();
            sb.setLength(0);
            String todayString = sb.append(todayYear).append(todayMonth).append(todayDays).toString();
            
            if(dueDate.compareTo(todayString) < 0) {
                answerList.add(i + 1);
            }
        }
        
        answer = new int[answerList.size()];
        
        for(int i=0; i<answerList.size(); i++) {
            answer[i] = answerList.get(i);
        }

        return answer;
    }
}