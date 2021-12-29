<h2><b> Washing machine REST API </b></h2>

Where to test: http://localhost:8080/swagger-ui.html#/
<br>
<br>
Link to h2 database: http://localhost:8080/h2-console
<br>
<br>
How it looks in swagger:
<br>
![](./example.png)

<b> PUT /turn-on-off </b> - turn on or turn off the machine. The machine can't work if it's turned off.
<br>
<br>
<b> GET /start </b> - start chosen program.
<br>
<br>
<b> GET /programs </b> - show all possible programs.
<br>
<br>
<b> GET /choose-program </b> - set one of the possible programs (QUICK, DELICATE, COTTON, ONLYSPIN).
<br>
<br>
<b> GET /machine-info </b> - show information about the washing machine.
<br>
<br>
<b> PUT /pause </b> - puts machine on pause if it's working now or set WORKING status again if it's already on pause.
<br>
<br>
<b> POST /delay-start </b> - puts certain delayed start time.
