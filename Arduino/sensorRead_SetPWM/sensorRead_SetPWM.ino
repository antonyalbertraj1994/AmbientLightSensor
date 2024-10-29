void setup() {
  // put your setup code here, to run once:
pinMode(A0, INPUT);
Serial.begin(9600);
pinMode(3, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:
int a = analogRead(A0);
int pwm = int(a/4.0);
analogWrite(3, pwm);
Serial.println(pwm);
delay(600);
}
