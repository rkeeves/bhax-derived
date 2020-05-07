# %tensorflow_version 1.13
import tensorflow
import numpy
import matplotlib.pyplot

x_data = numpy.random.uniform(-1,1,10)
e_data = numpy.random.normal(1,.05,10)

y_data = .3 * x_data + .1 + e_data


b = tensorflow.Variable(.0,name="b")
m = tensorflow.Variable(.0,name="m")

x = tensorflow.placeholder(tensorflow.float32,[10],name="x_data")
y = tensorflow.placeholder(tensorflow.float32,[10],name="y_data")

# y=mx+b
lin_model = tensorflow.add(tensorflow.multiply(m,x), b )

loss = tensorflow.reduce_mean(tensorflow.square(lin_model - y),name="error")

opt = tensorflow.train.GradientDescentOptimizer(.5)

train = opt.minimize(loss)

init = tensorflow.global_variables_initializer()
session = tensorflow.Session()
session.run(init)

i=0;
prev = 0;
while True:
	session.run(train,{x:x_data, y:y_data})
	print(session.run(m),session.run(b))
	matplotlib.pyplot.figure()
	matplotlib.pyplot.plot(x_data,y_data,"r^")
	matplotlib.pyplot.plot(x_data,session.run(m) * x_data +session.run(b))
	matplotlib.pyplot.savefig("linearreg"+str(i)+".png")
	i = i+1
	if abs(prev-session.run(m)) < .001:
		break
	prev = session.run(m)

tensorflow.summary.FileWriter("C:/Users/ubmcgu/tmp/lr",session.graph)