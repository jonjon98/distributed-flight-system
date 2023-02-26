import sys
import json
from setuptools import find_packages, setup
from pathlib import Path
from setuptools.command.install import install

class install_config_dir(install):
    user_options = install.user_options + [
        ('serverIP=', None, 'IP of flight booking server to connect to.'),
        ('semantics=', None, 'Semantics to use to transfer information (1 for at-least-once and 2 for at-most-once).'),
    ]           

    def initialize_options(self):          
        install.initialize_options(self)   
        self.serverIP = None
        self.semantics = None

    def finalize_options(self):                   
        print("value of serverIP is", self.serverIP)
        print("value of semantics is", self.semantics)
        install.finalize_options(self)

    def run(self):
        # Use inputs from pip install's "--install-option"
        global serverIP
        serverIP = self.serverIP
        global semantics
        semantics = self.semantics

        install.run(self)
        dfs_dir_path = Path(Path.home(), ".dfs")
        if dfs_dir_path.exists():
            return
        dfs_dir_path.mkdir(exist_ok=True)

        # defining path for config file
        dfs_config_path = dfs_dir_path / "config.json"

        # creating configs
        configs_data = {}
        
        configs_data["serverIP"] = str(self.serverIP)

        if int(self.semantics) == 1:
            configs_data["semantics"] = "at-least-once"
        elif int(self.semantics) == 2:
            configs_data["semantics"] = "at-most-once"
            
        # Serializing json
        json_object = json.dumps(configs_data, indent=4)

        # Writing to sample.json
        with open(dfs_config_path, "w") as outfile:
            outfile.write(json_object)

        print("Successfully created .dfs folder in $HOME directory.", file=sys.stderr)

with open("README.md", "r", encoding="utf-8") as fhand:
    long_description = fhand.read()

setup(
    name="dfs-command-line",
    version="0.1",
    author="Jonathan Yap",
    author_email="yapzh.jon@gmail.com",
    description=("A command line interface to allow users to access the distributed flight system APIs."),
    long_description=long_description,
    long_description_content_type="text/markdown",
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
    install_requires=["requests"], # not sure if need to add more (change project structure of cli.py and api.py first)
    packages=find_packages(),
    python_requires=">=3.7",
    entry_points={
        "console_scripts": [
            "dfs = dfs.cli:main", # not sure if this is correct (change project structure of cli.py and api.py first)
        ]
    },
        cmdclass={
        "install": install_config_dir
    }
)