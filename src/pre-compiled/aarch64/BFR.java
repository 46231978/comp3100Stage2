import java.io.*;
import java.net.*;
import java.util.*;

public class BFR {
   	public static String username = System.getProperty("user.name");
    	
    	public static ArrayList<dsServer> servers_list = new ArrayList<>();
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
				
				ArrayList<dsServer> servers_list = new ArrayList<>();
				//receive records' information
				System.out.print("RCVD ");
				
				for (int i = 0; i < numberServer; i++){
					str = din.readLine();
					String[] sysInfo = str.split("\\s+");
					dsServer curServer = new dsServer(sysInfo);
					servers_list.add(curServer);
					System.out.println(str);
				}
				
				//send OK
				write("OK");
				
				//receive .
				str = receive();
				
				dsServer scheduleServer = null;
				
				for (int i = 0; i < servers_list.size(); i++){
					if (servers_list.get(i).cores >= scheduleJob.cores && servers_list.get(i).memory >= scheduleJob.memory && servers_list.get(i).disk >= scheduleJob.disk){
						if (scheduleServer == null){
							scheduleServer = servers_list.get(i);
						} else {
							if (servers_list.get(i).cores <= scheduleServer.cores && servers_list.get(i).memory <= scheduleServer.memory && servers_list.get(i).disk <= scheduleServer.disk && !servers_list.get(i).status.equals("inactive")){
							scheduleServer = servers_list.get(i);
							}
						}
					}
				}
				
				
				if (scheduleServer == null){
					for (int i= 0; i < servers_list.size(); i++){
						if (servers_list.get(i).status.equals("active") || servers_list.get(i).status.equals("booting")){
							if (scheduleServer == null){
								scheduleServer = servers_list.get(i);
							} else {
							if ((servers_list.get(i).waitJobs + servers_list.get(i).runJobs) < (scheduleServer.waitJobs + scheduleServer.runJobs)){
								scheduleServer = servers_list.get(i);
							}
							}
						}
					}
				}
					
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
