package component;

public class Bomberman{
	int action;
	private int x,y;
	
	public void up(){
		setY(getY()-1);
	}
	public void down(){
		setY(getY()+1);
	}
	public void left(){
		setX(getX()-1);
	}
	public void right(){
		setX(getX()+1);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
