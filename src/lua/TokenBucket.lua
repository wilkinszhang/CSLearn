local rate=tonumber(ARGV[1])
local capacity=tonumber(ARGV[2])
local now=tonumber(ARGV[3])

local last=tonumber(redis.call('get',KEYS[2])or 0)
local tokens=tonumber(redis.call('get',KEYS[1])or capacity)

local delta=math.floor((now-last)*rate)
if delta>0 then
    tokens=math.min(tokens+delta,capacity)
    last=now
end

if tokens > 0 then
    tokens=tokens-1
    redis.call('set',KEYS[1],tokens)
    redis.call('set',KEYS[2],last)
    return 1
else
    return 0
end