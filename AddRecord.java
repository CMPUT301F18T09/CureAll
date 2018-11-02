import java.util.*;
import java.awt.*;
import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//https://blog.csdn.net/weixin_37577039/article/details/79186183?utm_source=blogxgwz5
//https://blog.csdn.net/w18756901575/article/details/52085157?utm_source=blogxgwz4
//https://blog.csdn.net/u012796139/article/details/50084517
class AddRecord {
	 // 拍照回传码
	public final static int CAMERA_REQUEST_CODE = 0;
		// 相册选择回传吗
	public final static int GALLERY_REQUEST_CODE = 1;
	// 拍照的照片的存储位置
	private String mTempPhotoPath;
		// 照片所在的Uri地址
	private Uri imageUri;
	
	//the list of record use to store the record history
	ArrayList<Records> RList = new ArrayList<Records>()
	//the record which user want to add
	Records newRecord = new Records();
	//read history record from file
	public load(){}//RList now containt all Records
	//get user text input
	public void getInput(){
		newRecord.setTitle(XXX.getInput())
		newRecord.setComment(XXX.getInput())
		newRecord.setDate(/*timeDialog*/)
		
	}
	//if user tap gallery button
	//after user back to this activity, show the selected photo.
	public void gallery(View view) {
		//startActivityForResult
		newRecord.setTrackPhoto()
		showphoto()
	}
	//if user tap camera button
	//after user back to this activity, show the selected photo.
	public void camera(View view) {
		//Intent with file back
		newRecord.setTrackPhoto()
			showphoto()
	}
	//if user tap body button
	//after user back to this activity, show the selected photo.
	public void Body(){
		onSaveInstanceState()
		Intent.Body
		onRestoreInstanceState()//restore activity avoid losing info
		showphoto()
	}
	//if user tap location button
	//after user back to this cativity, show the selected location
	public void Location(){
		onSaveInstanceState()
		Intent.Map
		Map.type = 2
		onRestoreInstanceState()
		showLocation()
	}
	//if user tap the Save button, save the new list, and go back to previous activity
	public void Save(){
		RList.add(newRecord);
		SaveTo();
	}
}


}
