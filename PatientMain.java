import java.util.*;

class Patient{
	ArrayList<Problems> problemList = new ArrayList<Problems>()
	public String usename
	ListView OldProblemList
	
	public void ListProblem(){//Show the listview of previous problem which have two button: view record & delete
		ReadFromSQLite(problemList = gson.fromJson(....))
		MyAdapter adapter = new MyAdapter(problemList) //list view all the problem
		OldProblemList.setAdapter(adapter)
	}
	
	public void ProfilePage(View view){Intent.Profile; Profile.username = username}//go to the profileprage
	
	public void AddProblem(View view){}// go to the AddProblem page
	
	public void Search(View view){//go to the search page
		/*first show a popsup to select the type of search, then go to different activity*/
	}
}