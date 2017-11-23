import numpy as np
import pandas as pd
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import cross_val_score
from sklearn.neural_network import MLPClassifier
from sklearn.model_selection import train_test_split
from sklearn.model_selection import GridSearchCV
from sklearn.decomposition import PCA


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
    return 1-compute_pred_score(y, y_pred)


class MyEstimator(MLPClassifier):
    def __init__(self, hidden_layer_sizes=(100,), activation="relu",
                 solver='adam', alpha=0.0001,
                 batch_size='auto', learning_rate="constant",
                 learning_rate_init=0.001, power_t=0.5, max_iter=200,
                 shuffle=True, random_state=None, tol=1e-4,
                 verbose=False, warm_start=False, momentum=0.9,
                 nesterovs_momentum=True, early_stopping=False,
                 validation_fraction=0.1, beta_1=0.9, beta_2=0.999,
                 epsilon=1e-8, threshold=0.9):

        super(MyEstimator, self).__init__(hidden_layer_sizes=hidden_layer_sizes,
                                          activation=activation, solver=solver, alpha=alpha,
                                          batch_size=batch_size, learning_rate=learning_rate,
                                          learning_rate_init=learning_rate_init, power_t=power_t,
                                          max_iter=max_iter, shuffle=shuffle,
                                          random_state=random_state, tol=tol, verbose=verbose,
                                          warm_start=warm_start, momentum=momentum,
                                          nesterovs_momentum=nesterovs_momentum,
                                          early_stopping=early_stopping,
                                          validation_fraction=validation_fraction,
                                          beta_1=beta_1, beta_2=beta_2, epsilon=epsilon)
        self.threshold = threshold

    def predict(self, X):
        y_pred = MLPClassifier.predict(self, X)
        y_pred_pro = MLPClassifier.predict_proba(self, X)
        return uncerAjust(y_pred, y_pred_pro, self.threshold)


hls = 1200
al = 0.0001
minS = 1
threshold = 0.9

X_train_1, X_test_1, y_train_1, y_test_1 = train_test_split(X_train, y_train,
                                                            train_size=0.5, test_size=0.3, random_state=42)

pca = PCA(svd_solver='randomized', n_components=128, whiten=True)
X_train_pca = pca.fit_transform(X_train_1)
X_test_pca = pca.transform(X_test_1)
print 1

clf = MyEstimator(threshold=threshold, hidden_layer_sizes=(500,), alpha=al, max_iter=200, activation='relu')
clf.fit(X_train_pca, y_train_1)
print compute_pred_score(clf.predict(X_test_pca), y_test_1)

X_test = pca.transform(X_test)
y_pred = clf.predict(X_test)
np.savetxt('y_pred_mlp.txt', y_pred, fmt='%d')