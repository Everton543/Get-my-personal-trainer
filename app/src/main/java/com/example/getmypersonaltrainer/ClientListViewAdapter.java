package com.example.getmypersonaltrainer;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class ClientListViewAdapter extends FirebaseRecyclerAdapter<Client,
        ClientListViewAdapter.ClientListViewHolder>{
   private Activity activity = null;
   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    *
    * @param options
    */
   public ClientListViewAdapter(@NonNull FirebaseRecyclerOptions<Client> options, Activity activity) {
      super(options);
      this.activity = activity;
   }

   @Override
   protected void onBindViewHolder(@NonNull ClientListViewHolder holder, int position, @NonNull final Client model) {
      String name = model.getName();
      String id = model.getUserId();

      holder.userID.setText(id);
      holder.userName.setText(name);

      if(position % 2 != 0){
         holder.background.setBackgroundColor(ContextCompat.getColor(activity, R.color.light_list_separate));
      }

      holder.seeClientInfoButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            MainActivity.presenter.setChangingClient(model);
            Intent intent = new Intent(MainActivity.presenter.getActualActivity(), ClientInfoActivity.class);
            MainActivity.presenter.getActualActivity().startActivity(intent);
         }
      });

   }

   @NonNull
   @Override
   public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.my_client, parent, false);
      return new ClientListViewHolder(view);
   }

   public static class ClientListViewHolder extends RecyclerView.ViewHolder {
      TextView userID;
      TextView userName;
      ConstraintLayout background;
      Button seeClientInfoButton;

      public ClientListViewHolder(@NonNull View itemView) {
         super(itemView);
         userID = itemView.findViewById(R.id.client_id_my_client);
         userName = itemView.findViewById(R.id.client_name_my_client);
         background = itemView.findViewById(R.id.background_my_client_layout);
         seeClientInfoButton = itemView.findViewById(R.id.button_see_client_info);
      }
   }

}