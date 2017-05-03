import numpy as np
from sklearn import tree
from scipy import stats
# 2.1
training_data = np.load('training_data.npy')
traing_class = np.load('training_class.npy')
test_data = np.load('test_data.npy')
test_class = np.load('test_class.npy')
clf = tree.DecisionTreeClassifier(criterion='gini', random_state=np.random.RandomState(130), max_depth=2)
clf.fit(training_data, traing_class)
with open('iris.dot', 'w') as f:
    f = tree.export_graphviz(clf, out_file=f)
predict_class = clf.predict(test_data)
# 2.2
a = np.array([])
for i in xrange(len(test_class)):
    if test_class[i] == predict_class[i]:
        a = np.append(a, [1])
    else:
        a = np.append(a, [0])
print stats.norm.interval(0.95, loc=np.mean(a), scale=stats.sem(a))
# 2.3
