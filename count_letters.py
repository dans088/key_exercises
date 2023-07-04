first = ""
second = ""
third = ""
fourth = ""
count = 0
with open("cipher2.txt") as f:
    for line in f:
        for letter in line:
            if count == 0:
                first = first + letter
            elif count == 1:
                second = second + letter
            elif count == 2:
                third = third + letter
            else:
                fourth = fourth + letter
            count = (count + 1) % 4
f1 = open("f1.txt", "w")
f1.write(first)
f2 = open("f2.txt", "w")
f2.write(second)
f3 = open("f3.txt", "w")
f3.write(third)
f4 = open("f4.txt", "w")
f4.write(fourth)