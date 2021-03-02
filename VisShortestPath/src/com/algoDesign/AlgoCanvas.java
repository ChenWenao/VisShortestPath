package com.algoDesign;

import javax.swing.*;
import java.awt.*;


public class AlgoCanvas extends JPanel {

    private int canvasWidth;
    private int canvasHeight;
    private MazeData data;   //迷宫数据

    public void setData(MazeData data) {
        this.data = data;
    }

    public MazeData getData() {
        return data;
    }

    public AlgoCanvas(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.setMaximumSize(new Dimension(canvasWidth, canvasHeight));
        System.out.println(canvasWidth);
        System.out.println(canvasHeight);
    }


    public void render(MazeData data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        if (data == null) {

        } else {

            int w = canvasWidth / data.getM();
            int h = canvasHeight / (data.getN() + 3);

            // 具体绘制
            for (int i = 0; i < data.getN(); i++) {
                for (int j = 0; j < data.getM(); j++) {
                    if (data.getMaze(i, j) == '1')
                        g2d.setColor(new Color(173, 216, 230, 255));

                    else
                        g2d.setColor(new Color(255, 255, 255, 255));

                    if (data.path[i][j])
                        AlgoVisHelper.setColor(g2d, Color.YELLOW);
                    AlgoVisHelper.fillRectangle(g2d, j * w, i * h, w, h);
                }
            }
        }


    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1440, 1080);
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getGetCanvasHeight() {
        return canvasHeight;
    }
}
