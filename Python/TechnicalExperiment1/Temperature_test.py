import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot as plt
from scipy.optimize import curve_fit


def readlines1(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[1])
        lightsensor_value = int(values[4])
        current = voltage / 220000.0
        if (lightsensor_value < 10000 and lightsensor_value   > 100):
            x.append(lightsensor_value)
            y.append(voltage)
    #print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return x, y


def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[0])
        lightsensor_value = int(values[3])
        current = voltage / 220000.0
        if (lightsensor_value < 20000 and lightsensor_value   > 100):
            x.append(lightsensor_value)
            y.append(voltage)
    #print("Sample count", len(x))
    voltage = np.asarray(y)
    light = np.asarray(x)
    return light, voltage



def process():
    filename_white = "white_dac_zeroing_5k_bright100.txt"
    # filename_white_1 = white_dac_zeroing_bright_0_2kres_10attempts
    x_white, y_white = readlines1(filename_white)
    print("len", len(x_white),x_white)
    coefficients = np.polyfit(x_white, y_white, 2)
    function = np.poly1d(coefficients)
    print("coefficient",coefficients, x_white[-1])
    plt.scatter(x_white, y_white)
    plt.show()
    res = 1
    #figure, axis = plt.subplots(2, 2)
    #filename_temp = "white_temperature_test_100.txt"
    #light, voltage = readlines(filename_temp)

    scaling = x_white[-1] / np.max(light)
    #scaling = 1
    print("Scaling", scaling)
    print(light, voltage)
    sensor_scale = 10
    plt.scatter(voltage, voltage /sensor_scale , color='green')
    out_value = []
    out_value1 = []

    print("len1", len(voltage))
    diff_array = []
    count = 0

    y_fit = function(light * scaling)
    light_scaled = light * scaling
    for j  in range(len(light)):
        y_fitted = coefficients[0] * light_scaled[j] * light_scaled[j] + coefficients[1]*light_scaled[j] + coefficients[2]
        #plt.scatter(voltage[j], y_fitted / sensor_scale, marker="+")
        count += 1
    mse = ((y_fit/sensor_scale - voltage/sensor_scale)**2)
    sum_error = np.sum(np.sqrt(mse))
    print("root mean error", sum_error)
    plt.scatter(voltage, y_fit/sensor_scale, marker="+")

    # for i, volt in enumerate(voltage):
    #     #print("temp", volt)
    #     voltage1 = slope * light[i] + intercept
    #     actual_temp = volt / sensor_scale
    #     fit_temp = voltage1 / sensor_scale
    #     diff_temp = np.abs(fit_temp - actual_temp)
    #     diff_array.append(diff_temp)
    #     print("Fit Temperature", fit_temp,"Actual Temperature", actual_temp, "Diff temp", diff_temp, light[i])
    #     out_value.append(voltage1/sensor_scale)
    #     out_value1.append(voltage1)
    #     plt.scatter( volt, voltage1/sensor_scale,color='red', marker="+")
    #     #plt.plot
    #     #print(count)
    #     count += 1

    #print("Coer", np.corrcoef(voltage/sensor_scale, out_value ))
    #print("error _average", np.mean(diff_array))
    #print(out_value)
    plt.show()
if __name__ == '__main__':
    process()