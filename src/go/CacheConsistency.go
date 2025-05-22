var lastUpdateTime int64
func init(){
    go func(){
        PollDBForData()
    }()
}

// 本地维护最新时间戳记录，以固定时间间隔轮询，拉取一定时间内数据库表发生更改的记录，从而实现对本地缓存数据的更新
// https://time.geekbang.org/column/article/839538
func PollDBForData(){
    interval:=5
    for{
        // 获取当前时间并减去interval，防止主从延迟
        now:=time.Now()
        secondsAgo:=now.add(-interval*time.Second)

        query:=fmt.Sprintf("SELECT * FROM your_table WHERE update_time>%d and  update_time<= %d",lastUpdateTime,tenSecondsAgo)
        rows,err:=db.Query(query)

        lastUpdateTime=secondsAgo
        time.Sleep(time.Duration(interval)*time.Second)
    }
}