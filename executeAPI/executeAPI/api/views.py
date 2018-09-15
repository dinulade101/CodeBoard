from django.shortcuts import render
from django.http import Http404
from rest_framework.views import APIView
from rest_framework.decorators import api_view
from rest_framework.response import Response
from django.http import JsonResponse
from django.core import serializers
from django.conf import settings
import json

from django.http import HttpResponse
# Create your views here.

@api_view(["POST"])
def executeCode(codeData):

    return codeData
    lang_to_file = {"python":"main.py","CPP":"main.cpp", "Java":"main.java"}
    codeData = json.loads(codeData)
    lang = codeData["language"]
    code = codeData["code"]

    if lang == "Python":
        output = "Hello This is python"
    elif lang == "C++":
        output = "This is C++"
    elif lang == "Java":
        output = "This is Java"
    else:
        output = "Invalid language"



    outputData = {"lang":lang, "output":output}
    return json.dumps(outputData)
    #return HttpResponse("<h1>This is a test")
