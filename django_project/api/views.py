from django.shortcuts import render
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.http import JsonResponse
from django.core import serializers
from django.conf import settings

import json
import os
import subprocess
import os.path
from yapf.yapflib.yapf_api import FormatCode

from django.http import HttpResponse
# Create your views here.

def formatCode(lang, code):
    if lang == "Python":
        code = FormatCode(code)

    outputData = {"lang":lang, "code":code}
    return JsonResponse(outputData)

@api_view(["POST"])
def executeCode(request):
    print(request.POST)
    if "code" in request.POST: 
        code = request.POST["code"]
        lang = request.POST["language"]
    else:
        codeStyle = request.POST["style"]
        lang = request.POST["language"]
        return formatCode(lang, codeStyle)

    if lang == "Python":
        codeFile = open("api/main.py", "w+")
        codeFile.write(code) 
        codeFile.close()
        subprocess.call(["docker", "run", "--mount", "type=bind,source=/home/django/django_project/api,target=/usr/src/app", "--name", "code", "kaibailey/codeboardpython"])
        #docker run --mount type=bind,source=/Users/dinula/repos/CodeBoard/executeAPI/executeAPI/api,target=/usr/src/app --name codepy codeboardpython
    elif lang == "C++":
        codeFile = open("main.cpp", "w+")
        codeFile.write(code) 
        codeFile.close()
        subprocess.call(["docker", "run", "--mount", "type=bind,source=/home/django/django_project/api,target=/usr/src/app", "--name", "code", "kaibailey/codeboardcpp"])
    else:
        output = "Invalid language"

#    if os.path.isfile("api/log.txt"):
#        os.remove("api/log.txt")
    output = subprocess.run(["docker", "logs", "code"], stdout=subprocess.PIPE)
    output = output.stdout.decode('utf-8')
#    subprocess.call(["docker", "logs", "code", ">", "log.txt"])
    subprocess.call(["docker", "rm", "code"])

    os.remove("api/main.py")

    outputData = {"lang":lang, "output":output}
    return JsonResponse(outputData)

