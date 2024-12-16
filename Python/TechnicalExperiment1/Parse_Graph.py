import numpy as np
import seaborn as sns
import pandas as pd
from matplotlib import pyplot, pyplot as plt

def readlines(filename):
    f = open(filename, 'r')
    lines = f.readlines()
    lux_array = []
    voltage_array = []
    for line in lines:
        values = line.split(",")
        voltage = int(values[1])
        lightsensor_value = int(values[3])
        #current = voltage / 220000.0
        if (lightsensor_value>100 and lightsensor_value < 60000):
            lux_array.append(lightsensor_value)
            voltage_array.append(voltage)
    #print("Sample count", len(x))
    lux_array = np.asarray(lux_array)
    voltage_array = np.asarray(voltage_array)
    return voltage_array, lux_array

def fit_line(voltage, lux ):
    coefficients = np.polyfit(voltage, lux, 1)
    function = np.poly1d(coefficients)
    #print("coefficient",coefficients, x_white[-1])

    #scaling = x_white[-1] / np.max(light)

    scaling = 1
    lux_fit = function(voltage * scaling)

    mse = ((lux - lux_fit)**2)
    sum_error = np.sum(np.sqrt(mse))
    print("root mean error", sum_error)
    #plt.scatter(voltage, y_fit/sensor_scale, marker="+")
    return sum_error, lux_fit
def process():
    figure, axis = plt.subplots(1, 3, figsize = (10,4))
    sns.set_theme()

    brightness = [0, 50,100]
    res = 5000
    #plt.subplots_adjust(left=0.1, bottom= 0.1, right=0.9, top=0.9, wspace=0.4, hspace=0.4)
    for i, bright in enumerate(brightness):
        print("i",i)
        filename = "white_dac_zeroing_5k_bright" + str(bright) + ".txt"
        voltage, lux = readlines(filename)
        error, lux_fitted = fit_line(voltage, lux)

        data = pd.DataFrame()
        data['Current (mA)'] = voltage / res
        data['Lux'] = lux
        print("Data", data)

        data_fit = pd.DataFrame()
        data_fit['Current (mA)'] = voltage / res
        data_fit['Lux'] = lux_fitted
        print("head", data.head())
        #sns.relplot(data=data)
        #data = pd.DataFrame({'A': [1, 2, 3], 'B': [4, 5, 6], 'C': [7, 8, 9]})

        # plt.subplot(1,3,i + 1)
        # plt.scatter(x_white_100/res, y_white_100)
        # plt.plot(x_white_100/res, y_fitted, color = 'red') # Fitted line
        # plt.title("Brightness=" + str(bright))
        # plt.xlabel("Current (mA)")
        # plt.ylabel("Lux")
        sns.scatterplot(data=data,x="Current (mA)", y="Lux",  ax=axis[i], marker="o", color='red')
        sns.lineplot(data=data_fit,x="Current (mA)", y="Lux",  ax=axis[i], color='blue')

        axis[i].set_title("Brightness=" + str(bright))

    #figure.suptitle("LED current vs Lux for different screen brightness", ha='center')
    figure.text(0.5,0.04, "LED current vs Lux for different screen brightness", ha='center', va='center', fontsize =10, fontweight='bold')
    #plt.legend(brightness)
    # axis[0, 0].scatter(y_red, x_red, label='RED', color='red', marker="+")
    # axis[0, 1].scatter(y_red_0, x_red_0, label='RED', color='red', marker=">")
    # axis[0, 0].scatter(y_blue, x_blue, label='Blue', color='blue')
    plt.tight_layout(pad=2)
    plt.show()

if __name__ == '__main__':
    process()
    print("Stared")


