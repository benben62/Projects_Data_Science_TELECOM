from sklearn.feature_extraction.text import CountVectorizer
from sklearn.neighbors import KNeighborsClassifier
import numpy as np
import random

# x = xrange(1, 10)
# g = []
# for i in x:
#     f = open(str(i), 'r')
#     g.append(f.read())
# print g
# count_vect = CountVectorizer()
# X_train_counts = count_vect.fit_transform(g)
# data = X_train_counts.toarray()
# print data
f = open('total', 'r')
count_vect = CountVectorizer()
X_train_counts = count_vect.fit_transform(f)
data = X_train_counts.toarray()
label = np.array(['company', 'company', 'company', 'company', 'company', 'company', 'company', 'company', 'company',
                  'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit'])
N = data.shape[1]  # the size of data
# print X_train_counts


def uniformization(X):
    if X.ndim != 2:
        return None
    X2 = X.copy()
    X2 = X2.astype(float)
    rows = X2.shape[0]
    for i in xrange(0, rows):
        sum_of_squares = sum(X2[i, :]**2)
        if sum_of_squares == 0: continue
        sqrt_sum_of_squares = sum_of_squares**0.5
        X2[i, :] = X2[i, :] / sqrt_sum_of_squares
    return X2


def training_split(data_set_temp, class_data_set_temp, split):
    trainingset = np.array([])
    class_trainingset = np.array([])
    testset = np.array([])
    class_testset = np.array([])
    for x in range(len(data_set_temp)):
        if random.random() < split:
            trainingset = np.append(trainingset, data_set_temp[x])
            class_trainingset = np.append(class_trainingset, class_data_set_temp[x])
        else:
            testset = np.append(testset, data_set_temp[x])
            class_testset = np.append(class_testset, class_data_set_temp[x])
    trainingset = trainingset.reshape((len(trainingset)/N, N))
    testset = testset.reshape((len(testset)/N, N))
    return trainingset, class_trainingset, testset, class_testset


def k_training(data_set_temp, class_data_set_temp, split):
    accurate = 0
    count = 0
    k_temp = 1
    len_testset = 0
    for k_temp_temp in xrange(1, 5):
        for i in xrange(100):
            trainingset, class_trainingset, testset, class_testset = \
                training_split(data_set_temp, class_data_set_temp, split)
            if trainingset.shape[0] != 0 and testset.shape[0] != 0:
                neigh = KNeighborsClassifier(n_neighbors=k_temp_temp, metric='euclidean')
                neigh.fit(trainingset, class_trainingset)
                predict_labels = neigh.predict(testset)
                count += sum(predict_labels == class_testset)
                len_testset += len(testset)
        print float(count)/len_testset
        if float(count)/len_testset > accurate:
            accurate = float(count)/len_testset
            k_temp = k_temp_temp
        count = 0
        len_testset = 0
    return k_temp, accurate

print np.shape(data)

data_new = uniformization(data)

print k_training(data_new, label, 0.67)






