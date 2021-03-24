package lab1_opengl;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyInput implements KeyListener {

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_W) {
			EventListener.xx += 0.1;
		} else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_S) {
			EventListener.xx -= 0.1;
		}

	}

	public void keyReleased(KeyEvent arg0) {
		//
	}

}
