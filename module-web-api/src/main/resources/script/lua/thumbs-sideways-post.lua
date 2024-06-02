local result = redis.call("SREM", KEYS[1], ARGV[1])

if result == 1 then
    redis.call("DECR", KEYS[2])
end

