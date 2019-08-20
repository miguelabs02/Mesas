#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
#include <FirebaseArduino.h>

#ifndef STASSID
#define STASSID "AndroidAPabef"
#define STAPSK  "1234prueba"
#endif

#define FIREBASE_HOST "mesas-base-de-dats.firebaseio.com" //link of api
#define FIREBASE_AUTH "0f7BsbmAnZtZ6xvLBCUm4BPz8o2MlrPrwrOsSoDt" //database secret

const char* ssid     = STASSID;
const char* password = STAPSK;
const String silla = "Silla/1/Estado"; //Se define la silla a la cual pertenece este sensor
String estado;

void setup() {
  Serial.begin(115200);

  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);             //connect to Database
  delay(1000);
}

void loop() {
  Serial.flush();
  String hxreading = "" ;
  if (Serial.available() ){
    char c = Serial.read();
    while ( c != '\n' ){ //Hasta que el caracter sea intro != significa diferente a... 
      hxreading = hxreading + c ;
      c = Serial.read();
    }
     Serial.println(hxreading);
  }
  else {return;}
  delay(500);
  if(hxreading == "1" || hxreading == "0"){ //Modificar a partir de aquí
    if(hxreading=="1"){
      estado = "ocupado";
    }
    else{
      estado = "desocupado";
    }
    Serial.println(estado);
    Firebase.setString(silla,estado);
  }
  delay(1000);
}
