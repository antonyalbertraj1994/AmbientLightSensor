

import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot as plt

def linear_model(x1, m, c):
    return m * x1 + c


def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[1])
        lightsensor_value = int(values[4])
        current = voltage / 220000.0
        if (lightsensor_value < 40000 and lightsensor_value   > 100):
            x.append(lightsensor_value)
            y.append(voltage)
    #print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return x, y


def process():
    res = 1
    #figure, axis = plt.subplots(2, 2)
    filename_white = "white_dac_zeroing_220.txt"
    filename_white_2k = "yellow_dac_zeroing_100.txt"
    #filename_white_1 = white_dac_zeroing_bright_0_2kres_10attempts
    x_white_220, y_white_220 = readlines(filename_white)
    x_white_2k, y_white_2k = readlines(filename_white_2k)

    #params, covariance = curve_fit(linear_model, y, x)
    # m, c = params
    # y_fit = linear_model(x_white_2k, m, c)

    coefficients = np.polyfit(x_white_2k, y_white_2k, 2)
    function = np.poly1d(coefficients)
    y_fit = function(x_white_2k)
    #slope, intercept = coefficients
    #y_fit = slope * x_white_2k + intercept
    bincount =  879.9
    # for i in range(0, 10000, bincount):
    #
    #     plt.axhline(y = i)

    #x_value = 562
    #g = slope * x_value + intercept
    #print("G", g, intercept, slope)
    #index = np.abs(x_white_2k - x_value).argmin()
    #print(int(x_value / bincount), g, y_white_2k[index])
    #print("temperature", g/10, "acutal", 120/10)
    #plt.plot(g,x_value,marker="+", color = "black")

    plt.plot(y_fit/res, x_white_2k, label=" Fit", color="green")
    #plt.scatter(y_white_220, x_white_220 , color="red",marker=".")
    plt.scatter(y_white_2k/res, x_white_2k , color="red",marker=".")

    plt.ylabel("Lux Values")
    plt.xlabel("Current (mA)")
    plt.title(" Lux vs Current")
    #y_fit = linear_model(y, m, c)

    i = 0
    checkvalues = x_white_220
    checkvalues1 = y_white_220
    step = 2
    while i < len(checkvalues) - step:
        #print(checkvalues[i + step] - checkvalues[i], (checkvalues1[i + step] - checkvalues1[i])/res,checkvalues1[i + step],checkvalues[i + step] )
        i += step
    #plt.scatter(y_white_220/res,x_white_220, color = 'green')
    #plt.scatter(y_white_2k/res,x_white_2k, color = 'red')

    plt.show()


if __name__ == '__main__':
    process()