//
// Created by zhangweijian on 2025/4/16.
//
class CircularQueue {
public:
    CircularQueue(int capacity)
        : capacity(capacity), head(0), tail(0) {
        data = new int[capacity]; // 假设存储整数
    }

    ~CircularQueue() {
        delete[] data;
    }

    // 入队操作
    bool push(int value) {
        // 如果队列满，则不能插入
        if ((tail + 1) % capacity == head) {
            return false; // 队列已满
        }
        data[tail] = value;
        tail = (tail + 1) % capacity;
        return true;
    }

    // 出队操作
    bool pop(int &value) {
        // 如果队列为空，则无法出队
        if (head == tail) {
            return false; // 队列为空
        }
        value = data[head];
        head = (head + 1) % capacity;
        return true;
    }

private:
    int *data;
    int capacity;
    int head;
    int tail;
};

int main(){

}
