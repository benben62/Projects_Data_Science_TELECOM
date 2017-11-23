import numpy as np
import matplotlib.pyplot as plt

from sklearn.ensemble import BaggingRegressor
from sklearn.tree import DecisionTreeRegressor
import numpy as np
from sklearn.tree import DecisionTreeRegressor
from sklearn.ensemble import BaggingRegressor
import matplotlib.pyplot as plt
from sklearn.model_selection import cross_val_score
import mpl_toolkits.mplot3d

# create a random dataset
rng = np.random.RandomState(1)
X = np.sort(5 * rng.rand(80, 1), axis=0)
y = np.sin(X).ravel()
y[::5] += 1 * (0.5 - rng.rand(16))

n_estimators = 10 # L in the text
tree_max_depth = 10
bagging_max_depth = 10
# define the regressor by bagging stumps
tree = DecisionTreeRegressor(max_depth=tree_max_depth)
tree.fit(X, y)
bagging = BaggingRegressor(base_estimator=DecisionTreeRegressor(max_depth=bagging_max_depth), n_estimators=n_estimators)
bagging.fit(X, y)
# Predict
X_test = np.arange(0.0, 5.0, 0.01)[:, np.newaxis]
y_tree = tree.predict(X_test)
y_bagging = bagging.predict(X_test)
# Plot the results
plt.figure(figsize=(12, 8))
plt.plot(X, y, 'o', c="k", label="data")
# add plots for Bagging/Tree
plt.plot(X_test, y_tree, label="tree")
plt.plot(X_test, y_bagging, label="bagging")
plt.title("Decision Tree Regression")
plt.legend(loc=1, numpoints=1)
plt.show()

maxDepth = 15
maxNumEsti = 15
argsDepth = xrange(1, maxDepth)
argsNumEsti = xrange(1, maxNumEsti)
scoresTree = []
for i in argsDepth:
    reg = DecisionTreeRegressor(max_depth=i)
    temp = np.mean(cross_val_score(reg, X=X, y=y, cv=5))
    scoresTree.append(temp)
plt.figure()
plt.plot(argsDepth, scoresTree)
plt.title('The number of estimators')
plt.xlabel('L')
plt.ylabel('score')
plt.show()

scoresBagging = []
for i in argsNumEsti:
    reg = BaggingRegressor(n_estimators=i)
    temp = np.mean(cross_val_score(reg, X=X, y=y, cv=5))
    scoresBagging.append(temp)
plt.figure()
plt.plot(argsNumEsti, scoresBagging)
plt.title('The tree depth')
plt.xlabel('tree_max_depth')
plt.ylabel('score')
plt.show()


scores = np.zeros((len(argsDepth), len(argsNumEsti)))
for i in argsDepth:
    for j in argsNumEsti:
        tree = DecisionTreeRegressor(max_depth=i)
        reg = BaggingRegressor(base_estimator=tree, n_estimators=j)
        score = np.mean(cross_val_score(reg, X, y, cv=5))
        scores[i-1][j-1] = score

argsX, argsY = np.mgrid[0:maxDepth-1, 0:maxNumEsti-1]
ax = plt.subplot(111, projection='3d')
ax.plot_surface(argsX, argsY, scores, rstride=2, cstride=1, cmap=plt.cm.coolwarm, alpha=0.8)
ax.set_xlabel('Depth')
ax.set_ylabel('Number of estimators')
ax.set_zlabel('score')
plt.show()

x,y=np.mgrid[-2:2:20j,-2:2:20j]
z=x*np.exp(-x**2-y**2)
print z.shape


ax=plt.subplot(111,projection='3d')
ax.plot_surface(x,y,z,rstride=2,cstride=1,cmap=plt.cm.coolwarm,alpha=0.8)
ax.set_xlabel('x')
ax.set_ylabel('y')
ax.set_zlabel('z')
plt.show()

