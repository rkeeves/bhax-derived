# [Compatibility for COLLAB]
# %tensorflow_version 1.x
import tensorflow
# [Compatibility for NON COLLAB]
# import tensorflow.compat.v1 as tensorflow
# tensorflow.disable_v2_behavior()
import numpy
import matplotlib.pyplot

x = numpy.random.uniform(-1,1,10000)

e_data = numpy.random.normal(1,.05,10000)

y = .3 * x_data + .1 + e_data


b = tensorflow.Variable(.0,name="b")
m = tensorflow.Variable(.0,name="m")


opt = tensorflow.optimizers.SGD(.008)

# y=mx+b
def train():
  with tensorflow.GradientTape() as tape:
    lin_model = tensorflow.add(tensorflow.multiply(m,x), b )
    loss = tensorflow.reduce_mean(tensorflow.square(lin_model - y),name="error")
    grads = tape.gradient(loss, [m,b])
    opt.apply_gradients(zip(grads, [m,b]))

i=0;
prev = 0;
while True:
  train()
  print(m.numpy(),b.numpy())
	#matplotlib.pyplot.figure()
	#matplotlib.pyplot.plot(x,y,"r^")
	#matplotlib.pyplot.plot(x,m.numpy() * x + b.numpy())
	#matplotlib.pyplot.savefig("linearreg"+i+".png")
  i = i+1
  if abs(prev-m.numpy()) < .00001 and i > 1000:
    break
  prev = m.numpy()