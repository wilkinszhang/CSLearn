// 微信读书《计算广告》10.1.1倒排索引代码
template <class TKey>
class InvIndex:public map<TKey,list<int>>{
    public:
        vector<vector<TKey>> docs;
    public:
        void add(vector<TKey> doc){
            docs.push_back(doc);
            int curDocID=docs.size()-1;
            for ( int w=0;w<doc.size();w++){
                map<TKey,list<int>>::iterator it=this->find(doc[w]);
                if (it==this->end()){
                    list<int>newList;
                    index[doc[w]]=newList;
                    it=this->find(doc[w]);
                }
                it->second.push_back(curDocID);
            }
        }
        void retrieve(vector<TKeyu>& query, set<int>& docIDs){
            int termNum=query.size();
            docIDs.clear();
            for(int t=0;t<termNum;t++){
                map<TKey,list<int>>::iterator it=this->find(query[t]);
                if(it!=this->end()){
                    docIDs.insert(it->second.begin(),it->second.end());
                }
            }
        }
}