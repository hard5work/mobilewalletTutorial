package anish.tutorial.mobilewallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapters extends RecyclerView.Adapter<ListAdapters.ListHolders> {

    List<ColumnNames> cols;
    Context c;

    public ListAdapters(List<ColumnNames> cols, Context c) {
        this.cols = cols;
        this.c = c;
    }

    @NonNull
    @Override
    public ListHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ListHolders( LayoutInflater.from(c).inflate(R.layout.statement_list, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolders holder, int position) {
        ColumnNames co = cols.get(position);
        holder.pb.setText(String.valueOf(co.getPb()));
        holder.cb.setText(String.valueOf(co.getCb()));
        holder.remarks.setText(co.getRemarks());
        holder.au.setText(String.valueOf(co.getAdd()));
        holder.dat.setText(co.getDate());


    }

    @Override
    public int getItemCount() {
        return cols.size();
    }

    public class ListHolders extends RecyclerView.ViewHolder{
        TextView cb,pb,au,dat,remarks;

        public ListHolders(@NonNull View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.currbl);
            pb = itemView.findViewById(R.id.prevbl);
            au = itemView.findViewById(R.id.addused);
            dat = itemView.findViewById(R.id.datetime);
            remarks = itemView.findViewById(R.id.rema);

        }
    }


}
