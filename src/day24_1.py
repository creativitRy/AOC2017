input = open("day24_input.in", "r").read()

pairs = []
for line in input.split("\n"):
    args = line.split("/")
    pairs.append([int(i) for i in args])

largest = 0

def rec(sum, prev, been):
    global largest
    largest = max(sum, largest)
    for i, pair in enumerate(pairs):
        if i in been:
            continue
        if prev in pair:
            newBeen = been.copy()
            newBeen.add(i)
            rec(sum + pair[0] + pair[1], pair[1] if pair[0] == prev else pair[0], newBeen)

rec(0, 0, set())
print(largest)
