package the.team.shotgun.driving.garage;

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

public class GarageAdapter extends ArrayAdapter<Garage> {

    private ArrayList<Garage> garages;
    private final LayoutInflater layoutInflater;

    public GarageAdapter(Context context,ArrayList<Garage> garages) {
        super(context, -1);
        this.garages = garages;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public Garage getItem(int position) {
        return garages.get(position);
    }

    @Override
    public int getCount() {
        return garages.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Garage garage = garages.get(position);
        View view = layoutInflater.inflate(R.layout.garage_layout_item, parent, false);
        GarageAdapter.ViewHolder viewHolder = new GarageAdapter.ViewHolder() ;

        viewHolder.txtv_garage_name = (AppCompatTextView) view.findViewById(R.id.txtv_garage_name);

        view.setTag(garage);

        viewHolder.txtv_garage_name.setText(garage.garage_name);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Garage g = ((Garage)v.getTag());
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+g.garage_src_lat+","+g.garage_src_lon+"&avoid=tf");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                getContext().startActivity(mapIntent);
            }
        });
//        viewHolder.survey_clicked_yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                card.value = Boolean.TRUE;
//                cardAnswerCallback.answerSelected(card.id,card.value);
//                swipeCallback.throwCard();
//                if(position==cards.size()-1){
//                    surveyFinishedCallback.finishSurvey();
//                }
//
//            }
//        });
//        viewHolder.survey_clicked_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                card.value = Boolean.FALSE;
//                cardAnswerCallback.answerSelected(card.id,card.value);
//                swipeCallback.throwCard();
//                if(position==cards.size()-1){
//                    surveyFinishedCallback.finishSurvey();
//                }
//            }
//        });

        return view;
    }

    public class ViewHolder {
        AppCompatTextView txtv_garage_name;
    }
}
