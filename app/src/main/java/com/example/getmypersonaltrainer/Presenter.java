package com.example.getmypersonaltrainer;

public class Presenter {
   private Model model;
   private boolean logged;
   private UserInterface user;


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

   public UserInterface getUser() {
      return user;
   }

   public void setUser(UserInterface user) {
      this.user = user;
   }
}
