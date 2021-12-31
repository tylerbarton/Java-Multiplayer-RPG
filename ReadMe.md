# CS 242 Final Project

## Database

### Running the database
The database is essential to running the server as it saves the game state 


1. Run the following command to start the local server:
```bash
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqld.exe" --defaults-file="C:\ProgramData\MySQL\MySQL Server 8.0\my.ini" MySQL80
```

For issues with this step, see [here](https://stackoverflow.com/questions/41504580/cant-create-test-file-lower-test-start-server-mysql).

2. Open powershell and type
```bash
services.msc
```

then start the MySQL80 service if it is not already running.