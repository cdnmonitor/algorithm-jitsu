#!/bin/bash
# This script will run the server, client, and simple AI in separate terminal windows for testing purposes on a Mac.
# To run this script, open a terminal window and type: sh playai.sh
cwd=$(pwd)
osa=$(osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java Server"')
osa=$(osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java Client"')
osa=$(osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java SimpleAI"')
