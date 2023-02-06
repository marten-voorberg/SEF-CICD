# DD2480 Assignment 2

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