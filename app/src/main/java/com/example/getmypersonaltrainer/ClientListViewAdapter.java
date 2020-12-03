package com.example.getmypersonaltrainer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.TypeAdapterFactory;

public class ClientListViewAdapter extends RecyclerView.Adapter<
      ClientListViewAdapter.ClientListViewHolder> {

   private static final String TAG = "ClientAdapter";
   public PersonalTrainer getPersonalTrainer() {
      return personalTrainer;
   }

   public void setPersonalTrainer(PersonalTrainer personalTrainer) {
      this.personalTrainer = personalTrainer;
   }

   private PersonalTrainer personalTrainer;
   private final Context context;

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
         Log.i(TAG, "holder constructor");
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
      Log.i(TAG, "adapter constructor");
      this.personalTrainer = personalTrainer;
      this.context = context;
   }

   @NonNull
   @Override
   public ClientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      Log.i(TAG, "called onCreateViewHolder");
      LayoutInflater inflater = LayoutInflater.from(context);
      View view = inflater.inflate(R.layout.my_client, parent, false);
      return new ClientListViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ClientListViewHolder holder, final int position) {
      Log.i(TAG, "called onBindViewHolder");
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
            Log.i(TAG, "Add exercise setOnclickListener");
            if(context instanceof PersonalTrainerMainActivity){
               Log.i(TAG, "Setting error to true");
               ((PersonalTrainerMainActivity) context)
                     .setRecyclerViewLoadingError(true);
            }

            Intent intent = new Intent(context, CreateExerciseActivity.class);
            String index = String.valueOf(position);
            intent.putExtra("index", index);
            context.startActivity(intent);
         }
      });

      holder.removeClient.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Log.i(TAG, "remove client setOnclickListener");
            personalTrainer.getClients().get(position).setPersonalTrainerId(null);
            MainActivity.presenter.getModel().updateClient(personalTrainer.getClients().get(position));
            if(context instanceof PersonalTrainerMainActivity){
               ((PersonalTrainerMainActivity) context).removeClient(position);
            }
         }
      });

      holder.changeExercise.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(context instanceof PersonalTrainerMainActivity){
               ((PersonalTrainerMainActivity) context)
                     .setRecyclerViewLoadingError(true);
            }
            Log.i(TAG, "changeExercise setOnclickListener");
            Intent intent = new Intent(context, ClientExerciseListActivity.class);
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
