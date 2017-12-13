input = open("day13_input.in", "r").read()

walls = []
for line in input.split("\n"):
    index = line.index(':')
    a = int(line[0:index])
    b = int(line[index + 2:])

    while len(walls) <= a:
        walls.append(0)
    walls[a] = b

severity = 0
for i, wall in enumerate(walls):
    if wall == 0:
        continue
    if i % (2 * (wall - 1)) == 0:
        severity += i * wall

print(severity)