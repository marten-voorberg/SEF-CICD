# DD2480 Assignment 2


## Project Description
This is a small CI server dedicated for Github. The server supports the building and testing of Gradle projects.

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

## Build and Run Instructions
The project can be build and tested through Gradle by running `gradle build` and `gradle test` respectively.
One must have Gradle version 7.6 installed in directory `/opt/gradle/gradle-7.6/bin`
The server should run on a linux distro and was tested extensively on Fedora. 
To send API requests to the GitHub status API, you should add an api key to `/secrets/github_token`. 
The main method is contained in `ContiniousIntegrationServer`. This launches the server on port 8080.
You should either run this CI server on a machine with a fixed IP address or tunnel to this IP address using ngrok.
