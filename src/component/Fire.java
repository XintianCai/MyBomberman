package component;

public class Fire {
	private int x=0;
	private int y=0;
	private long startFireTime;
	private long fireTime;
	
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
	public int randomFire(){
		int direction=(int)(Math.random()*4);
		return direction;
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
}
