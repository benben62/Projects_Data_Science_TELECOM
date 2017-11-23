set.seed(42,kind = "Marsaglia-Multicarry")
#Exercice1
#a
h = 10/(11-0.2)
theta_0 = 0.2
n = 10
M = 10**5
Z = matrix(nrow = n, ncol = M)
for(i in 1:M)
{
  v= rgeom(10, theta_0)
  Z[,i] = matrix(v, nrow=10)+1
}
#b
Tx = colMeans(Z)
Sx = h*Tx
#c
hist(Sx, col = "red", probability = TRUE,
     main = "Histogramme de Tx et Sx", breaks = 50,
     xlab = "Estimations Sx et Tx de g(theta)",
     ylab = "Probabilite")
hist(Tx, col = "blue", probability = TRUE, add = TRUE,
     density = 15, breaks = 50)
abline(v = 1/theta_0, lwd = 3)
legend("topright", lwd = 2,col = c("red", "blue", "black"),
       legend = c("Sx", "Tx","g(theta)"))
mean(Sx)
mean(Tx)
#d
L1 = (Tx-mean(Tx))**2
Lh = (Sx-mean(Tx))**2
#e
(1-theta_0)/(n*(theta_0**2)) #R(theta_0,T)
(1/theta_0**2+(1-theta_0)/(n*(theta_0**2)))*h**2-(2/theta_0**2)*h+1/theta_0**2

mean(L1)
mean(Lh)
#f
hist(L1, col = "red", 
     probability = TRUE,
     main = "Histogramme de L1 et Lh",
     breaks = 50,
     xlab = "Estimations L1 et Lh de g(theta)",
     ylab = "Probabilite")
hist(Lh, col = "blue", probability = TRUE, add = TRUE,
     density = 15, breaks = 50)
abline(v = mean(L1), lwd = 2, col = "yellow")
abline(v = mean(Lh),lwd = 2, col ="green")
legend("topright", lwd = 2,col = c("red", "blue", "yellow", "green"),
       legend = c("L1", "Lh", "R(theta_0,Tx)", "R(theta_0,Sx)"))

#Excercice2
#a
grille = seq(0, 1, by = 0.01)
L = length(grille)
grille = grille[-c(1,L)]
x= rgeom(500, 0.6)+1
#b
n_pi = 500
v_beta = dbeta(grille, 1/2+n_pi, 3/2-n_pi+sum(x[1:n_pi]))
sp = spline(grille, v_beta, n=1000)
plot(sp,type="l", col="red", xlab = "Theta", ylab = "Beta",
     main = "Le Densite Des Lois")
n_pi = 100
v_beta = dbeta(grille, 1/2+n_pi, 3/2-n_pi+sum(x[1:n_pi]))
sp = spline(grille, v_beta, n=1000)
lines(sp, type="l", col="green")
n_pi = 20
v_beta = dbeta(grille, 1/2+n_pi, 3/2-n_pi+sum(x[1:n_pi]))
sp = spline(grille, v_beta, n=1000)
lines(sp, type="l", col="yellow")
n_pi = 5
v_beta = dbeta(grille, 1/2+n_pi, 3/2-n_pi+sum(x[1:n_pi]))
sp = spline(grille, v_beta, n=1000)
lines(sp, type="l", col="blue")

v_pi = dbeta(grille, 1/2, 3/2)
sp = spline(grille, v_pi, n=1000)
lines(sp, type="l", col="black")

abline(v=0.6, col="grey", lwd = 3)
legend("topright", lwd = 2,col = c("blue", "yellow", "green", "red", "black", "grey"),
       legend = c("n=5", "n=20", "n=100", "n=500", "pi(theta)","theta_0"))
#c
E_theta_x = (1/2+(1:500))/(2+cumsum(x[1:500]))
sp = spline(1:500, E_theta_x, n=1000)
plot(sp,type="l", col="red", xlab = "n", ylab = "Theta",
     main = "L'esperance A Posteriori")
abline(h=0.6)




#Exercice3
#3
a = 0.05
p_a = 1-(a/2)
A_10 = qnorm(p_a,sd = 1/10**0.5)
A_100 = qnorm(p_a,sd = 1/100**0.5)
A_1000 = qnorm(p_a, sd = 1/1000**0.5)
A_10;A_100;A_1000
#4
exp(0.1+0.5)
#5
grille_risque = seq(50,5000,200)
R_H1 = c()
for (n_3 in grille_risque) 
{
  v = pnorm(qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)-pnorm(-qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)
  R_H1 = append(R_H1, v)
}
sp = spline(grille_risque, R_H1, n=1000)
plot(sp,type="l", col="red", xlab = "n", ylab = "R_H1",
     main = "Risque De Deuxieme Espece")
abline(h=0.05)
n_0 = 0
for (n_3 in grille_risque) {
  s = pnorm(qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)
  b = pnorm(-qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)
  v= s-b 
  if(v<=0.05)
  {
    n_0 = n_3
    break
  }
}
n_0
#6
for (n_3 in 1:5000) {
  s = pnorm(qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)
  b = pnorm(-qnorm(p_a,sd = 1/n_3**0.5), mean = 0.1, sd=1/n_3**0.5)
  v= s-b
  if(v<=0.05)
  {
    n_0 = n_3
    break
  }
}
n_0
