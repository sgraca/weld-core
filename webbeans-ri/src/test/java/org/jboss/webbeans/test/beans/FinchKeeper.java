package org.jboss.webbeans.test.beans;

import javax.webbeans.Observes;
import javax.webbeans.RequestScoped;

import org.jboss.webbeans.test.beans.StarFinch.Mess;

@RequestScoped
public class FinchKeeper
{
   public FinchKeeper()
   {
   }

   public boolean newMessDetected = false;

   public void observesMesses(@Observes Mess aNewMess)
   {
      newMessDetected = true;
   }
}
