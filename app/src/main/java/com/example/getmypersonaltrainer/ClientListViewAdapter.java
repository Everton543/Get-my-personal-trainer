package com.example.getmypersonaltrainer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ClientListViewAdapter extends RecyclerView.Adapter<
      ClientListViewAdapter.ClientListViewHolder> {
   PersonalTrainer personalTrainer;
   Context context;


   public static class ClientListViewHolder extends RecyclerView.ViewHolder {
      TextView userID;
      TextView userAge;
      TextView userBodyMass;
      TextView userName;
      TextView userPhone;
      Button addExercise;
      Button changeExercise;
      Button removeClient;
      public ClientListViewHolder(@NonNull View itemView) {
         super(itemView);
         userID = itemView.findViewById(R.id.client_id_my_client);
         userAge = itemView.findViewById(R.id.client_age_my_client);
         userBodyMass = itemView.findViewById(R.id.client_body_mass_my_client);
         userName = itemView.findViewById(R.id.client_name_my_client);
         userPhone = itemView.findViewById(R.id.client_phone_my_client);

         addExercise = itemView.findViewById(R.id.button_add_new_exercise_client_list);
         changeExercise = itemView.findViewById(R.id.button_change_exercise_client_list);
         removeClient = itemView.findViewById(R.id.button_remove_client_client_list);
      }
   }

   public ClientListViewAdapter(Context context, PersonalTrainer personalTrainer){
      this.personalTrainer = personalTrainer;
      this.context = context;
   }

   @NonNull
   @Override
   public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View view = inflater.inflate(R.layout.my_client, parent, false);
      return new ClientListViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ClientListViewHolder holder, final int position) {
      String bodyMass = String.valueOf(personalTrainer.getClients().get(position).getBodyMass());
      String age = String.valueOf(personalTrainer.getClients().get(position).getAge());
      String phone = personalTrainer.getClients().get(position).getPhone();
      String name = personalTrainer.getClients().get(position).getName();
      String id = personalTrainer.getClients().get(position).getUserId();

      holder.userID.setText(id);
      holder.userBodyMass.setText(bodyMass);
      holder.userAge.setText(age);
      holder.userPhone.setText(phone);
      holder.userName.setText(name);

      holder.addExercise.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context, CreateExerciseActivity.class);
            String index = String.valueOf(position);
            intent.putExtra("index", index);
            context.startActivity(intent);
         }
      });

   }

   @Override
   public int getItemCount() {
      return personalTrainer.getClients().size();
   }
}
