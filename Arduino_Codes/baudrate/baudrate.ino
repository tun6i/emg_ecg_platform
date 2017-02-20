#include <SoftwareSerial.h>
const byte RX_PIN = 10;
const byte TX_PIN = 11;
SoftwareSerial mySerial(RX_PIN, TX_PIN);
const int signalEKG = A0;

void setup() {
  mySerial.begin(9600);
  mySerial.print("AT");
  delay(100);
  mySerial.print("AT+NAMEHC-06-arduino-3"); //Change name here
  delay(100);
  mySerial.print("AT+PIN1234"); //Change pin here
  delay(100);
  mySerial.print("AT+BAUD7"); // Set baudrate to 57600
  delay(1000);
}

void loop() {
  /*int data = analogRead(signalEKG);
  mySerial.println(data);*/
  
  /*mySerial.print("$$$");
  delay(1000);
  mySerial.println("SF,1");
  //mySerial.println("SN,SparkfunBtModule");
  //delay(200);
  //mySerial.println("SM,0");
  delay(200);
  mySerial.println("SU,57");
  delay(200);
  mySerial.println("R,1");*/
}

