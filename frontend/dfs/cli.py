# standard library imports
import argparse
import sys
import json
from datetime import datetime
from pathlib import Path
# third party library imports

# my own library imports (call the conntion apis)
from api import callAPI_queryID, callAPI_queryDetails, callAPI_reserve, callAPI_retrieve, callAPI_cancel, callAPI_setSemantics

# defining path variables
dfs_dir_path = Path.home() / ".dfs"
dfs_credentials_path = dfs_dir_path / "credentials.txt"
dfs_config_path = dfs_dir_path / "config.json"
dfs_certificate_path = dfs_dir_path / "consolidate.pem"

# default values for config.json
serverIP = "localhost"
semantics = "at-least-once"

# defining all loading functions
def read_config() -> dict: # reads branch info from config.json file in .dfs directory in $HOME directory
	configs = open(dfs_config_path)
	configs_data = json.load(configs)
	configs.close() 
	return configs_data

def write_config(serverIP: str, semantics: str) -> dict: # writes branch info from config.json file in .dfs directory in $HOME directory
	# Data to be written, do a read_config first to get the data currently in the config.json file
	configs_data = load_config()
	if serverIP:
		configs_data["serverIP"] = str(serverIP)
	if semantics:
		if int(semantics) == 1:
			configs_data["semantics"] = "at-least-once"
		elif int(semantics) == 2:
			configs_data["semantics"] = "at-most-once"
		
		print("Semantics changed: ", callAPI_setSemantics(configs_data["serverIP"], configs_data["semantics"]))
	
	# Serializing json
	json_object = json.dumps(configs_data, indent=4)
	
	# Writing to sample.json
	with open(dfs_config_path, "w") as outfile:
		outfile.write(json_object) 

	return configs_data

def load_config() -> dict:
	try:
		configs_data = read_config()
		configs_data["serverIP"] 
		configs_data["semantics"]
	except (FileNotFoundError, KeyError, json.decoder.JSONDecodeError) as e:
		print("No config.json file detected or config.json is of wrong format, creating default config.json now.", file=sys.stderr)
		configs_data = {}
        
		configs_data["serverIP"] = serverIP
		configs_data["semantics"] = semantics
        
    # Serializing json
		json_object = json.dumps(configs_data, indent=4)

    # Writing to config.json
		with open(dfs_config_path, "w+") as outfile:
			outfile.write(json_object)
			return configs_data

	return configs_data

def queryID(args):
	source = args.source
	destination = args.destination
	configs_data = read_config()
	flightID = callAPI_queryID(configs_data["serverIP"], source, destination)
	print("Flight ID: " + flightID)

def queryDetails(args):
	flightID = args.flightID
	configs_data = read_config()
	flightDetails = callAPI_queryDetails(configs_data["serverIP"], flightID)
	print("Flight Details: " + str(flightDetails))

def reserve(args):
	flightID = args.flightID
	noOfSeat = args.noOfSeats
	configs_data = read_config()
	reservationDetails = callAPI_reserve(configs_data["serverIP"], flightID, noOfSeat)
	print("Reservation Details: " + str(reservationDetails))

def subscribe():
	pass

def retrieve(args):
	bookingID = args.bookingID
	configs_data = read_config()
	retrieveDetails = callAPI_retrieve(configs_data["serverIP"], bookingID)
	print("Reservation Details: " + retrieveDetails)

def cancel(args):
	bookingID = args.bookingID
	configs_data = read_config()
	cancelDetails = callAPI_cancel(configs_data["serverIP"], bookingID)
	print("Reservation Details: " + cancelDetails)

def config(args):
	if not (args.serverIP or args.semantics): # if there are no flags provided, then just print contents of config.json file
		print(json.dumps(load_config(), indent=4), file=sys.stderr)
	else: # overwrite config.json with the provided flags and prints the contents of the updated config.json file
		print(json.dumps(write_config(args.serverIP, args.semantics), indent=4), file=sys.stderr)

# Main function
def main() -> None:
	# create the top-level parser
	parser = argparse.ArgumentParser(
		description="DFS CLI tool for flight booking."
	)

	# create subparser
	subparsers = parser.add_subparsers(title='subcommands',
                                   description='valid subcommands',
                                   help="'-h', '--help' after subcommand for subcommand usage help")

	# queryID subcommand
	parser_queryID = subparsers.add_parser('queryID', help="Query flight ID based source and destination of flights.")

	parser_queryID.add_argument( # required positional argument
		"source", type=str,
		help="Source of the flight."
	)

	parser_queryID.add_argument(
		"destination", type=str,
		help="Destination of the flight."
	)

	parser_queryID.set_defaults(func=queryID)

	# queryDetails subcommand
	parser_queryDetails = subparsers.add_parser('queryDetails', help="Query flight details based on flight ID.")

	parser_queryDetails.add_argument( # required positional argument
		"flightID", type=str,
		help="ID of the flight."
	)

	parser_queryDetails.set_defaults(func=queryDetails)

	# reserve subcommand
	parser_reserve = subparsers.add_parser('reserve', help="Make seat reservation based on flight ID and number of seats to reserve.")

	parser_reserve.add_argument( # required positional argument
		"flightID", type=str,
		help="ID of the flight."
	)

	parser_reserve.add_argument(
		"noOfSeats", type=str,
		help="Number of seats to reserve."
	)

	parser_reserve.set_defaults(func=reserve)

	# subscribe subcommand
	parser_subscribe = subparsers.add_parser('subscribe', help="Subscribe to callback monitoring based on flight ID and monitor interval.")

	parser_subscribe.add_argument( # required positional argument
		"flightID", type=str,
		help="ID of the flight."
	)

	parser_subscribe.add_argument(
		"interval", type=str,
		help="Monitoring interval in minutes for callback monitoring."
	)

	parser_subscribe.set_defaults(func=subscribe)

	# retrieve subcommand
	parser_retrieve = subparsers.add_parser('retrieve', help="Retrieves booking information based on booking ID.")

	parser_retrieve.add_argument(
		"bookingID", type=str,
		help="ID of the booking"
	)

	parser_retrieve.set_defaults(func=retrieve)

	# cancel subcommand
	parser_cancel = subparsers.add_parser('cancel', help="Cancels seat reservation based on booking ID.")

	parser_cancel.add_argument(
		"bookingID", type=str,
		help="ID of the booking"
	)

	parser_cancel.set_defaults(func=cancel)

	# Config subcommand
	parser_config = subparsers.add_parser('config', help="Prints the current configuration file for the user if no arguments are given. If arguments are given, then overwrite the config.json file with provided arguments and print out the result.")

	parser_config.add_argument(
		"--serverIP",
		help="Flag to overwrite IP of server stored in config.json"
	)

	parser_config.add_argument(
		"--semantics", "-s",
		help="Flag to overwrite semantics stored in config.json and sends updated semantics to server"
	)

	parser_config.set_defaults(func=config)

	# Parsing subcommands and arguments
	args = parser.parse_args()

	try:
		func = args.func # this would call the subfunctions based on what is passed into args
		func(args)
	except AttributeError as e:
		print(e, file=sys.stderr)
		parser.error("Give a subcommand")

if __name__ == "__main__":
	main()