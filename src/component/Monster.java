package component;

public class Monster{
private int action;
private boolean iskilled=false;
private long fireTime=3;
private long startFireTime;
private int coolDown=3;
private int x,y;

	public int randomMove(){
		int direction=(int)(Math.random()*4);
		return direction;
	}
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
	public boolean getIskilled() {
		return iskilled;
	}
	public void setIskilled(boolean iskilled) {
		this.iskilled = iskilled;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public long getFireTime() {
		return fireTime;
	}
	public void setFireTime(long fireTime) {
		this.fireTime = fireTime;
	}
	public long getStartFireTime() {
		return startFireTime;
	}
	public void setStartFireTime(long startFireTime) {
		this.startFireTime = startFireTime;
	}
	public int getCoolDown() {
		return coolDown;
	}
	public void setCoolDown(int coolDown) {
		this.coolDown = coolDown;
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
