import javax.swing.border.*;
import java.awt.*;

abstract class OverAllDetail {
	String Title
	String Comment
	Date date
	
	public OverAllDetail(String Title, String Comment,Date date){
		this.Title = Title
		this.Comment = Comment
		this.date = date
	}
	
	public getTitle(){}
	public getComment(){}
	public getDate(){}
	
	public setTitle(String Title){}
	public setComment(String Comment){}
	public setDate(Date date){}
}