package com.example.getmypersonaltrainer;

import java.util.HashMap;

public interface ClientInterface {
   float getBodyMass();
   String getBirthDate();
   float getSize();
   String getPhone();

   void setBirthDate(String birthDate);
   void setSize(float size);
   void setBodyMass(float bodyMass);
   void setPhone(String phone);
}
