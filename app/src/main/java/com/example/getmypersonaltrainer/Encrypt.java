package com.example.getmypersonaltrainer;

/*
   This code is from https://gist.github.com/lfalin/0ee8d2e4a2ede1e7eec225f5b7edc163

 */

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Random;
import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Arrays;
import java.util.Base64;

public class Encrypt {
   private static final Random RANDOM = new SecureRandom();
   private static final int ITERATIONS = 10000;
   private static final int KEY_LENGTH = 256;

   private static byte[] getNextSalt() {
      byte[] salt = new byte[16];
      RANDOM.nextBytes(salt);
      return salt;
   }

   private static byte[] getHash(char[] password, byte[] salt) throws Exception {

      PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
      Arrays.fill(password, Character.MIN_VALUE);

      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] hash = skf.generateSecret(spec).getEncoded();

      return hash;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public static void hashUserPassword(User user) throws Exception {

      // Get the next random salt value to use for this password
      byte[] salt = getNextSalt();
      char[] password = user.getPassword().toCharArray();

      // Once we've generated the hash, clear the old password
      // from memory for security purposes
      byte[] hash = getHash(password, salt);
      Arrays.fill(password, Character.MIN_VALUE);
      user.setPassword("");

      if(hash != null) {

         // By Base64-encoding the raw bytes, we can store them as strings.
         // This allows us to save the values to a file or database if needed.
         // For more information on Base64 encoding, see:
         // http://stackoverflow.com/questions/201479/what-is-base-64-encoding-used-for
         // https://en.wikipedia.org/wiki/Base64
         String hashString = Base64.getEncoder().encodeToString(hash);
         String saltString = Base64.getEncoder().encodeToString(salt);

         user.setHashedPassword(hashString);
         user.setSalt(saltString);
      }
      else
      {
         user.setHashedPassword(null);
         user.setSalt(null);
      }
   }

   /**
    * This function uses the password and salt in the {@link User} to generate a hash,
    * then compares that hash to the original hash value.
    * @param user The user whose password needs to be hashed.
    * @return Whether or not the password values match.
    * @exception Exception If there is a problem with the chosen hash function.
    */
   @RequiresApi(api = Build.VERSION_CODES.O)
   public static Boolean verifyPassword(User user) throws Exception {

      // Have to get the raw data values to use on our hash function
      char[] password = user.getPassword().toCharArray();
      byte[] salt = Base64.getDecoder().decode(user.getSalt());

      //Erase User password again
      user.setPassword("");

      // Generate the new hash, and retrieve the user's hash
      byte[] expectedHash = getHash(password, salt);
      byte[] userHash = Base64.getDecoder().decode(user.getHashedPassword());

      // If the new hash came out as null, or the lengths don't match,
      // we know that the original password is different
      if(expectedHash == null || expectedHash.length != userHash.length)
         return false;

      // Check each byte of the two hashes and as soon as we find one
      // that is different, we know that the passwords don't match
      for(int i = 0; i < expectedHash.length; i++) {
         if(expectedHash[i] != userHash[i])
            return false;
      }

      // If we got this far, it means the password hashes match, so we
      // can assume the passwords do as well.
      return true;
   }

}
