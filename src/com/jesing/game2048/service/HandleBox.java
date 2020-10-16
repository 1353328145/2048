package com.jesing.game2048.service;

import com.jesing.game2048.object.GameBox;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * 游戏对象
 */
public class HandleBox {
    private GameBox[]gameBox;
    private List<Integer> unactivated;
    private Random random;
    private Properties pps;
    private int score;
    private int maxScore;

    public Properties getPps() {
        return pps;
    }

    public void setPps(Properties pps) {
        this.pps = pps;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public HandleBox(){
        this.score=0;
        pps = new Properties();
        unactivated=new ArrayList<>();
        this.random=new Random();
        for (int i = 0; i < 16; i++) {
            unactivated.add(i);
        }
        this.gameBox=new GameBox[16];
        for (int i = 0; i < gameBox.length; i++) {
            gameBox[i]=new GameBox(i);
        }
        initMax();
    }
    public void initMax(){
        FileInputStream inStream = null;
        try {
             inStream = new FileInputStream("src/com/jesing/game2048/max.properties");
            pps.load(inStream);
        } catch (IOException e) {
            System.out.println("找不到文件");
            System.exit(0);
        }
        String max = pps.getProperty("max");
        this.maxScore=Integer.valueOf(max);
        try {
            inStream.close();
        } catch (IOException e) {
            System.out.println("关闭失败");
        }
    }
    public GameBox[] getGameBox() {
        return gameBox;
    }
    public List<Integer> getUnactivated() {
        return this.unactivated;
    }
    public void draw2048(Graphics g){
        for (int i = 0; i < gameBox.length; i++) {
            gameBox[i].paintSelf(g);
        }
    }
    public void left(){
        for (int i = 0; i <= 12; i+=4) {
            for (int j = i+1; j <= i+3; j++) {
                if (!gameBox[j].isStatus()){
                    continue;
                }
                for (int k = j-1; k >= i; k--) {
                    if (handle(k+1,k)){
                        break;
                    }
                }
            }
        }
        awaken(1);
    }
    public void right(){
        for (int i = 0; i <= 12; i+=4) {
            for (int j = i+2; j >= i; j--) {
                if (!gameBox[j].isStatus()){
                    continue;
                }
                for (int k = j+1; k <= i+3; k++) {
                    if (handle(k-1,k)){
                        break;
                    }
                }
            }
        }
        awaken(1);
    }
    public void top(){
        for (int i = 0; i < 4; i++) {
            for (int j = i+4; j <= 12+i; j+=4) {
                if (!gameBox[j].isStatus()){
                    continue;
                }
                for (int k = j-4; k>=0; k-=4) {
                    if (handle(k+4,k)){
                        break;
                    }
                }
            }
        }
        awaken(1);
    }
    public void down(){
        for (int i = 0; i < 4; i++) {
            for (int j = i+8; j >= 0; j-=4) {
                if (!gameBox[j].isStatus()){
                    continue;
                }
                for (int k = j+4; k <= 12+i; k+=4) {
                    if (handle(k-4,k)){
                        break;
                    }
                }
            }
        }
        awaken(1);
    }

    /**
     * 如果k空闲  则x与k交换状态
     * 如果x与k相等 则释放x 分数加到k上
     * 否则返回true
     * @param x
     * @param k
     * @return
     */
    public boolean handle(int x,int k){
        if (!gameBox[k].isStatus()){
            gameBox[k].setStatus(true);
            gameBox[k].setScore(gameBox[x].getScore());
            unactivated.remove((Integer) k);
            unactivated.add(x);
            gameBox[x].sleep();
        }else if (gameBox[k].getScore()==gameBox[x].getScore()){
            int sum=gameBox[k].getScore()+gameBox[x].getScore();
            this.score+=sum;
            gameBox[k].setScore(sum);
            unactivated.add(x);
            gameBox[x].sleep();
            return true;
        }else{
            return true;
        }
        return false;
    }
    /**
     * 随机唤醒num个格子
     * @param num
     */
    public void awaken(int num){
        HashSet<Integer> set=new HashSet<>();
        while (set.size()!=num){
            int a=random.nextInt(unactivated.size());
            if (!set.contains(unactivated.get(a))){
                set.add(unactivated.get(a));
                unactivated.remove(a);
            }
        }
        for (Integer integer : set) {
            gameBox[integer].awaken();
        }
    }
    public void updateScore(){
        if (this.score>maxScore){
            OutputStream os = null;
            try {
                os=new FileOutputStream("src/com/jesing/game2048/max.properties");
            } catch (FileNotFoundException e) {
                System.out.println("更新失败");
            }
            pps.setProperty("max", String.valueOf(this.score));
            try {
                pps.store(os,"max");
            } catch (IOException e) {
                System.out.println("更新失败");
            }finally {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("关闭失败");
                }
            }
        }
    }
    /**
     * 游戏是否结束
     * @return
     */
    public boolean isEnd(){
        if (unactivated.size()==0){
            updateScore();
            return true;
        }
        return false;
    }
    /**
     * 游戏是否获胜
     */
    public boolean isWin(){
        for (int i = 0; i < 16; i++) {
            if (gameBox[i].getScore()==2048){
                updateScore();
                return true;
            }
        }
        return false;
    }
}
