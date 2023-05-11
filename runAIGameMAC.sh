#!/bin/bash
# EXPERIMENTAL MAC SHELL SCRIPT
# This script will run the server, client, and simple AI in separate terminal windows for testing purposes
# To run this script, open a terminal window and type: ./runAIGameMAC.sh

client_window_id=""
ai_window_id=""

while true; do
  cwd=$(pwd)

  # Start the server in the background
  java "Server" &

  # Store the process ID of the server
  server_pid=$!

  # Open the client and AI scripts in separate terminal windows
  if [ -z "$client_window_id" ]; then
    osa=$(osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java Client"')
    client_window_id=$(osascript -e 'tell application "Terminal" to id of front window')
  else
    osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java Client" in window id '$client_window_id
  fi

  if [ -z "$ai_window_id" ]; then
    osa=$(osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java SimpleAI"')
    ai_window_id=$(osascript -e 'tell application "Terminal" to id of front window')
  else
    osascript -e 'tell application "Terminal" to do script "cd '$cwd'; java SimpleAI" in window id '$ai_window_id
  fi

  # Wait for the server to finish
  wait $server_pid

  read -p "Do you want to play again? (Y/N): " choice

  if [ "$choice" != "Y" ] && [ "$choice" != "y" ]; then
    break
  fi
done