FROM node:18-bullseye-slim as build-stage

# Copy and unzip the vsix file
RUN apt-get update && apt-get install -y unzip && rm -rf /var/lib/apt/lists/*
COPY theiacloud-monitor-0.12.0-next.vsix /tmp/theiacloud-monitor.vsix
RUN mkdir /tmp/extracted && unzip /tmp/theiacloud-monitor.vsix -d /tmp/extracted

FROM theiacloud/theia-cloud-demo:0.12.0-next as production-stage

COPY --chown=theia:theia --from=build-stage /tmp/extracted /home/theia/plugins