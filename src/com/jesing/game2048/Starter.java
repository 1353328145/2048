package com.jesing.game2048;

import com.jesing.game2048.view.MyGameFrame;

import javax.swing.*;

public class Starter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyGameFrame::new);
    }
}
