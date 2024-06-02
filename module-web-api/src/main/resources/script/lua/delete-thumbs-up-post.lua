local count = redis.call("SCARD", KEYS[1])

local result = redis.call("DEL", KEYS[1])

if result == 1 then
    redis.call("DECRBY", KEYS[2], count)
end
