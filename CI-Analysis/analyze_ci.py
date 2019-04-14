import os
import sys
import subprocess

errorMessages = []
successMessages = []
executionMessages = []


for line in sys.stdin:
    if ("error" in line.lower()) or ("fail" in line.lower()):
        errorMessages.append(line)
    if ("success" in line.lower()) or ("complete" in line.lower()) or ("pass" in line.lower()):
        successMessages.append(line)
    if "executed" in line.lower():
        executionMessages.append(line)


print("LOG ANALYSIS RESULTS for -> ")
print("Build Number: " + TRAVIS_BUILD_NUMBER)
print("Commit Message: " + TRAVIS_COMMIT_MESSAGE)
print("Job Number" + TRAVIS_JOB_NUMBER)
print("Stage: " + TRAVIS_BUILD_STAGE_NAME)

print("")
if len(successMessages) > 0:
    print("There are a total of " + str(len(successMessages)) + " success messages in this test")
    print("Here are the success messages:")
    for successMessage in successMessages:
        print(successMessage)
else:
    print("There are no success messages in this test")

print("")
if len(errorMessages) > 0:
    print("There are a total of " + str(len(errorMessages)) + " error messages in this test")
    print("Here are the error messages: ")
    for errorMessage in errorMessages:
        print(errorMessage)
else:
    print("There are no error messages in this test.")

print("")
if len(executionMessages) > 0:
    print("There are a total of " + str(len(executionMessages)) + " general execution messages in this test")
    print("Here are the general execution messages")
    for executionMessage in executionMessages:
        print(executionMessage)
else:
    print("There were no general execution messages in this test")


