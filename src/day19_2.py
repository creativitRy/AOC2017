input = open("day19_input.in", "r").read()

r = 0
c = 0
maze = []
for line in input.split("\n"):
    if len(maze) == 0:
        c = line.index('|')
    maze.append(line)

dr = 1
dc = 0

ans = 0

while True:
    if r < 0 or c < 0 or r >= len(maze) or c >= len(maze[r]):
        break
    cc = maze[r][c]

    if cc == ' ':
        break
    if cc == '+':
        if dc == 0:
            dr = 0
            if c - 1 < 0 or maze[r][c - 1] == ' ':
                dc = 1
            else:
                dc = -1
        else:
            dc = 0
            if r - 1 < 0 or len(maze[r - 1]) <= c or maze[r - 1][c] == ' ':
                dr = 1
            else:
                dr = -1

    ans += 1
    r += dr
    c += dc

print(ans)