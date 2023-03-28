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

def send_request(serverIP: str, serverPort: int, request: bytes):
    
  with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.settimeout(10)  # set a timeout of 10 seconds
    retries = 0
    while retries < MAX_RETRIES:
        try:
            s.connect((serverIP, serverPort))
            s.sendall(request)
            print("Message Sent!")
            response = s.recv(MAX_PACKET_SIZE)
            print("Response received")
            break  # exit loop if response is received
        except socket.timeout:
            print(f"Timeout reached, retrying ({retries+1}/{MAX_RETRIES})...")
            retries += 1
            time.sleep(RETRY_DELAY)  # wait before retrying

    if retries == MAX_RETRIES:
        print("Max retries reached, giving up.")
        return None
    else:
        return response

def sendToJava(serverIP: str, serverPort: int, marshalled_data: bytes):
  print("serverIP: " + serverIP)
  print("marshalled_data: " + str(marshalled_data))

  ### send to java server code here
  print("Sending Request")
  request = marshalled_data
  response = send_request(serverIP, serverPort, request)

  return response
#####

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
  start_time = time.time()
  end_time = start_time + (interval * 60)
  while time.time() < end_time:
    print("Monitoring for " + str(int((end_time - time.time()) // 60) + 1) + " more minutes...")
    ### monitor callback from java server

    time.sleep(1)
  

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
