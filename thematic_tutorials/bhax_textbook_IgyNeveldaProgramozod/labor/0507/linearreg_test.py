import numpy as np
import matplotlib.pyplot as matplot

x_data = np.random.uniform(-1,1)
e_data = np.random.normal(1,.05)
y_data = .3 * x_data + .1 + e_data
matplot.figure()
matplot.plot(x_data,y_data,"r^")