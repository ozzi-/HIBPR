# HIBPR
Checks if email addresses defined in a json file can be found on haveibeenpwned.com and creates a HTML report mail.
HIBPR will save all found breaches per email address - showing you only what breaches are new every time you run it.

Example of the cli output:
```Loaded addresses: [info@adobe.com, good@examples.com, info@msn.com]
<b>pwnd!</b> info@adobe.com<br>Adobe - <a href="https://haveibeenpwned.com/PwnedWebsites#Adobe">Info about Breach</a><br>Evony - <a href="https://haveibeenpwned.com/PwnedWebsites#Evony">Info about Breach</a><br>OnlinerSpambot - <a href="https://haveibeenpwned.com/PwnedWebsites#OnlinerSpambot">Info about Breach</a><br>RiverCityMedia - <a href="https://haveibeenpwned.com/PwnedWebsites#RiverCityMedia">Info about Breach</a><br>VK - <a href="https://haveibeenpwned.com/PwnedWebsites#VK">Info about Breach</a><br>
<b>pwnd!</b> info@msn.com<br>Adobe - <a href="https://haveibeenpwned.com/PwnedWebsites#Adobe">Info about Breach</a><br>Dailymotion - <a href="https://haveibeenpwned.com/PwnedWebsites#Dailymotion">Info about Breach</a><br>Disqus - <a href="https://haveibeenpwned.com/PwnedWebsites#Disqus">Info about Breach</a><br>Dropbox - <a href="https://haveibeenpwned.com/PwnedWebsites#Dropbox">Info about Breach</a><br>Forbes - <a href="https://haveibeenpwned.com/PwnedWebsites#Forbes">Info about Breach</a><br>iMesh - <a href="https://haveibeenpwned.com/PwnedWebsites#iMesh">Info about Breach</a><br>Lastfm - <a href="https://haveibeenpwned.com/PwnedWebsites#Lastfm">Info about Breach</a><br>MySpace - <a href="https://haveibeenpwned.com/PwnedWebsites#MySpace">Info about Breach</a><br>Neopets - <a href="https://haveibeenpwned.com/PwnedWebsites#Neopets">Info about Breach</a><br>OnlinerSpambot - <a href="https://haveibeenpwned.com/PwnedWebsites#OnlinerSpambot">Info about Breach</a><br>RiverCityMedia - <a href="https://haveibeenpwned.com/PwnedWebsites#RiverCityMedia">Info about Breach</a><br>Tumblr - <a href="https://haveibeenpwned.com/PwnedWebsites#Tumblr">Info about Breach</a><br>
sending mail from X.Y@Z.ch to X.Y@Z.ch with subject Have I been pwnd Report 05-03-2018 via server smtp.gmail.com:25 and user XYZ@gmail.com
```

Example of the generated email:

![email](https://i.imgur.com/dWVlW2x.png)

# Getting started
Edit src/email.json with a valid SMTP configuration.

Edit src/emailaddresses.json with the addresses you wish to receive a report for.

Build a jar, copy the json files from within the jar to the same directory as the jar.

