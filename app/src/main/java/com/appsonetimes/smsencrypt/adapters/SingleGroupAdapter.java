package com.appsonetimes.smsencrypt.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.appsonetimes.smsencrypt.R;
import com.appsonetimes.smsencrypt.utils.ColorGeneratorModified;
import com.appsonetimes.smsencrypt.utils.Helpers;

/**
 * Created by R Ankit on 25-12-2016.
 */

public class SingleGroupAdapter extends RecyclerView.Adapter<SingleGroupAdapter.MyViewHolder> {

    private ColorGeneratorModified generator;
    private Context context;
    private Cursor dataCursor;
    private int color;
    private static final int sent_item = 2;
    private static final int receive_item = 1;

    public SingleGroupAdapter(Context context, Cursor dataCursor, int color) {

        this.context = context;
        this.dataCursor = dataCursor;
        this.color = color;

        if (color == 0)
            generator = ColorGeneratorModified.MATERIAL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resource;

        if (viewType == receive_item) {
            resource = R.layout.single_sms_detailed;
        } else {
            resource = R.layout.item_message_sent;
        }


        View view = inflater.inflate(resource, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        dataCursor.moveToPosition(position);

        holder.message.setText(dataCursor.getString(dataCursor.getColumnIndexOrThrow("body")));

        long time = dataCursor.getLong(dataCursor.getColumnIndexOrThrow("date"));
        holder.time.setText(Helpers.getDate(time));


        String name = Helpers.getName(context, dataCursor.getString(dataCursor.getColumnIndexOrThrow("address")));
        String firstChar = String.valueOf(name.charAt(0));

        if (color == 0){
            if (generator!=null)
                color = generator.getColor(name);
        }

        TextDrawable drawable = TextDrawable.builder().buildRound(firstChar, color);
        holder.image.setImageDrawable(drawable);

    }
    @Override
    public int getItemViewType(int position) {

        dataCursor.moveToPosition(position);

        int type = dataCursor.getInt(dataCursor.getColumnIndex("type"));

        if (type == sent_item) {
            return sent_item;
        }else {
            return receive_item;
        }

    }


    public void swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return;
        }
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
    }



    @Override
    public int getItemCount() {
        return (dataCursor == null) ? 0 : dataCursor.getCount();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView message;
        private ImageView image;
        private TextView time;

        MyViewHolder(View itemView) {
            super(itemView);

            message =  itemView.findViewById(R.id.message);
            image =  itemView.findViewById(R.id.smsImage);
            time =  itemView.findViewById(R.id.time);

        }

    }
}
