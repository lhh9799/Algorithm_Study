class Solution {
	int posInSeconds;
	int videoLengthInSeconds;
	int opStartInSeconds, opEndInSeconds;
	
	public void startRangeCheck() {
		if(posInSeconds < 0) {
			posInSeconds = 0;
		}
	}
	
	public void endRangeCheck() {
		if(posInSeconds > videoLengthInSeconds) {
			posInSeconds = videoLengthInSeconds;
		}
	}
	
	public void openRageCheck() {
		if(posInSeconds >= opStartInSeconds && posInSeconds <= opEndInSeconds) {
			posInSeconds = opEndInSeconds;
		}
	}
	
	public String positionPrinter() {
        //"mm:ss" 형식 변환
    	StringBuilder sb = new StringBuilder();
    	int min = posInSeconds / 60;
    	int seconds = posInSeconds % 60;
    	
    	if(min < 10) {
    		sb.append("0");
    	}
    	sb.append(min).append(":");
    	
    	if(seconds <10) {
    		sb.append("0");
    	}
    	sb.append(seconds);
    	
    	return sb.toString();
	}
	
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
		String[] tokens = video_len.split(":");
		videoLengthInSeconds = Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
		
		tokens = pos.split(":");
		posInSeconds = Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
		
		tokens = op_start.split(":");
		opStartInSeconds = Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
		
		tokens = op_end.split(":");
		opEndInSeconds = Integer.parseInt(tokens[0]) * 60 + Integer.parseInt(tokens[1]);
    	
        for(int i=0; i<commands.length; i++) {
        	openRageCheck();

        	//사용자 명령 처리
        	if(commands[i].equals("prev")) {
        		posInSeconds -= 10;
        		startRangeCheck();
        	} else if(commands[i].equals("next")) {
        		posInSeconds += 10;
        		endRangeCheck();
        	}
        	
        	openRageCheck();
        }
        
        //"mm:ss" 형식 출력
        return positionPrinter();
    }
}