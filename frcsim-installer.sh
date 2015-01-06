#!/bin/bash

# This is a modified FRCSim installer to get the dependencies for Travis CI

# Add FRCSim Repository and Key
wget users.wpi.edu/~adhenning/frcsim.key -O - | sudo apt-key add -

# Update and install frcsim and its dependencies
sudo apt-get update
sudo apt-get install -y frcsim

