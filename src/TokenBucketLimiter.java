import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TokenBucketLimiter {

    private final int capacity; // 桶的最大容量
    private final int refillRate; // 每秒生成的令牌数
    private final AtomicInteger tokens; // 当前桶中的令牌数
    private final ScheduledExecutorService scheduler; // 定时生成令牌的线程池

    public TokenBucketLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = new AtomicInteger(capacity); // 初始令牌数为桶的最大容量
        this.scheduler = Executors.newScheduledThreadPool(1);

        // 定时任务：每秒添加令牌到桶中，确保不超过桶的容量
        scheduler.scheduleAtFixedRate(this::addTokens, 0, 1, TimeUnit.SECONDS);
    }

    // 每次添加 refillRate 个令牌
    private void addTokens() {
        int currentTokens = tokens.get();
        if (currentTokens < capacity) {
            int newTokens = Math.min(currentTokens + refillRate, capacity);
            tokens.set(newTokens);
            System.out.println("Added tokens, current tokens: " + newTokens);
        }
    }

    // 获取令牌：若桶中有足够的令牌则消耗一个令牌并允许请求，否则拒绝请求
    public boolean allowRequest() {
        int currentTokens = tokens.get();
        if (currentTokens > 0 && tokens.compareAndSet(currentTokens, currentTokens - 1)) {
            System.out.println("Request allowed, remaining tokens: " + (currentTokens - 1));
            return true;
        } else {
            System.out.println("Request denied, insufficient tokens");
            return false;
        }
    }

    // 关闭定时任务
    public void shutdown() {
        scheduler.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketLimiter limiter = new TokenBucketLimiter(5, 2); // 容量为5，令牌生成速率为2个/秒

        // 模拟多个请求
        for (int i = 0; i < 10; i++) {
            Thread.sleep(300); // 每300ms发一个请求
            System.out.println("Request #" + (i + 1) + " - " + (limiter.allowRequest() ? "Allowed" : "Denied"));
        }

        limiter.shutdown();
    }
}
