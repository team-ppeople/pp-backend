local result = redis.call("SADD", KEYS[1], ARGV[1])

if result == 1 then
    redis.call("INCR", KEYS[2])
end
