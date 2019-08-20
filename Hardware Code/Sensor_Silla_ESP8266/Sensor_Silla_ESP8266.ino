//Sensor_Silla_ESP8266.ino
#include <ESP8266WiFi.h>
#include <SoftwareSerial.h>
#include <FirebaseArduino.h> //Se incluye la librería para conectar con Firebase

#ifndef STASSID
#define STASSID "AndroidAPabef" //Se define la red wifi a la cual se quiere conectar.
#define STAPSK  "1234prueba" //Se define la contraseña de la red wifi.
#endif

#define FIREBASE_HOST "mesas-base-de-dats.firebaseio.com" //Link del API del proyecto en Firebase
#define FIREBASE_AUTH "0f7BsbmAnZtZ6xvLBCUm4BPz8o2MlrPrwrOsSoDt" //Database secret del proyecto en Firebase

const char* ssid     = STASSID; //Variable que contiene la red wifi.
const char* password = STAPSK; //Variable que contiene la contraseña de la red wifi.
const String silla = "Silla/1/Estado"; //Se define la silla a la cual pertenece este sensor.
String estado; //Se define la variable el cual contendrá el estado de la silla sea ocupado o desocupado.

void setup() {
  Serial.begin(115200);
  //Se comienza la conexión a la red wifi.
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  /* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
     would try to act as both a client and an access-point and could cause
     network-issues with your other WiFi-devices on your WiFi-network. */
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) { //Se crea un bucle del cual no se saldrá hasta que se logre conectar con la red wifi definida.
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH); //Se conecta a la base de datos de Firebase.
  Serial.println("Conexión a Firebase exitosa");
  delay(1000);
}

void loop() {
  Serial.flush();
  String hxreading = "" ;
  if (Serial.available() ){
    char c = Serial.read(); //Se lee cada caracter enviado por comunicación Serial
    while ( c != '\n' ){ //Hasta que el caracter sea diferente a "\n".
      hxreading = hxreading + c ; //Se completa el String enviado por comunicación Serial.
      c = Serial.read();
    }
     Serial.println(hxreading);
  }
  else {return;}
  delay(500);
  if(hxreading == "1" || hxreading == "0"){
    if(hxreading=="1"){ //Si el mensaje recibido es 1 entonces estado será ocupado.
      estado = "ocupado";
    }
    else{ //Si el mensaje recibido es 0 entonces estado será desocupado.
      estado = "desocupado";
    }
    Serial.println(estado);
    Firebase.setString(silla,estado); //Se modifica el estado de la silla en la base de datos de Firebase.
  }
  delay(1000);
}
