
import sys

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

print("")
print("=======================================")
print("LOG ANALYSIS RESULTS:")

print("")
if len(successMessages) > 0:
    print("THERE ARE A TOTAL OF " + str(len(successMessages)) + " SUCCESS MESSAGES IN THIS TEST")
    for successMessage in successMessages:
        print(successMessage)
else:
    print("THERE WERE NO SUCCESS MESSAGES IN THIS TEST")

print("")
if len(errorMessages) > 0:
    print("THERE ARE A TOTAL OF " + str(len(errorMessages)) + " ERROR MESSAGES IN THIS TEST")
    for errorMessage in errorMessages:
        print(errorMessage)
else:
    print("THERE WERE NO ERROR MESSAGES IN THIS TEST")

print("")
if len(executionMessages) > 0:
    print("THERE ARE A TOTAL OF " + str(len(executionMessages)) + " GENERAL EXECUTION MESSAGES IN THIS TEST")
    for executionMessage in executionMessages:
        print(executionMessage)
else:
    print("THERE WERE NO GENERAL EXECUTION MESSAGES IN THIS TEST")


