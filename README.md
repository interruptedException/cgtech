# cgtech
The application is developed using Spring Boot and Maven. 
In order to run the app locally, you need to clone the repository,
build it and run the main method from the com.cgtech.users.Application class.

You can make a POST request to "/accounts" to create or update an account.
It accepts a JSON of the form: {"name":"J Doe","email":"test@test"}

You can make a GET request to "/accounts/{id}" in order to get the resource
for a particular id.
