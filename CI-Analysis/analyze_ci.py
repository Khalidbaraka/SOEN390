import os
import sys
import subprocess

successKeywords = ["success", "complete", "pass"]
failureKeywords = ["error", "fail"]
generalExecutionKeywords = ["executed"]

allSuccessMessages = []
allFailureMessage = []
allExecutionMessages = []

print("dorin-test-3-no-log-printing")

totalMessageCount = 0

for line in sys.stdin:
    totalMessageCount += 1




