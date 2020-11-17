package com.example.getmypersonaltrainer;

import android.widget.EditText;

import java.io.Serializable;

public class Presenter implements Serializable {
   private Model model;
   private boolean logged;

   Presenter(){
      model = new Model();
      logged = false;
   }

   public Model getModel() {
      return model;
   }

   public void setModel(Model model) {
      this.model = model;
   }

   public boolean isLogged() {
      return logged;
   }

   public void setLogged(boolean logged) {
      this.logged = logged;
   }
}
