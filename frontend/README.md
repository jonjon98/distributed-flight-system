# DFS Command Line Tool

This is a command line tool to be able to access the Distributed Flight system

## Installation

1. Clone the repo by using

```bash
git clone https://github.com/jonjon98/distributed-flight-system.git
```

2. Install the package

```bash
cd <repo path>
pip install .
```


## Using the CLI command
```bash
dfs <subcommand> <appropriate arguments>
```

Subcommands:

- queryID
- queryDetails
- reserve
- subscribe
- retrieve
- cancel
- config

### queryID:
Query flight ID based on source and destination.
```bash
usage: dfs queryID source destination
positional arguments:
  source       Source of the flight.
  destination  Destination of the flight.
```

### queryDetails:
Query the details of a flight based on the flight ID.
```bash
usage: dfs queryDetails flightID
positional arguments:
  flightID    ID of the flight.
```

### reserve:
Reserve a specified number of seats based on the flight ID.
```bash
usage: dfs reserve flightID noOfSeats
positional arguments:
  flightID    ID of the flight.
  noOfSeats   Number of seats to reserve.
```

### subscribe:
Engages a callback function at the server to present updates when the specified flight ID has an update.
```bash
usage: dfs subscribe flightID interval
positional arguments:
  flightID    ID of the flight.
  interval    Monitoring interval in minutes for callback monitoring.
```

### retrieve:
Retrieve the booking information based on a booking ID.
```bash
usage: dfs retrieve bookingID
positional arguments:
  bookingID   ID of the booking
```

### cancel:
Cancel a booking based on the booking ID.
```bash
usage: dfs cancel bookingID
positional arguments:
  bookingID   ID of the booking
```

### config:
Changes the configurations based on the arguments given.
Displays the current configurations if no arguments are present.
```bash
usage: dfs config [-h] [--serverIP SERVERIP] [--serverPort SERVERPORT] [--semantics SEMANTICS]
optional arguments:
  --serverIP SERVERIP   Flag to overwrite IP of server stored in config.json
  --serverPort SERVERPORT
                        Flag to overwrite port of server stored in config.json
  --semantics SEMANTICS, -s SEMANTICS
                        Flag to overwrite semantics stored in config.json and sends updated semantics to server
```
