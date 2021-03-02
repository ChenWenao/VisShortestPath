package com.algoDesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MazeGeneration {
    private MazeData data;
    private static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int deleteMate=70;



    // 深度优先搜索生成迷宫
    public void mazeInit(MazeData data){
        this.data  = data;
        // 建立一张地图，设置迷宫全部是Wall
        for (int i = 0; i < data.getN(); i++) {
            for (int j = 0; j < data.getM(); j++ ) {
                data.maze[i][j] = '1';
            }
        }
    }
    Random random = new Random() ;
    public void dfs(int x, int y) {
        data.maze[x][y] = '0';
        Integer[] randDirs = generateRandomDirection();

        for (int i = 0; i < 4; i++) {
            int newX = d[randDirs[i]][0] + x;
            int newY = d[randDirs[i]][1] + y;


            if (!data.inArea(newX, newY))
                continue;

            // 判断是否挖穿路径：如果一个(newX, newY)周围有两个及以上的'0'那我们就不能挖它,确保只有一条路
            int count = 0;
            for (int j = 0; j < 4; j++) {
                if (data.inArea(newX + d[j][0], newY + d[j][1]) && data.getMaze(newX + d[j][0], newY + d[j][1]) == '0')
                    count++;
            }

            if (count > 1)
                continue;
            else {
                dfs(newX, newY);
            }
        }


        while (true) {
            if (deleteMate < 0)
                break;
            int mazeX = (int) (Math.random() * data.getN());
            int mazeY = (int) (Math.random() * data.getM());
            if (data.maze[mazeX][mazeY] == '1') {
                data.maze[mazeX][mazeY] = '0';
                System.out.println(mazeX + " " + mazeY + " " + deleteMate);
                deleteMate -= 1;
            } else {
                continue;
            }
        }

//
//        data.maze[data.getN() - 1][data.getM() - 1] = '0';
//        data.maze[data.getN() - 1][data.getM() - 2] = '0';
//        data.maze[data.getN() - 2][data.getM() - 1] = '0';
    }


    public MazeData getData() {
        return data;
    }

    private Integer[] generateRandomDirection(){
        ArrayList<Integer> randoms = new ArrayList<Integer>();

        for (int i = 0; i < 4; i++) {
            randoms.add(i);
        }

        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

}
