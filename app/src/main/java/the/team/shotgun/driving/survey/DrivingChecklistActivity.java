package the.team.shotgun.driving.survey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.arjsna.swipecardlib.SwipeCardView;
import the.team.shotgun.R;
import the.team.shotgun.driving.SelectDrivingDestinationActivity;
import the.team.shotgun.driving.garage.FindGarageActivity;
import the.team.shotgun.driving.survey.callbacks.CardAnswerCallback;
import the.team.shotgun.driving.survey.callbacks.SurveyFinishedCallback;
import the.team.shotgun.driving.survey.callbacks.SwipeCallback;

public class DrivingChecklistActivity extends AppCompatActivity implements SwipeCallback,CardAnswerCallback,SurveyFinishedCallback {

    private ArrayList<Card> al;
    private CardAdapter arrayAdapter;
    SwipeCardView swipeCardView;
    HashMap<String,String> answerMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_cards);

        al = new ArrayList<>();
        answerMap = new HashMap<>();
        getDummyData(al);
        arrayAdapter = new CardAdapter(this, al);

        swipeCardView= (SwipeCardView) findViewById(R.id.frame);
        swipeCardView.setAdapter(arrayAdapter);
        swipeCardView.setFlingListener(new SwipeCardView.OnCardFlingListener() {
            @Override
            public void onCardExitLeft(Object dataObject) {
                Log.i("TAG", "Left Exit");
            }

            @Override
            public void onCardExitRight(Object dataObject) {
                Log.i("TAG", "Right Exit");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Log.i("TAG", "Adater to be empty");
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                Log.i("TAG", "Scroll");
            }

            @Override
            public void onCardExitTop(Object dataObject) {
                Log.i("TAG", "Top Exit");
            }

            @Override
            public void onCardExitBottom(Object dataObject) {
                Log.i("TAG", "Bottom Exit");
            }
        });
    }



    private void getDummyData(ArrayList<Card> al) {
        Card card = new Card();
        card.question = "Is the oil level appropriate in the car?";
        card.id = "1";
        al.add(card);

        Card card2 = new Card();
        card2.question = "Is the coolant level appropriate in the car?";
        card2.id = "2";
        al.add(card2);

        Card card3 = new Card();
        card3.question = "Is the air pressure normal in the car?";
        card3.id = "3";
        al.add(card3);

        Card card4 = new Card();
        card4.question = "Are the lights working in the car?";
        card4.id = "4";
        al.add(card4);

        Card card5 = new Card();
        card5.question = "Are the vipers working in the car?";
        card5.id = "5";
        al.add(card5);
    }

    @Override
    public void throwCard() {
        swipeCardView.throwBottom();
    }

    @Override
    public void answerSelected(String key, boolean value) {
        answerMap.put(key,String.valueOf(value));
        Iterator it = answerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(!Boolean.parseBoolean(pair.getValue().toString())){
                startActivity(new Intent(DrivingChecklistActivity.this, FindGarageActivity.class));
            }
            it.remove(); // avoids a ConcurrentModificationException
        }

    }

    @Override
    public void finishSurvey() {
        startActivity(new Intent(DrivingChecklistActivity.this, SelectDrivingDestinationActivity.class));
    }
}
