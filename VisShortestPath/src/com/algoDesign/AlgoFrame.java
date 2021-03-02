package com.algoDesign;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class AlgoFrame extends JFrame {
    //页面宽度和页面高度。
    private int frameWidth;  // 记录保存。
    private int frameHeight;
    private AlgoCanvas algoCanvas;
    private static int delay = 100;
    //暂停标志符。
    public boolean isPause;
    //存储最短路径。
    private ShortestPath shortestPath;
    //存储迷宫数据。
    private MazeData data;
    private  MazeGeneration mazeGeneration;

    public MazeData getData() {
        return data;
    }

    public void setData(MazeData data) {
        this.data = data;
    }

    public AlgoFrame(String title, int frameWidth, int frameHeight) {
        super(title);
        initUI(frameWidth, frameHeight);
        shortestPath = new ShortestPath(this);
        mazeGeneration = new MazeGeneration();
    }

    //界面初始化。
    private void initUI(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.setContentPane(setupAllWidget());
        this.pack();
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private JPanel setupAllWidget() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(frameWidth, frameHeight));


        AlgoCanvas canvas = new AlgoCanvas(getFrameWidth() - 480, getFrameHeight());
        canvas.setBackground(new Color(40, 45, 52, 255));
        this.algoCanvas = canvas;

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setMaximumSize(new Dimension(480, 1080));
        controlPanel.setBackground(new Color(112, 128, 114, 255));

        // 在这里添加最短路径的算法的项目
        String[] listData = new String[]{"回溯法"};
        JComboBox<String> algorithms = new JComboBox<String>(listData);
        algorithms.setSelectedIndex(-1);
        algorithms.setBounds(20, 100, 400, 20);
        algorithms.setMaximumSize(new Dimension(20, 20));
        algorithms.addItemListener(new ShortestPathChangeListener());
        JLabel label1 = new JLabel("最短路径算法选择器");
        label1.setBounds(150, 120, 150, 70);

        JSlider slider = new JSlider(0, 200, 100);
        slider.setBounds(20, 300, 400, 40);
        slider.addChangeListener(new SpeedChangeListener());

        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);


        JButton startButton = new JButton("开始");
        startButton.setBounds(20, 500, 100, 100);
        startButton.setBackground(new Color(135, 206, 235, 255));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPause == true) {
                    isPause = false;

                }
            }
        });
        JButton pauseButton = new JButton("暂停");
        pauseButton.setBounds(300, 500, 100, 100);
        pauseButton.setBackground(new Color(135, 206, 235, 255));
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPause == false) {
                    isPause = true;

                }
            }
        });

        JComboBox<String> mazeGenerationComboBox = new JComboBox<>(mazeGeneration());
        mazeGenerationComboBox.setSelectedIndex(-1);
        mazeGenerationComboBox.setBounds(20, 700, 400, 20);
        mazeGenerationComboBox.addItemListener(new ItemChangeListener());


        JLabel label2 = new JLabel("迷宫生成选择器");
        label2.setBounds(150, 720, 150, 70);


        controlPanel.add(algorithms);
        controlPanel.add(slider);
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(mazeGenerationComboBox);
        controlPanel.add(label1);
        controlPanel.add(label2);

        mainPanel.add(controlPanel);
        mainPanel.add(canvas);

        return mainPanel;
    }

    // 在这里添加迷宫生成的项目
    public String[] mazeGeneration() {
        String[] mazeGemerations = {"文件生成迷宫", "深度优先遍历生成迷宫"};
        return mazeGemerations;
    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public AlgoCanvas getAlgoCanvas() {
        return algoCanvas;
    }

    // 这里是算法调用的函数，先更新数据，然后绘制到屏幕上
    public void setData(int x, int y, boolean isPath) {
        if (data.inArea(x, y)) {
            data.path[x][y] = isPath;
        }
        getAlgoCanvas().render(data);
        //算法延迟。
        AlgoVisHelper.pause(200-delay);
    }


    private class ItemChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = e.getItem().toString();

                switch (item) {
                    case "文件生成迷宫":
                        data = new MazeData("maze1");
                        setData(-1, -1, false);
                        break;
                    case "深度优先遍历生成迷宫":
                        data = new MazeData(40, 40);
                        mazeGeneration.mazeInit(data);
                        mazeGeneration.dfs(data.getEntranceX(), data.getEntranceY());
                        setData(-1, -1, false);
                        break;
                    default:
                        break;

                }
            }
        }
    }
    //最短路径算法的监听器。
    private class ShortestPathChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String item = e.getItem().toString();
                switch (item) {
                    case "回溯法":
                        new Thread(// 先传递迷宫给最短路径算法,以便算法对数据更新，从而绘制到屏幕上
                                this::huiSu).start();

                        break;
                    default:
                        break;

                }
            }
        }

        private void huiSu() {
            shortestPath.setData(data);
            shortestPath.dfs(data.getEntranceX(), data.getEntranceY());
            if (!data.isOk) {
                JOptionPane.showMessageDialog(null, "No Solution!");
            }else {
                JOptionPane.showMessageDialog(null, "you got it,the length is: "+data.lowLength);
                for(int i=0;i<data.getN();i++) {
                    for (int j = 0; j < data.getM(); j++)
                        if (data.lowPath[i][j] == true) {
                            setData(i,j,true);
                            getAlgoCanvas().render(data);
                        }
                }
                getAlgoCanvas().render(data);
            }
        }
    }

    private class SpeedChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JSlider slider = (JSlider) e.getSource();
            delay = slider.getValue();
        }
    }


    public static int getDelay() {
        return delay;
    }
}
