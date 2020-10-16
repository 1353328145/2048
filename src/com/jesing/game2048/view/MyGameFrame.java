package com.jesing.game2048.view;

import com.jesing.game2048.service.HandleBox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MyGameFrame extends JFrame {
    private boolean isStart;
    private HandleBox handle;
    public MyGameFrame(){
        this.isStart=false;
        init();
    }
    public void init(){
        this.handle=new HandleBox();
        getContentPane().setBackground(Color.GRAY);//背景颜色
        setBounds(450,200,1300,800);//窗口大小
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("2048 game jesing");
        addKeyListener(new Keyboard());
    }

    class Keyboard extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode()==82){//重新开始
                handle=new HandleBox();
                isStart=false;
                repaint();
                return;
            }

            if (!isStart&&e.getKeyCode()==32){//暂停
                handle.awaken(2);
                isStart=true;
                repaint();
                return;
            }

            if (!handle.isWin()&&!handle.isEnd()&&isStart){
                switch (e.getKeyCode()){
                    case 38:handle.top();break;
                    case 40:handle.down();break;
                    case 37:handle.left();break;
                    case 39:handle.right();break;
                    default:break;
                }
                repaint();
            }
         }
    }
    @Override
    public void paint(Graphics g) {//画图方法
        super.paint(g);
        handle.draw2048(g);
        Color color = g.getColor();
        g.setColor(Color.PINK);
        g.setFont(new Font("宋体",Font.BOLD,30));
        g.drawString("按下R键重新开始",750,350);
        g.drawString("您的积分是"+handle.getScore(),750,400);
        g.drawString("历史最高是"+handle.getMaxScore(),750,450);
        g.setFont(new Font("宋体",Font.BOLD,20));
        g.drawString("游戏规则:",750,500);
        g.drawString("1.按下一个方向键，所有格子会向那个方向运动",750,520);
        g.drawString("2.相同数字的两个格子，相撞时数字会相加",750,540);
        g.drawString("3.每次滑动时，空白处会随机刷新出一个数字的格子",750,560);
        g.drawString("4.当界面不可运动时,当界面全部被数字填满时",750,580);
        g.drawString("游戏结束；当界面中最大数字是2048时，游戏胜利",750,600);
        g.setColor(color);
        if (!isStart){
            g.setColor(Color.ORANGE);
            g.setFont(new Font("宋体",Font.BOLD,50));
            g.drawString("请按下空格开始游戏",750,250);
            g.setColor(color);
        }else  if(handle.isWin()){
            g.setColor(Color.red);
            g.setFont(new Font("宋体",Font.BOLD,50));
            g.drawString("你赢了!",750,250);
            g.setColor(color);
        }else if (handle.isEnd()){
            g.setColor(Color.red);
            g.setFont(new Font("宋体",Font.BOLD,50));
            g.drawString("你输了!",750,250);
            g.setColor(color);
        }
    }
}
