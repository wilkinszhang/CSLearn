package com.wilkinszhang;

import java.util.*;

/*一个软件公司，有几种类型的资源(开发，测试，设计人员等)，他会承接多个项目，一个项目包含多个任务，任务需要一种类型的资源以及天数。问给定项目列表和
拥有资源数量后，如果能完成所有项目，最少多少天，如果不能，最多完成几个项目？*/
public class ProjectScheduler {
    enum ResType{DEV,TEST,DESIGN}

    static class Task{
        ResType type;
        int days;
        public Task(ResType t,int d){type=t;days=d;}
    }

    static class Project{
        List<Task> tasks=new ArrayList<>();
        int[] getWorkloads(){
            int[]w=new int[ResType.values().length];
            for(Task task:tasks){
                w[task.type.ordinal()]+=task.days;
            }
            return w;
        }
    }

    public static int minDaysToFinishAll(List<Project> projects,int[]R){
        int m=R.length;
        for(Project p:projects){
            int[]w=p.getWorkloads();
            for(int i=0;i<m;i++){
                if(R[i]==0 && w[i]>0)return -1;
            }
        }
        long totalWork=0;
        int[]sumW=new int[m];
        for(Project project:projects){
            int[]w=project.getWorkloads();
            for(int i=0;i<m;i++){
                sumW[i]+=w[i];
                totalWork+=w[i];
            }
        }
        long low=0,high=totalWork;
        while (low<high){
            long mid=(low+high)/2;
            if(canFinishIn((int)mid,sumW,R)){
                high=mid;
            }else{
                low=mid+1;
            }
        }
        return (int)low;
    }

    private static boolean canFinishIn(int D,int[]sumW,int[]R){
        for(int i=0;i<R.length;i++){
            if((long)R[i]*D<sumW[i])return false;
        }
        return true;
    }

    public static int maxProjectsWithinD(List<Project>projects,int[]R,int D){
        int n=projects.size(),m=R.length;
        int []cap=new int[m];
        for(int i=0;i<m;i++)cap[i]=R[i]*D;
        Map<String,Integer>dp=new HashMap<>();
        dp.put(zeroKey(m),0);
        for(Project project:projects){
            int[]w=project.getWorkloads();
            Map<String,Integer>next=new HashMap<>(dp);
            for(Map.Entry<String,Integer>entry:dp.entrySet()){
                String key=entry.getKey();
                int done=entry.getValue();
                int[]used=unpack(key,m);
                boolean ok=true;
                int[]nu= Arrays.copyOf(used,m);
                for(int i=0;i<m;i++){
                    nu[i]+=w[i];
                    if(nu[i]>cap[i]){
                        ok=false;
                        break;
                    }
                }
                if(!ok)continue;
                String k2=pack(nu);
                next.put(k2,Math.max(next.getOrDefault(k2,0),done+1));
            }
            dp=next;
        }
        int ans=0;
        for(int v:dp.values()){
            ans=Math.max(ans,v);
        }
        return ans;
    }

    private static String pack(int[]a){
        StringBuilder sb=new StringBuilder();
        for(int x:a){
            sb.append(',');
        }
        return sb.toString();
    }

    private static int[] unpack(String s,int m){
        String []parts=s.split(",");
        int[]a=new int[m];
        for(int i=0;i<m;i++){
            a[i]=Integer.parseInt(parts[i]);
        }
        return a;
    }

    private static String zeroKey(int m){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<m;i++){
            sb.append("0,");
        }
        return sb.toString();
    }

    public static void main(String[]args){
        int[]R=new int[]{3,2};
        List<Project> projects=new ArrayList<>();
        Project a=new Project();
        a.tasks.add(new Task(ResType.DEV,2));
        a.tasks.add(new Task(ResType.DEV,2));
        a.tasks.add(new Task(ResType.TEST,1));
        projects.add(a);
        System.out.println(minDaysToFinishAll(projects,R));
        System.out.println(maxProjectsWithinD(projects,R,3));
    }
}
