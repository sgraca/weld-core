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

package org.jboss.webbeans.context.api;

import javax.enterprise.context.spi.Contextual;


/**
 * Interface for different implementations of Contextual instance storage.
 * 
 * @author Nicklas Karlsson
 * 
 */
public interface BeanStore
{
   /**
    * Gets an instance of a contextual from the store
    * 
    * @param contextual The contextual whose instance is to be return
    * @return The instance. Null if not found
    */
   public abstract <T> ContexutalInstance<T> get(Contextual<? extends T> contextual);

   /**
    * Clears the store of contextual instances
    */
   public abstract void clear();

   /**
    * Returns an iteration over the current contextual instances in the store
    * 
    * @return the iteration
    */
   public abstract Iterable<Contextual<?>> getContextuals();

   /**
    * Adds a bean instance to the storage
    * 
    * @param contextualInstance the contextual instance
    * @return The instance added
    */
   public abstract <T> void put(ContexutalInstance<T> contextualInstance);
}