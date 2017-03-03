from __future__ import division
from scipy.sparse import coo_matrix
import numpy as np


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


def remove_dead_end(m_temp):
    while True:
        n_temp = len(m_temp)
        is_dead_end = np.zeros(n_temp, dtype=bool)+True
        row_temp, col_temp, data_temp = matrix_make_row_col_data(m_temp)
        for i in col_temp:
            is_dead_end[i] = False
        print is_dead_end
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

row = np.array([1, 3, 0, 4, 5, 1, 2, 3, 4, 5])
col = np.array([0, 2, 3, 0, 4, 3, 3, 3, 3, 3])
data = np.array([1/2, 1, 1/6, 1/2, 1, 1/6, 1/6, 1/6, 1/6, 1/6])
N = 6
M = coo_matrix((data, (row, col)), shape=(N, N)).todense()
print np.count_nonzero(M[0:, [0]])
print M
print remove_dead_end(M)
print len(M)
