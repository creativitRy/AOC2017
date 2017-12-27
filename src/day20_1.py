import sys

input = open("day20_input.in", "r").read()

class Particle:
    def __init__(self, p, v, a):
        self.p = p
        self.v = v
        self.a = a
    
    def update(self):
        add(self.v, self.a)
        add(self.p, self.v)

def parse(vector):
    return [int(i) for i in vector[3:-1].split(",")]

def add(a, b):
    for i in range(3):
        a[i] += b[i]

def manhattan(vector):
    return sum([abs(i) for i in vector])

particles = []
for line in input.split("\n"):
    args = [parse(v) for v in line.split(", ")]
    particles.append(Particle(args[0], args[1], args[2]))

ans = -1
while True:
    dist = sys.maxsize
    
    for i, particle in enumerate(particles):
        particle.update()
        m = manhattan(particle.p)
        if m < dist:
            dist = m
            ans = i
    # copy the answer once it stabilizes lol
    print(ans)
