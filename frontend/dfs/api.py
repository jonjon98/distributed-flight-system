# standard library imports

# third party library imports

# my own library imports
from marshalling import marshal, unmarshal

def sendToJava(serverIP, marshalled_data):
  print("serverIP: " + serverIP)
  print("marshalled_data: " + str(marshalled_data))
  # send to java server code here

  return "Return Value"
  pass

def callAPI_queryID(serverIP: str, src: str, dest: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["command"] = "queryID"
  data["source"] = src
  data["destination"] = dest

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_flightID = sendToJava(serverIP, marshalled_data)

  # unmarshal the data and return
  unmarshalled_flightID = unmarshal(marshalled_flightID)
  return unmarshalled_flightID

def callAPI_queryDetails(serverIP: str, flightID: str):
  data = {}
  data["command"] = "queryDetails"
  data["flightID"] = flightID

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_flightDetails = sendToJava(serverIP, marshalled_data)

  # unmarshal the data and return
  unmarshalled_flightDetails = unmarshal(marshalled_flightDetails)
  return unmarshalled_flightDetails

def callAPI_reserve(serverIP: str, flightID: str, noOfSeats: str):
  data = {}
  data["command"] = "reserve"
  data["flightID"] = flightID
  data["noOfSeats"] = noOfSeats

  # marshal the data
  marshalled_data = marshal(data)
  marshalled_reservationDetails = sendToJava(serverIP, marshalled_data)

  # unmarshal the data and return
  unmarshalled_reservationDetails = unmarshal(marshalled_reservationDetails)
  return unmarshalled_reservationDetails



def callAPI_setSemantics(serverIP: str, semantics: str):
  # format data in a dict to be sent for marshalling
  data = {}
  data["command"] = "setSemantics"
  data["semantics"] = semantics


  # marshal the data
  marshalled_data = marshal(data)
  marshalled_ack = sendToJava(serverIP, marshalled_data)

  # unmarshal the data and return
  unmarshalled_ack = unmarshal(marshalled_ack)
  return unmarshalled_ack

  
  