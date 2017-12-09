input = open("day9_input.in", "r").read()

answer = 0
curr = 0
garbage = 0
ignoreNext = 0
for c in input:
    if garbage:
        if ignoreNext:
            ignoreNext = 0
        elif c == ">":
            garbage = 0
        elif c == "!":
            ignoreNext = 1
    else:
        if c == "{":
            curr += 1
        elif c == "}":
            answer += curr
            curr -= 1
        elif c == "<":
            garbage = 1

print(answer)