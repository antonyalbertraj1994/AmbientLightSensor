void setup() {
  // put your setup code here, to run once:
pinMode(12, INPUT_PULLUP);
Serial.begin(9600);
pinMode(12, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
int a = digitalRead(12);
if(a==1){
analogWrite(3, 255);
delay(500);
}else
{
analogWrite(3, 0);
delay(500);
}

Serial.println(a);
delay(600);
}
