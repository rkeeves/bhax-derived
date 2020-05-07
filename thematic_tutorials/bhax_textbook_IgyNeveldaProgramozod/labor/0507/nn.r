library(neuralnet)

x1 <- c(0,0,1,1)
x2 <- c(0,1,0,1)
y <- c(0,1,1,1)

or.data <- data.frame(x1, x2, y)

nn.or <- neuralnet(
  y~x1+x2, 
  or.data, 
  hidden = 0, 
  linear.output = FALSE)

plot(nn.or);

print(nn.or);

compute(nn.or, or.data[,1:2]);







library(neuralnet)

x1 <- c(0,0,1,1)
x2 <- c(0,1,0,1)
y1 <- c(0,1,1,1)
y2 <- c(0,0,0,1)
orand.data <- data.frame(x1, x2, y)

nn.orand <- neuralnet(
  y1+y2~x1+x2, 
  or.data, 
  hidden = 0, 
  linear.output = FALSE)

plot(nn.orand);

print(nn.orand);

compute(nn.orand, orand.data[,1:2]);







library(neuralnet)

x1 <- c(0,0,1,1)
x2 <- c(0,1,0,1)
y <- c(0,1,1,0)

exor.data <- data.frame(x1, x2, y)

nn.exor <- neuralnet(
  y~x1+x2, 
  exor.data, 
  hidden = 0, 
  linear.output = FALSE,threshold=.00000001)

plot(nn.exor);

print(nn.exor);

compute(nn.exor, exor.data[,1:2]);






library(neuralnet)

x1 <- c(0,0,1,1)
x2 <- c(0,1,0,1)
y <- c(0,1,1,0)

exor.data <- data.frame(x1, x2, y)

nn.exor <- neuralnet(
  y~x1+x2, 
  exor.data, 
  hidden = c(6,4,6), 
  linear.output = FALSE,
  threshold=.000001)

plot(nn.exor);

print(nn.exor);

compute(nn.exor, exor.data[,1:2]);