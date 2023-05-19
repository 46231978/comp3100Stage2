import java.io.*;
import java.net.*;
import java.util.*;

public class stage2_idea8 {
   	public static String username = System.getProperty("user.name");
    	
    	public static DataInputStream din;
	public static DataOutputStream dout;
	public static BufferedReader br;
    	
    	
	public static dsJob job(String s){
		String[] JobInfo; 
		JobInfo = s.split("\\s+");
		dsJob scheduleJob = new dsJob(JobInfo);
		return scheduleJob;
	}
	
	
	public static void write(String s) throws IOException{
		String send = s + "\n";
		dout.write(send.getBytes());
		dout.flush();
		System.out.println("SENT " + s);
	}
	
	
	public static String receive() throws IOException{
		String str = din.readLine();
		System.out.println("RCVD " + str);
		return str;
	}
	
	
	public static dsServer iterateList (ArrayList<dsServer> list, dsJob job){
		dsServer server = null;
		System.out.println(list.get(0).status);
		for (int i = 0; i < list.size(); i++){
			if (list.get(i).cores >= job.cores && list.get(i).memory >= job.memory && list.get(i).disk >= job.disk){
				if (server == null){
					server = list.get(i);
				} else {
					if (list.get(i).cores < server.cores && list.get(i).memory < server.memory && list.get(i).disk < server.disk){
						server = list.get(i);
					}
				}
			}
		}
		return server;
	}
	
	
	public static void main (String[] args) throws IOException{
		Socket s = new Socket("localhost", 50000);
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String str = "";
		
		//send HELO
		write("HELO");

		//receive HELO
		str = receive();

		//send authentic
		write("AUTH " + username);

		//receive ok 
		str = receive();
	
		//send REDY
		write("REDY");

		//receive JOB
		str = receive();
	
		while(!str.contains("NONE")){
			if(str.contains("OK") || str.contains("JCPL")){
				//send REDY
				write("REDY");
			} 
			else if (str.contains("JOBN") || str.contains("JOBP")){
				dsJob scheduleJob = job(str);
				
				write("GETS Capable " + scheduleJob.cores + " " + scheduleJob.memory + " " + scheduleJob.disk);
				
				//receive data
				str = receive();
				String[] info = str.split("\\s+");
				Integer numberServer = Integer.parseInt(info[1]);
				
				//write ok
				write("OK");
				
				ArrayList<dsServer> idle_list = new ArrayList<>();
				ArrayList<dsServer> active_list = new ArrayList<>();
				ArrayList<dsServer> inactive_list = new ArrayList<>();
				
				//receive records' information
				System.out.print("RCVD ");
				
				for (int i = 0; i < numberServer; i++){
					str = din.readLine();
					String[] sysInfo = str.split("\\s+");
					dsServer curServer = new dsServer(sysInfo);
					if (curServer.status.equals("idle")){
						idle_list.add(curServer);
					} else if (curServer.status.equals("active") || curServer.status.equals("booting")){
						active_list.add(curServer);
					} else {
						inactive_list.add(curServer);
					}
					System.out.println(str);
				}
				
				//send OK
				write("OK");
				
				//receive .
				str = receive();
				
				dsServer scheduleServer = null;
				
				if (!idle_list.isEmpty()){
					scheduleServer = iterateList(idle_list, scheduleJob);
				}
				if (scheduleServer == null && !active_list.isEmpty()){
					scheduleServer = iterateList(active_list, scheduleJob);
				}
				if (scheduleServer == null && !inactive_list.isEmpty()){
					scheduleServer = inactive_list.get(0);
				}
					
				if (scheduleServer == null){
					double minJobs = Integer.MAX_VALUE;
					for (int i= 0; i < active_list.size(); i++){
						dsServer temp = active_list.get(i);
						double numJobs = temp.waitJobs + temp.runJobs;
						if (numJobs < minJobs){
							minJobs = numJobs;
							scheduleServer = temp;
						}
					}
					
				}
				
				//System.out.println (scheduleServer.type);
				String schedule = "SCHD" + " " + scheduleJob.id + " " + scheduleServer.type + " " + scheduleServer.id;
				
				//send schedule job
				write(schedule);
				
				//count++;
			} 
			//receive next job
			str = receive();
		}

		//send QUIT
		write("QUIT");

		//receive QUIT
		str = receive();

		//terminate
		dout.close();
		s.close();

	}
}
