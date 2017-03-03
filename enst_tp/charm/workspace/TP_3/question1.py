from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
from sklearn.naive_bayes import MultinomialNB

f = open('total', 'r')
count_vect = CountVectorizer()
X_train_counts = count_vect.fit_transform(f)
data = X_train_counts.toarray()
label = np.array(['company', 'company', 'company', 'company', 'company', 'company', 'company', 'company', 'company',
                  'company', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit', 'fruit'])
N = data.shape[1]  # the size of data
order = np.random.permutation(xrange(20))
print order
k = 5
train_data = np.array([])
train_label = np.array([])
count = 0
for i in xrange(k):
    test_data = data[order[20/k*i:20/k*(i+1)]]
    test_label = label[order[20/k*i:20/k*(i+1)]]
    for j in xrange(k):
        if i != j:
            train_data = np.append(train_data, data[order[20 / k * j:20 / k * (j + 1)]])
            train_label = np.append(train_label, label[order[20 / k * j:20 / k * (j + 1)]])
    train_data = train_data.reshape(len(train_data)/N, N)
    clf = MultinomialNB()
    clf.fit(train_data, train_label)
    predict_labels = clf.predict(test_data)
    count += sum(predict_labels == test_label)
    print sum(predict_labels == test_label)
accuracy = count/20.0
print accuracy
f.close()




