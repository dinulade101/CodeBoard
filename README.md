#Code Board

## Inspiration
When studying for school, learning algorithms and preparing for interviews, we would often write code on whiteboards to collaborate and come up with solutions. Hoping to quickly turn these ideas into real code, we would try to type it all up but this is a bit tedious and annoying. To save us valuable time we decided to automate this process.

## What it does
This program takes whiteboard code, handwritten code or printed out code and uses computer vision to convert it to text. Then, we send it to our Django server to run the code and return the output.

## How we built it
The image recognition done in this app is powered by Google Vision API. Google Vision had a great Android API that made this integration seamless. Our native Android app was built using Android studio. The back-end for this program is powered by Django. Our Django server is running on a DigitalOcean VM in the cloud.

## Challenges we ran into
Getting used to the Android SDK and Android Studio was a bit of a challenge. Building layouts using different sorts of views were proving to be quite difficult but we managed to create a clean, simple UI.

We initially tried to use Microsoft Azure to host our server. Unfortunately, this caused us a lot of issues since Django on Azure was a much older version. Next, we switched to DigitalOcean to host this server.

## Accomplishments that we're proud of
We're very proud of the fact that we were able to combine many amazing APIs to build this program. We're also very proud of the fact that we were able to build a good looking app while only having minimal experience in Android development.

## What we learned
We learned that having a great idea coupled with reasonable expectations and a willingness to learn will lead to a great product.

## What's next for CodeBoard
Using AR technology to provide syntax highlighting and errors would be very cool. Improving the accuracy of Google Vision API would also be very fun.