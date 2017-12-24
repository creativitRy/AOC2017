input = open("day24_input.in", "r").read()

pairs = []
for line in input.split("\n"):
    args = line.split("/")
    pairs.append([int(i) for i in args])

longest = 0
largest = 0

def rec(sum, prev, been, len):
    global largest, longest
    if len >= longest:
        if len > longest:
            longest = len
        largest = max(sum, largest)
    for i, pair in enumerate(pairs):
        if i in been:
            continue
        if prev in pair:
            newBeen = been.copy()
            newBeen.add(i)
            rec(sum + pair[0] + pair[1], pair[1] if pair[0] == prev else pair[0], newBeen, len + 1)

rec(0, 0, set(), 0)
print(largest)
