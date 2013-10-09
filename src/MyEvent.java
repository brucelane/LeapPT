import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

class MyEvent extends EventObject {
  public MyEvent(Object source) {
    super(source);
  }
}
