//
// Created by zhangweijian on 2025/4/27.
//
// 基于“哈希分桶 + 分治”，在外存（≈300 GB 数据、每个文件 1 亿行）高效地求两个文件的交集。
#include<bits/stdc++.h>
#include <sys/stat.h>

using namespace std;

size_t bucket_id(const string &s,size_t N) {
    return hash<string>{}(s)%N;
}

void partition_file(const string & in_path,const string &out_prefix,size_t N) {
    vector<ofstream> outs(N);
    for(size_t i=0;i<N;i++) {
        string fn=out_prefix + "_bucket_"+to_string(i)+".txt";
        outs[i].open(fn,ios::binary);
    }
    ifstream in(in_path,ios::binary);
    string line;
    while(getline(in,line)) {
        size_t h=bucket_id(line,N);
        outs[h]<<line<<'\n';
    }
    in.close();
    for(auto &o:outs)o.close();
}

void intersect_bucket(const string &a_pref,const string &b_pref,const string &r_pref,size_t h) {
    unordered_set<string>s;
    {
        ifstream inA(a_pref+"_bucket_"+to_string(h)+".txt",ios::binary);
        string line;
        while(getline(inA,line)) {
            s.insert(move(line));
        }
    }
    ifstream inB(b_pref+"_bucket_"+to_string(h)+".txt",ios::binary);
    ofstream outR(r_pref+"_bucket_"+to_string(h)+".txt",ios::binary);
    string line;
    while (getline(inB,line)) {
        if(s.find(line)!=s.end()) {
            outR<<line<<'\n';
        }
    }
}

size_t file_size(const string& fn) {
    struct stat st;
    return (stat(fn.c_str(), &st)==0 ? st.st_size : 0);
}

void merge_results(const string &r_pref, size_t N, const string &out_path) {
    ofstream out(out_path, ios::binary);
    for (size_t h = 0; h < N; h++) {
        string fn = r_pref + "_bucket_" + to_string(h) + ".txt";
        size_t sz = file_size(fn);
        if (sz > 0) {
            ifstream in(fn, ios::binary);
            if(!in){ continue; }
            out << in.rdbuf();
        }
    }
    out.close();
}

int main() {
    const size_t N=80;
    string fileA="fileA.txt",fileB="fileB.txt";
    string prefixA="A",prefixB="B",prefixR="R";
    partition_file(fileA,prefixA,N);
    partition_file(fileB,prefixB,N);
    for(size_t h=0;h<N;h++) {
        intersect_bucket(prefixA,prefixB,prefixR,h);
    }
    merge_results(prefixR,N,"final_intersection.txt");
    return 0;
}