# DD2480 Assignment 2
## Contributions
Karlis: Wrote the wrappers and their tests.

Marten: Wrote the HTTP request handling and the CI pipeline to build and test the project through Gradle. 

David: Wrote the HTTP client to send request to the GitHub status API, wrote the essence document, wrote documentation, and did some code reviewing.

Chenyi: Wrote the code to notify of CI results through email.

Nils: Wrote the code to store and display a history of CI results.


## Project Description
This is a small CI server dedicated for Github. The server supports the building and testing of Gradle projects.

## Keys and Configurations
In order to correctly interact with Github's commit status API, you should keep your github token as a single string in a text file located in secrets/github_token.

To work with the email notifications, store the email password in secrets/email_password.



## Git Conventions
- All commit messages take the following structure:
`<commit type> #<issue-number>: <description>`
  - The `commit-type` should be one of the following:
    - `Feat`: feature, new functionality has been added
    - `Fix`: bug fix, when existing functionality used to be broken but is now working
    - `Doc`: documentation, when you are not changing the code but only documenting it
    - `Ref`: refactor, when you are not changing what the code does, but only how it does it (for the better)
    - `Test`: testing, when you only add additional tests to existing functionality
    - `Misc`: miscellaneous, anything that does not fit into the categories above
  - The `issue-number` is mandatory and should link to an issue on Github
  - The `description` should briefly describe the changes made by the commit
- All changes to the main branch should be done through pull requests.
- All pull requests must be merged into the main branch using the merge & squash method.

## History
History of previous commits (recorded when the software was running) can be found at
[localhost:8080/commits/](http://localhost:8080/commits/) (localhost).

## Build and Run Instructions
The project can be build and tested through Gradle by running `gradle build` and `gradle test` respectively.
One must have Gradle version 7.6 installed in directory `/opt/gradle/gradle-7.6/bin`
The server should run on a linux distro and was tested extensively on Fedora. 
To send API requests to the GitHub status API, you should add an api key to `/secrets/github_token`. 
The main method is contained in `ContiniousIntegrationServer`. This launches the server on port 8080.
You should either run this CI server on a machine with a fixed IP address or tunnel to this IP address using ngrok.

