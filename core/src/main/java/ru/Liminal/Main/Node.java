package ru.Liminal.Main;

public class Node {
    int x,y;
    int gCost;
    int hCost;
    Node parent;
    boolean isVisidet = false;

    Node(int x, int y){
        this.x = x;
        this.y = y;

    }
    int fCos(){
        return gCost + hCost;
    }
}
