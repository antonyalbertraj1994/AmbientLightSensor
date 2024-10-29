void setup() {
  // put your setup code here, to run once:
pinMode(A0, INPUT);
Serial.begin(9600);
pinMode(3, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
if(Serial.available() >0){

String val =  Serial.readStringUntil('\n');
int value = val.toInt();
//int pwm = int(value/4.0);

analogWrite(3, value);
Serial.println(value);
delay(600);
}

}
