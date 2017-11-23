from keras.models import Sequential
from keras.layers import Dense, Activation, Conv1D, MaxPooling1D, Dropout, Flatten
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn import datasets
from keras.optimizers import SGD
from keras.utils import np_utils
import numpy as np

# prepare data
X_train_fname = 'training_templates.csv'
y_train_fname = 'training_labels.txt'
X_test_fname = 'testing_templates.csv'
X_train = pd.read_csv(X_train_fname, sep=',', header=None).values
X_test = pd.read_csv(X_test_fname,  sep=',', header=None).values
y_train = np.loadtxt(y_train_fname, dtype=np.int).reshape(-1, 1)
# X_train = np.reshape(X_train, (X_train.shape[0], 16, 8))
# X_test = np.reshape(X_test, (X_test.shape[0], 16, 8))
# y_train = np.reshape(y_train, (y_train.shape[0], 1, 1))

# Critere de performance
def compute_pred_score(y_true, y_pred):
    y_pred_unq = np.unique(y_pred)
    # for i in y_pred_unq:
    #     if (i != -1) & (i!= 1) & (i!= 0):
    #         print i
    #         raise ValueError('The predictions can contain only -1, 1, or 0!')
    y_comp = y_true * y_pred
    score = float(10*np.sum(y_comp == -1) + np.sum(y_comp == 0))
    score /= y_comp.shape[0]
    return score

# build the model
model = Sequential()
model.add(Dense(105600, activation='sigmoid', input_shape=(128,)))
model.add(Activation("relu"))
model.add(Dense(units=1))
model.add(Activation("softmax"))
model.compile(loss='sparse_categorical_crossentropy', optimizer='sgd', metrics=['binary_accuracy'])
model.fit(x=X_train, y=y_train, batch_size=32)
