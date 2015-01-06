#!/bin/bash

# This is a modified FRCSim installer to get the dependencies for Travis CI

# Add FRCSim Repository and Key
echo "deb http://users.wpi.edu/~adhenning/frcsim `lsb_release -cs` main" | tee -a /etc/apt/sources.list.d/frcsim-latest.list
wget users.wpi.edu/~adhenning/frcsim.key -O - | apt-key add -

# Update and install frcsim and its dependencies
apt-get update
apt-get install -y frcsim
