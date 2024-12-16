#include <TimerOne.h>
double buffer[10] = {0,0,0,0,0,0,0,0,0,0};
double buffer_size[10] = {0,0,0,0,0,0,0,0,0,0};

int i =0;
volatile unsigned long state = 0;
unsigned long state_copy=0;
int delayms = 20;

int total = 0;
double time1 = 0, time2 = 0, elapsed = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(2, INPUT);
pinMode(3, OUTPUT);
pinMode(13, OUTPUT);
pinMode(A1, INPUT);
pinMode(A2, INPUT);

//attachInterrupt(digitalPinToInterrupt(2), blink, FALLING);
Serial.begin(9600);
analogReference(INTERNAL);

}

void loop() {
  int thresh = 150;

  int pinselected = A1;

  while(true) {
    int g = analogRead(pinselected);
    if (g < thresh){
      //Serial.println(g);
      break;
    }
  }

  delay(delayms);
  //Serial.println(analogRead(A1));
  //int r = analogRead(pinselected);

  byte recevieddata  = 0x00;
  int start = 0;
  int end = delayms;
  // while(analogRead(A1) < thresh) {

  // }

  for(int i = 0; i <8; i++) { //0,100  100,200   200,300   300,400  400,500, 500,600 600,700, 700,800
    float total = 0, total_samples = 0;
    unsigned long time1 = millis();
    while((millis() - time1) >= start && (millis() - time1) <= end) {
      int f = analogRead(pinselected);
      //Serial.println(f);
      if(f > thresh)
        total += 1;
      total_samples += 1;
    }
    
    //Serial.println(i);
    buffer[i] = total/(total_samples * 1.0f);
    buffer_size[i] = total_samples;

    //start = end;
    //end = end + delayms;
  }
  delay(delayms - 10);
  while(analogRead(pinselected) <= thresh) {
  }

  for(int j = 7; j >= 0; j--) {
    //Serial.print(bitRead(recevieddata, i));
      //Serial.println(buffer[j]);
      //Serial.println(buffer_size[j]);
      if(buffer[j] > 0.75) {
        Serial.print("1");
      }
      else {
      Serial.print("0");
      }
  }
  Serial.println("");
}



