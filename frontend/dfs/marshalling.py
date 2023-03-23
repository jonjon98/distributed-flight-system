# standard library imports

# third party library imports

def marshal(unmarshalled_data):
  # marshal logic
  marshalled_data = bytearray()
  for key, value in unmarshalled_data.items():
      key_len = str(len(key.encode('utf-8')))
      if len(key_len) == 1:
        padded_key_len = "00" + key_len
      elif len(key_len) == 2:
        padded_key_len = "0" + key_len
      elif len(key_len) == 3:
        padded_key_len = key_len
      marshalled_data += padded_key_len.encode('utf-8')
      marshalled_data += key.encode('utf-8')
      
      value_len = str(len(value.encode('utf-8')))
      if len(value_len) == 1:
        padded_value_len = "00" + value_len
      elif len(value_len) == 2:
        padded_value_len = "0" + value_len
      elif len(value_len) == 3:
        padded_value_len = value_len
      marshalled_data += padded_value_len.encode('utf-8')
      marshalled_data += value.encode('utf-8')
  return marshalled_data

def unmarshal(marshalled_data):
  # unmarshal logic 
  unmarshalled_data = ""
  for i in range(1 ,len(marshalled_data), 2):
    unmarshalled_data += chr(marshalled_data[i])

  return unmarshalled_data
