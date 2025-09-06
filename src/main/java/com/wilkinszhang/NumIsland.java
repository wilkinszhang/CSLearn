package com.wilkinszhang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

//岛屿数量翻版，要求打印所有陆地块的位置
public class NumIsland {
    static final int[]dr={-1,1,0,0};
    static final int[]dc={0,0,-1,1};

    public static void main(String[]args) throws IOException {
        /*
        4 5
        11000
        11000
        00110
        00001
        island 1: (0,0) (1,0) (1,1) (0,1)
        island 2: (2,2) (2,3)
        island 3: (3,4)
        */
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk=new StringTokenizer(br.readLine());
        int n=Integer.parseInt(tk.nextToken());
        int m=Integer.parseInt(tk.nextToken());
        char [][]grid=new char[n][];
        for(int i=0;i<n;i++){
            grid[i]=br.readLine().trim().toCharArray();
        }
        boolean[][]vis=new boolean[n][m];
        List<List<int[]>>islands=new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(!vis[i][j] && grid[i][j]=='1'){
                    List<int[]>island=new ArrayList<>();
                    dfs(i,j,grid,vis,island);
                    islands.add(island);
                }
            }
        }
        for(int k=0;k<islands.size();k++){
            List<int[]>island=islands.get(k);
            System.out.print("island "+(k+1)+":");
            for(int []cell:island){
                System.out.print(" ("+cell[0]+","+cell[1]+")");
            }
            System.out.println();
        }
    }
    private static void dfs(int r,int c,char[][]grid,boolean[][]vis,List<int[]>island){
        vis[r][c]=true;
        island.add(new int[]{r,c});
        int n=grid.length,m=grid[0].length;
        for(int d=0;d<4;d++){
            int nr=r+dr[d],nc=c+dc[d];
            if(nr>=0 && nr<n && nc>=0 && nc<m
                    &&!vis[nr][nc] && grid[nr][nc]=='1'){
                dfs(nr,nc,grid,vis,island);
            }
        }
    }
}
