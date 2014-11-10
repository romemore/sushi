
# Sushi
![sushi](app/src/main/res/drawable-mdpi/ic_launcher.png)

Sushi is a framework to keep data always fresh in your mobile application.
With a little bit of code in your mobile application and the upload of a generic cloud function in [Parse](http://www.parse.com), when every data you are interested in is saved on server, your mobile application can get it.

Goals:

- only interesting data : client filtering
- network friendly : no request if no interesting data on server
- device (battery) friendly : only active when interesting data available
- no server side development

## Setup

1. Create a [Parse](http://www.parse.com) application
2. Copy your appId and clientKey in the properties file `/app/src/main/res/raw/credentials`, like this : 

```
parse.appid=0123456789abcdef
parse.clientkey=0123456789abcdef
``` 


## Under the hood

#### mobile applications register their topics of interests
The application save object class names to Parse installation object

#### when data is saved on server, subscribed mobile apps are notified
When an object is saved, a special object is saved on Parse ("SushiUpdates") with the class name of the object.
The cloud function send a special push notification to all subscibed mobiles apps.
The app wake up when it receive the push notification, and download the new data.


## Future Improvements

Sushi is a work in progress, and must not be used as is in a production environnement.

The main fix now is to prevent multiple push to a same mobile app for same class of data if it didn’t already query for the data.

Improvements will be :

- __Sync point__ : the client must query new data since last sync
- __Continuous query__ : add parameters to subscription
- __iOS support__ : use push notification to set a flag, that we use to make or not a request in a "background fetch" mode

Sushi is an open idea, and it can support other triggers too. Some ideas :

- __Git__ : on push, use a hook to synchronize data (can be a good CMS candidate)
- __Elastic Search__ : "percolate" capabilities can be interesting to trigger   