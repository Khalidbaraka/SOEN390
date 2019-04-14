import os
import sys
import subprocess
from TravisEnvirExtractor import TravisEnvirExtractor

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


travisOutput = TravisEnvirExtractor.get_travis_variables()
buildNumber = str(travisOutput['TRAVIS_BUILD_NUMBER'])
commitMessage = str(travisOutput['TRAVIS_COMMIT_MESSAGE'])
jobNumber = str(travisOutput['TRAVIS_COMMIT_MESSAGE'])
stage = str(travisOutput['TRAVIS_BUILD_STAGE_NAME'])

print("LOG ANALYSIS RESULTS for -> ")
print("Build Number: " + buildNumber)
print("Commit Message: " + commitMessage)
print("Job Number" + jobNumber)
print("Stage: " + stage)

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


