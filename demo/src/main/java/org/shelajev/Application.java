package org.shelajev;

import io.dekorate.docker.annotation.DockerBuild;
import io.dekorate.kubernetes.annotation.ImagePullPolicy;
import io.dekorate.kubernetes.annotation.KubernetesApplication;
import io.dekorate.kubernetes.annotation.Port;
import io.dekorate.kubernetes.annotation.Probe;
import io.micronaut.runtime.Micronaut;


@KubernetesApplication(
  name="demo", 
  ports = @Port(name = "http", containerPort = 8080),
  imagePullPolicy = ImagePullPolicy.Always,
  livenessProbe = @Probe(httpActionPath = "/health/liveness", initialDelaySeconds = 5),
  readinessProbe = @Probe(httpActionPath = "/health/readiness", initialDelaySeconds = 5)
)
@DockerBuild(group = "olegshelajev", name ="demo")
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
