package com.algoDesign;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        int frameWidth = 1920;
        int frameHeight = 1080;

        EventQueue.invokeLater(() -> {
            AlgoFrame frame = new AlgoFrame("VisShortestPath", frameWidth, frameHeight);
        });
    }
}
