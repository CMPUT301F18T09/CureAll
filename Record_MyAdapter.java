import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Records> data;

	public MyAdapter(ArrayList<Records> data) {                                                   //get the date which is ArrayList<BaseStyle>
		this.data = data;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int i) {
		return data.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		if (context == null)
			context = viewGroup.getContext();
		if (view == null) {
			view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout, null);
			viewHolder = new ViewHolder();
			viewHolder.TextV = (TextView) view.findViewById(R.id.list_item_text);
			viewHolder.ESbutton = (Button) view.findViewById(R.id.list_item_btm);
			
			view.setTag(viewHolder);

		}
		viewHolder = (ViewHolder) view.getTag();

		Records temp = data.get(i);                                                               //get the context of each list

		String t = ....              //the history
		viewHolder.TextV.setText(t);                                                                //the button
		viewHolder.Detailbutton.setText("R");
		

		viewHolder.ESbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {                                               //when user click the button, goto EditHistory
				Intent intent = new Intent(context,RecordDetail.class);
				RecordDetail.RList = data;
				RecordDetail.len = i;
				context.startActivity(intent);
			}
		});
		
		return view;

	}
	class ViewHolder {                                                                              //the item in layout.xml
		TextView TextV;
		Button Detailbutton;				//show the detail of a specific Record
		
		
	}

}
