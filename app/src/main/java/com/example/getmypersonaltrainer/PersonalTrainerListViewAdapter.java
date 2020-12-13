package com.example.getmypersonaltrainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

class PersonalTrainerListViewAdapter extends FirebaseRecyclerAdapter <PersonalTrainer, PersonalTrainerListViewAdapter.PersonalTrainerListViewHolder> {



    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PersonalTrainerListViewAdapter(@NonNull FirebaseRecyclerOptions<PersonalTrainer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PersonalTrainerListViewAdapter.PersonalTrainerListViewHolder holder, int position, @NonNull PersonalTrainer model) {
        holder.id.setText(model.getUserId());
        holder.name.setText(model.getName());
        if (model.getVoteQuantity() > 0){
            holder.score.setText(model.takeAverageScore());
        }

        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.presenter.setGoBack(AllPersonalTrainerListResultActivity.class);
                MainActivity.presenter.setGoingTo(AllPersonalTrainerListResultActivity.class);
                MainActivity.presenter.setGetInfoFromDatabase(true);

                if(MainActivity.presenter.getUser() instanceof Client) {
                    InvitationMessage invitationMessage = new InvitationMessage(
                            MainActivity.presenter.getUser().getUserId(),
                            model.getUserId(),
                            MainActivity.presenter.getUser().getName(),
                            UserTypes.CLIENT,
                            ((Client) MainActivity.presenter.getUser()).getPhone()
                    );

                    MainActivity.presenter.getModel().sendInvitationMessage(
                            invitationMessage
                    );
                }
                Intent intent = new Intent(MainActivity.presenter.getActualActivity(), LoadingActivity.class);
                MainActivity.presenter.getActualActivity().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public PersonalTrainerListViewAdapter.PersonalTrainerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personal_trainer, parent, false);
        return new PersonalTrainerListViewHolder(view);
    }

    public class PersonalTrainerListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView id;
        TextView score;
        Button send;

        public PersonalTrainerListViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.text_id_personal_trainer);
            name = itemView.findViewById(R.id.text_name_personal_trainer);
            score = itemView.findViewById(R.id.text_score_personal_trainer);
            send= itemView.findViewById(R.id.button_send_invitation);
        }


    }
}
