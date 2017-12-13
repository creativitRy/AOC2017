input = open("day13_input.in", "r").read()

walls = []
for line in input.split("\n"):
    index = line.index(':')
    a = int(line[0:index])
    b = int(line[index + 2:])

    while len(walls) <= a:
        walls.append(0)
    walls[a] = b

delay = 0
while True:
    found = True
    for i, wall in enumerate(walls):
        if wall == 0:
            continue
        if (i + delay) % (2 * (wall - 1)) == 0:
            found = False
            break

    if found:
        break

    delay += 1

print(delay)