The install of lightGBM:
Considering that lightgbm is a relatively new package. Here is how to install it. Thanks.

https://github.com/Microsoft/LightGBM

for linux:
git clone --recursive https://github.com/Microsoft/LightGBM ; cd LightGBM
mkdir build ; cd build
cmake .. 
make -j 

for mac:
brew install cmake
brew install gcc --without-multilib
git clone --recursive https://github.com/Microsoft/LightGBM ; cd LightGBM
mkdir build ; cd build
cmake -DCMAKE_CXX_COMPILER=g++-6 -DCMAKE_C_COMPILER=gcc-6 .. 
make -j 


@@Fubang ZHAO