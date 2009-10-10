/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,  
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.event;

import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

import org.jboss.weld.BeanManagerImpl;
import org.jboss.weld.bean.RIBean;
import org.jboss.weld.introspector.WeldMethod;
import org.jboss.weld.introspector.WeldParameter;
import org.jboss.weld.transaction.spi.TransactionServices;

/**
 * Basic factory class that produces implicit observers for observer methods.
 * 
 * @author David Allen
 * 
 */
public class ObserverFactory
{
   /**
    * Creates an observer
    * 
    * @param method The observer method abstraction
    * @param declaringBean The declaring bean
    * @param manager The Bean manager
    * @return An observer implementation built from the method abstraction
    */
   public static <X, T> ObserverMethodImpl<X, T> create(WeldMethod<T, X> method, RIBean<X> declaringBean, BeanManagerImpl manager)
   {
      ObserverMethodImpl<X, T> result = null;
      TransactionPhase transactionPhase = getTransactionalPhase(method);
      if (manager.getServices().contains(TransactionServices.class) && !transactionPhase.equals(TransactionPhase.IN_PROGRESS))
      {
         result = new TransactionalObserverMethodImpl<X, T>(method, declaringBean, transactionPhase, manager);
      }
      else
      {
         result = new ObserverMethodImpl<X, T>(method, declaringBean, manager);
      }
      return result;
   }
   
   /**
    * Tests an observer method to see if it is transactional.
    * 
    * @param observer The observer method
    * @return true if the observer method is annotated as transactional
    */
   public static TransactionPhase getTransactionalPhase(WeldMethod<?, ?> observer)
   {
      WeldParameter<?, ?> parameter = observer.getAnnotatedWBParameters(Observes.class).iterator().next();
      return parameter.getAnnotation(Observes.class).during();
   }
}