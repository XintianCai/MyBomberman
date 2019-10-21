package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class EntityPanel extends JPanel{
	BufferedImage image;
	private int gridType=0;
	private int actionType=0;
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	protected void paintComponent(Graphics g) {
		if (image != null) {
			super.paintComponent(g);
			if(gridType==4){
				if(actionType==0){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 0, 20, 100, 100,null);
				}
				else if(actionType==1){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 95, 20, 195, 100,null);
				}
				else if(actionType==2){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 190, 20, 290, 100,null);
				}
				else if(actionType==3){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 285, 20, 385, 100,null);
				}
			}
			else if(gridType==5){
				if(actionType==0){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 0, 308, 100, 388,null);
				}
				else if(actionType==1){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 95, 308, 195, 388,null);
				}
				else if(actionType==2){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 190, 308, 290, 388,null);
				}
				else if(actionType==3){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 285, 308, 385, 388,null);
				}
			}
			else if(gridType==6){
				if(actionType==0){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 0, 115, 100, 195,null);
				}
				else if(actionType==1){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 95, 115, 195, 195,null);
				}
				else if(actionType==2){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 190, 115, 290, 195,null);
				}
				else if(actionType==3){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 285, 115, 385, 195,null);
				}
			}
			else if(gridType==7){
				if(actionType==0){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 0, 210, 100, 290,null);
				}
				else if(actionType==1){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 95, 210, 195, 290,null);
				}
				else if(actionType==2){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 190, 210, 290, 290,null);
				}
				else if(actionType==3){
					g.drawImage(image,0, 0, getWidth(),getHeight(), 285, 210, 385, 290,null);
				}
			}
			else{
				g.drawImage(image,0,0, getWidth(), getHeight(), new Color(0,0,0,0), null);
			}
		}
	}

	public int getGridType() {
		return gridType;
	}

	public void setGridType(int gridType) {
		this.gridType = gridType;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
}
