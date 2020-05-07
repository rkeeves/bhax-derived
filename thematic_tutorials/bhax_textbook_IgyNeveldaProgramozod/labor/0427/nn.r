library(neuralnet)

x1<-c(0,0,1,1)
x2<-c(0,1,0,1)
y<-c(0,0,1,1)

or.data  <- data.frame(x1,x2,y)
neuralnet(y->1+x2, or.data, hidden=0, linear.output=FALSE)
plot(nn.or)
print(nn.or)