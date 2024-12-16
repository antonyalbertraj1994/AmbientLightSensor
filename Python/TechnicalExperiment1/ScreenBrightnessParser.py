import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot as plt
from scipy.optimize import curve_fit
from sklearn.linear_model import LinearRegression


def error_distance(x, y):
    model = LinearRegression()
    model.fit(x.reshape(-1, 1), y)

    # Get the slope (m) and intercept (c) of the best-fit line
    m = model.coef_[0]
    c = model.intercept_
    distances = np.abs(m * x - y + c) / np.sqrt(m ** 2 + 1)

    # Total Euclidean error
    total_error = np.sum(distances)
    print("Error", int(total_error))

def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    x = []
    y = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[0])
        lightsensor_value = int(values[5])
        current = voltage / 220000.0
        if (lightsensor_value < 40000):
            x.append(lightsensor_value)
            y.append(voltage)
    print("Sample count", len(x))
    x = np.asarray(x)
    y = np.asarray(y)
    return x, y


def process():
    #figure, axis = plt.subplots(2, 2)
    filename_white = "white_response_pwm_zeroing.txt"
    filename_white_bright25 = "white_response_pwm_zeroing_bright25.txt"
    filename_white_bright50 = "white_response_pwm_zeroing_bright50.txt"
    filename_white_bright75 = "white_response_pwm_zeroing_bright75.txt"
    filename_white_bright100 = "white_response_pwm_zeroing_bright100.txt"

    x_white, y_white = readlines(filename_white)
    x_white_25, y_white_25 = readlines(filename_white_bright25)
    x_white_50, y_white_50 = readlines(filename_white_bright50)
    x_white_75, y_white_75 = readlines(filename_white_bright75)
    x_white_100, y_white_100 = readlines(filename_white_bright100)

    error_distance(x_white, y_white)
    error_distance(x_white_25, y_white_25)
    error_distance(x_white_50, y_white_50)
    error_distance(x_white_75, y_white_75)
    error_distance(x_white_100, y_white_100)

    plt.scatter(y_white, x_white,label="0Bright", color="green")
    plt.scatter(y_white_25, x_white_25,label="0Bright", color="red")
    plt.scatter(y_white_50, x_white_50,label="0Bright", color="gray")
    plt.scatter(y_white_75, x_white_75,label="0Bright", color="yellow")
    plt.scatter(y_white_100, x_white_100,label="0Bright", color="blue")

    plt.legend(['0 Brightness','25 Brightness','50 Brightness','75 Brightness','100 Brightness'])

    plt.show()

if __name__ == '__main__':
    process()
    #find_correlation()
    print("Stared")