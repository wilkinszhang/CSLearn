package com.wilkinszhang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


// 求两点之间的最短距离，时间复杂度n log n
public class ClosestPair {
    public static class Point{
        public final double x,y;
        public Point(double x,double y){
            this.x=x;
            this.y=y;
        }
    }

    public static double closestPair(Point[]points){
        int n=points.length;
        if(n<2)return 0.0;
        Point[]Px=points.clone();
        Arrays.sort(Px, Comparator.comparingDouble(p->p.x));
        Point[]Py=points.clone();
        Arrays.sort(Py,Comparator.comparingDouble(p->p.y));
        return recur(Px,Py);
    }

    private static double recur(Point[] Px, Point[] Py) {
        int n=Px.length;
        if(n<=3){
            return bruteForce(Px,n);
        }
        int mid=n/2;
        Point midP=Px[mid];
        Point[]Lx=Arrays.copyOfRange(Px,0,mid);
        Point[]Rx=Arrays.copyOfRange(Px,mid,n);
        List<Point> LyList=new ArrayList<>();
        List<Point> RyList=new ArrayList<>();
        for(Point p:Py){
            if(p.x<midP.x || (p.x==midP.x && LyList.size()<Lx.length)){
                LyList.add(p);
            }else{
                RyList.add(p);
            }
        }
        Point[]Ly=LyList.toArray(new Point[0]);
        Point[]Ry=RyList.toArray(new Point[0]);
        double dl=recur(Lx,Ly);
        double dr=recur(Rx,Ry);
        double d=Math.min(dl,dr);
        List<Point>strip=new ArrayList<>();
        for(Point p:Py){
            if(Math.abs(p.x-midP.x)<d){
                strip.add(p);
            }
        }
        for(int i=0;i<strip.size();i++){
            Point a=strip.get(i);
            for(int j=i+1;j<strip.size();j++){
                Point b=strip.get(j);
                if(b.y-a.y>=d)break;
                d=Math.min(d,dist(a,b));
            }
        }
        return d;
    }

    private static double bruteForce(Point[]P,int n){
        double min=Double.POSITIVE_INFINITY;
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                min=Math.min(min,dist(P[i],P[j]));
            }
        }
        return min;
    }

    private static double dist(Point a,Point b){
        double dx=a.x-b.x;
        double dy=a.y-b.y;
        return Math.hypot(dx,dy);
    }

    public static void main(String[]args){
        Point[] pts = new Point[] {
                new Point(0, 0),
                new Point(0, 2),
                new Point(2, 0),
                new Point(2, 2),
                new Point(1, 0)
        };

        double d = closestPair(pts);
        System.out.printf("%.6f%n", d);
    }
}
