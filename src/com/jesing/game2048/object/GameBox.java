package com.jesing.game2048.object;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

/**
 * 默认盒子宽度是135 缝隙20
 */

/**
 *              600
 *       135 20 135 20 135 20 135
 *       20  -------------------
 *       135 |      |       |
 * 600   20  |      |       |
 *       135 |      |       |
 *       20  -------------------
 *       135
 */
public class GameBox {
    private static int[][]map={
            {100,100},{250,100},{400,100},{550,100},
            {100,250},{250,250},{400,250},{550,250},
            {100,400},{250,400},{400,400},{550,400},
            {100,550},{250,550},{400,550},{550,550},
    };
    private static HashMap<Integer,Color> colorMap=new HashMap();
    private Random random;
    private int index;//位置坐标
    private boolean status;
    private int score;
    private Font numFont;
    public GameBox(int index){
        this.random=new Random();
        this.index=index;
        colorMap.put(2,Color.ORANGE);
        colorMap.put(4,Color.BLUE);
        colorMap.put(8,Color.PINK);
        colorMap.put(16,Color.CYAN);
        colorMap.put(32,Color.GREEN);
        colorMap.put(64,Color.MAGENTA);
        colorMap.put(128,Color.PINK);
        colorMap.put(256,Color.ORANGE);
        colorMap.put(512,Color.YELLOW);
        colorMap.put(1024,Color.PINK);
        colorMap.put(2048,Color.red);
        this.numFont=new Font("宋体",Font.BOLD,40);
        this.status=false;
    }
    public void paintSelf(Graphics g){
        Color color = g.getColor();
        if (status){
            g.setColor(Color.WHITE);
            g.fillOval(map[index][0],map[index][1],135,135);
            g.setColor(colorMap.get(score));
            g.setFont(numFont);
            g.drawString(score+"",map[index][0]+20,map[index][1]+70);
            g.setColor(color);
        }else{
            g.setColor(Color.BLACK);
            g.fillOval(map[index][0],map[index][1],135,135);
            g.setColor(color);
        }
    }

    /**
     * 将空格关闭
     */
    public void awaken(){
        this.status=true;
        this.score=random.nextInt(2)==1?2:4;
    }

    /**
     * 关闭空格
     */
    public void sleep(){
        this.status=false;
        this.score=0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "status=" + status +
                ", score=" + score +
                '}';
    }
}
