package com.epam.pocmeetapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.pocmeetapp.Parse.ParseHelper;
import com.epam.pocmeetapp.R;
import com.epam.pocmeetapp.pojos.MeetUp;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Csaba_Bela_Nemeth on 9/29/2014.
 */
public class MyListAdapter extends BaseAdapter {

    public interface OnMeetUpDoneListener {
        void onMeetUpDone(MeetUp todo);
    }

    private OnMeetUpDoneListener listener;


    private List<MeetUp> items;
    private Context context;

    public MyListAdapter(Context context, List<MeetUp> list,
                         OnMeetUpDoneListener listener) {
        this.listener = listener;

        this.items = list;
    }

    public MyListAdapter(Context context, List<MeetUp> items) {
        super();
        this.items = items;
        this.context = context;
    }

    public void setList(List<MeetUp> list) {
        this.items = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MeetUp getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (!isChecked) {
                buttonView.setChecked(true);

            } else {
                listener.onMeetUpDone((MeetUp) buttonView.getTag());
            }
        }
    };

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder vh = null;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row, null);

            vh = new ViewHolder();
            vh.twTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
            vh.twDateStar = (TextView) convertView.findViewById(R.id.textViewStar);
            vh.twDateFinish = (TextView) convertView.findViewById(R.id.textViewFinish);
            vh.ivTheme = (ImageView) convertView.findViewById(R.id.imageViewTheme);
            vh.picture = (ParseImageView) convertView.findViewById(R.id.speaker_photo);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.twTitle.setText(items.get(position).getMeetUpTitle());
        SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        String startDate = sdt.format(items.get(position).getStart());
        vh.twDateStar.setText("Start: " + startDate);
        String finishDate = sdt.format(items.get(position).getFinish());
        vh.twDateFinish.setText("End: " + finishDate);

        final ParseImageView picture = vh.picture;
        picture.setParseFile(items.get(position).getSpeakerPicture());
        picture.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //notifyDataSetChanged();
            }
        });

        //Log.d("adapter",picture.toString() );

        if (items.get(position).getMeetTheme().equals("Android")) {
            vh.ivTheme.setImageDrawable(context.getResources().getDrawable(R.drawable.android));
        } else if (items.get(position).getMeetTheme().equals("Android-iOS")) {
            vh.ivTheme.setImageDrawable(context.getResources().getDrawable(R.drawable.ios_android));
        }else
        vh.ivTheme.setImageDrawable(context.getResources().getDrawable(R.drawable.ios));
        return convertView;
    }


    private static class ViewHolder {
        private TextView twTitle;
        private TextView twDateStar;
        private TextView twDateFinish;
        private ImageView ivTheme;
        private ParseImageView picture;
    }
}
