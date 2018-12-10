# Running Bash Crawler

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
