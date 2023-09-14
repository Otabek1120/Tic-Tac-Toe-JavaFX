/**
 * This interface must be implemented by any observer that wants
 * to be notified of ant change to the state of the model.
 * 
 */
package model;

public interface OurObserver {
   void update(Object theObserved);
}
