# schedulerservice
Tools used: Eclipse with Maven

To build : clean install

To run: Run as -> Java Application -> SchedulerserviceApp

#API
GET - http://127.0.0.1:8081/api/events/

GET - http://127.0.0.1:8081/api/events/{EVENTID}

POST - http://127.0.0.1:8081/api/events/

Sample POST DATA:
```json
{
  "worker": "Great worker",
  "role": "Another nurse",
  "team": "Team",
  "location": "Hospital",
  "status": "Rescheduled",
  "outcome": "Not completed",
  "start": "2016-09-20",
  "end": "2016-09-21"
}
````
DELETE - http://127.0.0.1:8081/api/events/{EVENTID}

PUT - http://127.0.0.1:8081/api/events/

