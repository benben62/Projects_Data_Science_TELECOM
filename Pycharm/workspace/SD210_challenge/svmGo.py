import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.decomposition import PCA
from sklearn import linear_model
from sklearn.model_selection import cross_val_score
from sklearn.svm import SVC
from sklearn import preprocessing


# Critere de performance
def compute_pred_score(y_true, y_pred):
    y_pred_unq = np.unique(y_pred)
    for i in y_pred_unq:
        if (i != -1) & (i!= 1) & (i!= 0):
            raise ValueError('The predictions can contain only -1, 1, or 0!')
    y_comp = y_true * y_pred
    score = float(10*np.sum(y_comp == -1) + np.sum(y_comp == 0))
    score /= y_comp.shape[0]
    return score

X_train_fname = 'training_templates.csv'
y_train_fname = 'training_labels.txt'
X_test_fname = 'testing_templates.csv'
X_train = pd.read_csv(X_train_fname, sep=',', header=None).values
X_test = pd.read_csv(X_test_fname,  sep=',', header=None).values
y_train = np.loadtxt(y_train_fname, dtype=np.int)


def uncerAjust(y_pred, y_pred_pro, threshold=0.9):
    temps = y_pred
    for i in xrange(len(y_pred)):
        if np.abs(y_pred_pro[i][1]-y_pred_pro[i][0]) < threshold:
            temps[i] = 0
    return temps


def scorer(estimator, X, y):
    y_pred = estimator.predict(X)
    y_pred_pro = estimator.predict_proba(X)
    y_pred = uncerAjust(y_pred, y_pred_pro)
    return compute_pred_score(y, y_pred)

X_train_1, X_test_1, y_train_1, y_test_1 = train_test_split(X_train, y_train,
                                                            train_size=0.5, test_size=0.5, random_state=42)
pca = PCA(svd_solver='randomized', n_components=128, whiten=True)
X_train_pca = pca.fit_transform(X_train_1)
X_test_pca = pca.transform(X_test_1)
print 1

clf = SVC(probability=True, C=100, tol=0.01)
clf.fit(X_train_pca, y_train_1)
print 2

result = clf.predict(X_test_pca)
pro = clf.predict_proba(X_test_pca)
result = uncerAjust(result, pro, 0.8)
print compute_pred_score(y_test_1, result)


y_pred = clf.predict(X_test)
y_pro = clf.predict_proba(X_test)
y_pred = uncerAjust(y_pred, y_pro, 0.8)
np.savetxt('y_pred.txt', y_pred, fmt='%d')