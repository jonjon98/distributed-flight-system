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
afp <subcommand> <appropriate arguments>
```

Subcommands:

- query
- add
- pin
- suppress
- config
- credentials

Query:

Query malware samples based on the arguments specified
```
- <hash> (required)
- -b, --branch <branch name> (optional)
- -f, --family <family name> (optional)
- -t, --type <type> (optional)
```

Add: 

Adds a new malware sample to the database
```
- <hash> (required)
- -b, --branch <branch name> (optional)
- -f, --family <family name> (required)
- -t, --type <type> (required)
- -s, --suppress (optional)
```

Pin:

Pins the malware sample specified
```
- <hash> (required)
- -b, --branch <branch name> (optional)
- -f, --family <family name> (required)
- -t, --type <type> (required)
- -u, --unpin (optional)
- -T, --ttl  <ttl in iso8601 format> (optional)
```

Suppress:

Suppresses the malware sample specified
```
<hash> (required)
- -b, --branch <branch name> (optional)
- -f, --family <family name> (required)
- -t, --type <type> (required)
- -u, --unsuppress (optional)
```

Config:

Prints the current configuration file for the user if no arguments are given. If arguments are given, then overwrite the `config.json` file with provided arguments and print out the result.
```
- -b, --branch <branch> (optional)
- -e, --endpoint <api endpoint> (optional)
- -v, --verify <True/False> (optional)
```

Credentials:

Prints the token in `credentials.txt`  if no arguments are given. If arguments are given, then overwrite the `credentials.txt` file with provided arguments and print out the result.
```
- -t, --token <token> (optional)
```