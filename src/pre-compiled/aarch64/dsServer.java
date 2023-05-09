public class dsServer {
	Integer id;
	String type;
	Integer avaiNow;
	String status;
   	Integer cores;
    	Integer memory;
    	Integer disk;
    	Integer waitJobs;
    	Integer runJobs;

	public dsServer (String[] systemInfo){
    		type = systemInfo[0];
		id = Integer.parseInt(systemInfo[1]);
		status = systemInfo[2];
		avaiNow = Integer.parseInt(systemInfo[3]);
		cores = Integer.parseInt(systemInfo[4]);
		memory = Integer.parseInt(systemInfo[5]);
		disk = Integer.parseInt(systemInfo[6]);
		waitJobs =  Integer.parseInt(systemInfo[7]);
		runJobs =  Integer.parseInt(systemInfo[8]);
    	}
}	
