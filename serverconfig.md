# Server Dokumentation

##  :bookmark_tabs: Übersicht
- [Wildfly 15](#wildfly-15)
- [TomEE](#tomee)
- [Payara](#payara)
- [Glassfish](#glassfish)
- [Liberty](#liberty)

## Wildfly 15

Die Vorgehensweise ist die selbe wie bei Wildfly 14 (siehe <a href="https://edufs.edu.htl-leonding.ac.at/~t.stuetz/download/nvs/presentations/02.install.wildfly14.pdf">Folien</a>).

## TomEE

### Konfiguration

1. TomEE <a href="http://tomee.apache.org/download-ng.html">downloaden</a> (Vorzugsweise im Ordner /opt/ speichern).
2. Im root Ordner von TomEE das file setenv.sh
3. In dem file setenv.sh JAVA_HOME setzen
`JAVA_HOME=/usr/lib/jvm/java-8-oracle`
4. startup.sh Script im Ordner bin ausführen
5. User im File /conf/tomcat-users.xml setzen
`<role rolename="manager-gui"/>`
`<user username="admin" password="passme" roles="tomee-admin, admin-gui, manager-gui"/>`
6. derbyclient.jar in den Ordner /lib kopieren
7. Datasource in /conf/tomee.xml setzen
```
    <Resource id="jdbc/dbDS" type="DataSource">
            #dbDS Datasource

            JdbcDriver = org.apache.derby.jdbc.ClientDriver
            JdbcUrl = jdbc:derby://localhost:1527/db;create=true
            Password = app
            UserName = app
        </Resource>
```

### Deployment

1. In der persistence.xml (Achtung kein Hibernate verwenden!):
```
    <jta-data-source>jdbc/dbDS</jta-data-source>
    <properties>
	    <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
    </properties>
```
2. In der Toolbar auf "Edit Configuration"
3. TomEE Server local auswählen
4. Pfad zum root Ordner von TomEE setzen
5. Fix Deployment
6. Projekt starten

## Payara

### Konfiguration

1. Payara <a href="https://www.payara.fish/software/downloads/all-downloads/">downloaden</a> (Vorzugsweise im Ordner /opt/ speichern).
2. Script /bin/asadmin ausführen
3. Im Browser <a href="localhost:4848">localhost:4848</a> aufrufen
4. JDBC Connection Pool erstellen
	- Neuen Pool erstellen (Resources => JDBC => JDBC Connection Pool)
	- Poolname setzen (derby-connector)
	- Resource Type: javax.sql.DataSource
	- Database Driver Vendor: Derby
	- Zusätzliche Eigenschaften setzen
		- password => app
		- user => app
		- serverName => localhost
		- portNumber => 1527
		- databaseName => db
5. JDBC Resource erstellen
	- Neue Resource erstellen (Resources => JDBC => JDBC Resources)
	- JNDI Name setzen (DbDS)
	- Pool Name setzen (zuvor erstellter Pool)

### Deployment

1. persistence.xml anpassen: <jta-data-source> auf DbDS setzen
2. Edit Configurations -> + -> GlassFish Server
3. Payara5 Pfad auswählen
4. Server Domain setzten (domain1)
5. Fix Deployment
6. Projekt starten

## Glassfish

### Konfiguration

1. Glassfish <a href="https://javaee.github.io/glassfish/download">downloaden</a> (Vorzugsweise im Ordner /opt/ speichern).
2. Script /bin/asadmin ausführen
3. Start domain eingeben
4. Im Browser <a href="localhost:4848">localhost:4848</a> aufrufen
5. DerbyPool bearbeiten
    - Resources => JDBC => JDBC Connection Pool => DerbyPool
    - Zusätzliche Eigenschaften verändern:
        - password => app
        - user => app
        - serverName => localhost
        - portNumber => 1527
        - databaseName => db
6. Neue JDBC Resource erstellen
	- Neue Resource erstellen (Resources => JDBC => JDBC Resources)
	- JNDI Name setzen (DbDS)
	- Pool Name setzen (DerbyPool)

### Deployment

1. persistence.xml anpassen: <jta-data-source> auf DbDS setzen
2. Edit Configurations -> + -> GlassFish Server
3. Glassfish Pfad auswählen
4. Server Domain setzten (domain1)
5. Fix Deployment
6. Projekt starten

## Liberty

### Konfiguration

1. Liberty <a href="https://openliberty.io/downloads/">downloaden</a> (Vorzugsweise im Ordner /opt/ speichern).
2. Im Ordner /bin/server create ausführen (Erstellt Server)
3. usr/servers/defaultServer/server.env JAVA_HOME setzen
    ```
    JAVA_HOME=/usr/lib/jvm/java-8-oracle
    ```
4. /bin/installUtility install adminCenter-1.0
5. usr/servers/defaulServer/server.xml ändern (Passwort ist "passme"):
    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <server description="new server">
    <featureManager>
        <feature>adminCenter-1.0</feature>
        <feature>javaee-8.0</feature>
        <feature>localConnector-1.0</feature>
    </featureManager>
    <applicationMonitor updateTrigger="mbean" />
    <remoteFileAccess>
        <writeDir>${server.config.dir}</writeDir>
    </remoteFileAccess>
    <basicRegistry id="basic" realm="BasicRealm">
        <user name="admin" password="{xor}Lz4sLDI6" />
    </basicRegistry>
    <administrator-role>
        <user>admin</user>
    </administrator-role>
    <httpEndpoint id="defaultHttpEndpoint" httpPort="9080" httpsPort="9443" />
    <applicationManager autoExpand="true" />
    </server>
    ```
6. Im Browser <a href="https://localhost:9443/adminCenter/">https://localhost:9443/adminCenter/</a> aufrufen
7. Server Config
8. Add Child -> JDBC Driver => id: derby-connector
    - Add Child => Shared Library => Name setzen (DerbyLib)
    - Add Child => Fileset
        - Id: DerbyFileset
        - Base directory: ${shared.resource.dir}/DerbyLibs
        - Includes pattern: derbyclient.jar
9. Add Child -> Data Source
    - Id: DbDS
    - JNDI name: jdbc/DbDS
    - JDBC driver reference: derby-connector
    - Add Child -> Derby Netowrk Client Properties
        - Create database: create
        - Database name: db
        - Server name: localhost
        - Port number: 1527
        - Password: app
        - User: app

### Deployment

- persistence.xml:
    ```
    <jta-data-source>jdbc/dbDS</jta-data-source>
    <properties>
	    <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
    </properties>
    ```
- Edit Configuration -> WebSphere Server
- Pfad auswählen
- Fix Deployment

