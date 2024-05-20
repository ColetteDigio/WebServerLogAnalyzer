Future plan:
- Start the app planning as REST API application, where the user can interact with the endpoints with tools such as Postman/ Insomnia


* In real life application - let's say a company analyzing web server traffic. It probably worth investing tools like kafka and scheduler to do the work.
  - a. Quart scheduler - can set a x interval time to process the web server log, so the logic is not constantly running in the background.
  - b. Kafka - produce the data to a separate server that's specifically has a main responsibility to analyzer web server log, this will separate the layers and jobs to increase efficiency of application
  - c. Outbox pattern design could be used ti keep track of the batches that has been processed -  - proven that they have been analyzed and store in a database



* Other edge cases to be considered for future implementation
- There's a few others edge cases should be taken as part of consideration:
- special characters in URLS or Ips? (in current implementation, URL format is validated, IPs should be validated)
- missing fields in data structure?
- handle the case when application crashed mid-way while processing the data, the process can be restarted where it stopped last. 