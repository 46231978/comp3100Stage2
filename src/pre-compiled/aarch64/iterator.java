import java.util.*;

public class iterator extends Thread {
	public ArrayList<dsServer> servers_list;
	public dsJob scheduleJob;
	private int startIndex;
	private int endIndex;
	public dsServer server = null;
	public int fit;
	
	public iterator (ArrayList<dsServer> list, dsJob job, int start, int end){
		servers_list = list;
		scheduleJob = job;
		startIndex = start;
		endIndex = end;
	}
	
	@Override 
	public void run(){
		//int fit = 0;
		boolean active = false;
		for (int i = startIndex; i < endIndex; i++){
			if (servers_list.get(i).cores >= scheduleJob.cores && servers_list.get(i).memory >= scheduleJob.memory && servers_list.get(i).disk >= scheduleJob.disk) {
				if(server == null){
					fit = servers_list.get(i).cores + servers_list.get(i).memory + servers_list.get(i).disk + servers_list.get(i).avaiNow;
					server = servers_list.get(i);
				} else {
					if (servers_list.get(i).status.equals("active") || servers_list.get(i).status.equals("booting")){
						//if (active == false){
						//	server = servers_list.get(i);
						//	fit = servers_list.get(i).cores + servers_list.get(i).memory + servers_list.get(i).disk + servers_list.get(i).avaiNow;
						//	active = true;
						//} else{
						
						int temp = servers_list.get(i).cores + servers_list.get(i).memory + servers_list.get(i).disk + servers_list.get(i).avaiNow;
						if (temp < fit){
							fit = temp;
							server = servers_list.get(i);
						//}
						}
						
				}
				}
			}
		}
	}
	}

		
