# This script will run the server, client, and simple AI in separate terminal windows for testing purposes on a windows machine.
# To run this script, open a powershell terminal and navigate to the directory containing this script.
# Then, run the command "Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser" to allow the script to run.
# Finally, run the command ".\runAIGameWIN.ps1" to run the script.
$cwd = Get-Location
Start-Process -NoNewWindow -WorkingDirectory $cwd -FilePath "java" -ArgumentList "Server"
Start-Process -NoNewWindow -WorkingDirectory $cwd -FilePath "java" -ArgumentList "Client"
Start-Process -NoNewWindow -WorkingDirectory $cwd -FilePath "java" -ArgumentList "SimpleAI"
