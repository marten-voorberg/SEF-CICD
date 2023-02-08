# DD2480 Assignment 2
## Contributions
Karlis: Wrote the wrappers and their tests.

Marten: Wrote the HTTP request handling and the CI pipeline to build and test the project through Gradle. 

David: Wrote the HTTP client to send request to the GitHub status API, wrote the essence document, wrote documentation, and did some code reviewing.

Chenyi: Wrote the code to notify of CI results through email.

Nils: Wrote the code to store and display a history of CI results.

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
