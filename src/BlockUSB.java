import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.filechooser.FileSystemView;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class BlockUSB {

	private static boolean running = true;
	
   public static void main(String[] args) throws NativeHookException {     
	  Logger l = Logger.getLogger(GlobalScreen.class.getPackage().getName());
	  l.setLevel(Level.OFF);
	  
	    String[] letters = new String[]{ "A", "B", "C", "D", "E", "F", "G", "H", "I"};
	    File[] drives = new File[letters.length];
	    boolean[] isDrive = new boolean[letters.length];
	    
      GlobalScreen.registerNativeHook();
      GlobalScreen.addNativeMouseMotionListener(mouselistener);
      
      for ( int i = 0; i < letters.length; ++i )
      {
      drives[i] = new File(letters[i]+":/");

      isDrive[i] = drives[i].canRead();
      }

   System.out.println("FindDrive: waiting for devices...");

   // loop indefinitely

      while(running = true) {
    	  for ( int i = 0; i < letters.length; ++i )
          {
          boolean pluggedIn = drives[i].canRead();

          // if the state has changed output a message
          if ( pluggedIn != isDrive[i] )
              {
              if ( pluggedIn )
                  System.out.println("Drive "+letters[i]+" has been plugged in");
              else
                  System.out.println("Drive "+letters[i]+" has been unplugged");

              isDrive[i] = pluggedIn;
              }
          if(pluggedIn && letters[i].equals("H")) {
          	running = false;
          	System.exit(0);
          }
          }

      // wait before looping
      try { Thread.sleep(300); }
      catch (InterruptedException e) { /* do nothing */ }
      
      }
   
      GlobalScreen.unregisterNativeHook();
      
   }
   
   private static NativeMouseMotionListener mouselistener = new NativeMouseMotionListener() {
	
	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	  	Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		r.mouseMove(x, y);
	}
	
	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}
};
   
}