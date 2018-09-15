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

from django.http import HttpResponse
# Create your views here.

@api_view(["POST"])
def executeCode(codeData):

    lang_to_file = {"python":"main.py","CPP":"main.cpp", "Java":"main.java"}
    codeData = json.loads(codeData)
    lang = codeData["language"]
    code = codeData["code"]

    os.chdir("../../../codeexecution")
    if lang == "Python":
        codeFile = open("main.py", "w") 
    elif lang == "C++":
        codeFile = open("main.cpp", "w")
    elif lang == "Java":
        codeFile = open("main.java", "w")
    else:
        output = "Invalid language"

    codeFile.write(codeFile) 
    codeFile.close()

    #subprocess.call(["docker", "run"])

    outputData = {"lang":lang, "output":output}
    return JsonResponse(outputData)
    #return HttpResponse("<h1>This is a test")
