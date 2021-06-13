package gachon.seteam2.airlinereservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlightReservationUserInfoAdapter {

    //item list
    private ArrayList<FlightReservationUserInfoItem> searchList = new ArrayList();
    private int index;

    public void switchLayout(int position) {
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }


    // 리스너 객체 참조를 저장하는 변수
    private FlightReservationUserInfoAdapter.OnItemClickListener mListener = null;

    public void setOnItemClickListener(FlightReservationUserInfoAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    public FlightReservationUserInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.flight_reservation_user_info_item, parent, false);
        return new FlightReservationUserInfoAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull FlightReservationUserInfoAdapter.ViewHolder holder, int position) {
        holder.onBind(searchList.get(position));
    }

    public int getItemCount() {
        return searchList.size();
    }

    //item을 추가했을 때 쓰는 함수
    void addItem(FlightReservationUserInfoItem data) {
        searchList.add(data);
    }

    //subView 세팅해주는거
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView seatClass;
        TextView customerName;
        TextView customerEmail;

        ViewHolder(View itemView) {
            super(itemView);

            seatClass = itemView.findViewById(R.id.seatClass);
            customerName = itemView.findViewById(R.id.customerName);
            customerEmail = itemView.findViewById(R.id.customerEmail);

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
                }
            });

        }

        void onBind(FlightReservationUserInfoItem data) {
            seatClass.setText(data.getSeatClass());
            customerName.setText(data.getCustomerName());
            customerEmail.setText(data.getCustomerEmail());

        }
    }
}
