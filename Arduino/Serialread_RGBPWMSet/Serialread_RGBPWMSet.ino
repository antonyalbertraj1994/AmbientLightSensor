#define R 5
#define G 6
#define B 9
int value_tot = 0;
void setup() {
  // put your setup code here, to run once:
pinMode(A0, INPUT);
Serial.begin(9600);
pinMode(3, OUTPUT);
pinMode(R, OUTPUT);
pinMode(G, OUTPUT);
pinMode(B, OUTPUT);
pinMode(A5, INPUT);
pinMode(A4, INPUT);

}

void loop() {
int g = analogRead(A5);
//Serial.println(g);
  // put your main code here, to run repeatedly:
if(Serial.available() >0){

String val =  Serial.readStringUntil("\n");
String h = val.substring(1);

int value = h.toInt();
if(val[0] == 'r'){
  analogWrite(R, value);
}

if(val[0] == 'g'){
  analogWrite(G, value);
}

if(val[0] == 'b'){
  analogWrite(B, value);
}
//int pwm = int(value/4.0);
value_tot = value;
//analogWrite(3, value);
Serial.print(val[0]);
Serial.println(value);
delay(10);
}
value_tot = int(g/10);
//analogWrite(3, value_tot);
digitalWrite(3, HIGH);

if(value_tot!=0) {
  digitalWrite(3, HIGH);
  delayMicroseconds(value_tot);
  digitalWrite(3, LOW);
  delayMicroseconds(100 - value_tot);
} else if(value_tot == 0) {
  digitalWrite(3, LOW);
}

}
