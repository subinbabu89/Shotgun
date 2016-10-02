package the.team.shotgun.driving.survey;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import the.team.shotgun.R;
import the.team.shotgun.driving.survey.callbacks.CardAnswerCallback;
import the.team.shotgun.driving.survey.callbacks.SurveyFinishedCallback;
import the.team.shotgun.driving.survey.callbacks.SwipeCallback;

/**
 * Created by Subin on 10/1/2016.
 */

public class CardAdapter extends ArrayAdapter<Card> {
    private final ArrayList<Card> cards;
    private final LayoutInflater layoutInflater;
    private final SwipeCallback swipeCallback;
    private final CardAnswerCallback cardAnswerCallback;
    private final SurveyFinishedCallback surveyFinishedCallback;

    public CardAdapter(Context context, ArrayList<Card> cards) {
        super(context, -1);
        this.swipeCallback = (SwipeCallback) context;
        this.cards = cards;
        this.layoutInflater = LayoutInflater.from(context);
        this.cardAnswerCallback = (CardAnswerCallback) context;
        this.surveyFinishedCallback= (SurveyFinishedCallback)context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Card card = cards.get(position);
        View view = layoutInflater.inflate(R.layout.question_single_answer, parent, false);
        ViewHolder viewHolder = new ViewHolder() ;

        viewHolder.survey_question_text = (AppCompatTextView) view.findViewById(R.id.survey_question_text);
        viewHolder.survey_clicked_yes = (AppCompatButton) view.findViewById(R.id.survey_clicked_yes);
        viewHolder.survey_clicked_no = (AppCompatButton) view.findViewById(R.id.survey_clicked_no);

        view.setTag(card);

        viewHolder.survey_question_text.setText(card.question);
        viewHolder.survey_clicked_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.value = Boolean.TRUE;
                cardAnswerCallback.answerSelected(card.id,card.value);
                swipeCallback.throwCard();
                if(position==cards.size()-1){
                    surveyFinishedCallback.finishSurvey();
                }

            }
        });
        viewHolder.survey_clicked_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                card.value = Boolean.FALSE;
                cardAnswerCallback.answerSelected(card.id,card.value);
                swipeCallback.throwCard();
                if(position==cards.size()-1){
                    surveyFinishedCallback.finishSurvey();
                }
            }
        });

        return view;
    }

    @Override
    public Card getItem(int position) {
        return cards.get(position);
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    public class ViewHolder{
        AppCompatTextView survey_question_text;
        AppCompatButton survey_clicked_yes;
        AppCompatButton survey_clicked_no;

    }
}
