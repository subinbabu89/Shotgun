package the.team.shotgun.carpool;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import the.team.shotgun.R;

/**
 * Created by Subin on 10/2/2016.
 */

public class RiderAdapter extends ArrayAdapter<Rider> {

    private ArrayList<Rider> riders;
    private final LayoutInflater layoutInflater;

    public RiderAdapter(Context context, ArrayList<Rider> riders) {
        super(context, -1);
        this.riders = riders;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Rider getItem(int position) {
        return riders.get(position);
    }

    @Override
    public int getCount() {
        return riders.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Rider rider = riders.get(position);
        View view = layoutInflater.inflate(R.layout.rider_layout_item, parent, false);
        RiderAdapter.ViewHolder viewHolder = new RiderAdapter.ViewHolder() ;

        viewHolder.txtv_rider_name = (AppCompatTextView) view.findViewById(R.id.txtv_rider_name);
        viewHolder.txtv_rider_phonenumber = (AppCompatTextView) view.findViewById(R.id.txtv_rider_phonenumber);

        view.setTag(rider);

        viewHolder.txtv_rider_name.setText(rider.name);
        viewHolder.txtv_rider_phonenumber.setText(String.valueOf(rider.phonenumber));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rider g = ((Rider)v.getTag());
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("'tel:"+g.phonenumber+"'"));
                getContext().startActivity(phoneIntent);
            }
        });
        return view;
    }

    public class ViewHolder {
        AppCompatTextView txtv_rider_name;
        AppCompatTextView txtv_rider_phonenumber;
    }
}
