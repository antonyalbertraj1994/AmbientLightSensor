import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot, pyplot as plt


def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[1])
        lightsensor_value = int(values[3])
        current = voltage / 220000.0
        if (lightsensor_value < 10000):
            x.append(lightsensor_value)
            y.append(voltage)
    #print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return y, x


def process():
    res = 5000
    #filename = "white_dac_zeroing_100_bright" + str(bright) + ".txt"
    filename = "white_dac_zeroing_5k_bright100.txt"
    x_white_100, y_white_100 = readlines(filename)
    #plt.subplot(1, 3, i + 1)
    plt.scatter(x_white_100 / res, y_white_100)
    plt.show()





if __name__ == '__main__':
    process()