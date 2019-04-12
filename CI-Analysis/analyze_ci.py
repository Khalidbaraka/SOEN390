import os
import sys
import subprocess

successKeywords = ["success", "complete", "pass"]
failureKeywords = ["error", "fail"]
generalExecutionKeywords = ["executed"]

allSuccessMessages = []
allFailureMessage = []
allExecutionMessages = []

print("dorin-test-2-no-log-printing")

for line in sys.stdin:
    #print(line)
    #add the logic in to get all our stats in here and then print the out at the end.
    #this will be better than just outputting are the random stuff from the travis builds
    

