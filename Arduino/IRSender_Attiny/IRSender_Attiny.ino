#include <IRremote.h>

const byte butonPin = 2; //button input
const byte IRPin = 1; // LED output

void setup()
{
   // connect button to ground and input
   pinMode(butonPin, INPUT_PULLUP);

   IrSender.begin(IRPin);
}

uint16_t sAddress = 0x0102;
uint8_t sCommand = 0x29;
uint16_t s16BitCommand = 0x5634;
uint8_t sRepeats = 0;
void loop()
{
   static bool lastButtonState = HIGH;
   bool buttonState = digitalRead(butonPin);
   //IrSender.sendNEC(0x3D00FF, 32);
   IrSender.sendSony(sAddress & 0x1F, sCommand & 0x7F, sRepeats);
   delay(2000);

}
