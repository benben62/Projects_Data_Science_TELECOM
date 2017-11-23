from scipy.stats import binom
L = 10
P = 0.7
rv = binom(L, P)
pre = 0
for i in xrange(L/2+1, L+1):
    pre += rv.pmf(i)
print pre