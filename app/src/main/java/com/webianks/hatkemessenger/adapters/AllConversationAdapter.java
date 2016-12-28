package com.webianks.hatkemessenger.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.webianks.hatkemessenger.R;
import com.webianks.hatkemessenger.Sms;
import com.webianks.hatkemessenger.customViews.RoundedImageView;

import java.util.List;

/**
 * Created by R Ankit on 25-12-2016.
 */

public class AllConversationAdapter extends RecyclerView.Adapter<AllConversationAdapter.MyHolder> {

    private Context context;
    private List<Sms> data;
    private ItemCLickListener itemClickListener;

    public AllConversationAdapter(Context context, List<Sms> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public AllConversationAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.single_sms_small_layout, parent, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(AllConversationAdapter.MyHolder holder, int position) {

        Sms sms = data.get(position);

        holder.senderContact.setText(sms.getAddress());
        holder.message.setText(sms.getMsg());

    }

   /* public Cursor swapCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        Cursor oldCursor = dataCursor;
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }*/

    @Override
    public int getItemCount() {
        return (data == null) ? 0 : data.size();
    }


    public void setItemClickListener(ItemCLickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RoundedImageView senderImage;
        private TextView senderContact;
        private TextView message;
        private RelativeLayout mainLayout;

        public MyHolder(View itemView) {
            super(itemView);
            senderImage = (RoundedImageView) itemView.findViewById(R.id.smsImage);
            senderContact = (TextView) itemView.findViewById(R.id.smsSender);
            message = (TextView) itemView.findViewById(R.id.smsContent);
            mainLayout = (RelativeLayout) itemView.findViewById(R.id.small_layout_main);

            mainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                itemClickListener.itemClicked(getAdapterPosition(), senderContact.getText().toString());
            }

        }
    }
}
