void setup() {
  // put your setup code here, to run once:
Serial.begin(115200);
pinMode(13, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
if(Serial.available() > 0) {
  String value1 = Serial.readStringUntil('\n');
  if(value1 == "1"){
    digitalWrite(13,HIGH);
  } 
  if(value1 == "0") {
    digitalWrite(13, LOW);
  }
}
}
