class PatientProblemList {
	ArrayList<Problems> problemList = new ArrayList<Problems>()
	public String usename
	ListView OldProblemList
		
	public void ListProblem(){//Show the listview of previous problem which have two button: view record & delete
		ReadFromSQLite(problemList = gson.fromJson(....))
		CareProblem_MyAdapter adapter = new CareProblem_MyAdapter(problemList) //list view all the problem
		OldProblemList.setAdapter(adapter)
	}

}