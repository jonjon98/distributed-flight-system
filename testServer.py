import time
import socket

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
  


def sendRequestSubscribe(serverIP: str, serverPort: int, request: bytes, marshalled_cancelData: bytes, interval: int):
  start_time = time.time()
  end_time = start_time + (interval * 60)
  with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
    s.connect((serverIP, serverPort))
    s.sendall(request)
    print("Subscribe Request Sent!")
    print("Waiting for " + str(interval) + " minutes...")
    s.settimeout(interval * 60)  # set a timeout of interval minutes
    while time.time() < end_time:
      # print("Entering while loop")
      try:
        response = s.recv(MAX_PACKET_SIZE)
        if response:
          print("Response received")
          print(unmarshal(response))
      except socket.timeout:
        pass
    
    s.close()

  responseCancel = sendRequest(serverIP, serverPort, marshalled_cancelData)
  print(unmarshal(responseCancel))

  print("Subscription ended after " + str(interval) + " minutes.")
  return None


# get the hostname
host = "192.168.188.80"
port = 12345  # initiate port no above 1024

# server_socket = socket.socket()  # get instance
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# look closely. The bind() function takes tuple as argument
print("Host: " + host)
print("Port number: " + str(port))
server_socket.bind((host, port))  # bind host address and port together

# configure how many client the server can listen simultaneously
# server_socket.listen(1)
end_time = time.time() + 60
while time.time() < end_time:
    server_socket.settimeout(end_time - time.time())
    try:
        print("Waiting to receive...")
        data, address = server_socket.recvfrom(MAX_PACKET_SIZE)  # receive data from client
        print("Received from: " + str(address))
        if data:
            print(str(data.decode()))
    except socket.timeout:
        pass
    # data = input(' -> ')
    # conn.send(data.encode())  # send data to the client

try:
    server_socket.close()  # close the connection
except:
    pass

