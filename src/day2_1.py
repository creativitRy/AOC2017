input = """5 1 9 5
7 5 3
2 4 6 8"""

sum = 0
for row in input.split("\n"):
    max = -999999
    min = 999999
    for num in row.split():
        n = int(num)
        if n > max:
            max = n
        if n < min:
            min = n
    sum += max - min

print(sum)
