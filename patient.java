import java.util.*;

public class Patient extends userinfo{
	private Integer patientid;
	private ArrayList<Problem> problems = new ArrayList<Problems>();
	private ArrayList<Record> records = new ArrayList<Record>();
	//modify following methods
	
	//when user click view button
	public void ViewRecordsOfAProblem(){
		//this will read from a file/DB first, and diaplay the list of records belong to that problem
		//test: 1. the read is successful or not; 2, the display(textview) is successful or not
	}
	public void ViewProblems(){
		//this will read from a file/DB first, and diaplay the list of problems belong to that patient
		//test: 1. the read is successful or not; 2, the display(textview) is successful or not
	}
	public void retrieveContactinfoByUserid(){
		//this will read from a file/DB first, and diaplay the list of contact info belong to that patient
		//1. the read is successful; 2.get the userid of this <email,phone> and compare it with the patient's userid
	}
	public void viewPhotosOfARecord(){
		//1.the read is successful
	}
	public void viewGeoLoactionsfOfARecord(){
		//1. the read is successful
	}
	public void viewRecordsOneMapWithLocations(){
		//1. the read is successful, the map open currectly
	}
	public boolean userAuthenticate(){
		//1. add a userid+password(new userinfo(username, password), and get the password according to the userid, check the userid is same or not
	}
	public void viewBodyLocationPicture(){
		//1. add a bodyLocationtoa to a test record			(new record(title=Null,comment=Null .... budyLocationPicture), 
		//and read it from that test recor, see get the same picture or not
	}
	public void viewProfile(){
		//1.add a test user(new userinfo(username, password,email,phone), and read info from that test user, check the information is same or not
	}
	public void viewAnimation(){
		//add a series of photos to a test record, 			(new record(title=Null,comment=Null .... RecordTrackingPhoto)
		//and read it from that test recor, see get the same picture or not
	}
	public void eidtProblem(){
		// create a new problem 							(new Problem(title,description,date))
		//, and edit the problem, check the edition successful
	}
	public void addTime(){
		//create a new Problem and Record, 					(new Problem(title,description,date).  |.   new Record(.... Date date....)
		//check the time save success
	}
	public void DeleteProblem(){
		//read the last problem as p2 and then create a new Problem and then delete it 			(new Problem(....))
		//then read the last problem, compare it with p1 
	}	
	public void AddProblem(){
		// create a new Problem and read the last problem, then compare this two
	}
}