n = int(input())
total = 2018

list = [0]
index = 0
for i in range(1, total):
    for j in range(0, n):
        index += 1
        if index == i:
            index = 0
    index += 1
    list.insert(index, i)

for i, j in enumerate(list):
    if j == total - 1:
        print(list[i + 1])
        break