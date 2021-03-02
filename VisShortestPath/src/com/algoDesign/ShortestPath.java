package com.algoDesign;

// 最短路径算法需要交互的文件有AlgoFrame, MazeData, 极少数情况要和AlgoCanvas打交道
public class ShortestPath {
    private static final int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private MazeData data;
    private AlgoFrame frame;

    public ShortestPath(AlgoFrame frame) {
        this.frame = frame;
        frame.isPause = true;
    }

    public void setData(MazeData data) {
        this.data = data;
    }


    // dfs :深度优先搜索。
    // 如果从(x, y)的位置开始求解迷宫，求解成功，返回true, 否则返回false
    public void dfs(int x, int y) {
        if(data.nowLength>=data.lowLength)
            return;

        // 根据按钮的情况来阻塞函数，做出函数暂停的效果。
        while (frame.isPause == true) {
            System.out.println(frame.isPause);
        }

        //判断节点是否在迷宫范围外。
        if (!data.inArea(x, y)){
            throw new IllegalArgumentException("x, y are out of index in go function");
        }
        data.visited[x][y] = true;  //访问改点
        data.nowLength++;
        frame.setData(x, y, true);   //设置数据，在view显示改点
        //如果当前节点为终点，就根据情况更新数据，仍然返回false。
        if (x == data.getExitX() && y == data.getExitY()) {
            data.isOk=true;
            data.upPath();
            data.nowLength--;
            data.visited[x][y]=false;
            frame.setData(x,y,false);
            return;
        }
        for (int i = 0; i < 4; i++) {
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            //如果新的x和y仍然在迷宫范围内，且新的x和y的节点没有被访问过。
            if (data.inArea(newX, newY) && data.getMaze(newX, newY) == '0' && !data.visited[newX][newY]) {
                //形成递归
                dfs(newX, newY);
            }
        }
        data.nowLength--;
        data.visited[x][y]=false;
        data.path[x][y] = false;
        frame.setData(x, y, false);
    }
}