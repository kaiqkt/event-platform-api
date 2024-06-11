package com.kaiqkt.eventplatform.resources.sqs;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(AWSProperties.class)
@Configuration
public class AmazonSQSClientConfig {
    private final AWSProperties awsProperties;

    @Autowired
    public AmazonSQSClientConfig(AWSProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public AmazonSQSAsync amazonSQSAsync() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(awsProperties.endpoint(), awsProperties.region());
        BasicAWSCredentials credentials = new BasicAWSCredentials(awsProperties.accessKey(), awsProperties.secretKey());
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        return AmazonSQSAsyncClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }

    @Bean
    public SQSConnectionFactory sqsConnectionFactory(AmazonSQSAsync amazonSQSAsync) {
        return new SQSConnectionFactory(new ProviderConfiguration(), amazonSQSAsync);
    }
}
