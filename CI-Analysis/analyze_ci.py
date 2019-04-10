import os
import sys
import subprocess

successKeywords = ["success", "complete", "pass"]
failureKeywords = ["error", "fail"]
generalExecutionKeywords = ["executed"]


for line in sys.stdin:
    print(line)

