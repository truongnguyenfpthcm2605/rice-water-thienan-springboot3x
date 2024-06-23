# Step 1 : Setup JDK 17 and Mysql 8.0.3 in your Computer
# Step 2 : install Ubuntu 
# Step 3 : 
1. install : redis => Start redis 
2. install elasticsearch :
   - wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add –
     -	sudo sh -c 'echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" > /etc/apt/sources.list.d/elastic-7.x.list'
     -	sudo apt update
     -	sudo apt install elasticsearch
     -	sudo nano /etc/elasticsearch/elasticsearch.yml :
     - cluster.name: my-cluster
     node.name: node-1
     -	sudo systemctl enable elasticsearch
     -	sudo systemctl start elasticsearch
     -	curl -X GET "localhost:9200/"
# Step 4 : Start servers 
# Step 5 : Install Maven 
# Document API = > http://localhost:8080/swagger-ui/index.html

    