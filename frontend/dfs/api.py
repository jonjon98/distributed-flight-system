# standard library imports
import time
import socket

# third party library imports

# my own library imports
from .marshalling import marshal, unmarshal

### means need to add code to send to java server

##### functions for sending data to Java server
MAX_RETRIES = 3
RETRY_DELAY = 10  # seconds
MAX_PACKET_SIZE = 2048

def sendRequest(serverIP: str, serverPort: int, request: bytes):
  retries = 0
  while retries < MAX_RETRIES:
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
      s.settimeout(10)  # set a timeout of 10 seconds
      try:
          s.connect((serverIP, serverPort))
          s.sendall(request)
          print("Message Sent!")
          response = s.recv(MAX_PACKET_SIZE)
          print("Response received")
          s.close()
          break  # exit loop if response is received
      except socket.timeout:
          print(f"Timeout reached, retrying ({retries+1}/{MAX_RETRIES})...")
          retries += 1
          time.sleep(RETRY_DELAY)  # wait before retrying
      finally:
          s.close()
  if retries == MAX_RETRIES:
      print("Max retries reached, giving up.")
      return bytearray()
  else:
      return response

def sendToJava(serverIP: str, serverPort: int, marshalled_data: bytes):
  print("serverIP: " + serverIP)
  print("marshalled_data: " + str(marshalled_data))

  ### send to java server code here
  print("Sending Request")
  request = marshalled_data
  response = sendRequest(serverIP, serverPort, request)

  return response

def sendRequestSubscribe(serverIP: str, serverPort: int, request: bytes, interval: int):
  start_time = time.time()
  end_time = start_time + (interval * 60)
  with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((serverIP, serverPort))
    s.sendall(request)
    print("Subscribe Request Sent!")
    print("Waiting for " + str(interval) + " minutes...")
    s.settimeout(interval * 60)  # set a timeout of interval minutes
    while time.time() < end_time:
      try:
        response = s.recv(MAX_PACKET_SIZE)
        if response:
          print("Response received")
          print(unmarshal(response))
      except socket.timeout:
        pass
    s.close()
  print("Subscription ended after " + str(interval) + " minutes.")
  return None

def sendToJavaSubscribe(serverIP: str, serverPort: int, marshalled_data: bytes, interval: int):
  print("serverIP: " + serverIP)
  print("marshalled_data: " + str(marshalled_data))

  ### send to java server code here
  print("Sending Request")
  request = marshalled_data
  sendRequestSubscribe(serverIP, serverPort, request, interval)

  return None

# APIs for cli.py to call on
def callAPI_queryID(serverIP: str, serverPort: int, commandID: str, src: str, dest: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "queryID"
  data["source"] = src
  data["destination"] = dest

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_flightID = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_flightID = unmarshal(marshalled_flightID)
  return unmarshalled_flightID

def callAPI_queryDetails(serverIP: str, serverPort: int, commandID: str, flightID: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "queryDetails"
  data["flightID"] = flightID

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_flightDetails = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_flightDetails = unmarshal(marshalled_flightDetails)
  return unmarshalled_flightDetails

def callAPI_reserve(serverIP: str, serverPort: int, commandID: str, flightID: str, noOfSeats: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "reserve"
  data["flightID"] = flightID
  data["noOfSeats"] = noOfSeats

  # marshal the data
  print(data)
  marshalled_data = marshal(data)
  marshalled_reservationDetails = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_reservationDetails = unmarshal(marshalled_reservationDetails)
  return unmarshalled_reservationDetails

def callAPI_subscribe(serverIP: str, serverPort: int, commandID: str, flightID: str, interval: int):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "subscribe"
  data["flightID"] = flightID
  data["interval"] = str(interval)
  # data["source"] = "SINGAPORE"
  # data["destination"] = "CHINA"

  # marshal the data
  print(data)
  marshalled_data = marshal(data)
  sendToJavaSubscribe(serverIP, serverPort, marshalled_data, interval)
  return None

def callAPI_retrieve(serverIP: str, serverPort: int, commandID: str, bookingID: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "retrieve"
  data["bookingID"] = bookingID

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_retrieveDetails = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_retrieveDetails = unmarshal(marshalled_retrieveDetails)
  return unmarshalled_retrieveDetails

def callAPI_cancel(serverIP: str, serverPort: int, commandID: str, bookingID: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "cancel"
  data["bookingID"] = bookingID

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_cancelDetails = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_cancelDetails = unmarshal(marshalled_cancelDetails)
  return unmarshalled_cancelDetails

def callAPI_setSemantics(serverIP: str, serverPort: int, commandID: str, semantics: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["id"] = commandID
  data["command"] = "setSemantics"
  data["semantics"] = semantics


  # marshal the data
  marshalled_data = marshal(data)
  marshalled_ack = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_ack = unmarshal(marshalled_ack)
  return unmarshalled_ack
