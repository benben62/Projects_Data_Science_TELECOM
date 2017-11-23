import numpy as np
import pandas as pd
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.decomposition import PCA
from sklearn.ensemble import BaggingClassifier


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
        if (y_pred_pro[i][0] < threshold) and (y_pred_pro[i][1] < threshold):
            temps[i] = 0
    return temps


def scorer(estimator, X, y):
    y_pred = estimator.predict(X)
    return 1-compute_pred_score(y, y_pred)


pca = PCA(svd_solver='randomized', n_components=128, whiten=True)
X_train_pca = pca.fit_transform(X_train)


base = MLPClassifier(hidden_layer_sizes=(100, 100, 100, 100))


class MyEstimator(BaggingClassifier):
    def __init__(self,
                 base_estimator=None,
                 n_estimators=10,
                 max_samples=1.0,
                 max_features=1.0,
                 bootstrap=True,
                 bootstrap_features=False,
                 oob_score=False,
                 warm_start=False,
                 n_jobs=1,
                 random_state=None,
                 verbose=0, threshold=0.5):

        super(MyEstimator, self).__init__(
            base_estimator=base_estimator,
            n_estimators=n_estimators,
            max_samples=max_samples,
            max_features=max_features,
            bootstrap=bootstrap,
            bootstrap_features=bootstrap_features,
            oob_score=oob_score,
            warm_start=warm_start,
            n_jobs=n_jobs,
            random_state=random_state,
            verbose=verbose)
        self.threshold = threshold

    def predict(self, X):
        y_pred = BaggingClassifier.predict(self, X)
        y_pred_pro = BaggingClassifier.predict_proba(self, X)
        return uncerAjust(y_pred, y_pred_pro, self.threshold)

parameters = {'base_estimator': [base], 'max_samples': np.arange(0.1, 1, 0.2),
              'max_features': np.arange(0.1, 1, 0.2), 'n_jobs': [-1]}

myEst = MyEstimator()
clf = GridSearchCV(myEst, parameters, scoring=scorer)
clf.fit(X_train_pca, y_train)
