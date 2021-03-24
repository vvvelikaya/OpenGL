package lab1_opengl;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

class Renderer {
	
	private static GLWindow window =null;
	private static String TITLE = "Rabbit";  // window's title
    private static final int WINDOW_WIDTH = 550;  // width of the drawable
    private static final int WINDOW_HEIGHT = 550; // height of the drawable
    private static final int FPS = 60; // frames per second
	
	public static float unitsW = 4; //units for scaling


    public static int getHeight() {
		return window.getHeight();
	}

	public static int getWidth() {
		return window.getWidth();
	}


	public static void main(String[] args) {
	
	  	GLProfile profile = GLProfile.get(GLProfile.GL2);
    	GLCapabilities caps = new GLCapabilities(profile);
    	
    	window = GLWindow.create(caps);
    	final FPSAnimator animator = new FPSAnimator(window, FPS, true);
    	 window.addWindowListener(new WindowAdapter() {
            
             public void windowDestroyNotify(WindowEvent arg0) {
                 // Use a dedicate thread to run the stop() to ensure that the
                 // animator stops before program exits.
                 new Thread() {
                   
                     public void run() {
                         if (animator.isStarted()) {
                        	 animator.stop();    // stop the animator loop
                         }
                            
                         System.exit(0);
                     }
                 }.start();
             }
         });
    	
    	window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    	window.setTitle(TITLE);
    	window.addGLEventListener(new EventListener());
    	window.addKeyListener(new KeyInput());
    	
    	window.setVisible(true);

    	
    	animator.start();
    	
	}
}
