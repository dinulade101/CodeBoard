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
import autopep8

from django.http import HttpResponse
# Create your views here.

def formatCode(lang, code):
    if lang == "Python":
        code = autopep8.fix_code(code)
    print("Formatted", code)
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
        os.remove("api/main.py")   
 #docker run --mount type=bind,source=/Users/dinula/repos/CodeBoard/executeAPI/executeAPI/api,target=/usr/src/app --name codepy codeboardpython
    elif lang == "C++":
        codeFile = open("api/main.cpp", "w+")
        codeFile.write(code) 
        codeFile.close()
        subprocess.call(["docker", "run", "--mount", "type=bind,source=/home/django/django_project/api,target=/usr/src/codeexec", "--name", "code", "kaibailey/codeboardcpp"])
        os.remove("api/main.cpp")
    else:
        output = "Invalid language"
        result = output


    output = subprocess.run(["docker", "logs", "code"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    result = output.stdout.decode('utf-8')
    result += output.stderr.decode('utf-8')
    subprocess.call(["docker", "rm", "code"])

    outputData = {"lang":lang, "output":result}
    return JsonResponse(outputData)

