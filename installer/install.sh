#!/bin/bash

sudo mkdir -p /usr/share/lianos-renamer/ && \
sudo cp lianos-renamer.jar /usr/share/lianos-renamer && \
sudo chown -R root:root /usr/share/lianos-renamer && \
sudo chmod 664 /usr/share/lianos-renamer/lianos-renamer.jar && \
sudo cp lianos-renamer /usr/bin/lianos-renamer && \
sudo chown root:root /usr/bin/lianos-renamer && \
sudo chmod 755 /usr/bin/lianos-renamer