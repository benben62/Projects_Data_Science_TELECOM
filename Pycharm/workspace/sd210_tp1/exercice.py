from sklearn.svm import SVC
from sklearn import datasets

iris = datasets.load_iris()
X = iris.data
y = iris.target
X = X[y != 0, :2]
y = y[y != 0]
size = len(y)
X_train = X[0:size:2]
y_train = y[0:size:2]
X_test = X[1:size:2]
y_test = y[1:size:2]
clf = SVC(kernel='linear')
clf.fit(X_train, y_train)


def getAccuracy(prediction, real_class):
    correct = 0
    for i in xrange(len(real_class)):
        if prediction[i] == real_class[i]:
            correct += 1
    return (correct/float(len(real_class)))*100.0

print getAccuracy(clf.predict(X_test), y_test)

clf = SVC(kernel='poly')
clf.fit(X_train, y_train)
print getAccuracy(clf.predict(X_test), y_test)