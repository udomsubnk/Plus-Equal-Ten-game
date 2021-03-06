import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Random;
public class Sprite extends JLabel implements MouseListener, Runnable{
	static int maxnumber=3,maxcountclick=10+1,size =62;
	static int count=0,countclick=0,equal=10,sum=0,level=1,checklv=0,score=0,c=0;
	Random random;
	int number,numsprite;
	boolean clicked=false;
	static Sprite sp;
	int cc=0;
	Thread t = new Thread(this);
	static ArrayList spriteclick;
	public void run(){
		while(true){
			if(clicked&&sp==this){
				try {
					setIcon(new ImageIcon("pic//"+number+"-3.png"));
					t.sleep(100);
					setIcon(new ImageIcon("pic//"+number+"-2.png"));
				} catch (Exception e) {
				}
			}
			else if(clicked){
				try {
					setIcon(new ImageIcon("pic//"+number+"-2.png"));
				} catch (Exception e) {
			}
			}
			else if(cc==0)setIcon(new ImageIcon("pic//"+number+" (Custom).png"));
			try {
				t.sleep(100);
				Data.count.setText("Clicked : "+countclick);
			} catch (Exception e) {
			}
		}
	}
	public Sprite(){
		spriteclick = new ArrayList();
		t.start();
		numsprite = count;
		random = new Random();
		number = random.nextInt(maxnumber+1);
		setIcon(new ImageIcon("pic//"+number+" (Custom).png"));
		addMouseListener(this);
		setLocation(Playgame.positionX[numsprite],Playgame.positionY[numsprite]);
		setSize(size,size);
		count++;
	}
	void newran(){
		number = random.nextInt(maxnumber+1);
		setIcon(new ImageIcon("pic//"+number+" (Custom).png"));
	}
	void checkscore(){
		if(sum==equal){
			new Thread() {
				public void run() {
					try{
						Thread.sleep(300);
						Data.score.setText("Score : +"+100*countclick+"");
						score+=100*countclick;
						checklv+=100*countclick;
						Thread.sleep(500);
						reset(true,true);
						Thread.sleep(500);
						Data.score.setText("Score : "+(score));
						if(checklv>=2000){
							Playgame.lvup.setVisible(true);
							Thread.sleep(100);
							Playgame.lvup.setVisible(false);
							Thread.sleep(100);
							Playgame.lvup.setVisible(true);
							Thread.sleep(100);
							Playgame.lvup.setVisible(false);
							Thread.sleep(100);
							Playgame.lvup.setVisible(true);
							Thread.sleep(100);
							Playgame.lvup.setVisible(false);
							changetime();
							checklv=score%2000;
							level++;
							maxnumber++;
							Data.maxnum.setText(""+Sprite.maxnumber);
							for(int i=0;i<Playgame.sprite.size();i++)Playgame.sprite.get(i).newran();
							if(level%5==0){
								equal+=2;
								Data.need.setText(""+equal);
							}
						}
					}catch (Exception e) {}
				}
			}.start();
		}
		else if(sum>equal){
			new Thread() {
				public void run() {
					try{
						sum=0;
						Thread.sleep(500);
						reset(false,false);
					}catch (Exception e) {}
				}
			}.start();
		}
	}
	void changetime(){
		State.time+=15;
	}
	void reset(boolean b,boolean c){
		this.c=0;
		countclick=0;
		if(b)
			sum=0;
		Playgame.setsum(sum);
		for(int i=0;i<Playgame.n;i++){
				if(Playgame.sprite.get(i).clicked){
					if(c){
						Playgame.sprite.get(i).clicked=false;
						spriteclick.add(Playgame.sprite.get(i).numsprite);
//						Playgame.sprite.remove(i);
						Playgame.sprite.get(i).setVisible(false);
//                        
					}
					else Playgame.sprite.get(i).clicked=false;
				}
				Playgame.sprite.get(i).setIcon(new ImageIcon("pic//"+Playgame.sprite.get(i).number+" (Custom).png"));
				
		}
		new Thread() {
			public void run() {
				System.out.println(spriteclick.size());
				try{
					Thread.sleep(2000);
					for(int i=0;i<spriteclick.size();i++){
						Playgame.sprite.get((int) spriteclick.get(i)).newran();
						Playgame.sprite.get((int) spriteclick.get(i)).setVisible(true);
						
					}
					spriteclick = new ArrayList();
				}catch (Exception e) {}
			}
		}.start();
	}
	boolean checkposition(){
		//เช็คระยะห่าง
		System.out.println(Math.sqrt(Math.pow(Playgame.positionX[numsprite]-Playgame.positionX[sp.numsprite], 2)+Math.pow(Playgame.positionY[numsprite]-Playgame.positionY[sp.numsprite],2)));
		if(Math.sqrt(Math.pow(Playgame.positionX[numsprite]-Playgame.positionX[sp.numsprite], 2)+Math.pow(Playgame.positionY[numsprite]-Playgame.positionY[sp.numsprite],2))<100)return true;
		else reset(true,false);
		return false;
		//เช็คตำแหน่งของแต่ละตัว
//		if(numsprite==sp.numsprite-11||numsprite==sp.numsprite+9)return true; //left
//		else if(numsprite==sp.numsprite+11||numsprite==sp.numsprite-9)return true; //right
//		else if(numsprite==sp.numsprite-10)return true; //up
//		else if(numsprite==sp.numsprite+10)return true; //down
//		else if(numsprite==sp.numsprite-21||numsprite==sp.numsprite-1)return true;
//		else if(numsprite==sp.numsprite+1||numsprite==sp.numsprite-19)return true;
//		else if(numsprite==sp.numsprite+21||numsprite==sp.numsprite+1)return true;
//		else if(numsprite==sp.numsprite-1)return true;
//		else reset(true,false);
//		return false;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	 System.out.println(numsprite);
	}
	public void mouseExited(MouseEvent arg0) {
		cc=0;
		if(clicked)setIcon(new ImageIcon("pic//"+number+"-2.png"));
		else setIcon(new ImageIcon("pic//"+number+" (Custom).png"));
		 Playgame.setsum(sum);
	}
	public void mousePressed(MouseEvent arg0) {
		c=1;
		if(countclick+1<maxcountclick){
			if(countclick==0){
				sp=this;
				clicked=true;
				Playgame.setsum(sum+=number);
				countclick++;
			}
			else {
				if(clicked==false&&checkposition()){
					setIcon(new ImageIcon("pic//"+number+"-2.png"));
					clicked=true;
					Playgame.setsum(sum+=number);
					countclick++;
//					checkscore();
					sp=this;
				}
			}
		}
		else if(countclick+1==maxcountclick){
			if(clicked==false&&checkposition()){
				setIcon(new ImageIcon("pic//"+number+"-2.png"));
				clicked=true;
				Playgame.setsum(sum+=number);
//				checkscore();
				sp=this;
				reset(true,false);
			}
		}
		else reset(true,false);
		}
	public void mouseEntered(MouseEvent arg0) {
		cc=1;
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			if(c==1){
				if(countclick+1<maxcountclick){
				if(countclick==0){
					sp=this;
					clicked=true;
					Playgame.setsum(sum+=number);
					countclick++;
				}
				else {
					if(clicked==false&&checkposition()){
						setIcon(new ImageIcon("pic//"+number+"-2.png"));
						clicked=true;
						Playgame.setsum(sum+=number);
						countclick++;
//						checkscore();
						sp=this;
					}
				}
				}
				else if(countclick+1==maxcountclick){
				if(clicked==false&&checkposition()){
					setIcon(new ImageIcon("pic//"+number+"-2.png"));
					clicked=true;
					Playgame.setsum(sum+=number);
//					checkscore();
					sp=this;
					reset(true,false);
				}
			}
			else {
				reset(true,false);
			}
			}
			else if(clicked==false)Playgame.setsum(sum+number);
			setIcon(new ImageIcon("pic//"+number+"-2.png"));
	}
	public void mouseReleased(MouseEvent arg0) {
		c=0;
		checkscore();
	}
}
