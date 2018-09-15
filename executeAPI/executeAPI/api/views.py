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
def executeCode(request):

    lang_to_file = {"python":"main.py","CPP":"main.cpp", "Java":"main.java"}
    lang = request.POST["language"]
    code = request.POST["code"]

    os.chdir("../../codeexecution")
    if lang == "Python":
        codeFile = open("main.py", "w")
        codeFile.write(code) 
        codeFile.close()
        subprocess.call(["docker", "run", "--rm", "codeboardpython" ]) 
    elif lang == "C++":
        codeFile = open("main.cpp", "w")
        codeFile.write(code) 
        codeFile.close()
    elif lang == "Java":
        codeFile = open("main.java", "w")
        codeFile.write(code) 
        codeFile.close()
    else:
        output = "Invalid language"


#docker run -it --rm --name my-running-app my-python-app
 
    outputData = {"lang":lang, "output":output}
    return JsonResponse(outputData)
