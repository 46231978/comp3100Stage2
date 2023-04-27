public class dsServer {
	Integer id;
	String type;
	Integer limit;
   	Integer cores;
    	Integer memory;
    	Integer disk;

	public dsServer (String[] systemInfo){
    		type = systemInfo[0];
		id = Integer.parseInt(systemInfo[1]);
		cores = Integer.parseInt(systemInfo[4]);
		memory = Integer.parseInt(systemInfo[5]);
		disk = Integer.parseInt(systemInfo[6]);
    	}
}	
