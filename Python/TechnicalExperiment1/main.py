# This is a sample Python script.
import serial
# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

from printrun.printcore import printcore
from printrun import gcoder
import time
from tkinter import Tk, Button, font, DISABLED
import serial
ser  = serial.Serial(port='COM14', baudrate="115200")
stepsize_button = 5
pos_x = 0
pos_y = 0

global x_start, y_start, x_end, y_end, stepsize_scan
stepsize_scan = 5
x_start = 50
y_start = 50
x_end = 100
y_end = 100

while ser.is_open == False:
    pass

window = Tk()
window.geometry("300x300")

p = printcore('COM16', 250000) # or p.printcore('COM3',115200) on Windows
while not p.online:
    time.sleep(1)

bold_font = font.Font(family = "Arial", size = 12, weight= "bold")
global start_experiment
start_experiment = None

def up():
    global pos_x, pos_y
    print("UP")
    pos_y += stepsize_button
    send_string = "G0" + " X" + str(pos_x) + " Y" + str(pos_y) + " Z50"
    p.send_now(send_string)

def down():
    global pos_x, pos_y

    print("Down")
    pos_y -= stepsize_button
    send_string = "G0" + " X" + str(pos_x) + " Y" + str(pos_y) + " Z50"
    p.send_now(send_string)

def left():
    global pos_x, pos_y
    print("Left")
    pos_x += stepsize_button
    send_string = "G0" + " X" + str(pos_x) + " Y" + str(pos_y) + " Z50"
    p.send_now(send_string)

def right():
    global pos_x, pos_y
    print("Right")
    pos_x -= stepsize_button
    send_string = "G0" + " X" + str(pos_x) + " Y" + str(pos_y) + " Z50"
    p.send_now(send_string)

def start():
    global start_experiment, x_start, y_start, x_end, y_end, stepsize_scan

    start_experiment["state"] = DISABLED
    scan(x_start, y_start, x_end, y_end, stepsize_scan)

def center():
    global x_start, y_start, x_end, y_end
    center_x = (x_start + x_end) / 2
    center_y = (y_start + y_end) / 2
    ser.write("1\n".encode('utf-8'))
    send_string = "G0" + " X" + str(center_x) + " Y" + str(center_y) + " Z50"
    p.send_now(send_string)

def setupUI():
    print("Setting up UI")
    global start_experiment

    button_up = Button(window, text="\u2191", font=bold_font, command=up, width=5, height=2)
    button_up.pack(pady=20)
    button_up.place(x=50, y=50)

    button_down = Button(window, text = "\u2193", command=down, width=5, height=2)
    button_down.pack(pady=20)
    button_down.place(x=60, y=150)

    button_right = Button(window, text = "\u2192", command=right, width=5, height=2)
    button_right.pack(pady=20)
    button_right.place(x=120, y=100)

    button_left = Button(window, text = "\u2190", command=left, width=5, height=2)
    button_left.pack(pady=20)
    button_left.place(x=0, y=100)

    start_experiment = Button(window, text = "START", command=start, width=5, height=2)
    start_experiment.pack()
    start_experiment.place(x = 200, y = 200)

    move_center = Button(window, text = "CENTER", command=center, width=5, height=2)
    move_center.pack()
    move_center.place(x = 150, y = 200)
    window.mainloop()

def scan(x1, y1, x2, y2, stepsize):
    x_pos = x1
    #global x_start, y_start

    send_string = "G0" + " X" + str(x1) + " Y" + str(y1) + " Z50"
    p.send_now(send_string)
    time.sleep(5)

    print("Scanning start")
    while x_pos < x2:
        y_pos = y1
        while y_pos < y2:
            send_string  = "G0" +" X" + str(x_pos) + " Y" + str(y_pos)
            print(send_string)
            p.send_now(send_string)
            time.sleep(1)
            ser.write("1\n".encode('utf-8'))
            time.sleep(1)
            ser.write("0\n".encode('utf-8'))
            y_pos += stepsize
        x_pos += stepsize
        send_string = "G0" + " X" + str(x_pos) + " Y" + str(y_pos)
        p.send_now(send_string)
        time.sleep(5)

        #p.send_now(send_string)

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    p.send_now("G28")
    setupUI()

    # ser.write("0\n".encode('utf-8'))
    #
    # p.send_now("G28")
    # time.sleep(5)
    # start_x = 50
    # start_y = 50
    # send_string = "G0" + " X" + str(start_x) + " Y" + str(start_y) + " Z50"
    # p.send_now(send_string)
    # time.sleep(10)
    #
    # scan(start_x,start_y, 80,80)
    # #p.send_now("G0 X12 Y12 Z10")
    # p.disconnect()

