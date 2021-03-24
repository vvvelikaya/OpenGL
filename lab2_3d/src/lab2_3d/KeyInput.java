package lab2_3d;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

public class KeyInput implements KeyListener {

	static int n = 0;
	static boolean f = false;

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (f)
				n = n + 2;
			if (Math.abs(EventListener.angleV) != Math.abs(90.0 * n + 90.0)) {
				EventListener.angleV += 3.0f;
				System.out.println("if: " + EventListener.angleV + " n: " + n);
			} else {
				EventListener.e = -EventListener.e;
				EventListener.angleV += 3.0f;
				System.out.println("else: " + EventListener.angleV + " n: " + n);
				n = n + 2;
			}
			f = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!f)
				n = n - 2;
			if (Math.abs(EventListener.angleV) != Math.abs(90.0 * n + 90.0)) {
				EventListener.angleV -= 3.0f;
				System.out.println("if: " + EventListener.angleV + " n: " + n);
			} else {
				EventListener.e = -EventListener.e;
				EventListener.angleV -= 3.0f;
				System.out.println("else: " + EventListener.angleV + " n: " + KeyInput.n);
				n = n - 2;
			}
			f = true;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			EventListener.angleH += 3.0f;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			EventListener.angleH -= 3.0f;
		} else if (e.getKeyCode() == KeyEvent.VK_P) {
			EventListener.p = false;
		} else if (e.getKeyCode() == KeyEvent.VK_O) {
			EventListener.p = true;
		}

	}

	public void keyReleased(KeyEvent arg0) {
		//
	}

}
