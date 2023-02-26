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

def build_request(request):
    # TODO: Implement request building logic
    return bytes(request, "UTF-8")

def handle_response(response):
    # TODO: Implement response handling logic
    print(response.decode())
    pass

if __name__ == '__main__':
    print("Sending Request")
    request = build_request("Hello Java!")
    response = send_request(request)
    handle_response(response)