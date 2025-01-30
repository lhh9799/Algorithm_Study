class Solution {
    public int solution(String[] board) {
        //1. "O"를 표시할 차례인데 "X"를 표시하거나 반대로 "X"를 표시할 차례인데 "O"를 표시한다.
        int oCount = 0;
        int xCount = 0;
        
        for(int i=0; i<3; i++) {
        	for(int j=0; j<3; j++) {
        		if(board[i].charAt(j) == 'O') {
        			oCount++;
        		} else if(board[i].charAt(j) == 'X') {
        			xCount++;
        		}
        	}
        }
        
        if(oCount - xCount > 1) {
        	return 0;
        }
        if(xCount > oCount) {
        	return 0;
        }
        
        //2. 선공이나 후공이 승리해서 게임이 종료되었음에도 그 게임을 진행한다.
        boolean consequentO = false;
        boolean consequentX = false;
        
        //(1) 가로줄 확인
        for(int i=0; i<3; i++) {
        	if(board[i].equals("OOO")) {
        		consequentO = true;
        	}
            if(board[i].equals("XXX")) {
        		consequentX = true;
        	}
        }
        
        if((consequentO && xCount == oCount) || consequentX && oCount == xCount + 1) {
        	System.out.println("2-1");
        	return 0;
        }
        
        //(2) 세로줄 확인
        consequentO = false;
        consequentX = false;
        
        for(int i=0; i<3; i++) {
        	if(board[0].charAt(i) == 'O' && board[1].charAt(i) == 'O' && board[2].charAt(i) == 'O') {
        		consequentO = true;
        	}
            if(board[0].charAt(i) == 'X' && board[1].charAt(i) == 'X' && board[2].charAt(i) == 'X') {
        		consequentX = true;
        	}
        }
        
        if((consequentO && xCount == oCount) || consequentX && oCount == xCount + 1) {
        	return 0;
        }
        
        //(3) 오른쪽 아래 대각선 확인
        consequentO = true;
        consequentX = true;
        
        for(int i=0; i<3; i++) {
    		if(board[i].charAt(i) != 'O') {
    			consequentO = false;
            }
            if(board[i].charAt(i) != 'X') {
    			consequentX = false;
    		}
        }
        
        if((consequentO && xCount == oCount) || consequentX && oCount == xCount + 1) {
        	return 0;
        }
        
        //(4) 왼쪽 아래 대각선 확인
        consequentO = true;
        consequentX = true;
        
        for(int i=0; i<3; i++) {
    		if(board[i].charAt(2-i) != 'O') {
    			consequentO = false;
    		}
            if(board[i].charAt(2-i) != 'X') {
    			consequentX = false;
    		}
        }
        
        if((consequentO && xCount == oCount) || consequentX && oCount == xCount + 1) {
        	return 0;
        }
        
        return 1;
    }
}