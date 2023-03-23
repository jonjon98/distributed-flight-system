import socket

HOST = 'localhost'
PORT = 12345
MAX_PACKET_SIZE = 1024

def send_request(request):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        s.sendall(request)
        # s.sendall(b'\0')
        print("Message Sent!")
        # s.shutdown(socket.SHUT_WR)
        response = s.recv(MAX_PACKET_SIZE)
        print("Response received")
    return response

def build_request():
    # TODO: Implement request building logic
    # marshal logic
    marshalled_data = bytearray()

    unmarshalled_data = {}
    unmarshalled_data["QueryId"] = "1"
    unmarshalled_data["command"] = "1"
    unmarshalled_data["source"] = "SINGAPORE"
    unmarshalled_data["destination"] = "CHINA"

    # for key, value in unmarshalled_data.items():
    #     key_len = len(key.encode('utf-8'))
    #     marshalled_data += key_len.to_bytes(1, byteorder='big', signed=False)
    #     marshalled_data += key.encode('utf-8')
    #     value_len = len(value.encode('utf-8'))
    #     marshalled_data += value_len.to_bytes(1, byteorder='big', signed=False)
    #     marshalled_data += value.encode('utf-8')
    marshalled_data += "007QueryId".encode('utf-8')
    marshalled_data += "0011".encode('utf-8')
    marshalled_data += "006source".encode('utf-8')
    marshalled_data += "009SINGAPORE".encode('utf-8')
    marshalled_data += "011destination".encode('utf-8')
    marshalled_data += "005CHINA".encode('utf-8')
    return marshalled_data

def handle_response(response):
    # TODO: Implement response handling logic
    print(response.decode())
    pass

if __name__ == '__main__':
    print("Sending Request")
    request = build_request()
    response = send_request(request)
    handle_response(response)