# GeRDI Harvester Image for 'Sea Around Us'

FROM jetty:9.4.7-alpine

COPY \/target\/*.war $JETTY_BASE\/webapps\/seaaroundus.war

EXPOSE 8080