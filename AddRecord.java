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
	
	
	ArrayList<Records> RList = new ArrayList<Records>()
	
	Records newRecord = new Records();
	
	public load(){}//RList now containt all Records
	public void getInput(){
		newRecord.setTitle(XXX.getInput())
		newRecord.setComment(XXX.getInput())
		newRecord.setDate(/*timeDialog*/)
		
	}
	
	public void gallery(View view) {
		//startActivityForResult
		newRecord.setTrackPhoto()
	}
	public void camera(View view) {
		//Intent with file back
		newRecord.setTrackPhoto()
	}
	public void Body(){
		onSaveInstanceState()
		Intent.Body
		onRestoreInstanceState()
	}
	public void Location(){
		onSaveInstanceState()
		Intent.Map
		Map.type = 2
		onRestoreInstanceState()
	}
	
	public void Save(){
		RList.add(newRecord);
		SaveTo();
	}
}


}
