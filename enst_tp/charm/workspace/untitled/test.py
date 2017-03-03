import numpy as np
res =[(1,3),(1,2),(2,3)]
s = np.array(res)
t =[]
for i in range(3):
    t.append(s[i][0]*s[i][0])
print np.mean(t)

