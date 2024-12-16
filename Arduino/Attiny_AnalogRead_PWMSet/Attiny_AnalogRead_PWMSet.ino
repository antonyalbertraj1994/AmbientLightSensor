#define R 5
#define G 6
#define B 9
int value_tot = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(0, OUTPUT);
pinMode(3, INPUT);

}

void loop() {
//digitalWrite(0, HIGH);
//delay(150);
//digitalWrite(0, LOW);
//delay(150);
int g = analogRead(3);

int value_tot = (g/10);



if(value_tot!=0) {
  digitalWrite(0, HIGH);
  delayMicroseconds(value_tot);
  digitalWrite(0, LOW);
  delayMicroseconds(100 - value_tot);
} else if(value_tot == 0) {
  digitalWrite(0, LOW);
}

}
