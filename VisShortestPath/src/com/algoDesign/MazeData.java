package com.algoDesign;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData {
    private int N, M;
    public char[][] maze;
    private int entranceX, entranceY;
    private int exitX, exitY;

    public boolean[][] visited;
    //path数组存储当前的路径。
    public boolean[][] path;
    public int nowLength=0;
    public boolean[][] lowPath;
    public int lowLength=0x7fffffff;
    public boolean isOk=false;

    public MazeData(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename can not be null");
        }
        Scanner scanner = null;
        try {
            File file = new File(filename);
            FileInputStream fis = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");

            // 读取第一行
            String nmline = scanner.nextLine();
            String[] nm = nmline.trim().split("\\s+");
            N = Integer.parseInt(nm[0]);
            M = Integer.parseInt(nm[1]);

            maze = new char[N][M];
            visited = new boolean[N][M];   // 默认值为false
            path = new boolean[N][M];
            lowPath=new boolean[N][M];
            for (int i = 0; i < N; i++) {
                String line = scanner.nextLine();
                if (line.length() != M)
                    throw new IllegalArgumentException("Maze file" + filename + " error!");
                for (int j = 0; j < M; j++)
                    maze[i][j] = line.charAt(j);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null)
                scanner.close();
        }

        entranceX = 0;
        entranceY = 0;
        exitX = N-1;
        exitY = M-1;
    }

    public MazeData(int n, int m){
        N = n;
        M = m;

        maze = new char[N][M];
        visited = new boolean[N][M];   // 默认值为false
        path = new boolean[N][M];
        lowPath=new boolean[N][M];

        entranceX = 0;
        entranceY = 0;
        exitX = N - 1;
        exitY = M - 1;
    }

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public char getMaze(int i, int j) {
        if (!inArea(i, j)) {
            throw new IllegalArgumentException("i or j is out of index in getMaze");
        }
        return maze[i][j];
    }

    public void upPath(){
        if(lowLength>nowLength){
            lowLength=nowLength;
            for(int i=0;i<getN();i++) {
                for (int j = 0; j < getM(); j++)
                    lowPath[i][j]=path[i][j];
            }
        }
    }

    public boolean inArea(int i, int j) {
        return i >= 0 && i < N && j >= 0 && j < M;
    }
}
