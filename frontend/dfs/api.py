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
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
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

def server_program(serverPort: int, end_time):
    # get the hostname
    host = "192.168.188.117"
    port = 23456  # initiate port no above 1024


    # server_socket = socket.socket()  # get instance
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    # look closely. The bind() function takes tuple as argument
    print("Host: " + host)
    print("Port number: " + str(port))
    server_socket.bind((host, port))  # bind host address and port together

    # configure how many client the server can listen simultaneously

    # server_socket.listen(1)
    while time.time() < end_time:
      server_socket.settimeout(end_time - time.time())
      try:
        print("Waiting to receive...")
        data, address = server_socket.recvfrom(MAX_PACKET_SIZE)  # receive data from client
        print("Received from: " + str(address))
        data = unmarshal(data)

        if data:
          print(str(data))
      except socket.timeout:
        pass
      # data = input(' -> ')
      # conn.send(data.encode())  # send data to the client

    try:
      socket.close()  # close the connection
    except:
      pass

# def server_program(serverPort: int, end_time):
#     # get the hostname
#     host = "192.168.188.117"
#     port = 23456  # initiate port no above 1024

#     server_socket = socket.socket()  # get instance
#     # look closely. The bind() function takes tuple as argument
#     print("Host: " + host)
#     print("Port number: " + str(port))
#     server_socket.bind((host, port))  # bind host address and port together

#     # configure how many client the server can listen simultaneously
#     server_socket.listen(1)
#     while time.time() < end_time:
#       server_socket.settimeout(end_time - time.time())
#       try:
#         conn, address = server_socket.accept()  # accept new connection
#         print("Connection from: " + str(address))
#         # receive data stream. it won't accept data packet greater than 1024 bytes
#         data = unmarshal(conn.recv(MAX_PACKET_SIZE))
#         if data:
#           print(str(data))
#       except socket.timeout:
#         pass
#       # data = input(' -> ')
#       # conn.send(data.encode())  # send data to the client

#     try:
#       conn.close()  # close the connection
#     except:
#       pass

def sendRequestSubscribe(serverIP: str, serverPort: int, request: bytes, marshalled_cancelData: bytes, interval: int):
  start_time = time.time()
  end_time = start_time + float(interval * 60)
  with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.connect((serverIP, serverPort))
    s.sendall(request)
    print("Subscribe Request Sent!")
    print("Waiting for " + str(interval) + " minutes...")
    s.settimeout(10)  # set a timeout of interval minutes
    try:
      response = s.recv(MAX_PACKET_SIZE)
      if response:
            print("Response received")
            print(unmarshal(response))
    except socket.timeout:
        print("Timeout for initial connection.")

    s.close()

  server_program(serverPort, end_time)

    # while time.time() < end_time:
    #   # print("Entering while loop")
    #   try:
    #     response = s.recv(MAX_PACKET_SIZE)
    #     if response:
    #       print("Response received")
    #       print(unmarshal(response))
    #   except socket.timeout:
    #     pass
    
    # s.close()

  responseCancel = sendRequest(serverIP, serverPort, marshalled_cancelData)
  print(unmarshal(responseCancel))

  print("Subscription ended after " + str(interval) + " minutes.")
  return None

def sendToJavaSubscribe(serverIP: str, serverPort: int, marshalled_data: bytes, marshalled_cancelData: bytes, interval: int):
  print("serverIP: " + serverIP)
  print("marshalled_data: " + str(marshalled_data))

  ### send to java server code here
  print("Sending Request")
  request = marshalled_data
  sendRequestSubscribe(serverIP, serverPort, request, marshalled_cancelData, interval)

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

  cancelData = {}
  cancelData["command"] = "cancelCallback"
  cancelData["flightID"] = flightID

  # marshal the data
  print(data)
  marshalled_data = marshal(data)
  marshalled_cancelData = marshal(cancelData)
  sendToJavaSubscribe(serverIP, serverPort, marshalled_data, marshalled_cancelData, interval)
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
  data["command"] = "config"
  data["semantics"] = semantics


  # marshal the data
  marshalled_data = marshal(data)
  marshalled_ack = sendToJava(serverIP, serverPort, marshalled_data)

  # unmarshal the data and return
  unmarshalled_ack = unmarshal(marshalled_ack)
  return unmarshalled_ack