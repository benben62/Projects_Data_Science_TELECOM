import numpy as np
import csv
import random

# data_set = np.array([[1, 0], [2, 0], [3, 0], [4, 0], [5, 0], [1, 4], [2, 3], [3, 2], [4, 3], [5, 4]])
# class_data_set = np.array(['a', 'a', 'a', 'a', 'a', 'b', 'b', 'b', 'b', 'b'])
test = np.array([5.9, 3.0, 5.1, 5])
k = 1
filename = 'data_set'   # the name of dataset
N = 4   # the number of each data


def load_dataset(file_name):
    with open(file_name, 'rb') as csvfile:
        lines = csv.reader(csvfile)
        dataset = list(lines)
        dataset = np.array(dataset)
        data_set_string = dataset[0:, 0:N]
        data_set_temp = np.zeros(shape=(len(data_set_string), N))
        for i in xrange(len(data_set_temp)):
            for j in xrange(N):
                data_set_temp[i, j] = float(data_set_string[i, j])
        class_data_set_temp = dataset[0:, N]
    return data_set_temp, class_data_set_temp


def training_split(data_set_temp, class_data_set_temp, split):
    trainingset = np.array([])
    class_trainingset = np.array([])
    testset = np.array([])
    class_testset = np.array([])
    for x in range(len(data_set_temp) - 1):
        if random.random() < split:
            trainingset = np.append(trainingset, data_set_temp[x])
            class_trainingset = np.append(class_trainingset, class_data_set_temp[x])
        else:
            testset = np.append(testset, data_set_temp[x])
            class_testset = np.append(class_testset, class_data_set_temp[x])
    trainingset = trainingset.reshape((len(trainingset)/N, N))
    testset = testset.reshape((len(testset)/N, N))
    return trainingset, class_trainingset, testset, class_testset


def max_distance(data_temp, data_set_temp):
    max_temp = 0
    max_num = 0
    for i in xrange(len(data_set_temp)):
        if np.linalg.norm(data_set_temp[i]-data_temp) > max_temp:
            max_temp = np.linalg.norm(data_set_temp[i]-data_temp)
            max_num = i
    return max_temp, max_num


def knn_classify(new_data_temp, data_set_temp, class_data_set_temp, k_temp):
    # int min_k_temp and num_min_k_temp
    min_k_temp = data_set_temp[0:k_temp]
    num_min_k_temp = np.array(xrange(k_temp))

    # maintain the min_k_temp and num_min_k_temp
    for i in xrange(k_temp, len(data_set_temp), 1):
        # print min_k_temp
        # print num_min_k_temp
        m_distance, num_m = max_distance(new_data_temp, min_k_temp)
        # print m_distance, num_m
        if np.linalg.norm(data_set_temp[i]-new_data_temp) < m_distance:
            min_k_temp[num_m] = data_set_temp[i]
            num_min_k_temp[num_m] = i

    # count the result of voting
    count_class = {}
    for i in xrange(k_temp):
        vote_class = class_data_set_temp[num_min_k_temp[i]]
        count_class[vote_class] = count_class.get(vote_class, 0) + 1

    max_count = 0
    for key, value in count_class.items():
        if value > max_count:
            max_count = value
            max_class = key

    return max_class


def k_training(data_set_temp, class_data_set_temp, split):
    accurate = 0
    count = 0
    k_temp = 1
    len_testset = 0
    for k_temp_temp in xrange(1, 10):
        for i in xrange(10):
            trainingset, class_trainingset, testset, class_testset = \
                training_split(data_set_temp, class_data_set_temp, split)
            for x in xrange(len(testset)):
                if knn_classify(testset[x], trainingset, class_trainingset, k_temp_temp) == class_testset[x]:
                    count += 1.0
            len_testset += len(testset)
        if count/len_testset > accurate:
            accurate = count/len_testset
            k_temp = k_temp_temp
        count = 0
    return k_temp, accurate

data_set, class_data_set = load_dataset(filename)
k, accu = k_training(data_set, class_data_set, 0.67)
print k, accu
print knn_classify(test, data_set, class_data_set, k)


