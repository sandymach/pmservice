package com.fa.portfolio_service.aws.rds;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rds.RdsUtilities;

import javax.sql.DataSource;
@Configuration
public class RdsIamTokenGenerator {
    private static final String DB_HOST =
            "database-1.c7me8euy81hb.eu-north-1.rds.amazonaws.com";

    private static final String DB_NAME = "finadvdb";
    private static  final String DB_USER_NAME = "iam_user_1";
    private static final String DB_URL =
            "jdbc:mysql://" + DB_HOST + ":3306/" + DB_NAME+"?sslMode=REQUIRED";

    public static  String generateTocken(){
        System.out.println(DB_URL);
        var credentials = AwsBasicCredentials.create(
        "ACCESS_KET","SECRET");
        var rdsClient =
                software.amazon.awssdk.services.rds.RdsClient.builder()
                        .region(Region.EU_NORTH_1)
                        .credentialsProvider(
                                StaticCredentialsProvider.create(credentials))
                        .build();

        RdsUtilities utilities = rdsClient.utilities();

        return utilities.generateAuthenticationToken(builder ->
                builder.hostname(DB_HOST)
                        .port(3306)
                        .username(DB_USER_NAME));
    }


    @Bean
    public DataSource dataSource() {

        String token = generateTocken();
        System.out.println("Token length = " + token.length());
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(DB_URL);
        ds.setUsername(DB_USER_NAME);
        ds.setPassword(token);

        return ds;
    }
}

