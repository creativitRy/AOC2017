input = """5 9 2 8
9 4 7 3
3 8 6 5"""


def div(a, b):
    if a < b:
        a, b = b, a
    if a % b == 0:
        return a / b
    return -1


sum = 0
for row in input.split("\n"):
    list = []
    for num in row.split():
        n = int(num)
        for i in list:
            a = div(n, i)
            if a > 0:
                sum += a
                break
        else:
            list.append(n)
            continue
        break

print(int(sum))
