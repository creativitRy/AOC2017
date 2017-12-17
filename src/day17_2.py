n = int(input())
total = 50000001

def m(a, b):
    if b < a:
        return a % b
    return a

index = 0
zeroIndex = 0
ans = -1
for i in range(1, total):
    index = (index + m(n, i)) % i + 1
    if zeroIndex < index:
        if zeroIndex + 1 == index:
            ans = i
        continue
    zeroIndex += 1

print(ans)