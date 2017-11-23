from __future__ import division
import numpy as np
from scipy.sparse import coo_matrix

f = open('graph', 'r')
g = open('result', 'w')


def make_row_col(f_temp):
    row_temp = np.array([])
    col_temp = np.array([])
    for line in f_temp:
        u_temp, v_temp = [int(x) for x in line.split()]
        row_temp = np.append(row_temp, u_temp)
        col_temp = np.append(col_temp, v_temp)
    return row_temp, col_temp


def make_data(col_temp):
    data_temp = np.array([])
    for i in range(0, len(col_temp), 1):
        cal = 0
        for j in range(0, len(col_temp), 1):
            if col_temp[j] == col_temp[i]:
                cal += 1
        data_temp = np.append(data_temp, [1/cal])
    return data_temp


def decide_n(row_temp, col_temp):
    if row_temp.max() > col_temp.max():
        n_temp = row_temp.max() + 1
    else:
        n_temp = col_temp.max() + 1
    return n_temp


def remove_dead_end(m_temp):
    while True:
        n_temp = len(m_temp)
        is_dead_end = np.zeros(n_temp, dtype=bool)+True
        row_temp, col_temp, data_temp = matrix_make_row_col_data(m_temp)
        for i in col_temp:
            is_dead_end[i] = False
        # print is_dead_end
        if np.count_nonzero(is_dead_end) == 0:
            break
        i = 0
        i_temp = 0
        while i < n_temp:
            if is_dead_end[i]:
                m_delete_row = np.delete(m_temp, i_temp, 0)
                m_delete_row_col = np.delete(m_delete_row, i_temp, 1)
                m_temp = m_delete_row_col
                i_temp -= 1
            i += 1
            i_temp += 1
        for j in range(len(m_temp)):
            for i in range(len(m_temp)):
                if m_temp[i, j] != 0:
                    n = np.count_nonzero(m_temp[0:, [j]])
                    m_temp[i, j] = 1/n
        print m_temp
    return m_temp


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


def matrix_make_row_col_data(matrix_temp):
    row_temp = np.array([])
    col_temp = np.array([])
    data_temp = np.array([])
    for i in range(len(matrix_temp)):
        for j in range(len(matrix_temp)):
            if matrix_temp[i, j] != 0:
                row_temp = np.append(row_temp, [i])
                col_temp = np.append(col_temp, [j])
                data_temp = np.append(data_temp, matrix_temp[i, j])
    return row_temp, col_temp, data_temp

row_G, col_G = make_row_col(f)
print row_G, col_G
data_G = make_data(col_G)
print row_G, col_G

N_G = decide_n(row_G, col_G)
G = coo_matrix((data_G, (row_G, col_G)), shape=(N_G, N_G)).todense()
H = remove_dead_end(G)

row_H, col_H, data_H = matrix_make_row_col_data(H)
N_H = len(H)
val_H = np.zeros(shape=(N_H, 1))+1/N_H
result = pr_iteration(row_H, col_H, data_H, val_H, 0.8, 0.01)

print H
for data_result in result:
    g.write(str(data_result)+'\n')
print result.sum()

print row_H
f.close()
g.close()
