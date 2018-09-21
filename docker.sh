./mvnw package
docker build -f DockerfileDesktop . -t "ramazanfirin/customerdesktop:latest"
docker build -f DockerfileDesktop . -t "ramazanfirin/customerraspberry:latest"
docker login --username ramazanfirin --password ra5699mo
docker push ramazanfirin/customerdesktop:latest
docker push ramazanfirin/customerraspberry:latest