

import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot as plt


def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    attempt = []
    for line in lines:
        values = line.split(",")
        attempt_val = int(values[0])
        voltage = int(values[1])
        attempt.append(attempt_val)
        lightsensor_value = int(values[3])
        current = voltage / 220000.0
        if True:
            x.append(lightsensor_value)
            y.append(voltage)
    print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    attempt = np.asarray(attempt)
    return attempt, x, y


def process():
    res = 2000
    #figure, axis = plt.subplots(2, 2)

    filename_white_1 = "white_dac_zeroing_bright_0_2kres_10attempts_100stepsize (1).txt"
    attempt, x_white_2k, y_white_2k = readlines(filename_white_1)
    #plt.scatter(y_white_2k/res,x_white_2k, color = 'green', marker=".")
    dict_values = {}
    #print(x_white_2k)
    print(np.unique(y_white_2k))  #y_white_2k in voltage mV
    voltage_values = np.unique(y_white_2k)
    dict_1 = {}
    sd_value = []

    for i in range(len(voltage_values)):
        indices = np.where(y_white_2k == voltage_values[i])[0]
        dict_1[i] = indices
        g = x_white_2k[indices]
        print(g,"indices")
        sd = np.var(g)
        mean = np.mean(g)
        min = np.min(g)

        diff = np.abs(g - mean)
        max_diff = np.max(diff)
        diff1 = max_diff - min
        sd_value.append(diff1)

        print("min,max_diff", min, max_diff)
    plt.plot(voltage_values,sd_value)
    #("dict1", dict_1)
    for i, value in enumerate(y_white_2k):
        #print(i,  value)
        if value not in dict_values:
            dict_values[value] = x_white_2k[i]
        else:
            dict_values[value] += x_white_2k[i]
    #plt.xlim(0,0.05)
    print("sdvalues", sd_value)
    #print("dict", dict_values)
    #print("key length", len(dict_values.keys()))

    # for key in dict_values.keys():
    #     print("key", dict_values[key]/10)
    # i = 0
    # checkvalues = x_white_220
    # checkvalues1 = y_white_220
    # step = 2
    # while i < len(checkvalues) - step:
    #
    #     print(checkvalues[i + step] - checkvalues[i], (checkvalues1[i + step] - checkvalues1[i])/res)
    #     i += step
    # plt.scatter(y_white_220/res,x_white_220, color = 'green')
    # plt.scatter(y_white_2k/res,x_white_2k, color = 'red')

    plt.show()


if __name__ == '__main__':
    process()