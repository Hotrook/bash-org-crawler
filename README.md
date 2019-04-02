# Running Bash Crawler [![Build Status](https://travis-ci.com/Hotrook/bash-org-crawler.svg?branch=master)](https://travis-ci.com/Hotrook/bash-org-crawler) [![Maintainability](https://api.codeclimate.com/v1/badges/7020ffe05aa620e55e53/maintainability)](https://codeclimate.com/github/Hotrook/bash-org-crawler/maintainability) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/1eaad7c275fb43b1b5a2f970bda27d58)](https://www.codacy.com/app/Hotrook/bash-org-crawler?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Hotrook/bash-org-crawler&amp;utm_campaign=Badge_Grade) [![codecov](https://codecov.io/gh/Hotrook/bash-org-crawler/branch/master/graph/badge.svg)](https://codecov.io/gh/Hotrook/bash-org-crawler)

You can run test using following command:
```console
foo@bar:~$ sbt test
``` 

You can run the app from terminal using following command:
```console
foo@bar:~$ sbt "run 50"
``` 

Of course instead of `50` any number can be used/ 

To specify location of a result file you need to change `conf.result.file.path` parameter in `application.conf` file in resources.
By default result will be saved in file `result.json` located in root directory of the project. 
