input = open("day12_input.in", "r").read()
graph = []
for line in input.split("\n"):
    adjacent = set()
    for s in line.split()[2:]: # I love Python!
        adjacent.add(int(s.replace(",", "", 1)))
    graph.append(adjacent)

ans=0
dfs = []
dfs.append(0)
visited = set()
while (dfs):
    curr = dfs.pop()
    if (curr in visited):
        continue
    ans += 1
    visited.add(curr)
    dfs.extend(graph[curr])
print(ans)