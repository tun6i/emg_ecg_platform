#include <SoftwareSerial.h>
#include <FlexiTimer2.h>
#define INPUT_CH1 A0
#define INPUT_CH2 A1
#define INPUT_CH3 A2
#define INPUT_CH4 A3
#define INPUT_CH5 A4
#define INPUT_CH6 A5

/*
 * Bluetooth connection text
 * RX = receive
 * tx = transmit
 */
const byte RX_PIN = 10; 
const byte TX_PIN = 11;
SoftwareSerial btSerial(RX_PIN, TX_PIN);

/*
 * Byte buffer package
 * buffer[0] For synchronization
 * buffer[1] in the stream ---> 1337 value
 * buffer[2] CH1 hightByte
 * buffer[3] CH1 lowByte
 * buffer[4] CH2 highByte
 * buffer[5] CH2 lowByte
 * buffer[6] CH3 highByte
 * buffer[7] CH3 lowByte
 * buffer[8] CH4 highByte
 * buffer[9] CH4 lowByte
 * buffer[10] CH5 highByte
 * buffer[11] CH5 lowByte
 * buffer[12] CH6 highByte
 * buffer[13] CH6 lowByte
 */
byte buffer[14];

void readEMG () {
  int valueCH1 = analogRead(INPUT_CH1);
  int valueCH6 = analogRead(INPUT_CH6);
  int valueCH3 = analogRead(INPUT_CH3);
  
  buffer[2] = highByte(valueCH1);
  buffer[3] = lowByte(valueCH1);
  buffer[6] = highByte(valueCH3);
  buffer[7] = lowByte(valueCH3);
  buffer[12] = highByte(valueCH6);
  buffer[13] = lowByte(valueCH6);
  
  //btSerial.write(highByte(valueCH1));
  //btSerial.write(lowByte(valueCH1));

  btSerial.write(buffer, 14);
  Serial.println(valueCH1);
  btSerial.flush();
}

void setup() {

  noInterrupts();
  /*
   * Default EMG value = 1024/2 = 512
   */
  buffer[0] = 0x05;
  buffer[1] = 0x39;
  buffer[2] = 0x02; 
  buffer[3] = 0x00;
  buffer[4] = 0x02;
  buffer[5] = 0x00;
  buffer[6] = 0x02;
  buffer[7] = 0x00;
  buffer[8] = 0x02;
  buffer[9] = 0x00;
  buffer[10] = 0x02;
  buffer[11] = 0x00;
  buffer[12] = 0x02;
  buffer[13] = 0x00;
  FlexiTimer2::set(4, readEMG);
  FlexiTimer2::start();

  Serial.begin(57600);
  while (!Serial) {
    ;
  }
  btSerial.begin(57600);

  interrupts();
}

void loop() {
}


