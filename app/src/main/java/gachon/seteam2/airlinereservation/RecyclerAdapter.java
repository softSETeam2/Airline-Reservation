package gachon.seteam2.airlinereservation;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    //item list
    private ArrayList<RecyclerItem> searchList = new ArrayList();
    private int index;
    private String airlineStr, sourceStr, destinationStr, dateStr, arrivalTimeStr, departureTimeStr;

    public void switchLayout(int position) {
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }


    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    //layoutinflater : recycler_item을 inflate시켜주는거
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(view);
    }

    //item을 하나하나 보여주는(=bind) 함수
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(searchList.get(position));
    }

    //recyclerView의 총 개수
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    //item을 추가했을 때 쓰는 함수
    void addItem(RecyclerItem data) {
        searchList.add(data);
    }

    //subView 세팅해주는거
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView airpalne;
        TextView source;
        TextView destination;
        TextView date;
        TextView arrival;
        TextView departure;

        ViewHolder(View itemView) {
            super(itemView);

            airpalne = itemView.findViewById(R.id.postTitleTextView);
            source = itemView.findViewById(R.id.sourceview);
            destination = itemView.findViewById(R.id.destinationview);
            date = itemView.findViewById(R.id.dateTextView1);
            arrival = itemView.findViewById(R.id.arrivalTime);
            departure = itemView.findViewById(R.id.departureTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener == null) {
                            System.out.println("NUll");
                        }
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }

                    Intent intent2 = new Intent(v.getContext(), PayActivity.class);
                    intent2.putExtra("airline", airlineStr);
                    intent2.putExtra("source", sourceStr);
                    intent2.putExtra("destination", destinationStr);
                    intent2.putExtra("date", dateStr);
                    intent2.putExtra("arrivalTime", arrivalTimeStr);
                    intent2.putExtra("departureTime", departureTimeStr);
                    v.getContext().startActivity(intent2);
                }
            });
        }

        void onBind(RecyclerItem data) {
            airlineStr = data.getairplane();
            Log.d("MainActivity", airlineStr);
            airpalne.setText(data.getairplane());
            dateStr = data.getodate();
            date.setText(data.getodate());
            sourceStr = data.getsource();
            source.setText(data.getsource());
            destinationStr = data.getdestination();
            destination.setText(data.getdestination());
            arrivalTimeStr = data.getarrivalTime();
            arrival.setText(data.getarrivalTime());
            departureTimeStr = data.getdepartureTime();
            departure.setText(data.getdepartureTime());

            Log.d("MainActivity", airlineStr + " " + dateStr + " " + sourceStr + " " + destinationStr + " " + arrivalTimeStr + " " + departureTimeStr);
        }
    }
}
