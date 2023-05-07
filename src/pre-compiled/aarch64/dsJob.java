public class dsJob {
	Integer id;
	Integer submitTime;
	Integer estRunTime;
	Integer cores;
	Integer memory;
	Integer disk;

	public dsJob (String[] jobDetails){
		id = Integer.parseInt(jobDetails[2]);
		submitTime = Integer.parseInt(jobDetails[1]);
		estRunTime = Integer.parseInt(jobDetails[3]);
		cores = Integer.parseInt(jobDetails[4]);
        	memory = Integer.parseInt(jobDetails[5]);
        	disk = Integer.parseInt(jobDetails[6].trim());
   	 }
}
	
