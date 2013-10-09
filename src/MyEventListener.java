import java.util.EventListener;



interface MyEventListener extends EventListener {
  public void myEventOccurred(MyEvent evt, String tCheev);
}
