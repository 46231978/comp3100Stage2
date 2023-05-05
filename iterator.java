import java.util.*;

public class iterator extends Thread {
	public ArrayList<dsServer> servers_list;
	public dsJob scheduleJob;
	private int startIndex;
	private int endIndex;
	public dsServer server = null;
	
	public iterator (ArrayList<dsServer> list, dsJob job, int start, int end){
		servers_list = list;
		scheduleJob = job;
		startIndex = start;
		endIndex = end;
	}
	
	@Override 
	public void run(){
		for (int i = startIndex; i < endIndex; i++){
			if (servers_list.get(i).cores >= scheduleJob.cores && servers_list.get(i).memory >= scheduleJob.memory){
				if(server == null || servers_list.get(i).cores < server.cores){
					server = servers_list.get(i);
				}
			}
		}
	}
}
		
