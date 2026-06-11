nc -vz database-1.c7me8euy81hb.eu-north-1.rds.amazonaws.com 3306


aws rds describe-db-instances \
--db-instance-identifier database-1

aws rds describe-db-instances \
--db-instance-identifier database-1 \
--query 'DBInstances[0].[DBInstanceClass,StorageType,PubliclyAccessible,DBInstanceStatus]' \
--output table

Sandy*994
aws rds describe-db-instances \
--db-instance-identifier database-1 \
--query 'DBInstances[0].VpcSecurityGroups[*].VpcSecurityGroupId' \
--output text

aws rds modify-db-instance \
--db-instance-identifier database-1 \
--publicly-accessible \
--apply-immediately


aws ec2 authorize-security-group-ingress --group-id sg-059be4b09c8fdd1ce --protocol tcp --port 3306 --cidr 90.234.126.8/32


aws rds describe-db-instances \
--db-instance-identifier database-1 \
--query 'DBInstances[0].[PubliclyAccessible,DBInstanceStatus]' \
--output table

mysql  -h database-1.c7me8euy81hb.eu-north-1.rds.amazonaws.com -P 3306 -u admin  -p

bin/zookeeper-server-start.sh config/zookeeper.properties

bin/kafka-server-start.sh config/server.properties


bin/kafka-topics.sh --create --topic trade-requests --bootstrap-server localhost:9092 --partitions 3 —replication-factor 1

bin/kafka-topics.sh --create --topic  trade-executed --bootstrap-server localhost:9092 --partitions 3 —replication-factor 1

bin/kafka-topics.sh --create --topic trade-failed --bootstrap-server localhost:9092 --partitions 3 —replication-factor 1


CREATE USER 'iam_user_1' IDENTIFIED WITH AWSAuthenticationPlugin AS 'RDS';

GRANT ALL PRIVILEGES ON finadvdb.* TO 'iam_user_1';

FLUSH PRIVILEGES;

SELECT user, plugin
FROM mysql.user

SELECT user, host, plugin
FROM mysql.user
WHERE user IN ('admin','iam_user_1','app_user');


CREATE USER 'app_user'@'%' IDENTIFIED WITH AWSAuthenticationPlugin AS 'RDS';
GRANT ALL PRIVILEGES ON finadvdb.* TO 'app_user'@'%';
FLUSH PRIVILEGES;