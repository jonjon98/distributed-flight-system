# standard library imports

# third party library imports

def marshal(unmarshalled_data):
  # marshal logic
  marshalled_data = bytearray()
  for key, value in unmarshalled_data.items():
      key_len = len(key.encode('utf-8'))
      marshalled_data += key_len.to_bytes(1, byteorder='big', signed=False)
      marshalled_data += key.encode('utf-8')
      value_len = len(value.encode('utf-8'))
      marshalled_data += value_len.to_bytes(1, byteorder='big', signed=False)
      marshalled_data += value.encode('utf-8')
  return marshalled_data

def unmarshal(marshalled_data):
  # unmarshal logic
  unmarshalled_data = marshalled_data
  return unmarshalled_data
  pass