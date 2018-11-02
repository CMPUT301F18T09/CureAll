import javax.print.attribute.standard.*;

public abstract class userinfo {
	String username
	String password
	String email
	String phone

	
	public userinfo(String username,String password){//check whether assigin sucess
		this.username = username;
		this.password = password;
	}
	public userinfo(String username, String password,String email,String phone)
	
	public String getUsername(){}//get the username
	public String getPassword(){}//get the username
	public String getEmail(){}//get the username
	public String getPhone(){}//get the username
	
	
	public String setemail(){}
	public String setphone(){}
	
	//following functions will imply in each class(patient or care provider)
	public abstract void ViewRecordsOfAProblem(){}
	public abstract void ViewProblems(){}
	public abstract void retrieveContactinfoByUserid(){}
	public abstract void viewPhotosOfARecord(){}
	public abstract void viewGeoLoactionsfOfARecord(){}
	public abstract void viewRecordsOneMapWithLocations(){}
	public abstract boolean userAuthenticate(){}
	
}