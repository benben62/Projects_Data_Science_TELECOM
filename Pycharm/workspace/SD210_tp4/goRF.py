from sklearn.ensemble import RandomForestRegressor, RandomForestClassifier
from sklearn.datasets import load_boston, load_diabetes, load_iris, load_digits
from sklearn.svm import SVC, SVR
from sklearn.model_selection import cross_val_score
import numpy as np
import matplotlib.pyplot as plt
from sklearn import preprocessing
from sklearn.tree import DecisionTreeClassifier
# X_boston = load_boston().data
# y_boston = load_boston().target
# X_diab = load_diabetes().data
# y_diab = load_diabetes().target
# X_iris = load_iris().data
# y_iris = load_iris().target
# X_digi = load_digits().data
# y_digi = load_digits().target
# print X_boston.shape, y_boston.shape  # reg
# print X_diab.shape, y_diab.shape  # reg
# print X_iris.shape, y_iris.shape  # clf
# print X_digi.shape, y_digi.shape  # clf
#
# svr = SVR(kernel='linear')
# rfr = RandomForestRegressor()
# svc = SVC(kernel='linear')
# rfc = RandomForestClassifier()
#
# score_svr_boston = np.mean(cross_val_score(svr, X_boston, y_boston, cv=7))
# score_rfr_boston = np.mean(cross_val_score(rfr, X_boston, y_boston, cv=7))
#
# score_svr_diab = np.mean(cross_val_score(svr, X_diab, y_diab, cv=7))
# score_rfr_diab = np.mean(cross_val_score(rfr, X_diab, y_diab, cv=7))
#
# score_svc_iris = np.mean(cross_val_score(svc, X_iris, y_iris, cv=7))
# score_rfc_iris = np.mean(cross_val_score(rfc, X_iris, y_iris, cv=7))
#
# score_svc_digi = np.mean(cross_val_score(svc, X_digi, y_digi, cv=7))
# score_rfc_digi = np.mean(cross_val_score(rfc, X_digi, y_digi, cv=7))
#
# print 'svr for boston %r' % score_svr_boston
# print 'rfr for boston %r' % score_rfr_boston
#
# print 'svr for diab %r' % score_svr_diab
# print 'rfr for diab %r' % score_rfr_diab
#
# print 'svc for iris %r' % score_svc_iris
# print 'rfc for iris %r' % score_rfc_iris
#
# print 'svc for digi %r' % score_svc_digi
# print 'rfc for digi %r' % score_rfc_digi
#
# score_svm = [score_svr_boston, score_svr_diab, score_svc_iris, score_svc_digi]
# score_rf = [score_rfr_boston, score_rfr_diab, score_rfc_iris, score_rfc_digi]
# plt.figure()
# index = np.arange(4)
# bar_width = 0.35
# plt.bar(index, score_svm, bar_width, label='Linear SVM')
# plt.bar(index+bar_width, score_rf, bar_width, label='Random Forest')
# plt.xlabel('Data')
# plt.ylabel('Score')
# plt.xticks(index+bar_width/2, ('boston', 'diabetes', 'iris', 'digits'))
# plt.title('The comparation between SVM and Random Forest')
# plt.legend()
# plt.show()

# Parameters
n_estimators = 2
plot_colors = "bry"
plot_step = 0.02
# Load data
iris = load_iris()
X_unscaled, y = iris.data[:, :2], iris.target
# Standardize
X = preprocessing.scale(X_unscaled)

scoresRf = []
scoresTree = []
for i in xrange(1, 31):
    model = RandomForestClassifier(n_estimators=n_estimators, max_depth=i)
    scoreRf = np.mean(cross_val_score(model, X, y, cv=6))
    scoresRf.append(scoreRf)
    model = DecisionTreeClassifier(max_depth=i)
    scoreTree = np.mean(cross_val_score(model, X, y, cv=6))
    scoresTree.append(scoreTree)

plt.figure()
plt.plot(xrange(1, 31), scoresTree, label='Tree')
plt.plot(xrange(1, 31), scoresRf, label='Random Forest')
plt.legend()
plt.show()