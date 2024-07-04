[//]: # (Outdated as of 25th Jun)
[//]: # (# Future plan:)

[//]: # (- If I were given this exercise a start over again:)

[//]: # (- start the app planning as REST API application, where the user can interact with the endpoints with tools such as Postman/ Insomnia)

[//]: # (- Using Spring framework to do all the magic, such as dependency injection, configuration setup, and more)

[//]: # ()
[//]: # (## Improve data accuracy and performance)

[//]: # (- In real life application - let's say a company analyzing web server traffic, depending on the budget of the company, there are tools such as kafka and scheduler to do the work.)

[//]: # (  - a. Quart scheduler - set x interval time schedule to process the web server log, i.e.: Daily or hourly depending on the need, so the logic is not constantly running in the background.)

[//]: # (  - b. Kafka - produce the data to a separate server that's specifically has a main responsibility to analyzer web server log, this will separate the layers and jobs to increase efficiency of application)

[//]: # (  - c. Outbox pattern design could be used to keep track of the batches that has been processed -  - proven that they have been analyzed and store in a database)

[//]: # ()
[//]: # (## Other edge cases to be considered for future implementation)

[//]: # (- handle the case when application crashed mid-way while processing the data, the process can be restarted where it stopped last.)

[//]: # (- *retry* feature can be introduced for when the app crashed during the run time, it will be useful to have other tools such as outbox pattern to keep track of the process.)

[//]: # ()
[//]: # (  )
[//]: # (## Performance testing)

[//]: # (- It would be good to use framework like Spring so the performance testing class can be injected as a bean, and have it turned on for specific environment required.)

[//]: # (- Other areas to be considered for performance testing:)

[//]: # (- Faster execution time)

[//]: # (- Thread distribution)
