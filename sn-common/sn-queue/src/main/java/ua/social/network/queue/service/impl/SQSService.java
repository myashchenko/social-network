package ua.social.network.queue.service.impl;

import java.util.stream.Stream;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.SneakyThrows;
import ua.social.network.queue.api.Message;
import ua.social.network.queue.properties.SQSConfigurationProperties;
import ua.social.network.queue.service.QueueService;

/**
 * @author Mykola Yashchenko
 */
public class SQSService implements QueueService {

    private static final Integer MAX_WAIT_TIME = 20;

    private static final Gson GSON = new Gson();
    private static final JsonParser JSON_PARSER = new JsonParser();

    private final AmazonSQSBufferedAsyncClient client;
    private final String queueUrl;

    public SQSService(final SQSConfigurationProperties configuration) {
        AmazonSQSAsyncClientBuilder builder = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(configuration.getAccessKey(), configuration.getSecretKey())));

        if (configuration.getEndpoint() != null && !configuration.getEndpoint().isEmpty()) {
            builder.setEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(configuration.getEndpoint(), configuration.getRegion())
            );
        } else {
            builder = builder.withRegion(configuration.getRegion());
        }

        final AmazonSQSAsync asyncClient = builder.build();

        client = new AmazonSQSBufferedAsyncClient(asyncClient);
        queueUrl = client.getQueueUrl(configuration.getQueueName()).getQueueUrl();
    }

    @Override
    public void send(final Message message) {
        client.sendMessage(queueUrl, GSON.toJson(message));
    }

    @Override
    @SneakyThrows
    public Stream<Message> receive() {
        final ReceiveMessageRequest request = new ReceiveMessageRequest(queueUrl)
                .withWaitTimeSeconds(MAX_WAIT_TIME);
        return client.receiveMessage(request).getMessages().stream()
                .map(message -> {
                    client.deleteMessageAsync(queueUrl, message.getReceiptHandle());
                    final JsonObject jsonObject = JSON_PARSER.parse(message.getBody()).getAsJsonObject();
                    final String className = jsonObject.get("className").getAsString();
                    final String payload = jsonObject.get("payload").getAsString();
                    return new Message(payload, className);
                });
    }
}
