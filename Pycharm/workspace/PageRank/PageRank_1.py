from __future__ import division
from scipy.sparse import coo_matrix
import numpy as np


def sparse_multiple(row_temp, col_temp, data_temp, val_temp, b):
    k = 0
    n_temp = len(val_temp)
    results_temp = np.zeros(shape=(n_temp, 1))
    while k < len(data_temp):
        results_temp[row_temp[k]] = results_temp[row_temp[k]] + b * data_temp[k] * val_temp[col_temp[k]]
        k += 1
    results_temp += ((1 - b) / n_temp)
    return results_temp


def pr_iteration(row_temp, col_temp, data_temp, val_temp, b, e):
    results_temp = []
    while True:
        results_temp = sparse_multiple(row_temp, col_temp, data_temp, val_temp, b)
        last_val_temp = val_temp
        val_temp = results_temp
        if np.linalg.norm(results_temp - last_val_temp) < e:
            break
    return results_temp


row = np.array([1, 2, 3, 0, 4, 5, 0])
col = np.array([0, 1, 2, 3, 0, 4, 5])
data = np.array([1/2, 1, 1, 1, 1/2, 1, 1])
N = 6
M = coo_matrix((data, (row, col)), shape=(N, N)).todense()
val = np.zeros(shape=(N, 1))+1/N

print M
print pr_iteration(row, col, data, val, 0.8, 0.1)
print pr_iteration(row, col, data, val, 1, 0.0001)
