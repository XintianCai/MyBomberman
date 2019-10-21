package listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameListener implements KeyListener{
	private int action=-1;
	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			setAction(0);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			setAction(1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			setAction(2);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			setAction(3);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			setAction(4);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_PAUSE) {
			setAction(5);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
