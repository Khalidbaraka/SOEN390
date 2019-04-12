import os
import sys
import subprocess

errorMessages = []
successMessages = []


for line in sys.stdin:
    if ("error" in line.lower()) or ("fail" in line.lower()):
        errorMessages.append(line)
    if ("success" in line.lower()) or ("complete" in line.lower()) or ("pass" in line.lower()):
        successMessages.append(line)


print("There are a total of " + str(len(successMessages)) + " success messages")
print("Here are the success messages")
for successMessage in successMessages:
    print(successMessage)

print("")

print("There are a total of " + str(len(errorMessages)) + " error messages")
print("Here are the error messages: ")
for errorMessage in errorMessages:
    print(errorMessage)



