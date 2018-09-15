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

@api_view(["GET","POST"])
def executeCode(codeData):
    # codeData = json.loads(codeData)

    # lang = codeData["language"]
    # code = codeData["code"]

    # if lang == "Python":
    #     output = "Hello This is python"

    # outputData = {"lang":lang, "output":output}

    # #return json.dumps(outputData)
    return HttpResponse("<h1>This is a test")
