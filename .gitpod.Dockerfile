FROM gitpod/workspace-full

USER root

# Install custom tools, runtime, etc. using apt-get

# RUN apt-get update \
#     && apt-get install -y graphviz \
#     && apt-get clean

# depends on 11.0.2-zulufx being installed in upsteam gitpod/workspace-full

RUN bash -c ". /home/gitpod/.sdkman/bin/sdkman-init.sh && sdk default java 11.0.2-zulufx"
