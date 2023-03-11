# standard library imports

# third party library imports

def marshal(unmarshalled_data):
  # marshal logic
  marshalled_data = bytearray()
  for key, value in unmarshalled_data.items():
      key_len = len(key.encode('utf-8'))
      marshalled_data += key_len.to_bytes(3, byteorder='big', signed=False)
      marshalled_data += key.encode('utf-8')
      value_len = len(value.encode('utf-8'))
      marshalled_data += value_len.to_bytes(3, byteorder='big', signed=False)
      marshalled_data += value.encode('utf-8')
  return marshalled_data

def unmarshal(marshalled_data):
  # unmarshal logic
  unmarshalled_data = {}
  idx = 0
  flag = "key"

  key = ""
  value = ""
  while idx != len(marshalled_data):
    if flag == "key":
      length = int(marshalled_data[idx] + marshalled_data[idx + 1] + marshalled_data[idx + 2])
      idx += 3

      key = marshalled_data[idx:idx + length]
      idx += length
      flag = "value"

    elif flag == "value":
      length = int(marshalled_data[idx] + marshalled_data[idx + 1] + marshalled_data[idx + 2])
      idx += 3

      value = marshalled_data[idx:idx + length]
      idx += length
      unmarshalled_data[key.decode('utf-8')] = value.decode('utf-8')
      key = ""
      value = ""
      flag = "key"
    
  return unmarshalled_data
