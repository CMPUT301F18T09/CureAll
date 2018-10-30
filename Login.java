import java.util.*;
import java.awt.*;
import javax.swing.text.*;

class Login {
	username;
	password;
	userinfo info
	ArrayList<login> InfoList = new ArrayList<login>();
	public void main{
		role = button.(provider 1 or patient 2)	//get role type from button

		InfoList.add(new Logininfo(UserNameInput.getText().toString(),PasswordInput.getText().toString(),role));
		
				
		if (role == 1){
			if (check(InfoList) == 1){
				Intent.provider
				provider.username = username //传递username，方便profile时查询
			}
			else{ ERROR}
		}//check match
		if(role == 2){
			if (check(InfoList) == 1){
				Intent.patient
			}
			else{ERROR}
		}
		
		
	}
	public int Check(InfoList){
		actualpassword = InfoList.getpassword()
		
		if (!actualpassword.equal(password)) {return 0}
		
		else return 1
	}
}
