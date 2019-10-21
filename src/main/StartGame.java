package main;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import component.Bomb;
import component.Bomberman;
import component.EntityPanel;
import component.Fire;
import component.Monster;

import java.util.ArrayList;

import listener.GameListener;

public class StartGame extends Thread {
	JFrame frame =new JFrame();
	JPanel panel1=new JPanel();
	JLabel label1=new JLabel("");
	JLabel label2=new JLabel("");
	EntityPanel[][] entityPanel=new EntityPanel[10][10];
	BufferedImage wallPic,monsterPic,bombermanPic,bombPic,explosionPic,firePic;
	Bomberman bomberman=new Bomberman();
	ArrayList<Monster> monsterList=new ArrayList<Monster>();
	ArrayList<Bomb> bombList=new ArrayList<Bomb>();
	ArrayList<Fire> fireList=new ArrayList<Fire>();
	int[] cannotMove=new int[4];
	GameListener gameListener=new GameListener();
	int action,isOutofRange=0,monsterNumber=3,monsterSpeed=2;
	long startTime,endTime,usedTime,actionTime,changeActionTime,gameTime;
	boolean gameResult;
	
	public void run(){
		System.out.println("Initializing the game...");
		//Set frame
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e){
		    	System.exit(0);
            }
		});
		frame.setTitle("Bomberman Version 1");
		frame.setLayout(new GridLayout(11, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//initialize panels
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				entityPanel[i][j]=new EntityPanel();
				entityPanel[i][j].setPreferredSize(new Dimension(30, 30));
				entityPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.blue));
			}
		}
		
		//initialize images
		initImage();
		
		//set images to panels
		setImage();
		
		//add panels to the frame
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 10; i++) {
				frame.add(entityPanel[i][j]);
			}
		}
		
		//panel for counting time an monster
		panel1.add(label1);
		panel1.add(label2);
		frame.add(panel1);
		frame.pack();
		frame.setSize(900,900);
		frame.setVisible(true);
		frame.addKeyListener(gameListener);
		
		//start play
		startPlay();
	}
	public void startPlay(){
		System.out.println("Game Start");
		//get current time
		startTime =  System.currentTimeMillis();
		changeActionTime=  System.currentTimeMillis();
		gameTime=System.currentTimeMillis();
		
		//start iteration
		while(true){
			//update player's location
			setPlayerAction();
			
			//update monsters' locations every second
			if(timingMonsterAction()>=monsterSpeed){
				setMonsterAction();
			}
			
			//make animation of the role
			changeRoleAction();
			
			//count time for bombs to explode
			timingBombAction();
			
			//update explosion 
			updateExplosion();
			
			//count time for explosion
			timingExplosionAction();
			
			//count time for fire
			timingFireAction();
			
			//end explosion
			endExplosion();
			
			//judge if the player is closed to the monster and set fire
			detectPlayer();
			
			//end fire
			endFire();
			
			//display game time
			displayTime();
			
			//display number of monsters
			displayMonsterNumber();
			
			//judge if bobmer man is killed or burned
			if(isKilled()){
				gameResult=false;
				break;
			}
			
			//judge if monsters are killed
			if(isBombed()){
				clearMonster();
			}
			
			//judge if there are remaining monsters
			if(!hasMonster()){
				gameResult=true;
				break;
			}
		}
		//jump to the endGame interface
		endGame();
	}
	private void initImage(){
		try {
			wallPic = ImageIO.read(new FileInputStream("src//image//wall.png"));
			monsterPic = ImageIO.read(new FileInputStream("src//image//monster.png"));
			bombermanPic = ImageIO.read(new FileInputStream("src//image//player.png"));
			bombPic = ImageIO.read(new FileInputStream("src//image//bomb.jpg"));
			explosionPic = ImageIO.read(new FileInputStream("src//image//explosion.png"));
			firePic = ImageIO.read(new FileInputStream("src//image//fire.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setImage(){
		
		entityPanel[0][6].setImage(wallPic);
		entityPanel[0][6].setGridType(2);
		entityPanel[1][6].setImage(wallPic);
		entityPanel[1][6].setGridType(2);
		for(int j=3;j<6;j++){
			entityPanel[2][j].setImage(wallPic);
			entityPanel[2][j].setGridType(2);
		}
		entityPanel[3][0].setImage(wallPic);
		entityPanel[3][0].setGridType(2);
		entityPanel[3][5].setImage(wallPic);
		entityPanel[3][5].setGridType(2);
		entityPanel[3][8].setImage(wallPic);
		entityPanel[3][8].setGridType(2);
		entityPanel[3][9].setImage(wallPic);
		entityPanel[3][9].setGridType(2);
		entityPanel[5][3].setImage(wallPic);
		entityPanel[5][3].setGridType(2);
		entityPanel[5][4].setImage(wallPic);
		entityPanel[5][4].setGridType(2);
		entityPanel[6][8].setImage(wallPic);
		entityPanel[6][8].setGridType(2);
		entityPanel[7][8].setImage(wallPic);
		entityPanel[7][8].setGridType(2);
		for(int j=0;j<6;j++){
			entityPanel[8][j].setImage(wallPic);
			entityPanel[8][j].setGridType(2);
		}
		entityPanel[8][8].setImage(wallPic);
		entityPanel[8][8].setGridType(2);
		
		//randomly generate locations of monsters and the player
		randomMonster();
		randomPlayer();
	}
	private void randomMonster(){
		int x,y;
 
		for(int i=0;i<monsterNumber;i++){
			while(true){
	    		x=(int)(Math.random()*10);
	        	y=(int)(Math.random()*10);
	    		if(entityPanel[x][y].getGridType()!=2){
	        		break;
	        	}
	    	}
	    	entityPanel[x][y].setGridType(3);
			entityPanel[x][y].setImage(monsterPic);
			
			Monster monster=new Monster();
			monster.setX(x);
			monster.setY(y);
			monsterList.add(monster);
		}
	}
    private void randomPlayer(){
    	int x,y;
    	while(true){
    		x=(int)(Math.random()*10);
        	y=(int)(Math.random()*10);
    		if(entityPanel[x][y].getGridType()!=2||entityPanel[x][y].getGridType()!=3){
    			break;
        	}
    	}
    	entityPanel[x][y].setGridType(4);
		entityPanel[x][y].setImage(bombermanPic);
		bomberman.setX(x);
		bomberman.setY(y);
	}
	private void updateBomberman(){
		//judge if there are bombs
		if(bombList.size()!=0){
			for (Bomb bomb : bombList) {
				// judge if the bomberman and the bomb are on the same location
				if (bomberman.getX() == bomb.getX()
						&& bomberman.getY() == bomb.getY()) {
					entityPanel[bomberman.getX()][bomberman.getY()]
							.setImage(bombermanPic);
				} 
				else {
					if(bomb.getHasExploded()){
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(bombermanPic);
					}
					else{
						entityPanel[bomb.getX()][bomb.getY()].setImage(bombPic);
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(bombermanPic);
					}
				}
			}
		}
		else{
			entityPanel[bomberman.getX()][bomberman.getY()].setImage(bombermanPic);
		}
	}
	private void updateMonster(){
		if(monsterList.size()!=0){
			for(Monster monster:monsterList){
				entityPanel[monster.getX()][monster.getY()].setImage(monsterPic);
				entityPanel[monster.getX()][monster.getY()].setGridType(3);
			}
			frame.repaint();
		}
	}
	private void updateExplosion() {
		if(bombList.size()!=0){
			for (Bomb bomb : bombList) {
				if (bomb.getWaitTime() == 2) {
					entityPanel[bomb.getX()][bomb.getY()].setImage(explosionPic);
					entityPanel[bomb.getX()][bomb.getY()].setGridType(1);
					if(bomb.getX()-1>=0){
						entityPanel[bomb.getX()-1][bomb.getY()].setImage(explosionPic);
						entityPanel[bomb.getX()-1][bomb.getY()].setGridType(1);
					}
					if(bomb.getY()-1>=0){
						entityPanel[bomb.getX()][bomb.getY()-1].setImage(explosionPic);
						entityPanel[bomb.getX()][bomb.getY()-1].setGridType(1);
					}
					if(bomb.getX()+1<=9){
						entityPanel[bomb.getX()+1][bomb.getY()].setImage(explosionPic);
						entityPanel[bomb.getX()+1][bomb.getY()].setGridType(1);
					}
					if(bomb.getY()+1<=9){
						entityPanel[bomb.getX()][bomb.getY()+1].setImage(explosionPic);
						entityPanel[bomb.getX()][bomb.getY()+1].setGridType(1);
					}
					bomb.setHasExploded(true);
					bomb.setStartExplodeTime(System.currentTimeMillis());
				}
			}
			frame.repaint();
		}
	}
	private void clearMonster(){
		if(monsterList.size()!=0){
			ArrayList<Monster> tempList=new ArrayList<Monster>();
			for(Monster monster:monsterList){
				tempList.add(monster);
			}
			for(Monster monster:tempList){
				if(monster.getIskilled()){
					entityPanel[monster.getX()][monster.getY()].setImage(null);
					monsterList.remove(monster);
				}
			}
		}
	}
	private void endExplosion(){
		if(bombList.size()!=0){
			ArrayList<Bomb> tempList=new ArrayList<Bomb>();
			for(Bomb bomb:bombList){
				tempList.add(bomb);
			}
			for (Bomb bomb : tempList) {
				if(bomb.getHasExploded()){
					if (bomb.getExplodeLastTime() == 1) {
						entityPanel[bomb.getX()][bomb.getY()].setImage(null);
						entityPanel[bomb.getX()][bomb.getY()].setGridType(0);
						if(bomb.getX()-1>=0){
							entityPanel[bomb.getX()-1][bomb.getY()].setImage(null);
							entityPanel[bomb.getX()-1][bomb.getY()].setGridType(0);
						}
						if(bomb.getY()-1>=0){
							entityPanel[bomb.getX()][bomb.getY()-1].setImage(null);
							entityPanel[bomb.getX()][bomb.getY()-1].setGridType(0);
						}
						if(bomb.getX()+1<=9){
							entityPanel[bomb.getX()+1][bomb.getY()].setImage(null);
							entityPanel[bomb.getX()+1][bomb.getY()].setGridType(0);
						}
						if(bomb.getY()+1<=9){
							entityPanel[bomb.getX()][bomb.getY()+1].setImage(null);
							entityPanel[bomb.getX()][bomb.getY()+1].setGridType(0);
						}
						bombList.remove(bomb);
					}
				}
			}
			frame.repaint();
		}
	}
	private void endFire(){
		if(fireList.size()!=0){
			ArrayList<Fire> tempList=new ArrayList<Fire>();
			for(Fire fire:fireList){
				tempList.add(fire);
			}
			for(Fire fire:tempList){
				if(fire.getFireTime()>=2){
					entityPanel[fire.getX()][fire.getY()].setImage(null);
					entityPanel[fire.getX()][fire.getY()].setGridType(0);
					fireList.remove(fire);
				}
			}
		} 
	}
	private long timingMonsterAction(){
		endTime =  System.currentTimeMillis();
		usedTime = (endTime-startTime)/1000;
		for(Monster monster:monsterList){
			monster.setFireTime((System.currentTimeMillis()-monster.getStartFireTime())/1000);
		}
		return usedTime;
	}
	private void timingBombAction(){
		if(bombList.size()!=0){
			for(Bomb bomb:bombList){
				bomb.setWaitTime((endTime-bomb.getSetTime())/1000);
			}
		}
	}
	private void timingExplosionAction(){
		if(bombList.size()!=0){
			for(Bomb bomb:bombList){
				if(bomb.getHasExploded()){
					bomb.setExplodeLastTime((endTime-bomb.getStartExplodeTime())/1000);
				}
			}
		}
	}
	private long timingRoleAction(){
		actionTime=System.currentTimeMillis()-changeActionTime;
		return actionTime;
	}
	private void timingFireAction(){
		if(fireList.size()!=0){
			for(Fire fire:fireList){
				fire.setFireTime((System.currentTimeMillis()-fire.getStartFireTime())/1000);
			}
		}
	}
	private void setPlayerAction(){
		while(true){
			action=gameListener.getAction();
			if(action!=5){
				if(action!=-1){
					entityPanel[bomberman.getX()][bomberman.getY()].setImage(null);
					
					if(action==1&&(bomberman.getY()-1)>=0&&entityPanel[bomberman.getX()][bomberman.getY()-1].getGridType()!=2){
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(null);
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(0);
						bomberman.up();	
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(5);
					}
					else if(action==2&&(bomberman.getY()+1)<=9&&entityPanel[bomberman.getX()][bomberman.getY()+1].getGridType()!=2){
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(null);
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(0);
						bomberman.down();
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(4);
					}
					else if(action==3&&(bomberman.getX()-1)>=0&&entityPanel[bomberman.getX()-1][bomberman.getY()].getGridType()!=2){
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(null);
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(0);
						bomberman.left();
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(6);
					}
					else if(action==4&&(bomberman.getX()+1)<=9&&entityPanel[bomberman.getX()+1][bomberman.getY()].getGridType()!=2){
						entityPanel[bomberman.getX()][bomberman.getY()].setImage(null);
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(0);
						bomberman.right();
						entityPanel[bomberman.getX()][bomberman.getY()].setGridType(7);
					}
					else if(action==0){
						Bomb bomb=new Bomb();
						bomb.setX(bomberman.getX());
						bomb.setY(bomberman.getY());
						bomb.setSetTime(System.currentTimeMillis());
						bombList.add(bomb);
					}
					gameListener.setAction(-1);
					
					updateBomberman();
					frame.repaint();
				}
				break;
			}
		}
	}
	private void setMonsterAction(){
		for(Monster monster:monsterList){
			entityPanel[monster.getX()][monster.getY()].setImage(null);
			entityPanel[monster.getX()][monster.getY()].setGridType(0);
		}
		
		for (Monster monster:monsterList) {
			while (isOutofRange == 0) {
				switch (monster.randomMove()) {
				case 0:
					if (monster.getY() - 1 >= 0&&entityPanel[monster.getX()][monster.getY()-1].getGridType()!=2&&entityPanel[monster.getX()][monster.getY()-1].getGridType()!=8) {
						isOutofRange = 1;
						for(Monster mons:monsterList){
							if(monster.getY()-1==mons.getY()&&monster.getX()==mons.getX()){
								isOutofRange = 0;
							}
						}
						if(isOutofRange==1){
							monster.up();
						}
					} else { 
						isOutofRange = 0;
					}
					if(isOutofRange==0){
						cannotMove[0]=1;
					}
					break;
				case 1:
					if (monster.getY() + 1 <= 9&&entityPanel[monster.getX()][monster.getY()+1].getGridType()!=2&&entityPanel[monster.getX()][monster.getY()+1].getGridType()!=8) {
						isOutofRange = 1;
						for(Monster mons:monsterList){
							if(monster.getY()+1==mons.getY()&&monster.getX()==mons.getX()){
								isOutofRange = 0;
							}
						}
						if(isOutofRange==1){
							monster.down();
						}
					} else {
						isOutofRange = 0;
					}
					if(isOutofRange==0){
						cannotMove[1]=1;
					}
					break;
				case 2:
					if (monster.getX() - 1 >= 0&&entityPanel[monster.getX()-1][monster.getY()].getGridType()!=2&&entityPanel[monster.getX()-1][monster.getY()].getGridType()!=8) {
						isOutofRange = 1;
						for(Monster mons:monsterList){
							if(monster.getY()==mons.getY()&&monster.getX()-1==mons.getX()){
								isOutofRange = 0;
							}
						}
						if(isOutofRange==1){
							monster.left();
						}
					} else {
						isOutofRange = 0;
					}
					if(isOutofRange==0){
						cannotMove[2]=1;
					}
					break;
				case 3:
					if (monster.getX() + 1 <= 9&&entityPanel[monster.getX()+1][monster.getY()].getGridType()!=2&&entityPanel[monster.getX()+1][monster.getY()].getGridType()!=8) {
						isOutofRange = 1;
						for(Monster mons:monsterList){
							if(monster.getY()==mons.getY()&&(monster.getX()+1)==mons.getX()){
								isOutofRange = 0;
							}
						}
						if(isOutofRange==1){
							monster.right();
						}
					} else {
						isOutofRange = 0;
					}
					if(isOutofRange==0){
						cannotMove[3]=1;
					}
					break;
				default:
					break;
				}
				if(cannotMove[0]==1&&cannotMove[1]==1&&cannotMove[2]==1&&cannotMove[3]==1){
					isOutofRange=1;
				}
			}
			for(int i=0;i<4;i++){
				cannotMove[i]=0;
			}
			isOutofRange=0;
		}
		
		startTime =  System.currentTimeMillis();
		updateMonster();
	}
	private void changeRoleAction(){
		try {
			Thread.sleep(100);
			int actionType=entityPanel[bomberman.getX()][bomberman.getY()].getActionType();
			if(actionType==3){
				actionType=0;
			}
			else{
				actionType++;
			}
			entityPanel[bomberman.getX()][bomberman.getY()].setActionType(actionType);
			entityPanel[bomberman.getX()][bomberman.getY()].setImage(bombermanPic);
			frame.repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void detectPlayer(){
		boolean judgeOverlap=true;
		
		for(Monster monster:monsterList){
			if(Math.abs(monster.getX()-bomberman.getX())<=2&&Math.abs(monster.getY()-bomberman.getY())<=2){
				if(monster.getFireTime()>=monster.getCoolDown()){
					Fire fire=new Fire();
					while(judgeOverlap){
						int randomFire=fire.randomFire();
						if(randomFire==0){
							if(monster.getY()-1>=0){
								for(Monster mons:monsterList){
									if(mons.getX()!=monster.getX()||mons.getY()!=(monster.getY()-1)){
										fire.setX(monster.getX());
										fire.setY(monster.getY()-1);
										judgeOverlap=false;
									}
								}
							}
						}
						else if(randomFire==1){
							if(monster.getY()+1<=9){
								for(Monster mons:monsterList){
									if(mons.getX()!=monster.getX()||mons.getY()!=(monster.getY()+1)){
										fire.setX(monster.getX());
										fire.setY(monster.getY()+1);
										judgeOverlap=false;
									}
								}
							}
						}
						else if(randomFire==2){
							if(monster.getX()-1>=0){
								for(Monster mons:monsterList){
									if(mons.getX()!=(monster.getX()-1)||mons.getY()!=monster.getY()){
										fire.setX(monster.getX()-1);
										fire.setY(monster.getY());
										judgeOverlap=false;
									}
								}
							}
						}
						else if(randomFire==3){
							if(monster.getX()+1<=9){
								for(Monster mons:monsterList){
									if(mons.getX()!=(monster.getX()+1)||mons.getY()!=monster.getY()){
										fire.setX(monster.getX()+1);
										fire.setY(monster.getY());
										judgeOverlap=false;
									}
								}
							}
						}
					}
					fire.setStartFireTime(System.currentTimeMillis());
					fire.setFireTime(0);
					monster.setFireTime(0);
					monster.setStartFireTime(System.currentTimeMillis());
					fireList.add(fire);
					entityPanel[fire.getX()][fire.getY()].setImage(firePic);
					entityPanel[fire.getX()][fire.getY()].setGridType(8);
				}
			}
		}
	}
	private boolean isKilled(){
		boolean iskilled=false;
		for(Monster monster:monsterList){
			if(monster.getX()==bomberman.getX()&&monster.getY()==bomberman.getY()){
				iskilled=true;
			}
		}
		for(Fire fire:fireList){
			if(bomberman.getX()==fire.getX()&&bomberman.getY()==fire.getY()){
				iskilled=true;
			}
		}
		if(entityPanel[bomberman.getX()][bomberman.getY()].getGridType()==1){
			iskilled=true;
		}
		return iskilled;
	}
	private boolean isBombed(){
		boolean isbombed=false;
		for(Monster monster:monsterList){
			if(entityPanel[monster.getX()][monster.getY()].getGridType()==1){
				monster.setIskilled(true);
				isbombed=true;
			}
		}
		return isbombed;
	}
	private boolean hasMonster(){
		if(monsterList.size()==0)
			return false;
		else
			return true;
	}
	private void displayTime(){
		long playTime=(System.currentTimeMillis()-gameTime)/1000;
		label1.setText("Time: "+playTime+" s");
	}
	private void displayMonsterNumber(){
		label2.setText("Monster: "+monsterList.size());
	}
	private void endGame(){
		frame.removeAll();
		frame.dispose();
		
		EndGame endgame=new EndGame();
		endgame.run(gameResult);
		
		while(true){
			if(endgame.restart){
				StartGame startGame=new StartGame();
				startGame.start();
				break;
			}
		}
	}
}

