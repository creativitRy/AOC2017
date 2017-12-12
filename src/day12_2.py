input = open("day12_input.in", "r").read()
graph = []
for line in input.split("\n"):
    adjacent = set()
    for s in line.split()[2:]: # I love Python!
        adjacent.add(int(s.replace(",", "", 1)))
    graph.append(adjacent)

ans=0
visited = set()
for i in range(0, len(graph)):
    if i in visited:
        continue
    ans += 1

    dfs = []
    dfs.append(i)
    while (dfs):
        curr = dfs.pop()
        if (curr in visited):
            continue
        visited.add(curr)
        dfs.extend(graph[curr])
print(ans)