package component;

public class Bomb {
	private int x=-1;
	private int y=-1;
	private long setTime;
    private long waitTime;
    private long startExplodeTime;
    private long explodeLastTime;
    private boolean hasExploded=false;
    
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
	public long getSetTime() {
		return setTime;
	}
	public void setSetTime(long setTime) {
		this.setTime = setTime;
	}
	public long getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
	public long getStartExplodeTime() {
		return startExplodeTime;
	}
	public void setStartExplodeTime(long startExplodeTime) {
		this.startExplodeTime = startExplodeTime;
	}
	public long getExplodeLastTime() {
		return explodeLastTime;
	}
	public void setExplodeLastTime(long explodeLastTime) {
		this.explodeLastTime = explodeLastTime;
	}
	public boolean getHasExploded() {
		return hasExploded;
	}
	public void setHasExploded(boolean hasExploded) {
		this.hasExploded = hasExploded;
	}
}
