package com.example.getmypersonaltrainer;

/**
 * author Everton Alves
 */
public class Encrypt {
   public static void hashUserPassword(User user){
      String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
      user.setHashedPassword(hashed);
      user.setPassword("");
   }

   public static Boolean verifyPassword(User user){
      boolean result = BCrypt.checkpw(user.getPassword(), user.getHashedPassword());
      user.setPassword("");
      return result;
   }
}
