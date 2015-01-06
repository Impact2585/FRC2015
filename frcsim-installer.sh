#!/bin/bash

# This is a modified FRCSim installer to get the dependencies for Travis CI

# Add Gazebo Repository and Key
echo "deb http://packages.osrfoundation.org/gazebo/ubuntu `lsb_release -cs` main" | tee -a /etc/apt/sources.list.d/gazebo-latest.list
wget http://packages.osrfoundation.org/gazebo.key -O - | apt-key add -

# Add FRCSim Repository and Key
echo "deb http://users.wpi.edu/~adhenning/frcsim `lsb_release -cs` main" | tee -a /etc/apt/sources.list.d/frcsim-latest.list
wget users.wpi.edu/~adhenning/frcsim.key -O - | apt-key add -

# Update and install frcsim and its dependencies
apt-get update
apt-get install frcsim
