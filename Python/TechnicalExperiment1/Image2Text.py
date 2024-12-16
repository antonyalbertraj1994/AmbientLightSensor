




from PIL import Image

import pytesseract

# If you don't have tesseract executable in your PATH, include the following:
pytesseract.pytesseract.tesseract_cmd = r'C:\Users\antony.irudayaraj\Desktop\VRTextEntry\Tesseract\tesseract.exe'
# Example tesseract_cmd = r'C:\Program Files (x86)\Tesseract-OCR\tesseract'

# Simple image to string
print("Afgd")
print(pytesseract.image_to_string(Image.open('A.png')))
print("SDfdsg")
# In order to bypass the image conversions of pytesseract, just use relative or absolute image path
# NOTE: In this case you should provide tesseract supported images or tesseract will return error
#print(pytesseract.image_to_string('A.png'))