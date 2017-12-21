input = open("day21_input.in", "r").read()

map = {}
for line in input.split("\n"):
    args = line.split(" => ")
    map[args[0]] = args[1].split("/")

ans = [".#.", "..#", "###"]

def getKey(square):
    return "/".join(square)

def rot(square):
    ans = []
    for row in square:
        for ind, val in enumerate(row):
            if len(ans) < len(square):
                ans.append(val)
            else:
                ans[ind] = val + ans[ind]
    return ans

def find(square):
    for i in range(0, 4):
        s = getKey(square)
        if s in map:
            return map[s]
        s = getKey(square[::-1])
        if s in map:
            return map[s]
        square = rot(square)
    return 0

for i in range(0, 5):
    if len(ans) % 2 == 0:
        newAns = []
        for r in range(0, len(ans), 2):
            str = ["", "", ""]
            for c in range(0, len(ans), 2):
                to = find([ans[r + d][c:(c + 2)] for d in range(0, 2)])
                for a, b in enumerate(to):
                    str[a] += b
            newAns.extend(str)
        ans = newAns
    else:
        newAns = []
        for r in range(0, len(ans), 3):
            str = ["", "", "", ""]
            for c in range(0, len(ans), 3):
                to = find([ans[r + d][c:(c + 3)] for d in range(0, 3)])
                for a, b in enumerate(to):
                    str[a] += b
            newAns.extend(str)
        ans = newAns

count = 0
for row in ans:
    for val in row:
        if val == "#":
            count += 1
print(count)
