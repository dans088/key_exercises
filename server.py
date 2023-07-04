import socket

s=socket.socket(socket.AF_PACKET,socket.SOCK_RAW)
s.bind(("127.0.0.1", 1337))
s.listen(1)
connection, address=s.accept()

s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
with connection:
    data=connection.recv(1024)
    print(data)
    connection.close()
s.close()



